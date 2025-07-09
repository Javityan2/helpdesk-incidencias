import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

export interface Incidencia {
  id: number;
  titulo: string;
  descripcion: string;
  prioridad: string;
  estado: string;
  categoria: string;
  fechaCreacion: string;
  frecuenciaBusqueda: number;
  usuario?: {
    id: number;
    empleadoId: string;
    nombre: string;
    apellido: string;
    email: string;
    departamento: string;
  };
  asignado?: {
    id: number;
    empleadoId: string;
    nombre: string;
    apellido: string;
    email: string;
    rol: string;
  };
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
export class IncidenciaService {
  private apiUrl = `${environment.apiUrl}/incidencias`;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  getIncidencias(): Observable<Incidencia[]> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<Incidencia[]>>(this.apiUrl, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener incidencias');
          }
        })
      );
  }

  getIncidencia(id: number): Observable<Incidencia> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<Incidencia>>(`${this.apiUrl}/${id}`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener incidencia');
          }
        })
      );
  }

  incrementarFrecuenciaBusqueda(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    
    return this.http.post<ApiResponse<string>>(`${this.apiUrl}/${id}/incrementar-busqueda`, {}, { headers })
      .pipe(
        map(response => {
          if (!response.success) {
            throw new Error(response.error || 'Error al incrementar frecuencia');
          }
        })
      );
  }

  /**
   * Cambiar el estado de una incidencia
   */
  cambiarEstado(id: number, nuevoEstado: string) {
    return this.http.patch<ApiResponse<Incidencia>>(
      `${this.apiUrl}/${id}/estado?nuevoEstado=${nuevoEstado}`,
      {}
    );
  }

  /**
   * Asignar técnico a una incidencia
   */
  asignarTecnico(id: number, tecnicoId: number) {
    return this.http.patch<ApiResponse<Incidencia>>(
      `${this.apiUrl}/${id}/asignar?tecnicoId=${tecnicoId}`,
      {}
    );
  }

  private getAuthHeaders(): HttpHeaders {
    return this.authService.getAuthHeaders();
  }
} 