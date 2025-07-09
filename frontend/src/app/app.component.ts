import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  
  isAuthenticated = false;
  currentUser: any = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    console.log('AppComponent - ngOnInit iniciado');
    
    // Suscribirse a cambios en la autenticación
    this.authService.currentUser.subscribe(
      user => {
        console.log('AppComponent - Cambio en currentUser:', user);
        this.currentUser = user;
        this.isAuthenticated = !!user;
        console.log('AppComponent - isAuthenticated actualizado:', this.isAuthenticated);
      }
    );

    // Verificar estado inicial
    this.isAuthenticated = this.authService.isAuthenticated();
    this.currentUser = this.authService.getCurrentUser();
    console.log('AppComponent - Estado inicial - isAuthenticated:', this.isAuthenticated);
    console.log('AppComponent - Estado inicial - currentUser:', this.currentUser);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
} 