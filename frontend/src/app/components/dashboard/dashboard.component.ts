import { Component, OnInit } from '@angular/core';
import { IncidenciasService, IncidenciaStats } from '../../services/incidencias.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  stats: IncidenciaStats | null = null;
  loading = true;
  currentUser: any = null;

  constructor(
    private incidenciasService: IncidenciasService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.loadStats();
  }

  loadStats(): void {
    this.loading = true;
    this.incidenciasService.getIncidenciasStats().subscribe({
      next: (response) => {
        if (response.success) {
          this.stats = response.data;
        }
      },
      error: (error) => {
        console.error('Error cargando estadísticas:', error);
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  getProgressPercentage(current: number, total: number): number {
    if (total === 0) return 0;
    return Math.round((current / total) * 100);
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'abiertas':
        return '#ff9800';
      case 'enProceso':
        return '#2196f3';
      case 'resueltas':
        return '#4caf50';
      case 'cerradas':
        return '#9e9e9e';
      default:
        return '#666';
    }
  }

  getPriorityColor(priority: string): string {
    switch (priority) {
      case 'alta':
        return '#f44336';
      case 'media':
        return '#ff9800';
      case 'baja':
        return '#4caf50';
      default:
        return '#666';
    }
  }
} 