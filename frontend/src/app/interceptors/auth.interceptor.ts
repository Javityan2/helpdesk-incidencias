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
    // Solo agregar token si la URL requiere autenticación
    const requiresAuth = this.requiresAuthentication(request.url);
    
    if (requiresAuth) {
      // Obtener token del servicio de autenticación
      const token = this.authService.getToken();
      
      console.log('Interceptor - URL que requiere auth:', request.url);
      console.log('Interceptor - Token disponible:', !!token);
      if (token) {
        console.log('Interceptor - Token length:', token.length);
        console.log('Interceptor - Token preview:', token.substring(0, 50) + '...');
      } else {
        console.log('Interceptor - NO HAY TOKEN - Verificando localStorage directamente...');
        const directToken = localStorage.getItem('token');
        console.log('Interceptor - Token directo de localStorage:', directToken ? `SÍ (${directToken.length} chars)` : 'NO');
        
        // Llamar al método de debug del AuthService
        this.authService.debugTokenStorage();
      }
      
      // Si hay token, agregarlo al header
      if (token) {
        request = request.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`
          }
        });
        console.log('Interceptor - Headers agregados:', request.headers.get('Authorization'));
        console.log('Interceptor - Header completo que se envía:', request.headers.get('Authorization'));
        console.log('Interceptor - URL de la petición:', request.url);
        console.log('Interceptor - Método de la petición:', request.method);
      } else {
        console.log('Interceptor - No hay token disponible');
      }
    } else {
      console.log('Interceptor - URL no requiere auth:', request.url);
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        console.log('Interceptor - Error:', error.status, error.url);
        
        // Solo manejar errores 401 si no estamos en la página de login
        if (error.status === 401) {
          const currentUrl = this.router.url;
          console.log('Interceptor - Current URL:', currentUrl);
          
          if (!currentUrl.includes('/login')) {
            console.log('Token expirado o inválido, redirigiendo al login');
            // Solo hacer logout si realmente hay un token inválido
            if (this.authService.getToken()) {
              this.authService.logout();
              this.router.navigate(['/login']);
            }
          }
        }
        
        // Si el error es 403 (Forbidden), mostrar mensaje de acceso denegado
        if (error.status === 403) {
          console.error('Acceso denegado: No tienes permisos para realizar esta acción');
        }
        
        return throwError(() => error);
      })
    );
  }

  private requiresAuthentication(url: string): boolean {
    // URLs que requieren autenticación
    const authUrls = [
      '/api/notificaciones',
      '/api/favoritos',
      '/api/borradores',
      '/api/incidencias',
      '/api/comentarios',
      '/api/auth/perfil',
      '/api/auth/validar',
      '/api/auth/test-auth',
      '/api/notificaciones/contador-no-leidas',
      '/api/favoritos/contador',
      '/api/borradores/contador'
    ];
    
    return authUrls.some(authUrl => url.includes(authUrl));
  }
} 