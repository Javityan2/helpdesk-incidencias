import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IncidenciaService, Incidencia } from '../../../services/incidencia.service';
import { trigger, state, style, transition, animate, keyframes } from '@angular/animations';

@Component({
  selector: 'app-incidencias-list',
  templateUrl: './incidencias-list.component.html',
  styleUrls: ['./incidencias-list.component.scss'],
  animations: [
    trigger('fadeInUp', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(20px)' }),
        animate('400ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ]),
    trigger('slideInRight', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateX(20px)' }),
        animate('300ms ease-out', style({ opacity: 1, transform: 'translateX(0)' }))
      ])
    ]),
    trigger('scaleIn', [
      transition(':enter', [
        style({ opacity: 0, transform: 'scale(0.9)' }),
        animate('300ms ease-out', style({ opacity: 1, transform: 'scale(1)' }))
      ])
    ]),
    trigger('cardHover', [
      transition('* => hovered', [
        animate('200ms ease-out', keyframes([
          style({ transform: 'translateY(0)', offset: 0 }),
          style({ transform: 'translateY(-5px)', offset: 1 })
        ]))
      ]),
      transition('hovered => *', [
        animate('200ms ease-in', keyframes([
          style({ transform: 'translateY(-5px)', offset: 0 }),
          style({ transform: 'translateY(0)', offset: 1 })
        ]))
      ])
    ])
  ]
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
      case 'ALTA': return 'badge-priority high';
      case 'MEDIA': return 'badge-priority medium';
      case 'BAJA': return 'badge-priority low';
      default: return 'badge-priority default';
    }
  }

  getEstadoClass(estado: string): string {
    switch (estado) {
      case 'PENDIENTE': return 'badge-status pending';
      case 'EN_PROCESO': return 'badge-status in-progress';
      case 'RESUELTA': return 'badge-status resolved';
      case 'CERRADA': return 'badge-status closed';
      default: return 'badge-status default';
    }
  }

  getCategoriasUnicas(): string[] {
    return [...new Set(this.incidencias.map(i => i.categoria))];
  }
} 