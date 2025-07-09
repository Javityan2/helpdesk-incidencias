import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { map } from 'rxjs/operators';

export interface Notificacion {
  id: number;
  titulo: string;
  mensaje: string;
  tipo: 'INFO' | 'WARNING' | 'ERROR' | 'SUCCESS';
  leida: boolean;
  fechaCreacion: string;
  fechaLectura?: string;
  incidenciaId?: number;
  usuarioId: number;
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
export class NotificationService {
  private apiUrl = `${environment.apiUrl}/notificaciones`;
  private notificacionesSubject = new BehaviorSubject<Notificacion[]>([]);
  public notificaciones$ = this.notificacionesSubject.asObservable();

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  /**
   * Obtener todas las notificaciones del usuario
   */
  getNotificaciones(): Observable<Notificacion[]> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<Notificacion[]>>(`${this.apiUrl}`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            this.notificacionesSubject.next(response.data);
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener notificaciones');
          }
        })
      );
  }

  /**
   * Obtener notificaciones no leídas
   */
  getNotificacionesNoLeidas(): Observable<Notificacion[]> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<Notificacion[]>>(`${this.apiUrl}/no-leidas`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener notificaciones no leídas');
          }
        })
      );
  }

  /**
   * Marcar notificación como leída
   */
  marcarComoLeida(notificacionId: number): Observable<void> {
    const headers = this.getAuthHeaders();
    
    return this.http.patch<ApiResponse<string>>(`${this.apiUrl}/${notificacionId}/leer`, {}, { headers })
      .pipe(
        map(response => {
          if (!response.success) {
            throw new Error(response.error || 'Error al marcar notificación como leída');
          }
        })
      );
  }

  /**
   * Marcar todas las notificaciones como leídas
   */
  marcarTodasComoLeidas(): Observable<void> {
    const headers = this.getAuthHeaders();
    
    return this.http.patch<ApiResponse<string>>(`${this.apiUrl}/marcar-todas-leidas`, {}, { headers })
      .pipe(
        map(response => {
          if (!response.success) {
            throw new Error(response.error || 'Error al marcar notificaciones como leídas');
          }
        })
      );
  }

  /**
   * Eliminar notificación
   */
  eliminarNotificacion(notificacionId: number): Observable<void> {
    const headers = this.getAuthHeaders();
    
    return this.http.delete<ApiResponse<string>>(`${this.apiUrl}/${notificacionId}`, { headers })
      .pipe(
        map(response => {
          if (!response.success) {
            throw new Error(response.error || 'Error al eliminar notificación');
          }
        })
      );
  }

  /**
   * Obtener contador de notificaciones no leídas
   */
  getContadorNoLeidas(): Observable<number> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<number>>(`${this.apiUrl}/contador-no-leidas`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data !== undefined) {
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener contador de notificaciones');
          }
        })
      );
  }

  /**
   * Crear notificación (para uso interno del sistema)
   */
  crearNotificacion(notificacion: Partial<Notificacion>): Observable<Notificacion> {
    const headers = this.getAuthHeaders();
    
    return this.http.post<ApiResponse<Notificacion>>(`${this.apiUrl}`, notificacion, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            return response.data;
          } else {
            throw new Error(response.error || 'Error al crear notificación');
          }
        })
      );
  }

  /**
   * Obtener headers de autenticación
   */
  private getAuthHeaders(): HttpHeaders {
    return this.authService.getAuthHeaders();
  }
} 