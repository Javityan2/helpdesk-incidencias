import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

export interface User {
  id: number;
  nombre: string;
  email: string;
  rol: string;
  activo: boolean;
}

export interface LoginRequest {
  identificador: string; // Cambiado de email a identificador
  password: string;
}

export interface LoginResponse {
  success: boolean;
  message: string;
  data: {
    token: string;
    id: number;
    empleadoId: string;
    nombre: string;
    apellido: string;
    email: string;
    rol: string;
    departamento: string;
    cargo: string;
  };
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api';
  private currentUserSubject: BehaviorSubject<User | null>;
  private isAuthenticatedSubject: BehaviorSubject<boolean>;
  
  public currentUser$: Observable<User | null>;
  public isAuthenticated$: Observable<boolean>;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.currentUserSubject = new BehaviorSubject<User | null>(this.getUserFromStorage());
    this.isAuthenticatedSubject = new BehaviorSubject<boolean>(this.isTokenValid());
    
    this.currentUser$ = this.currentUserSubject.asObservable();
    this.isAuthenticated$ = this.isAuthenticatedSubject.asObservable();
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`, credentials)
      .pipe(
        map(response => {
          if (response.success && response.data) {
            // Guardar token
            localStorage.setItem('token', response.data.token);
            
            // Crear objeto usuario
            const user: User = {
              id: response.data.id,
              nombre: `${response.data.nombre} ${response.data.apellido}`,
              email: response.data.email,
              rol: response.data.rol,
              activo: true
            };
            
            // Guardar usuario
            localStorage.setItem('user', JSON.stringify(user));
            
            // Actualizar observables
            this.currentUserSubject.next(user);
            this.isAuthenticatedSubject.next(true);
          }
          
          return response;
        })
      );
  }

  logout(): void {
    // Limpiar localStorage
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    
    // Actualizar observables
    this.currentUserSubject.next(null);
    this.isAuthenticatedSubject.next(false);
    
    // Redirigir al login
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isTokenValid(): boolean {
    const token = this.getToken();
    if (!token) return false;
    
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const currentTime = Date.now() / 1000;
      return payload.exp > currentTime;
    } catch (error) {
      return false;
    }
  }

  getUserFromStorage(): User | null {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      try {
        return JSON.parse(userStr);
      } catch (error) {
        return null;
      }
    }
    return null;
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }

  hasRole(role: string): boolean {
    const user = this.getCurrentUser();
    return user ? user.rol === role : false;
  }

  isAdmin(): boolean {
    return this.hasRole('ADMIN');
  }

  isTechnician(): boolean {
    return this.hasRole('TECHNICIAN');
  }

  isUser(): boolean {
    return this.hasRole('USER');
  }

  // Método para registrar un nuevo usuario
  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/registro`, userData);
  }

  // Método para cambiar contraseña
  changePassword(passwordData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/change-password`, passwordData);
  }

  // Método para refrescar el token
  refreshToken(): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/refresh`, {});
  }
} 