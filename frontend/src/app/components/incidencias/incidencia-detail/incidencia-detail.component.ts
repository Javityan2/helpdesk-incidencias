import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IncidenciasService } from '../../../services/incidencias.service';
import { MatSnackBar } from '@angular/material/snack-bar';

interface Incidencia {
  id: number;
  titulo: string;
  descripcion: string;
  estado: string;
  prioridad: string;
  categoria: string;
  fechaCreacion: string;
  usuario?: any;
  tecnico?: any;
}

@Component({
  selector: 'app-incidencia-detail',
  templateUrl: './incidencia-detail.component.html',
  styleUrls: ['./incidencia-detail.component.scss']
})
export class IncidenciaDetailComponent implements OnInit {
  incidencia: Incidencia | null = null;
  comentarios: any[] = [];
  loading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private incidenciasService: IncidenciasService,
    private snackBar: MatSnackBar
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
      next: (response: any) => {
        this.incidencia = response;
        this.loadComentarios(id);
      },
      error: (error) => {
        console.error('Error cargando incidencia:', error);
        this.snackBar.open('Error al cargar la incidencia', 'Cerrar', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
          panelClass: ['error-snackbar']
        });
        this.router.navigate(['/incidencias']);
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  loadComentarios(incidenciaId: number): void {
    // TODO: Implementar servicio de comentarios
    this.comentarios = [];
  }

  editarIncidencia(): void {
    if (this.incidencia) {
      this.router.navigate(['/incidencias', this.incidencia.id, 'editar']);
    }
  }

  volver(): void {
    this.router.navigate(['/incidencias']);
  }

  getEstadoColor(estado: string): string {
    switch (estado?.toUpperCase()) {
      case 'ABIERTA': return 'warn';
      case 'EN_PROCESO': return 'primary';
      case 'RESUELTA': return 'accent';
      case 'CERRADA': return 'basic';
      default: return 'basic';
    }
  }

  getPrioridadColor(prioridad: string): string {
    switch (prioridad?.toUpperCase()) {
      case 'ALTA': return 'warn';
      case 'MEDIA': return 'accent';
      case 'BAJA': return 'primary';
      default: return 'basic';
    }
  }
} 