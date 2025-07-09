import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ComentarioService, Comentario, TipoComentario, ComentarioCreateRequest } from '../../../services/comentario.service';

@Component({
  selector: 'app-comentarios',
  templateUrl: './comentarios.component.html',
  styleUrls: ['./comentarios.component.scss']
})
export class ComentariosComponent implements OnInit {
  
  @Input() incidenciaId!: number;
  @Input() usuarioActual: any;
  @Output() comentarioAgregado = new EventEmitter<Comentario>();
  @Output() comentarioEliminado = new EventEmitter<number>();

  comentarios: Comentario[] = [];
  nuevoComentario = '';
  tipoComentario: TipoComentario = TipoComentario.GENERAL;
  loading = false;
  error = '';
  mostrarFormulario = false;
  filtroTipo: TipoComentario | null = null;

  // Tipos de comentario disponibles
  tiposComentario = [
    { valor: TipoComentario.GENERAL, etiqueta: 'General', icono: 'fas fa-comment' },
    { valor: TipoComentario.TECNICO, etiqueta: 'Técnico', icono: 'fas fa-tools' },
    { valor: TipoComentario.INTERNO, etiqueta: 'Interno', icono: 'fas fa-eye-slash' },
    { valor: TipoComentario.SOLUCION, etiqueta: 'Solución', icono: 'fas fa-check-circle' }
  ];

  constructor(private comentarioService: ComentarioService) { }

  ngOnInit(): void {
    this.cargarComentarios();
  }

  cargarComentarios(): void {
    this.loading = true;
    this.error = '';

    this.comentarioService.getComentariosPorIncidencia(this.incidenciaId, this.filtroTipo || undefined)
      .subscribe({
        next: (comentarios) => {
          this.comentarios = comentarios;
          this.loading = false;
        },
        error: (error) => {
          console.error('Error al cargar comentarios:', error);
          this.error = 'Error al cargar los comentarios. Inténtalo de nuevo.';
          this.loading = false;
        }
      });
  }

  agregarComentario(): void {
    if (!this.nuevoComentario.trim()) {
      return;
    }

    const request: ComentarioCreateRequest = {
      contenido: this.nuevoComentario.trim(),
      tipo: this.tipoComentario,
      incidenciaId: this.incidenciaId
    };

    this.loading = true;
    this.error = '';

    this.comentarioService.crearComentario(request)
      .subscribe({
        next: (comentario) => {
          this.comentarios.unshift(comentario);
          this.nuevoComentario = '';
          this.tipoComentario = TipoComentario.GENERAL;
          this.mostrarFormulario = false;
          this.loading = false;
          this.comentarioAgregado.emit(comentario);
        },
        error: (error) => {
          console.error('Error al crear comentario:', error);
          this.error = 'Error al crear el comentario. Inténtalo de nuevo.';
          this.loading = false;
        }
      });
  }

  eliminarComentario(id: number): void {
    if (!confirm('¿Estás seguro de que quieres eliminar este comentario?')) {
      return;
    }

    this.loading = true;
    this.error = '';

    this.comentarioService.eliminarComentario(id)
      .subscribe({
        next: () => {
          this.comentarios = this.comentarios.filter(c => c.id !== id);
          this.loading = false;
          this.comentarioEliminado.emit(id);
        },
        error: (error) => {
          console.error('Error al eliminar comentario:', error);
          this.error = 'Error al eliminar el comentario. Inténtalo de nuevo.';
          this.loading = false;
        }
      });
  }

  cambiarFiltro(): void {
    this.cargarComentarios();
  }

  limpiarFiltro(): void {
    this.filtroTipo = null;
    this.cargarComentarios();
  }

  toggleFormulario(): void {
    this.mostrarFormulario = !this.mostrarFormulario;
  }

  cancelarComentario(): void {
    this.nuevoComentario = '';
    this.tipoComentario = TipoComentario.GENERAL;
    this.mostrarFormulario = false;
  }

  // Métodos de utilidad
  getNombreCompletoUsuario(comentario: Comentario): string {
    return this.comentarioService.getNombreCompletoUsuario(comentario);
  }

  getClaseTipoComentario(comentario: Comentario): string {
    return this.comentarioService.getClaseTipoComentario(comentario);
  }

  getIconoTipoComentario(comentario: Comentario): string {
    return this.comentarioService.getIconoTipoComentario(comentario);
  }

  esComentarioInterno(comentario: Comentario): boolean {
    return this.comentarioService.esComentarioInterno(comentario);
  }

  esComentarioPublico(comentario: Comentario): boolean {
    return this.comentarioService.esComentarioPublico(comentario);
  }

  esComentarioTecnico(comentario: Comentario): boolean {
    return this.comentarioService.esComentarioTecnico(comentario);
  }

  esComentarioSolucion(comentario: Comentario): boolean {
    return this.comentarioService.esComentarioSolucion(comentario);
  }

  puedeEliminarComentario(comentario: Comentario): boolean {
    // Solo el autor del comentario puede eliminarlo
    return this.usuarioActual && comentario.usuarioId === this.usuarioActual.id;
  }

  puedeVerComentarioInterno(comentario: Comentario): boolean {
    // Solo técnicos y administradores pueden ver comentarios internos
    if (!this.usuarioActual) return false;
    
    const rolesPermitidos = ['ADMIN', 'TECNICO', 'SUPERVISOR'];
    return rolesPermitidos.includes(this.usuarioActual.rol);
  }

  getComentariosFiltrados(): Comentario[] {
    if (!this.filtroTipo) {
      return this.comentarios;
    }
    return this.comentarios.filter(c => c.tipo === this.filtroTipo);
  }

  // Métodos para contar comentarios por tipo
  getComentariosTecnicosCount(): number {
    return this.comentarios.filter(c => this.esComentarioTecnico(c)).length;
  }

  getComentariosInternosCount(): number {
    return this.comentarios.filter(c => this.esComentarioInterno(c)).length;
  }

  getComentariosSolucionCount(): number {
    return this.comentarios.filter(c => this.esComentarioSolucion(c)).length;
  }
} 