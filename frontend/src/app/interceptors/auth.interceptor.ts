import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // Obtener token del servicio de autenticación
    const token = this.authService.getToken();
    
    // Si hay token, agregarlo al header
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        // Si el error es 401 (Unauthorized), redirigir al login
        if (error.status === 401) {
          this.authService.logout();
          this.router.navigate(['/login']);
        }
        
        // Si el error es 403 (Forbidden), mostrar mensaje de acceso denegado
        if (error.status === 403) {
          console.error('Acceso denegado: No tienes permisos para realizar esta acción');
        }
        
        return throwError(() => error);
      })
    );
  }
} 