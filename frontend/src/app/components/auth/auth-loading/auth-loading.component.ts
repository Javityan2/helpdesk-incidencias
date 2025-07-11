import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { trigger, state, style, transition, animate, keyframes } from '@angular/animations';
import { Subscription, interval } from 'rxjs';

@Component({
  selector: 'app-auth-loading',
  templateUrl: './auth-loading.component.html',
  styleUrls: ['./auth-loading.component.scss'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0, transform: 'scale(0.9)' }),
        animate('500ms ease-out', style({ opacity: 1, transform: 'scale(1)' }))
      ]),
      transition(':leave', [
        animate('300ms ease-in', style({ opacity: 0, transform: 'scale(0.9)' }))
      ])
    ]),
    trigger('slideInUp', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(20px)' }),
        animate('400ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ]),
    trigger('progressComplete', [
      transition('* => completed', [
        animate('300ms ease-out', keyframes([
          style({ transform: 'scale(0.8)', offset: 0 }),
          style({ transform: 'scale(1.2)', offset: 0.5 }),
          style({ transform: 'scale(1)', offset: 1 })
        ]))
      ])
    ])
  ]
})
export class AuthLoadingComponent implements OnInit, OnDestroy {
  
  step = 0;
  private subscription: Subscription | null = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.simulateAuthProgress();
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  private simulateAuthProgress(): void {
    // Simular el progreso de autenticación
    this.subscription = interval(800).subscribe(() => {
      this.step++;
      
      if (this.step >= 4) {
        // Cuando se completa el progreso, navegar al dashboard
        setTimeout(() => {
          this.router.navigate(['/dashboard']);
        }, 1000);
      }
    });
  }
} 