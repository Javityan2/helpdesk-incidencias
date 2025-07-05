import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, User } from '../../../services/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  @Input() currentUser: User | null = null;
  @Output() logout = new EventEmitter<void>();

  menuItems = [
    {
      label: 'Dashboard',
      icon: 'dashboard',
      route: '/dashboard',
      roles: ['ADMIN', 'USER', 'TECHNICIAN']
    },
    {
      label: 'Incidencias',
      icon: 'bug_report',
      route: '/incidencias',
      roles: ['ADMIN', 'USER', 'TECHNICIAN']
    },
    {
      label: 'Nueva Incidencia',
      icon: 'add_circle',
      route: '/incidencias/nueva',
      roles: ['ADMIN', 'USER']
    },
    {
      label: 'Mis Incidencias',
      icon: 'assignment',
      route: '/incidencias/mis',
      roles: ['USER']
    },
    {
      label: 'Asignadas',
      icon: 'engineering',
      route: '/incidencias/asignadas',
      roles: ['TECHNICIAN']
    },
    {
      label: 'Usuarios',
      icon: 'people',
      route: '/usuarios',
      roles: ['ADMIN']
    },
    {
      label: 'Reportes',
      icon: 'analytics',
      route: '/reportes',
      roles: ['ADMIN']
    }
  ];

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  getFilteredMenuItems() {
    if (!this.currentUser) return [];
    
    return this.menuItems.filter(item => 
      item.roles.includes(this.currentUser!.rol)
    );
  }

  isActiveRoute(route: string): boolean {
    return this.router.url === route;
  }

  onLogout(): void {
    this.logout.emit();
  }

  getUserDisplayName(): string {
    if (!this.currentUser) return '';
    return `${this.currentUser.nombre} ${this.currentUser.apellido}`;
  }

  getUserRole(): string {
    if (!this.currentUser) return '';
    
    const roleMap: { [key: string]: string } = {
      'ADMIN': 'Administrador',
      'USER': 'Usuario',
      'TECHNICIAN': 'Técnico'
    };
    
    return roleMap[this.currentUser.rol] || this.currentUser.rol;
  }
} 