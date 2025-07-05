import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Incidencia {
  id?: number;
  titulo: string;
  descripcion: string;
  estado: string;
  prioridad: string;
  categoria: string;
  fechaCreacion?: string;
  fechaActualizacion?: string;
  usuario?: any;
  tecnico?: any;
  comentarios?: Comentario[];
}

export interface Comentario {
  id?: number;
  contenido: string;
  fechaCreacion?: string;
  usuario?: any;
}

export interface IncidenciaResponse {
  success: boolean;
  message: string;
  data: Incidencia | Incidencia[];
}

export interface IncidenciaStats {
  total: number;
  abiertas: number;
  enProceso: number;
  resueltas: number;
  cerradas: number;
  alta: number;
  media: number;
  baja: number;
}

@Injectable({
  providedIn: 'root'
})
export class IncidenciasService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getIncidencias(): Observable<IncidenciaResponse> {
    return this.http.get<IncidenciaResponse>(`${this.apiUrl}/incidencias`);
  }

  getIncidencia(id: number): Observable<IncidenciaResponse> {
    return this.http.get<IncidenciaResponse>(`${this.apiUrl}/incidencias/${id}`);
  }

  createIncidencia(incidencia: Incidencia): Observable<IncidenciaResponse> {
    return this.http.post<IncidenciaResponse>(`${this.apiUrl}/incidencias`, incidencia);
  }

  updateIncidencia(id: number, incidencia: Incidencia): Observable<IncidenciaResponse> {
    return this.http.put<IncidenciaResponse>(`${this.apiUrl}/incidencias/${id}`, incidencia);
  }

  deleteIncidencia(id: number): Observable<IncidenciaResponse> {
    return this.http.delete<IncidenciaResponse>(`${this.apiUrl}/incidencias/${id}`);
  }

  getIncidenciasByEstado(estado: string): Observable<IncidenciaResponse> {
    return this.http.get<IncidenciaResponse>(`${this.apiUrl}/incidencias/estado/${estado}`);
  }

  getIncidenciasByPrioridad(prioridad: string): Observable<IncidenciaResponse> {
    return this.http.get<IncidenciaResponse>(`${this.apiUrl}/incidencias/prioridad/${prioridad}`);
  }

  getIncidenciasByUsuario(usuarioId: number): Observable<IncidenciaResponse> {
    return this.http.get<IncidenciaResponse>(`${this.apiUrl}/incidencias/usuario/${usuarioId}`);
  }

  getIncidenciasStats(): Observable<{ success: boolean; data: IncidenciaStats }> {
    return this.http.get<{ success: boolean; data: IncidenciaStats }>(`${this.apiUrl}/incidencias/estadisticas`);
  }

  addComentario(incidenciaId: number, comentario: Comentario): Observable<IncidenciaResponse> {
    return this.http.post<IncidenciaResponse>(`${this.apiUrl}/incidencias/${incidenciaId}/comentarios`, comentario);
  }

  updateEstado(incidenciaId: number, estado: string): Observable<IncidenciaResponse> {
    return this.http.patch<IncidenciaResponse>(`${this.apiUrl}/incidencias/${incidenciaId}/estado`, { estado });
  }

  assignTecnico(incidenciaId: number, tecnicoId: number): Observable<IncidenciaResponse> {
    return this.http.patch<IncidenciaResponse>(`${this.apiUrl}/incidencias/${incidenciaId}/asignar`, { tecnicoId });
  }
} 