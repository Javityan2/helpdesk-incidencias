# 🎨 Frontend - Helpdesk Incidencias

## 📋 Descripción

Frontend desarrollado en Angular 17 para el sistema de gestión de incidencias corporativas. Proporciona una interfaz moderna y responsiva para la gestión completa de incidencias.

## 🚀 Características

### ✨ Funcionalidades Principales
- **Autenticación JWT**: Sistema de login seguro con tokens
- **Dashboard Interactivo**: Estadísticas y métricas en tiempo real
- **Gestión de Incidencias**: CRUD completo con filtros avanzados
- **Interfaz Responsiva**: Diseño adaptativo para móviles y tablets
- **Tema Material Design**: UI moderna con Angular Material

### 🎯 Componentes Principales
- **Login**: Autenticación de usuarios
- **Dashboard**: Panel de control con estadísticas
- **Lista de Incidencias**: Tabla con filtros y paginación
- **Detalle de Incidencia**: Vista completa con comentarios
- **Formulario de Incidencia**: Crear/editar incidencias
- **Sidebar**: Navegación principal
- **Header**: Barra superior con menú de usuario

## 🛠️ Tecnologías

### Core
- **Angular 17**: Framework principal
- **TypeScript**: Lenguaje de programación
- **RxJS**: Programación reactiva

### UI/UX
- **Angular Material**: Componentes de Material Design
- **Bootstrap 5**: Framework CSS
- **SCSS**: Preprocesador CSS
- **Flexbox/Grid**: Layout moderno

### Utilidades
- **ngx-toastr**: Notificaciones
- **jwt-decode**: Manejo de tokens JWT
- **Chart.js**: Gráficos y estadísticas

## 📦 Instalación

### Prerrequisitos
- Node.js 18+ 
- npm 9+
- Angular CLI 17+

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd helpdesk-incidencias/frontend
```

2. **Instalar dependencias**
```bash
npm install
```

3. **Configurar variables de entorno**
```bash
# Crear archivo environment.ts
cp src/environments/environment.ts.example src/environments/environment.ts
```

4. **Iniciar servidor de desarrollo**
```bash
npm start
```

5. **Abrir en navegador**
```
http://localhost:4200
```

## 🔧 Scripts Disponibles

```bash
# Desarrollo
npm start          # Iniciar servidor de desarrollo
npm run build      # Construir para producción
npm run test       # Ejecutar pruebas
npm run lint       # Linting del código

# Utilidades
npm run build:prod # Build optimizado para producción
npm run serve:prod # Servir build de producción
```

## 🏗️ Estructura del Proyecto

```
src/
├── app/
│   ├── components/
│   │   ├── auth/
│   │   │   └── login/              # Componente de login
│   │   ├── dashboard/              # Dashboard principal
│   │   ├── incidencias/
│   │   │   ├── incidencias-list/   # Lista de incidencias
│   │   │   ├── incidencia-detail/  # Detalle de incidencia
│   │   │   └── incidencia-form/    # Formulario de incidencia
│   │   ├── layout/
│   │   │   ├── sidebar/            # Barra lateral
│   │   │   └── header/             # Barra superior
│   │   └── shared/
│   │       └── loading/            # Componente de carga
│   ├── services/
│   │   ├── auth.service.ts         # Servicio de autenticación
│   │   └── incidencias.service.ts  # Servicio de incidencias
│   ├── guards/
│   │   └── auth.guard.ts           # Guard de autenticación
│   ├── interceptors/
│   │   └── auth.interceptor.ts     # Interceptor JWT
│   ├── app.component.ts            # Componente principal
│   ├── app.module.ts               # Módulo principal
│   └── app-routing.module.ts       # Configuración de rutas
├── assets/                         # Recursos estáticos
├── environments/                   # Configuraciones de entorno
└── styles.scss                     # Estilos globales
```

## 🔐 Autenticación

### Flujo de Login
1. Usuario ingresa credenciales
2. Backend valida y retorna JWT
3. Token se almacena en localStorage
4. Interceptor agrega token a peticiones
5. Guard protege rutas privadas

### Roles de Usuario
- **ADMIN**: Acceso completo al sistema
- **USER**: Crear y ver sus incidencias
- **TECHNICIAN**: Gestionar incidencias asignadas

## 📱 Responsive Design

### Breakpoints
- **Desktop**: > 1200px
- **Tablet**: 768px - 1200px
- **Mobile**: < 768px

### Características
- Sidebar colapsable en móviles
- Tablas con scroll horizontal
- Botones adaptativos
- Tipografía escalable

## 🎨 Temas y Estilos

### Variables CSS
```scss
:root {
  --primary-color: #3f51b5;
  --secondary-color: #ff4081;
  --success-color: #4caf50;
  --warning-color: #ff9800;
  --danger-color: #f44336;
}
```

### Componentes Material
- MatCard: Tarjetas de contenido
- MatTable: Tablas de datos
- MatFormField: Campos de formulario
- MatButton: Botones estilizados
- MatIcon: Iconografía

## 🔄 Estado de la Aplicación

### Servicios Principales
- **AuthService**: Gestión de autenticación
- **IncidenciasService**: CRUD de incidencias
- **ToastrService**: Notificaciones

### Observables
- Estado de autenticación
- Lista de incidencias
- Estadísticas del dashboard

## 🧪 Testing

### Configuración
- **Jasmine**: Framework de pruebas
- **Karma**: Test runner
- **Angular Testing Utilities**

### Ejecutar Pruebas
```bash
npm test                    # Ejecutar todas las pruebas
npm run test:watch         # Modo watch
npm run test:coverage      # Con cobertura
```

## 🚀 Despliegue

### Build de Producción
```bash
npm run build:prod
```

### Configuración de Servidor
- Configurar CORS en backend
- Variables de entorno para API URL
- Optimización de assets

## 📊 Métricas de Calidad

### Cobertura de Código
- **Objetivo**: > 80%
- **Servicios**: > 90%
- **Componentes**: > 85%

### Performance
- **First Contentful Paint**: < 2s
- **Largest Contentful Paint**: < 3s
- **Cumulative Layout Shift**: < 0.1

## 🔧 Configuración Avanzada

### Environment Variables
```typescript
// environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  appName: 'Helpdesk Incidencias'
};
```

### Proxy Configuration
```json
// proxy.conf.json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true
  }
}
```

## 🐛 Troubleshooting

### Problemas Comunes

1. **Error de CORS**
   - Verificar configuración del backend
   - Revisar proxy configuration

2. **Token expirado**
   - Limpiar localStorage
   - Reautenticar usuario

3. **Errores de compilación**
   - Limpiar cache: `npm run clean`
   - Reinstalar dependencias: `rm -rf node_modules && npm install`

## 📝 Contribución

### Guías de Desarrollo
1. Seguir Angular Style Guide
2. Usar TypeScript strict mode
3. Implementar pruebas unitarias
4. Documentar componentes

### Commit Convention
```
feat: nueva funcionalidad
fix: corrección de bug
docs: documentación
style: formato de código
refactor: refactorización
test: pruebas
chore: tareas de mantenimiento
```

---

## ✅ Estado del Proyecto

- [x] Configuración inicial
- [x] Autenticación JWT
- [x] Dashboard básico
- [x] Lista de incidencias
- [x] Componentes de layout
- [ ] Formularios de incidencias
- [ ] Detalle de incidencia
- [ ] Pruebas unitarias
- [ ] Documentación completa

**Estado**: 🟡 En Desarrollo (70% completado)
