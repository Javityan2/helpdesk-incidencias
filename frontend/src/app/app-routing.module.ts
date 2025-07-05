import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';

// Componentes
import { LoginComponent } from './components/auth/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { IncidenciasListComponent } from './components/incidencias/incidencias-list/incidencias-list.component';
import { IncidenciaDetailComponent } from './components/incidencias/incidencia-detail/incidencia-detail.component';
import { IncidenciaFormComponent } from './components/incidencias/incidencia-form/incidencia-form.component';

const routes: Routes = [
  // Rutas públicas
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  
  // Rutas protegidas
  { 
    path: 'dashboard', 
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'incidencias', 
    component: IncidenciasListComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'incidencias/nueva', 
    component: IncidenciaFormComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'incidencias/:id', 
    component: IncidenciaDetailComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'incidencias/:id/editar', 
    component: IncidenciaFormComponent,
    canActivate: [AuthGuard]
  },
  
  // Rutas adicionales (placeholder para futuras implementaciones)
  { 
    path: 'usuarios', 
    component: DashboardComponent, // Temporalmente redirige al dashboard
    canActivate: [AuthGuard]
  },
  { 
    path: 'reportes', 
    component: DashboardComponent, // Temporalmente redirige al dashboard
    canActivate: [AuthGuard]
  },
  { 
    path: 'configuracion', 
    component: DashboardComponent, // Temporalmente redirige al dashboard
    canActivate: [AuthGuard]
  },
  
  // Ruta catch-all
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { } 