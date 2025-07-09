import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  
  loading = false;
  estadisticas = {
    totalIncidencias: 0,
    pendientes: 0,
    enProceso: 0,
    resueltas: 0
  };

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.cargarEstadisticas();
  }

  cargarEstadisticas(): void {
    this.loading = true;
    // TODO: Implementar carga de estadísticas desde el servicio
    setTimeout(() => {
      this.estadisticas = {
        totalIncidencias: 25,
        pendientes: 8,
        enProceso: 12,
        resueltas: 5
      };
      this.loading = false;
    }, 1000);
  }

  nuevaIncidencia(): void {
    this.router.navigate(['/incidencias/nueva']);
  }

  verIncidencias(): void {
    this.router.navigate(['/incidencias']);
  }

  verIncidenciasPendientes(): void {
    this.router.navigate(['/incidencias'], { queryParams: { estado: 'PENDIENTE' } });
  }
} 