import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IncidenciaService, Incidencia } from '../../../services/incidencia.service';

@Component({
  selector: 'app-incidencias-list',
  templateUrl: './incidencias-list.component.html',
  styleUrls: ['./incidencias-list.component.scss']
})
export class IncidenciasListComponent implements OnInit {
  
  incidencias: Incidencia[] = [];
  loading = false;
  error = '';
  
  // Filtros
  filtroEstado = '';
  filtroPrioridad = '';
  filtroCategoria = '';
  
  // Paginación
  currentPage = 1;
  itemsPerPage = 10;
  totalItems = 0;

  constructor(
    private incidenciaService: IncidenciaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarIncidencias();
  }

  cargarIncidencias(): void {
    this.loading = true;
    this.error = '';

    this.incidenciaService.getIncidencias()
      .subscribe({
        next: (data) => {
          this.incidencias = data;
          this.totalItems = data.length;
          this.loading = false;
        },
        error: (error) => {
          console.error('Error al cargar incidencias:', error);
          this.error = 'Error al cargar las incidencias. Inténtalo de nuevo.';
          this.loading = false;
        }
      });
  }

  verDetalle(id: number): void {
    // Incrementar frecuencia de búsqueda
    this.incidenciaService.incrementarFrecuenciaBusqueda(id).subscribe({
      next: () => {
        console.log('Frecuencia de búsqueda incrementada');
      },
      error: (error) => {
        console.error('Error al incrementar frecuencia:', error);
      }
    });

    // Navegar al detalle
    this.router.navigate(['/incidencias', id]);
  }

  aplicarFiltros(): void {
    this.currentPage = 1;
    this.cargarIncidencias();
  }

  limpiarFiltros(): void {
    this.filtroEstado = '';
    this.filtroPrioridad = '';
    this.filtroCategoria = '';
    this.currentPage = 1;
    this.cargarIncidencias();
  }

  getIncidenciasFiltradas(): Incidencia[] {
    let filtradas = this.incidencias;

    if (this.filtroEstado) {
      filtradas = filtradas.filter(i => i.estado === this.filtroEstado);
    }

    if (this.filtroPrioridad) {
      filtradas = filtradas.filter(i => i.prioridad === this.filtroPrioridad);
    }

    if (this.filtroCategoria) {
      filtradas = filtradas.filter(i => i.categoria === this.filtroCategoria);
    }

    return filtradas;
  }

  getIncidenciasPaginadas(): Incidencia[] {
    const filtradas = this.getIncidenciasFiltradas();
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return filtradas.slice(startIndex, startIndex + this.itemsPerPage);
  }

  getTotalPages(): number {
    return Math.ceil(this.getIncidenciasFiltradas().length / this.itemsPerPage);
  }

  cambiarPagina(pagina: number): void {
    this.currentPage = pagina;
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

  getCategoriasUnicas(): string[] {
    return [...new Set(this.incidencias.map(i => i.categoria))];
  }
} 