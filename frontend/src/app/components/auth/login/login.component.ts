import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, LoginRequest } from '../../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  hidePassword = true;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.loginForm = this.formBuilder.group({
      identificador: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    // Si ya está autenticado, redirigir a incidencias
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/incidencias']);
    }
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.loading = true;
      const loginRequest: LoginRequest = this.loginForm.value;

      this.authService.login(loginRequest).subscribe({
        next: (response) => {
          if (response.success) {
            this.snackBar.open('Inicio de sesión exitoso', 'Cerrar', {
              duration: 3000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
              panelClass: ['success-snackbar']
            });
            this.router.navigate(['/incidencias']);
          } else {
            this.snackBar.open(response.message || 'Credenciales inválidas', 'Cerrar', {
              duration: 5000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
              panelClass: ['error-snackbar']
            });
            // Detener el loading después de 2 segundos
            setTimeout(() => {
              this.loading = false;
            }, 2000);
          }
        },
        error: (error) => {
          console.error('Error en login:', error);
          let errorMessage = 'Error al iniciar sesión. Verifique sus credenciales.';
          
          if (error.error?.message) {
            errorMessage = error.error.message;
          } else if (error.status === 401) {
            errorMessage = 'Credenciales inválidas';
          } else if (error.status === 0) {
            errorMessage = 'No se puede conectar con el servidor. Verifique que el backend esté ejecutándose.';
          }
          
          this.snackBar.open(errorMessage, 'Cerrar', {
            duration: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'top',
            panelClass: ['error-snackbar']
          });
          
          // Detener el loading después de 2 segundos
          setTimeout(() => {
            this.loading = false;
          }, 2000);
        },
        complete: () => {
          // Solo detener loading si no hubo éxito (ya se detiene en error)
          if (!this.authService.isAuthenticated()) {
            setTimeout(() => {
              this.loading = false;
            }, 2000);
          }
        }
      });
    } else {
      this.markFormGroupTouched();
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.loginForm.controls).forEach(key => {
      const control = this.loginForm.get(key);
      control?.markAsTouched();
    });
  }

  getErrorMessage(field: string): string {
    const control = this.loginForm.get(field);
    if (control?.hasError('required')) {
      return 'Este campo es requerido';
    }
    if (control?.hasError('minlength')) {
      const minLength = control.errors?.['minlength'].requiredLength;
      return `Mínimo ${minLength} caracteres`;
    }
    return '';
  }
} 