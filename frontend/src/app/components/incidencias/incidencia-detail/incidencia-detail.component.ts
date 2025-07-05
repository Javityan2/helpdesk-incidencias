import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IncidenciasService, Incidencia } from '../../../services/incidencias.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-incidencia-detail',
  templateUrl: './incidencia-detail.component.html',
  styleUrls: ['./incidencia-detail.component.scss']
})
export class IncidenciaDetailComponent implements OnInit {
  incidencia: Incidencia | null = null;
  loading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private incidenciasService: IncidenciasService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadIncidencia(Number(id));
    }
  }

  loadIncidencia(id: number): void {
    this.loading = true;
    this.incidenciasService.getIncidencia(id).subscribe({
      next: (response) => {
        if (response.success) {
          this.incidencia = response.data as Incidencia;
        } else {
          this.toastr.error(response.message, 'Error');
          this.router.navigate(['/incidencias']);
        }
      },
      error: (error) => {
        console.error('Error cargando incidencia:', error);
        this.toastr.error('Error al cargar la incidencia', 'Error');
        this.router.navigate(['/incidencias']);
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  onEdit(): void {
    if (this.incidencia) {
      this.router.navigate(['/incidencias', this.incidencia.id, 'editar']);
    }
  }

  onBack(): void {
    this.router.navigate(['/incidencias']);
  }

  getEstadoColor(estado: string): string {
    switch (estado) {
      case 'ABIERTA': return '#ff9800';
      case 'EN_PROCESO': return '#2196f3';
      case 'RESUELTA': return '#4caf50';
      case 'CERRADA': return '#9e9e9e';
      default: return '#666';
    }
  }

  getPrioridadColor(prioridad: string): string {
    switch (prioridad) {
      case 'ALTA': return '#f44336';
      case 'MEDIA': return '#ff9800';
      case 'BAJA': return '#4caf50';
      default: return '#666';
    }
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
} 