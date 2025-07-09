import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
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

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<AuthResponse | null>(
      this.getUserFromStorage()
    );
    this.currentUser = this.currentUserSubject.asObservable();
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
    const token = this.getToken();
    if (!token) {
      return new Observable(observer => observer.next(false));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    
    return this.http.get<ApiResponse<string>>(`${this.apiUrl}/validar`, { headers })
      .pipe(
        map(response => response.success)
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
    return !!token;
  }

  /**
   * Obtener token del localStorage
   */
  getToken(): string | null {
    return localStorage.getItem('token');
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
    localStorage.setItem('currentUser', JSON.stringify(user));
    localStorage.setItem('token', user.token);
  }

  /**
   * Obtener usuario del localStorage
   */
  private getUserFromStorage(): AuthResponse | null {
    const userStr = localStorage.getItem('currentUser');
    return userStr ? JSON.parse(userStr) : null;
  }
} 