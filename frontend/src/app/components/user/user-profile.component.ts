import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  
  currentUser: any = null;
  loading = false;
  error = '';
  
  // Datos del perfil
  perfil = {
    nombre: '',
    apellido: '',
    email: '',
    departamento: '',
    rol: '',
    telefono: '',
    fechaRegistro: '',
    ultimoAcceso: ''
  };

  constructor(
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.cargarPerfil();
  }

  cargarPerfil(): void {
    this.loading = true;
    this.error = '';

    // Obtener usuario actual
    this.currentUser = this.authService.getCurrentUser();
    
    if (this.currentUser) {
      this.perfil = {
        nombre: this.currentUser.nombre || '',
        apellido: this.currentUser.apellido || '',
        email: this.currentUser.email || '',
        departamento: this.currentUser.departamento || '',
        rol: this.currentUser.rol || '',
        telefono: this.currentUser.telefono || 'No especificado',
        fechaRegistro: this.currentUser.fechaRegistro || 'No disponible',
        ultimoAcceso: this.currentUser.ultimoAcceso || 'No disponible'
      };
    } else {
      this.error = 'No se pudo cargar la información del usuario';
    }

    this.loading = false;
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

  actualizarPerfil(): void {
    // TODO: Implementar actualización del perfil
    console.log('Actualizar perfil');
  }

  cambiarPassword(): void {
    // TODO: Implementar cambio de contraseña
    console.log('Cambiar contraseña');
  }
} 