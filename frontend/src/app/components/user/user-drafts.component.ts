import { Component, OnInit } from '@angular/core';
import { IncidenciaService, Incidencia } from '../../services/incidencia.service';
import { DraftsService, Borrador } from '../../services/drafts.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-drafts',
  templateUrl: './user-drafts.component.html',
  styleUrls: ['./user-drafts.component.scss']
})
export class UserDraftsComponent implements OnInit {
  
  borradores: Borrador[] = [];
  loading = false;
  error = '';
  currentUser: any = null;

  constructor(
    private incidenciaService: IncidenciaService,
    private draftsService: DraftsService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.cargarBorradores();
  }

  cargarBorradores(): void {
    this.loading = true;
    this.error = '';

    this.draftsService.getBorradores().subscribe({
      next: (borradores) => {
        this.borradores = borradores;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar borradores:', error);
        this.error = 'Error al cargar los borradores. Inténtalo de nuevo.';
        this.loading = false;
        
        // Cargar datos de ejemplo si hay error
        this.cargarDatosEjemplo();
      }
    });
  }

  publicarBorrador(borradorId: number): void {
    this.draftsService.publicarBorrador(borradorId).subscribe({
      next: (incidencia) => {
        console.log('Borrador publicado correctamente:', incidencia);
        // La lista se actualiza automáticamente a través del servicio
        // Opcional: navegar a la incidencia creada
        this.router.navigate(['/incidencias', incidencia.id]);
      },
      error: (error) => {
        console.error('Error al publicar borrador:', error);
        this.error = 'Error al publicar el borrador. Inténtalo de nuevo.';
      }
    });
  }

  eliminarBorrador(borradorId: number): void {
    this.draftsService.eliminarBorrador(borradorId).subscribe({
      next: () => {
        console.log('Borrador eliminado correctamente');
        // La lista se actualiza automáticamente a través del servicio
      },
      error: (error) => {
        console.error('Error al eliminar borrador:', error);
        this.error = 'Error al eliminar el borrador. Inténtalo de nuevo.';
      }
    });
  }

  editarBorrador(borradorId: number): void {
    // Navegar a la página de edición de incidencias con el borrador
    this.router.navigate(['/incidencias/nueva'], { 
      queryParams: { borrador: borradorId } 
    });
  }

  getContadorPorPrioridad(prioridad: string): number {
    return this.borradores.filter(b => b.prioridad === prioridad).length;
  }

  cargarDatosEjemplo(): void {
    // Datos de ejemplo para cuando no hay conexión al backend
    this.borradores = [
      {
        id: 101,
        usuarioId: 1,
        titulo: 'Problema con la impresora de la oficina',
        descripcion: 'La impresora no imprime correctamente los documentos',
        prioridad: 'MEDIA',
        categoria: 'Hardware',
        estado: 'BORRADOR',
        fechaCreacion: '2024-01-15T09:15:00',
        fechaActualizacion: '2024-01-15T09:15:00'
      },
      {
        id: 102,
        usuarioId: 2,
        titulo: 'Solicitud de acceso al sistema de facturación',
        descripcion: 'Necesito acceso al módulo de facturación para generar reportes',
        prioridad: 'BAJA',
        categoria: 'Acceso',
        estado: 'BORRADOR',
        fechaCreacion: '2024-01-14T16:30:00',
        fechaActualizacion: '2024-01-14T16:30:00'
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
      case 'BORRADOR': return 'badge bg-secondary';
      case 'ABIERTA': return 'badge bg-warning';
      case 'EN_PROCESO': return 'badge bg-info';
      case 'RESUELTA': return 'badge bg-success';
      case 'CERRADA': return 'badge bg-secondary';
      default: return 'badge bg-secondary';
    }
  }
} 