import { Component, OnInit } from '@angular/core';
import { IncidenciaService, Incidencia } from '../../services/incidencia.service';
import { FavoritesService } from '../../services/favorites.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-user-favorites',
  templateUrl: './user-favorites.component.html',
  styleUrls: ['./user-favorites.component.scss']
})
export class UserFavoritesComponent implements OnInit {
  
  favoritos: Incidencia[] = [];
  loading = false;
  error = '';
  currentUser: any = null;

  constructor(
    private incidenciaService: IncidenciaService,
    private favoritesService: FavoritesService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.cargarFavoritos();
  }

  cargarFavoritos(): void {
    this.loading = true;
    this.error = '';

    this.favoritesService.getIncidenciasFavoritas().subscribe({
      next: (incidencias) => {
        this.favoritos = incidencias;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar favoritos:', error);
        this.error = 'Error al cargar los favoritos. Inténtalo de nuevo.';
        this.loading = false;
        
        // Cargar datos de ejemplo si hay error
        this.cargarDatosEjemplo();
      }
    });
  }

  quitarFavorito(incidenciaId: number): void {
    this.favoritesService.quitarFavorito(incidenciaId).subscribe({
      next: () => {
        // La lista se actualiza automáticamente a través del servicio
        console.log('Favorito eliminado correctamente');
      },
      error: (error) => {
        console.error('Error al quitar favorito:', error);
        this.error = 'Error al quitar de favoritos. Inténtalo de nuevo.';
      }
    });
  }

  getContadorPorEstado(estado: string): number {
    return this.favoritos.filter(f => f.estado === estado).length;
  }

  cargarDatosEjemplo(): void {
    // Datos de ejemplo para cuando no hay conexión al backend
    this.favoritos = [
      {
        id: 1,
        titulo: 'Problema con el sistema de correo',
        descripcion: 'No puedo acceder a mi cuenta de correo corporativo',
        prioridad: 'ALTA',
        estado: 'EN_PROCESO',
        categoria: 'Sistema',
        fechaCreacion: '2024-01-15T10:30:00',
        frecuenciaBusqueda: 5,
        usuario: {
          id: 1,
          empleadoId: 'EMP001',
          nombre: 'Juan',
          apellido: 'Pérez',
          email: 'juan.perez@empresa.com',
          departamento: 'IT'
        }
      },
      {
        id: 2,
        titulo: 'Error en la aplicación de facturación',
        descripcion: 'La aplicación no genera correctamente las facturas',
        prioridad: 'MEDIA',
        estado: 'ABIERTA',
        categoria: 'Aplicación',
        fechaCreacion: '2024-01-14T14:20:00',
        frecuenciaBusqueda: 3,
        usuario: {
          id: 2,
          empleadoId: 'EMP002',
          nombre: 'María',
          apellido: 'García',
          email: 'maria.garcia@empresa.com',
          departamento: 'Contabilidad'
        }
      }
    ];
  }

  getPrioridadClass(prioridad: string): string {
    switch (prioridad) {
      case 'ALTA': return 'badge bg-danger';
      case 'MEDIA': return 'badge bg-warning';
      case 'BAJA': return 'badge bg-success';
      default: return 'badge bg-secondary';
    }
  }

  getEstadoClass(estado: string): string {
    switch (estado) {
      case 'ABIERTA': return 'badge bg-warning';
      case 'EN_PROCESO': return 'badge bg-info';
      case 'RESUELTA': return 'badge bg-success';
      case 'CERRADA': return 'badge bg-secondary';
      default: return 'badge bg-secondary';
    }
  }
} 