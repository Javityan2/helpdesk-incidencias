import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { IncidenciaService, Incidencia } from './incidencia.service';

export interface Favorito {
  id: number;
  usuarioId: number;
  incidenciaId: number;
  fechaCreacion: string;
  incidencia?: Incidencia;
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
export class FavoritesService {
  private apiUrl = `${environment.apiUrl}/favoritos`;
  private favoritosSubject = new BehaviorSubject<Favorito[]>([]);
  public favoritos$ = this.favoritosSubject.asObservable();

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  /**
   * Obtener todos los favoritos del usuario
   */
  getFavoritos(): Observable<Favorito[]> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<Favorito[]>>(`${this.apiUrl}`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            this.favoritosSubject.next(response.data);
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener favoritos');
          }
        })
      );
  }

  /**
   * Agregar incidencia a favoritos
   */
  agregarFavorito(incidenciaId: number): Observable<Favorito> {
    const headers = this.getAuthHeaders();
    
    return this.http.post<ApiResponse<Favorito>>(`${this.apiUrl}`, { incidenciaId }, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            // Actualizar la lista de favoritos
            const favoritosActuales = this.favoritosSubject.value;
            this.favoritosSubject.next([...favoritosActuales, response.data]);
            return response.data;
          } else {
            throw new Error(response.error || 'Error al agregar a favoritos');
          }
        })
      );
  }

  /**
   * Quitar incidencia de favoritos
   */
  quitarFavorito(incidenciaId: number): Observable<void> {
    const headers = this.getAuthHeaders();
    
    return this.http.delete<ApiResponse<string>>(`${this.apiUrl}/${incidenciaId}`, { headers })
      .pipe(
        map(response => {
          if (response.success) {
            // Actualizar la lista de favoritos
            const favoritosActuales = this.favoritosSubject.value;
            const favoritosFiltrados = favoritosActuales.filter(f => f.incidenciaId !== incidenciaId);
            this.favoritosSubject.next(favoritosFiltrados);
          } else {
            throw new Error(response.error || 'Error al quitar de favoritos');
          }
        })
      );
  }

  /**
   * Verificar si una incidencia está en favoritos
   */
  esFavorito(incidenciaId: number): Observable<boolean> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<boolean>>(`${this.apiUrl}/verificar/${incidenciaId}`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data !== undefined) {
            return response.data;
          } else {
            throw new Error(response.error || 'Error al verificar favorito');
          }
        })
      );
  }

  /**
   * Obtener contador de favoritos
   */
  getContadorFavoritos(): Observable<number> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<number>>(`${this.apiUrl}/contador`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data !== undefined) {
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener contador de favoritos');
          }
        })
      );
  }

  /**
   * Obtener incidencias favoritas con detalles completos
   */
  getIncidenciasFavoritas(): Observable<Incidencia[]> {
    const headers = this.getAuthHeaders();
    
    return this.http.get<ApiResponse<Incidencia[]>>(`${this.apiUrl}/incidencias`, { headers })
      .pipe(
        map(response => {
          if (response.success && response.data) {
            return response.data;
          } else {
            throw new Error(response.error || 'Error al obtener incidencias favoritas');
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