import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationService, Notificacion } from '../../services/notification.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-notifications-panel',
  templateUrl: './notifications-panel.component.html',
  styleUrls: ['./notifications-panel.component.scss']
})
export class NotificationsPanelComponent implements OnInit {
  
  @Input() isOpen = false;
  @Output() closePanel = new EventEmitter<void>();
  
  notificaciones: Notificacion[] = [];
  loading = false;
  error = '';

  constructor(
    private notificationService: NotificationService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Solo cargar notificaciones si el usuario está autenticado
    this.authService.isAuthenticated$.subscribe(isAuth => {
      if (isAuth) {
        this.cargarNotificaciones();
      }
    });
  }

  cargarNotificaciones(): void {
    this.loading = true;
    this.error = '';

    this.notificationService.getNotificaciones().subscribe({
      next: (notificaciones) => {
        this.notificaciones = notificaciones;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar notificaciones:', error);
        this.error = 'Error al cargar las notificaciones.';
        this.loading = false;
        
        // Cargar datos de ejemplo si hay error
        this.cargarDatosEjemplo();
      }
    });
  }

  marcarComoLeida(notificacionId: number): void {
    this.notificationService.marcarComoLeida(notificacionId).subscribe({
      next: () => {
        // Actualizar el estado de la notificación localmente
        const notificacion = this.notificaciones.find(n => n.id === notificacionId);
        if (notificacion) {
          notificacion.leida = true;
          notificacion.fechaLectura = new Date().toISOString();
        }
      },
      error: (error) => {
        console.error('Error al marcar notificación como leída:', error);
      }
    });
  }

  marcarTodasComoLeidas(): void {
    this.notificationService.marcarTodasComoLeidas().subscribe({
      next: () => {
        // Marcar todas las notificaciones como leídas localmente
        this.notificaciones.forEach(n => {
          n.leida = true;
          n.fechaLectura = new Date().toISOString();
        });
      },
      error: (error) => {
        console.error('Error al marcar todas las notificaciones como leídas:', error);
      }
    });
  }

  eliminarNotificacion(notificacionId: number): void {
    this.notificationService.eliminarNotificacion(notificacionId).subscribe({
      next: () => {
        // Eliminar la notificación de la lista localmente
        this.notificaciones = this.notificaciones.filter(n => n.id !== notificacionId);
      },
      error: (error) => {
        console.error('Error al eliminar notificación:', error);
      }
    });
  }

  navegarAIncidencia(incidenciaId: number): void {
    this.router.navigate(['/incidencias', incidenciaId]);
    this.cerrarPanel();
  }

  cerrarPanel(): void {
    this.closePanel.emit();
  }

  getContadorNoLeidas(): number {
    return this.notificaciones.filter(n => !n.leida).length;
  }

  getTipoClass(tipo: string): string {
    switch (tipo) {
      case 'SUCCESS': return 'text-success';
      case 'WARNING': return 'text-warning';
      case 'ERROR': return 'text-danger';
      case 'INFO': return 'text-info';
      default: return 'text-secondary';
    }
  }

  getTipoIcon(tipo: string): string {
    switch (tipo) {
      case 'SUCCESS': return 'fas fa-check-circle';
      case 'WARNING': return 'fas fa-exclamation-triangle';
      case 'ERROR': return 'fas fa-times-circle';
      case 'INFO': return 'fas fa-info-circle';
      default: return 'fas fa-bell';
    }
  }

  cargarDatosEjemplo(): void {
    // Datos de ejemplo para cuando no hay conexión al backend
    this.notificaciones = [
      {
        id: 1,
        titulo: 'Nueva incidencia asignada',
        mensaje: 'Se te ha asignado una nueva incidencia de alta prioridad',
        tipo: 'INFO',
        leida: false,
        fechaCreacion: '2024-01-15T10:30:00',
        incidenciaId: 1,
        usuarioId: 1
      },
      {
        id: 2,
        titulo: 'Incidencia resuelta',
        mensaje: 'La incidencia #123 ha sido marcada como resuelta',
        tipo: 'SUCCESS',
        leida: true,
        fechaCreacion: '2024-01-15T09:15:00',
        fechaLectura: '2024-01-15T09:20:00',
        incidenciaId: 123,
        usuarioId: 1
      },
      {
        id: 3,
        titulo: 'Sistema de mantenimiento',
        mensaje: 'El sistema estará en mantenimiento el próximo domingo de 2:00 a 4:00 AM',
        tipo: 'WARNING',
        leida: false,
        fechaCreacion: '2024-01-14T16:45:00',
        usuarioId: 1
      }
    ];
  }
} 