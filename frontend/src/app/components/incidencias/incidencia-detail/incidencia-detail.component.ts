import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IncidenciaService, Incidencia } from '../../../services/incidencia.service';

@Component({
  selector: 'app-incidencia-detail',
  templateUrl: './incidencia-detail.component.html',
  styleUrls: ['./incidencia-detail.component.scss']
})
export class IncidenciaDetailComponent implements OnInit {
  
  incidencia: Incidencia | null = null;
  loading = false;
  error = '';
  activeTab = 'general';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private incidenciaService: IncidenciaService
  ) {}

  ngOnInit(): void {
    this.cargarIncidencia();
  }

  cargarIncidencia(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.error = 'ID de incidencia no válido';
      return;
    }

    this.loading = true;
    this.error = '';

    this.incidenciaService.getIncidencia(+id)
      .subscribe({
        next: (data) => {
          this.incidencia = data;
          this.loading = false;
        },
        error: (error) => {
          console.error('Error al cargar incidencia:', error);
          this.error = 'Error al cargar la incidencia. Inténtalo de nuevo.';
          this.loading = false;
        }
      });
  }

  cambiarTab(tab: string): void {
    this.activeTab = tab;
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
      case 'PENDIENTE': return 'badge bg-warning';
      case 'EN_PROCESO': return 'badge bg-info';
      case 'RESUELTA': return 'badge bg-success';
      case 'CERRADA': return 'badge bg-secondary';
      default: return 'badge bg-secondary';
    }
  }

  volver(): void {
    this.router.navigate(['/incidencias']);
  }
} 