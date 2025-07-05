import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { IncidenciasService, Incidencia } from '../../../services/incidencias.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-incidencias-list',
  templateUrl: './incidencias-list.component.html',
  styleUrls: ['./incidencias-list.component.scss']
})
export class IncidenciasListComponent implements OnInit {
  displayedColumns: string[] = ['titulo', 'usuario', 'estado'];
  dataSource: MatTableDataSource<Incidencia> = new MatTableDataSource<Incidencia>([]);
  loading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private incidenciasService: IncidenciasService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadIncidencias();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadIncidencias(): void {
    this.loading = true;
    this.incidenciasService.getIncidencias().subscribe({
      next: (response) => {
        if (response.success) {
          this.dataSource.data = response.data as Incidencia[];
        } else {
          this.snackBar.open(response.message || 'Error al cargar incidencias', 'Cerrar', {
            duration: 5000,
            panelClass: ['error-snackbar']
          });
        }
      },
      error: (error) => {
        console.error('Error cargando incidencias:', error);
        this.snackBar.open('Error al cargar las incidencias', 'Cerrar', {
          duration: 5000,
          panelClass: ['error-snackbar']
        });
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  onRowClick(incidencia: Incidencia): void {
    this.router.navigate(['/incidencias', incidencia.id]);
  }

  onEdit(incidencia: Incidencia): void {
    this.router.navigate(['/incidencias', incidencia.id, 'editar']);
  }

  onDelete(incidencia: Incidencia): void {
    if (confirm('¿Está seguro de que desea eliminar esta incidencia?')) {
      this.incidenciasService.deleteIncidencia(incidencia.id!).subscribe({
        next: (response) => {
          if (response.success) {
            this.snackBar.open('Incidencia eliminada correctamente', 'Cerrar', {
              duration: 3000,
              panelClass: ['success-snackbar']
            });
            this.loadIncidencias();
          } else {
            this.snackBar.open(response.message || 'Error al eliminar incidencia', 'Cerrar', {
              duration: 5000,
              panelClass: ['error-snackbar']
            });
          }
        },
        error: (error) => {
          console.error('Error eliminando incidencia:', error);
          this.snackBar.open('Error al eliminar la incidencia', 'Cerrar', {
            duration: 5000,
            panelClass: ['error-snackbar']
          });
        }
      });
    }
  }

  getEstadoColor(estado: string): string {
    switch (estado) {
      case 'ABIERTA':
        return '#ff9800';
      case 'EN_PROCESO':
        return '#2196f3';
      case 'RESUELTA':
        return '#4caf50';
      case 'CERRADA':
        return '#9e9e9e';
      default:
        return '#666';
    }
  }

  getPrioridadColor(prioridad: string): string {
    switch (prioridad) {
      case 'ALTA':
        return '#f44336';
      case 'MEDIA':
        return '#ff9800';
      case 'BAJA':
        return '#4caf50';
      default:
        return '#666';
    }
  }

  getEstadoText(estado: string): string {
    switch (estado) {
      case 'ABIERTA':
        return 'Abierta';
      case 'EN_PROCESO':
        return 'En Proceso';
      case 'RESUELTA':
        return 'Resuelta';
      case 'CERRADA':
        return 'Cerrada';
      default:
        return estado;
    }
  }

  getPrioridadText(prioridad: string): string {
    switch (prioridad) {
      case 'ALTA':
        return 'Alta';
      case 'MEDIA':
        return 'Media';
      case 'BAJA':
        return 'Baja';
      default:
        return prioridad;
    }
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
} 