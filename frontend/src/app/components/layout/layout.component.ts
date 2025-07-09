import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { NotificationService } from '../../services/notification.service';
import { FavoritesService } from '../../services/favorites.service';
import { DraftsService } from '../../services/drafts.service';
import { filter, take } from 'rxjs/operators';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {
  isAuthenticated = false;
  currentUser: any = null;
  sidebarOpened = true;
  notificationsPanelOpen = false;
  
  // Contadores para badges
  notificacionesCount = 0;
  favoritosCount = 0;
  borradoresCount = 0;

  constructor(
    private authService: AuthService,
    private router: Router,
    private notificationService: NotificationService,
    private favoritesService: FavoritesService,
    private draftsService: DraftsService
  ) {}

  ngOnInit(): void {
    this.authService.isAuthenticated$.subscribe(
      isAuth => {
        console.log('LayoutComponent - isAuthenticated:', isAuth);
        this.isAuthenticated = isAuth;
        // Solo cargar contadores si está autenticado
        if (isAuth) {
          console.log('LayoutComponent - Usuario autenticado, cargando contadores...');
          
          // Probar el token primero
          this.authService.debugToken().subscribe({
            next: (response) => {
              console.log('LayoutComponent - Token debug exitoso:', response);
              this.cargarContadores();
            },
            error: (error) => {
              console.error('LayoutComponent - Error al debug token:', error);
              // Aún así intentar cargar contadores
              this.cargarContadores();
            }
          });
        }
      }
    );
    
    this.authService.currentUser$.subscribe(
      user => this.currentUser = user
    );

    // Suscribirse a cambios en los servicios
    this.suscribirseAServicios();
  }

  toggleSidebar(): void {
    this.sidebarOpened = !this.sidebarOpened;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }

  abrirNotificaciones(): void {
    this.notificationsPanelOpen = !this.notificationsPanelOpen;
  }

  cerrarNotificaciones(): void {
    this.notificationsPanelOpen = false;
  }

  cargarContadores(): void {
    console.log('LayoutComponent - cargarContadores iniciado');
    // Esperar un poco para asegurar que el token esté disponible
    setTimeout(() => {
      console.log('LayoutComponent - cargarContadores después del delay');
      const token = this.authService.getToken();
      console.log('LayoutComponent - Token antes de cargar contadores:', token ? 'SÍ' : 'NO');
      
      // Cargar contador de notificaciones
      this.notificationService.getContadorNoLeidas().subscribe({
        next: (count) => {
          console.log('LayoutComponent - Contador notificaciones cargado:', count);
          this.notificacionesCount = count;
        },
        error: (error) => {
          console.error('Error al cargar contador de notificaciones:', error);
          this.notificacionesCount = 0;
        }
      });

      // Cargar contador de favoritos
      this.favoritesService.getContadorFavoritos().subscribe({
        next: (count) => {
          console.log('LayoutComponent - Contador favoritos cargado:', count);
          this.favoritosCount = count;
        },
        error: (error) => {
          console.error('Error al cargar contador de favoritos:', error);
          this.favoritosCount = 0;
        }
      });

      // Cargar contador de borradores
      this.draftsService.getContadorBorradores().subscribe({
        next: (count) => {
          console.log('LayoutComponent - Contador borradores cargado:', count);
          this.borradoresCount = count;
        },
        error: (error) => {
          console.error('Error al cargar contador de borradores:', error);
          this.borradoresCount = 0;
        }
      });
    }, 500); // Aumentado a 500ms
  }

  suscribirseAServicios(): void {
    // Suscribirse a cambios en favoritos
    this.favoritesService.favoritos$.subscribe(favoritos => {
      this.favoritosCount = favoritos.length;
    });

    // Suscribirse a cambios en borradores
    this.draftsService.borradores$.subscribe(borradores => {
      this.borradoresCount = borradores.length;
    });

    // Suscribirse a cambios en notificaciones
    this.notificationService.notificaciones$.subscribe(notificaciones => {
      this.notificacionesCount = notificaciones.filter(n => !n.leida).length;
    });
  }

  puedeAccederAdmin(): boolean {
    if (!this.currentUser) return false;
    const rolesAdmin = ['ADMIN', 'SUPERVISOR'];
    return rolesAdmin.includes(this.currentUser.rol);
  }

  getRolDisplayName(rol: string): string {
    const rolesMap: { [key: string]: string } = {
      'ADMIN': 'Administrador',
      'SUPERVISOR': 'Supervisor',
      'TECNICO': 'Técnico',
      'USUARIO': 'Usuario',
      'USER': 'Usuario'
    };
    return rolesMap[rol] || rol;
  }
} 