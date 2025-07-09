import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

export interface Comentario {
  id?: number;
  contenido: string;
  tipo: TipoComentario;
  fechaCreacion?: Date;
  usuarioId?: number;
  usuarioEmpleadoId?: string;
  usuarioNombre?: string;
  usuarioApellido?: string;
  usuarioEmail?: string;
  usuarioDepartamento?: string;
  incidenciaId: number;
  incidenciaTitulo?: string;
}

export enum TipoComentario {
  GENERAL = 'GENERAL',
  TECNICO = 'TECNICO',
  INTERNO = 'INTERNO',
  SOLUCION = 'SOLUCION'
}

export interface ComentarioCreateRequest {
  contenido: string;
  tipo: TipoComentario;
  incidenciaId: number;
}

export interface ComentarioUpdateRequest {
  contenido?: string;
  tipo?: TipoComentario;
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
export class ComentarioService {

  private apiUrl = `${environment.apiUrl}/comentarios`;

  constructor(private http: HttpClient) { }

  /**
   * Obtener comentarios de una incidencia
   */
  getComentariosPorIncidencia(incidenciaId: number, tipo?: TipoComentario): Observable<Comentario[]> {
    let url = `${this.apiUrl}/incidencia/${incidenciaId}`;
    if (tipo) {
      url += `?tipo=${tipo}`;
    }
    return this.http.get<ApiResponse<Comentario[]>>(url)
      .pipe(
        // @ts-ignore
        map((response: ApiResponse<Comentario[]>) => response.data || [])
      );
  }

  /**
   * Obtener comentarios paginados
   */
  getComentariosPaginados(incidenciaId: number, tipo?: TipoComentario, page: number = 0, size: number = 10): Observable<any> {
    let url = `${this.apiUrl}/incidencia/${incidenciaId}/paginated?page=${page}&size=${size}`;
    if (tipo) {
      url += `&tipo=${tipo}`;
    }
    return this.http.get<ApiResponse<any>>(url)
      .pipe(
        // @ts-ignore
        map((response: ApiResponse<any>) => response.data)
      );
  }

  /**
   * Obtener comentario por ID
   */
  getComentario(id: number): Observable<Comentario> {
    return this.http.get<ApiResponse<Comentario>>(`${this.apiUrl}/${id}`)
      .pipe(
        // @ts-ignore
        map((response: ApiResponse<Comentario>) => response.data!)
      );
  }

  /**
   * Crear nuevo comentario
   */
  crearComentario(request: ComentarioCreateRequest): Observable<Comentario> {
    return this.http.post<ApiResponse<Comentario>>(this.apiUrl, request)
      .pipe(
        // @ts-ignore
        map((response: ApiResponse<Comentario>) => response.data!)
      );
  }

  /**
   * Actualizar comentario
   */
  actualizarComentario(id: number, request: ComentarioUpdateRequest): Observable<Comentario> {
    return this.http.put<ApiResponse<Comentario>>(`${this.apiUrl}/${id}`, request)
      .pipe(
        // @ts-ignore
        map((response: ApiResponse<Comentario>) => response.data!)
      );
  }

  /**
   * Eliminar comentario
   */
  eliminarComentario(id: number): Observable<void> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`)
      .pipe(
        // @ts-ignore
        map(() => {})
      );
  }

  /**
   * Obtener comentarios por tipo
   */
  getComentariosPorTipo(tipo: TipoComentario): Observable<Comentario[]> {
    return this.http.get<ApiResponse<Comentario[]>>(`${this.apiUrl}/tipo/${tipo}`)
      .pipe(
        // @ts-ignore
        map((response: ApiResponse<Comentario[]>) => response.data || [])
      );
  }

  /**
   * Obtener comentarios de un usuario
   */
  getComentariosPorUsuario(usuarioId: number): Observable<Comentario[]> {
    return this.http.get<ApiResponse<Comentario[]>>(`${this.apiUrl}/usuario/${usuarioId}`)
      .pipe(
        // @ts-ignore
        map((response: ApiResponse<Comentario[]>) => response.data || [])
      );
  }

  /**
   * Obtener comentarios internos (solo para técnicos)
   */
  getComentariosInternos(incidenciaId: number): Observable<Comentario[]> {
    return this.http.get<ApiResponse<Comentario[]>>(`${this.apiUrl}/internos/incidencia/${incidenciaId}`)
      .pipe(
        // @ts-ignore
        map((response: ApiResponse<Comentario[]>) => response.data || [])
      );
  }

  /**
   * Obtener comentarios técnicos
   */
  getComentariosTecnicos(incidenciaId: number): Observable<Comentario[]> {
    return this.http.get<ApiResponse<Comentario[]>>(`${this.apiUrl}/tecnicos/incidencia/${incidenciaId}`)
      .pipe(
        // @ts-ignore
        map((response: ApiResponse<Comentario[]>) => response.data || [])
      );
  }

  /**
   * Obtener estadísticas de comentarios
   */
  getEstadisticasComentarios(incidenciaId: number): Observable<any> {
    return this.http.get<ApiResponse<any>>(`${this.apiUrl}/estadisticas/incidencia/${incidenciaId}`)
      .pipe(
        // @ts-ignore
        map((response: ApiResponse<any>) => response.data)
      );
  }

  /**
   * Obtener nombre completo del usuario
   */
  getNombreCompletoUsuario(comentario: Comentario): string {
    if (comentario.usuarioNombre && comentario.usuarioApellido) {
      return `${comentario.usuarioNombre} ${comentario.usuarioApellido}`;
    }
    return comentario.usuarioEmpleadoId || 'Usuario';
  }

  /**
   * Verificar si el comentario es interno
   */
  esComentarioInterno(comentario: Comentario): boolean {
    return comentario.tipo === TipoComentario.INTERNO;
  }

  /**
   * Verificar si el comentario es público
   */
  esComentarioPublico(comentario: Comentario): boolean {
    return comentario.tipo === TipoComentario.GENERAL;
  }

  /**
   * Verificar si el comentario es técnico
   */
  esComentarioTecnico(comentario: Comentario): boolean {
    return comentario.tipo === TipoComentario.TECNICO;
  }

  /**
   * Verificar si el comentario es solución
   */
  esComentarioSolucion(comentario: Comentario): boolean {
    return comentario.tipo === TipoComentario.SOLUCION;
  }

  /**
   * Obtener clase CSS para el tipo de comentario
   */
  getClaseTipoComentario(comentario: Comentario): string {
    switch (comentario.tipo) {
      case TipoComentario.GENERAL:
        return 'badge bg-primary';
      case TipoComentario.TECNICO:
        return 'badge bg-info';
      case TipoComentario.INTERNO:
        return 'badge bg-warning';
      case TipoComentario.SOLUCION:
        return 'badge bg-success';
      default:
        return 'badge bg-secondary';
    }
  }

  /**
   * Obtener icono para el tipo de comentario
   */
  getIconoTipoComentario(comentario: Comentario): string {
    switch (comentario.tipo) {
      case TipoComentario.GENERAL:
        return 'fas fa-comment';
      case TipoComentario.TECNICO:
        return 'fas fa-tools';
      case TipoComentario.INTERNO:
        return 'fas fa-eye-slash';
      case TipoComentario.SOLUCION:
        return 'fas fa-check-circle';
      default:
        return 'fas fa-comment';
    }
  }
} 