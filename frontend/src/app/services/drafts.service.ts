import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { IncidenciaService, Incidencia } from './incidencia.service';

export interface Borrador {
  id: number;
  usuarioId: number;
  titulo: string;
  descripcion: string;
  prioridad: string;
  categoria: string;
  estado: 'BORRADOR';
  fechaCreacion: string;
  fechaActualizacion: string;
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
export class DraftsService {
  private apiUrl = `${environment.apiUrl}/borradores`;
  private borradoresSubject = new BehaviorSubject<Borrador[]>([]);
  public borradores$ = this.borradoresSubject.asObservable();

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  /**
   * Obtener todos los borradores del usuario
   */
  getBorradores(): Observable<Borrador[]> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<Borrador[]>>(`${this.apiUrl}`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            this.borradoresSubject.next(response.data);
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener borradores');
          }
        })
      );
  }

  /**
   * Obtener un borrador específico
   */
  getBorrador(id: number): Observable<Borrador> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<Borrador>>(`${this.apiUrl}/${id}`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener borrador');
          }
        })
      );
  }

  /**
   * Crear nuevo borrador
   */
  crearBorrador(borrador: Partial<Borrador>): Observable<Borrador> {
    const headers = this.getAuthHeaders();
    
    return this.http.post<ApiResponse<Borrador>>(`${this.apiUrl}`, borrador, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            // Actualizar la lista de borradores
            const borradoresActuales = this.borradoresSubject.value;
            this.borradoresSubject.next([...borradoresActuales, response.data]);
            return response.data;
          } else {
            throw new Error(response.error || 'Error al crear borrador');
          }
        })
      );
  }

  /**
   * Actualizar borrador
   */
  actualizarBorrador(id: number, borrador: Partial<Borrador>): Observable<Borrador> {
    const headers = this.getAuthHeaders();
    
    return this.http.put<ApiResponse<Borrador>>(`${this.apiUrl}/${id}`, borrador, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            // Actualizar la lista de borradores
            const borradoresActuales = this.borradoresSubject.value;
            const borradoresActualizados = borradoresActuales.map(b => 
              b.id === id ? response.data! : b
            );
            this.borradoresSubject.next(borradoresActualizados);
            return response.data;
          } else {
            throw new Error(response.error || 'Error al actualizar borrador');
          }
        })
      );
  }

  /**
   * Eliminar borrador
   */
  eliminarBorrador(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    
    return this.http.delete<ApiResponse<string>>(`${this.apiUrl}/${id}`, { headers })
      .pipe(
        map(response => {
          if (response.success) {
            // Actualizar la lista de borradores
            const borradoresActuales = this.borradoresSubject.value;
            const borradoresFiltrados = borradoresActuales.filter(b => b.id !== id);
            this.borradoresSubject.next(borradoresFiltrados);
          } else {
            throw new Error(response.error || 'Error al eliminar borrador');
          }
        })
      );
  }

  /**
   * Publicar borrador como incidencia
   */
  publicarBorrador(id: number): Observable<Incidencia> {
    const headers = this.getAuthHeaders();
    
    return this.http.post<ApiResponse<Incidencia>>(`${this.apiUrl}/${id}/publicar`, {}, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            // Eliminar el borrador de la lista
            const borradoresActuales = this.borradoresSubject.value;
            const borradoresFiltrados = borradoresActuales.filter(b => b.id !== id);
            this.borradoresSubject.next(borradoresFiltrados);
            return response.data;
          } else {
            throw new Error(response.error || 'Error al publicar borrador');
          }
        })
      );
  }

  /**
   * Obtener contador de borradores
   */
  getContadorBorradores(): Observable<number> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<number>>(`${this.apiUrl}/contador`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data !== undefined) {
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener contador de borradores');
          }
        })
      );
  }

  /**
   * Guardar borrador automáticamente
   */
  guardarBorradorAutomatico(borrador: Partial<Borrador>): Observable<Borrador> {
    const headers = this.getAuthHeaders();
    
    return this.http.post<ApiResponse<Borrador>>(`${this.apiUrl}/auto-guardar`, borrador, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            // Actualizar la lista de borradores
            const borradoresActuales = this.borradoresSubject.value;
            const borradorExistente = borradoresActuales.find(b => b.id === response.data!.id);
            
            if (borradorExistente) {
              // Actualizar borrador existente
              const borradoresActualizados = borradoresActuales.map(b => 
                b.id === response.data!.id ? response.data! : b
              );
              this.borradoresSubject.next(borradoresActualizados);
            } else {
              // Agregar nuevo borrador
              this.borradoresSubject.next([...borradoresActuales, response.data]);
            }
            
            return response.data;
          } else {
            throw new Error(response.error || 'Error al guardar borrador automáticamente');
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