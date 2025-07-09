import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Observable } from 'rxjs';
import { tap, map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(): Observable<boolean> | boolean {
    console.log('AuthGuard - canActivate iniciado');
    
    if (!this.authService.isAuthenticated()) {
      console.log('AuthGuard - Usuario no autenticado, redirigiendo al login');
      this.router.navigate(['/login']);
      return false;
    }
    
    console.log('AuthGuard - Usuario autenticado, permitiendo acceso (sin validación backend)');
    return true;
    
    // Comentado temporalmente para debuggear
    /*
    console.log('AuthGuard - Usuario autenticado, validando token con backend');
    
    // Validar el token con el backend
    return this.authService.validateToken().pipe(
      tap(valid => {
        console.log('AuthGuard - Resultado de validación:', valid);
      }),
      map(valid => {
        if (!valid) {
          console.log('AuthGuard - Token inválido, haciendo logout y redirigiendo');
          this.authService.logout();
          this.router.navigate(['/login']);
          return false;
        }
        console.log('AuthGuard - Token válido, permitiendo acceso');
        return true;
      }),
      catchError((error) => {
        console.error('AuthGuard - Error en validación:', error);
        this.authService.logout();
        this.router.navigate(['/login']);
        return [false];
      })
    );
    */
  }
} 