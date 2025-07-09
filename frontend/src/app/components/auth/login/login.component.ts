import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  
  loginData = {
    identificador: '',
    password: ''
  };
  
  loading = false;
  error = '';
  showPassword = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Si ya está autenticado, redirigir al dashboard
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/dashboard']);
    }
  }

  onSubmit(): void {
    if (!this.loginData.identificador || !this.loginData.password) {
      this.error = 'Por favor, completa todos los campos';
      return;
    }

    this.loading = true;
    this.error = '';

    console.log('LoginComponent - Iniciando login...');

    this.authService.login(this.loginData.identificador, this.loginData.password)
      .subscribe({
        next: (response) => {
          console.log('LoginComponent - Login exitoso:', response);
          console.log('LoginComponent - Token recibido:', response.token ? `SÍ (${response.token.length} chars)` : 'NO');
          console.log('LoginComponent - Token completo:', response.token);
          
          // Verificar estado del token después del login
          setTimeout(() => {
            console.log('LoginComponent - Verificando estado del token después del login...');
            this.authService.checkTokenState();
          }, 100);
          
          // Probar autenticación después del login
          setTimeout(() => {
            console.log('LoginComponent - Probando autenticación...');
            this.authService.testAuth().subscribe({
              next: (result) => {
                console.log('LoginComponent - Prueba de autenticación exitosa:', result);
              },
              error: (error) => {
                console.error('LoginComponent - Error en prueba de autenticación:', error);
              }
            });
          }, 200);
          
          // Esperar un poco para asegurar que el token se guarde correctamente
          setTimeout(() => {
            console.log('LoginComponent - Navegando al dashboard...');
            console.log('LoginComponent - Estado de autenticación antes de navegar:', this.authService.isAuthenticated());
            console.log('LoginComponent - Token disponible:', this.authService.getToken() ? 'SÍ' : 'NO');
            
            // Verificar el token directamente del localStorage
            const directToken = localStorage.getItem('token');
            console.log('LoginComponent - Token directo de localStorage:', directToken ? `SÍ (${directToken.length} chars)` : 'NO');
            
            // Verificar estado del token antes de navegar
            this.authService.checkTokenState();
            
            // Verificar el token justo antes de navegar
            console.log('LoginComponent - Verificación final antes de navegar:');
            console.log('LoginComponent - localStorage.token:', localStorage.getItem('token') ? 'SÍ' : 'NO');
            console.log('LoginComponent - localStorage.currentUser:', localStorage.getItem('currentUser') ? 'SÍ' : 'NO');
            
            this.router.navigate(['/dashboard']).then(success => {
              console.log('LoginComponent - Navegación exitosa:', success);
              
              // Verificar el token después de la navegación
              setTimeout(() => {
                console.log('LoginComponent - Estado después de navegación:', this.authService.isAuthenticated());
                console.log('LoginComponent - Token después de navegación:', this.authService.getToken() ? 'SÍ' : 'NO');
                const tokenAfterNav = localStorage.getItem('token');
                console.log('LoginComponent - Token directo después de navegación:', tokenAfterNav ? `SÍ (${tokenAfterNav.length} chars)` : 'NO');
                
                // Verificar estado completo después de navegación
                this.authService.checkTokenState();
              }, 100);
            }).catch(error => {
              console.error('LoginComponent - Error en navegación:', error);
            });
          }, 500); // Aumentado de 200ms a 500ms
        },
        error: (error) => {
          console.error('LoginComponent - Error en login:', error);
          this.error = error.message || 'Error en el login. Verifica tus credenciales.';
          this.loading = false;
        },
        complete: () => {
          console.log('LoginComponent - Login completado');
          this.loading = false;
        }
      });
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  onKeyPress(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      this.onSubmit();
    }
  }
} 