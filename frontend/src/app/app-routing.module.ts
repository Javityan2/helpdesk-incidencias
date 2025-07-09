import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';

// Componentes
import { LoginComponent } from './components/auth/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { IncidenciasListComponent } from './components/incidencias/incidencias-list/incidencias-list.component';
import { IncidenciaDetailComponent } from './components/incidencias/incidencia-detail/incidencia-detail.component';
import { IncidenciaFormComponent } from './components/incidencias/incidencia-form/incidencia-form.component';
import { UserProfileComponent } from './components/user/user-profile.component';
import { UserFavoritesComponent } from './components/user/user-favorites.component';
import { UserDraftsComponent } from './components/user/user-drafts.component';

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
  
  // Nuevas rutas para funcionalidades del usuario
  { 
    path: 'perfil', 
    component: UserProfileComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'mis-incidencias', 
    component: IncidenciasListComponent, // Usar el mismo componente pero con filtro
    canActivate: [AuthGuard]
  },
  { 
    path: 'favoritos', 
    component: UserFavoritesComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'borradores', 
    component: UserDraftsComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'configuracion', 
    component: DashboardComponent, // Temporalmente redirige al dashboard
    canActivate: [AuthGuard]
  },
  
  // Rutas administrativas (solo para roles específicos)
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
  
  // Ruta catch-all
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { } 