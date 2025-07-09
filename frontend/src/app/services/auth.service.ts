import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map, tap, catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';

export interface AuthRequest {
  identificador: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  userId: number;
  empleadoId: string;
  nombre: string;
  apellido: string;
  email: string;
  rol: string;
  departamento: string;
  cargo: string;
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data?: T;
  error?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  private currentUserSubject: BehaviorSubject<AuthResponse | null>;
  public currentUser: Observable<AuthResponse | null>;
  public currentUser$: Observable<AuthResponse | null>;
  public isAuthenticated$: Observable<boolean>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<AuthResponse | null>(
      this.getUserFromStorage()
    );
    this.currentUser = this.currentUserSubject.asObservable();
    this.currentUser$ = this.currentUserSubject.asObservable();
    this.isAuthenticated$ = this.currentUserSubject.pipe(
      map(user => !!user && !!user.token)
    );
  }

  /**
   * Login con ID de empleado o email
   */
  login(identificador: string, password: string): Observable<AuthResponse> {
    const request: AuthRequest = { identificador, password };
    
    return this.http.post<ApiResponse<AuthResponse>>(`${this.apiUrl}/login`, request)
      .pipe(
        map(response => {
          if (response.success && response.data) {
            console.log('AuthService - Login exitoso, guardando token:', response.data.token ? 'SÍ' : 'NO');
            this.setUserInStorage(response.data);
            this.currentUserSubject.next(response.data);
            return response.data;
          } else {
            throw new Error(response.error || 'Error en el login');
          }
        })
      );
  }

  /**
   * Registro de usuario (solo admin)
   */
  register(userData: any): Observable<AuthResponse> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.apiUrl}/registro`, userData)
      .pipe(
        map(response => {
          if (response.success && response.data) {
            this.setUserInStorage(response.data);
            this.currentUserSubject.next(response.data);
            return response.data;
          } else {
            throw new Error(response.error || 'Error en el registro');
          }
        })
      );
  }

  /**
   * Validar token actual
   */
  validateToken(): Observable<boolean> {
    console.log('AuthService - validateToken iniciado');
    
    const token = this.getToken();
    if (!token) {
      console.log('AuthService - validateToken - No hay token disponible');
      return new Observable<boolean>(observer => observer.next(false));
    }

    console.log('AuthService - validateToken - Token encontrado, validando con backend');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    
    return this.http.get<ApiResponse<string>>(`${this.apiUrl}/validar`, { headers })
      .pipe(
        tap(response => {
          console.log('AuthService - validateToken - Respuesta del backend:', response);
        }),
        map(response => {
          const isValid = response.success;
          console.log('AuthService - validateToken - Token válido:', isValid);
          return isValid;
        }),
        catchError(error => {
          console.error('AuthService - validateToken - Error:', error);
          return new Observable<boolean>(observer => observer.next(false));
        })
      );
  }

  /**
   * Obtener perfil del usuario actual
   */
  getProfile(): Observable<AuthResponse> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<AuthResponse>>(`${this.apiUrl}/perfil`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            this.setUserInStorage(response.data);
            this.currentUserSubject.next(response.data);
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener perfil');
          }
        })
      );
  }

  /**
   * Logout
   */
  logout(): void {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
    this.currentUserSubject.next(null);
  }

  /**
   * Verificar si el usuario está autenticado
   */
  isAuthenticated(): boolean {
    const token = this.getToken();
    const currentUser = this.currentUserSubject.value;
    console.log('AuthService - isAuthenticated - Token:', token ? `SÍ (${token.length} chars)` : 'NO');
    console.log('AuthService - isAuthenticated - CurrentUser:', currentUser ? 'SÍ' : 'NO');
    console.log('AuthService - isAuthenticated - CurrentUser token:', currentUser?.token ? `SÍ (${currentUser.token.length} chars)` : 'NO');
    return !!token;
  }

  /**
   * Obtener token del localStorage
   */
  getToken(): string | null {
    const token = localStorage.getItem('token');
    console.log('AuthService - getToken:', token ? `Token encontrado (${token.length} chars)` : 'No hay token');
    return token;
  }

  /**
   * Verificar token directamente desde localStorage (para debug)
   */
  debugTokenStorage(): void {
    const token = localStorage.getItem('token');
    const currentUser = localStorage.getItem('currentUser');
    console.log('AuthService - debugTokenStorage - Token directo:', token ? `SÍ (${token.length} chars)` : 'NO');
    console.log('AuthService - debugTokenStorage - CurrentUser directo:', currentUser ? 'SÍ' : 'NO');
    if (token) {
      console.log('AuthService - debugTokenStorage - Token preview:', token.substring(0, 50) + '...');
    }
  }

  /**
   * Obtener usuario actual
   */
  getCurrentUser(): AuthResponse | null {
    return this.currentUserSubject.value;
  }

  /**
   * Obtener headers de autenticación
   */
  getAuthHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  /**
   * Probar token (método de debug)
   */
  testToken(): Observable<any> {
    const headers = this.getAuthHeaders();
    console.log('AuthService - testToken - Headers:', headers.get('Authorization'));
    
    return this.http.get<ApiResponse<string>>(`${this.apiUrl}/test-token`, { headers })
      .pipe(
        map(response => {
          console.log('AuthService - testToken - Respuesta:', response);
          return response;
        })
      );
  }

  /**
   * Debug token (método de debug detallado)
   */
  debugToken(): Observable<any> {
    const headers = this.getAuthHeaders();
    console.log('AuthService - debugToken - Headers:', headers.get('Authorization'));
    
    return this.http.get<ApiResponse<string>>(`${this.apiUrl}/debug-token`, { headers })
      .pipe(
        map(response => {
          console.log('AuthService - debugToken - Respuesta:', response);
          return response;
        })
      );
  }

  /**
   * Probar autenticación
   */
  testAuth(): Observable<any> {
    const headers = this.getAuthHeaders();
    console.log('AuthService - testAuth - Headers:', headers.get('Authorization'));
    
    return this.http.get<ApiResponse<string>>(`${this.apiUrl}/test-auth`, { headers })
      .pipe(
        map(response => {
          console.log('AuthService - testAuth - Respuesta:', response);
          return response;
        }),
        catchError(error => {
          console.error('AuthService - testAuth - Error:', error);
          return new Observable<any>(observer => observer.next({ error: error.message }));
        })
      );
  }

  /**
   * Verificar si el usuario tiene un rol específico
   */
  hasRole(role: string): boolean {
    const user = this.getCurrentUser();
    return user?.rol === role;
  }

  /**
   * Verificar si el usuario es admin
   */
  isAdmin(): boolean {
    return this.hasRole('ADMIN');
  }

  /**
   * Verificar si el usuario es técnico
   */
  isTecnico(): boolean {
    return this.hasRole('TECNICO') || this.hasRole('SUPERVISOR') || this.hasRole('ADMIN');
  }

  /**
   * Guardar usuario en localStorage
   */
  private setUserInStorage(user: AuthResponse): void {
    console.log('AuthService - setUserInStorage - Token:', user.token ? `SÍ (${user.token.length} chars)` : 'NO');
    console.log('AuthService - Token completo:', user.token);
    
    // Guardar el usuario completo
    localStorage.setItem('currentUser', JSON.stringify(user));
    
    // Guardar el token por separado
    if (user.token) {
      localStorage.setItem('token', user.token);
      console.log('AuthService - Token guardado en localStorage');
      
      // Verificar que se guardó correctamente
      const savedToken = localStorage.getItem('token');
      console.log('AuthService - Token verificado después de guardar:', savedToken ? `SÍ (${savedToken.length} chars)` : 'NO');
    } else {
      console.error('AuthService - ERROR: No hay token para guardar');
    }
  }

  /**
   * Obtener usuario del localStorage
   */
  private getUserFromStorage(): AuthResponse | null {
    const userStr = localStorage.getItem('currentUser');
    return userStr ? JSON.parse(userStr) : null;
  }

  /**
   * Verificar estado completo del token (para debug)
   */
  checkTokenState(): void {
    const token = localStorage.getItem('token');
    const currentUser = localStorage.getItem('currentUser');
    const currentUserObj = this.currentUserSubject.value;
    
    console.log('=== ESTADO COMPLETO DEL TOKEN ===');
    console.log('Token en localStorage:', token ? `SÍ (${token.length} chars)` : 'NO');
    console.log('CurrentUser en localStorage:', currentUser ? 'SÍ' : 'NO');
    console.log('CurrentUser en BehaviorSubject:', currentUserObj ? 'SÍ' : 'NO');
    console.log('CurrentUser token en BehaviorSubject:', currentUserObj?.token ? `SÍ (${currentUserObj.token.length} chars)` : 'NO');
    console.log('isAuthenticated():', this.isAuthenticated());
    console.log('================================');
  }
} 