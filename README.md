# 🎯 Helpdesk Incidencias

Sistema completo de gestión de incidencias corporativas con autenticación JWT, backend en Spring Boot y frontend en Angular.

**Desarrollado por:** Francisco Javier Sánchez López  
**Fecha:** Junio 2025  
**Tipo:** Proyecto personal para portafolio

## 🚀 Características

### ✅ Backend (Spring Boot + Java 21)
- **Autenticación JWT** con login por ID de empleado o email
- **Base de datos MySQL** con entidades optimizadas
- **API REST completa** con controladores para auth e incidencias
- **Seguridad configurada** con Spring Security
- **Ordenamiento inteligente** por prioridad y frecuencia de búsqueda
- **Datos de prueba** incluidos

### ✅ Frontend (Angular 17)
- **Arquitectura modular** con servicios, guards, interceptors
- **Autenticación completa** con JWT automático
- **Interfaz moderna** usando Angular + Bootstrap + Angular Material
- **Componentes principales:**
  - Login con validación
  - Dashboard con estadísticas
  - Lista de incidencias con filtros
  - Detalle de incidencia con pestañas
- **Guards de protección** de rutas
- **Interceptor HTTP** para manejo automático de tokens

### 🔐 Seguridad
- **Login seguro** por ID de empleado o email
- **Tokens JWT** con expiración automática
- **Protección de rutas** con AuthGuard
- **Logout automático** al expirar token
- **Roles de usuario** (ADMIN, TECNICO, SUPERVISOR, USUARIO)

### 📊 Gestión de Incidencias
- **CRUD completo** de incidencias
- **Filtros avanzados** por estado, prioridad, categoría
- **Ordenamiento inteligente** por prioridad y frecuencia
- **Paginación** y búsqueda
- **Estadísticas** en dashboard
- **Frecuencia de búsqueda** automática

## ✨ Novedades Visuales y de Experiencia de Usuario (Junio 2025)

- Rediseño completo de la pantalla de login, carga, lista y formulario de incidencias.
- Interfaz moderna, profesional y 100% responsiva en todos los componentes principales.
- Animaciones suaves, gradientes, glassmorphism y uso de iconos FontAwesome.
- Mejoras de accesibilidad, usabilidad y experiencia de usuario.
- Diseño adaptado para desktop, tablet y móvil.

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 21**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **MySQL 8.0**
- **JWT**
- **Maven**

### Frontend
- **Angular 17**
- **TypeScript**
- **Bootstrap 5**
- **Angular Material**
- **RxJS**
- **FontAwesome**

## 📋 Prerrequisitos

- **Java 21** o superior
- **Node.js 18** o superior
- **MySQL 8.0** o superior
- **Maven 3.6** o superior

## 🚀 Instalación

### 1. Clonar el repositorio
```bash
git clone https://github.com/Javityan2/helpdesk-incidencias.git
cd helpdesk-incidencias
```

### 2. Configurar Base de Datos
```sql
CREATE DATABASE helpdesk_incidencias;
```

### 3. Configurar Backend
```bash
cd backend
# Editar application.properties con tus credenciales de BD
mvn clean install
mvn spring-boot:run
```

### 4. Configurar Frontend
```bash
cd frontend
npm install
npm start
```

## 🔑 Usuarios de Prueba

| Usuario | ID Empleado | Email | Contraseña | Rol |
|---------|-------------|-------|------------|-----|
| Juan Pérez | EMP001 | juan.perez@empresa.com | password | ADMIN |
| María García | EMP002 | maria.garcia@empresa.com | password | TECNICO |
| Carlos López | EMP003 | carlos.lopez@empresa.com | password | SUPERVISOR |
| Ana Martínez | EMP004 | ana.martinez@empresa.com | password | USUARIO |
| Luis Rodríguez | EMP005 | luis.rodriguez@empresa.com | password | USUARIO |

## 🌐 Acceso

- **Frontend:** http://localhost:4200
- **Backend API:** http://localhost:8080/api
- **Base de Datos:** localhost:3306/helpdesk_incidencias

## 📁 Estructura del Proyecto

```
helpdesk-incidencias/
├── backend/                 # Spring Boot Backend
│   ├── src/main/java/
│   │   ├── com/helpdesk/incidencias/
│   │   │   ├── domain/      # Entidades y lógica de negocio
│   │   │   ├── infrastructure/ # Repositorios y configuración
│   │   │   └── application/ # Servicios y controladores
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql     # Datos de prueba
│   └── pom.xml
├── frontend/                # Angular Frontend
│   ├── src/app/
│   │   ├── components/      # Componentes Angular
│   │   ├── services/        # Servicios HTTP
│   │   ├── guards/          # Guards de autenticación
│   │   ├── interceptors/    # Interceptores HTTP
│   │   └── models/          # Interfaces TypeScript
│   ├── src/environments/    # Configuración de entornos
│   └── package.json
└── README.md
```

## 🔧 Configuración

### Backend (application.properties)
```properties
# Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/helpdesk_incidencias
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# JWT
jwt.secret=tu_secret_key_muy_segura
jwt.expiration=86400000

# Server
server.port=8080
```

### Frontend (environment.ts)
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

## 🧪 Pruebas

### Backend
```bash
cd backend
mvn test
```

### Frontend
```bash
cd frontend
npm test
```

## 📦 Despliegue

### Backend (JAR)
```bash
cd backend
mvn clean package
java -jar target/helpdesk-incidencias-1.0.0.jar
```

### Frontend (Build)
```bash
cd frontend
npm run build
# Los archivos se generan en dist/
```

## 🗺️ Roadmap

### Versión 1.1 (Próxima)
- [ ] **Notificaciones push** en tiempo real
- [ ] **Adjuntos de archivos** en incidencias
- [ ] **Reportes avanzados** con gráficos
- [ ] **Dashboard personalizable**
- [ ] **Exportación a PDF/Excel**

### Versión 1.2
- [ ] **API móvil** para apps nativas
- [ ] **Integración con LDAP/Active Directory**
- [ ] **Workflow de aprobación**
- [ ] **SLA y métricas de tiempo**
- [ ] **Integración con sistemas externos**

### Versión 2.0
- [ ] **Microservicios** con Spring Cloud
- [ ] **Docker y Kubernetes**
- [ ] **CI/CD Pipeline**
- [ ] **Monitoreo con Prometheus/Grafana**
- [ ] **Tests automatizados**

## 🚀 Características Futuras

### Funcionalidades Avanzadas
- **Chat en tiempo real** entre técnicos y usuarios
- **Sistema de tickets** con priorización automática
- **Módulo de inventario** de equipos y software
- **Sistema de backup** automático
- **Integración con calendario** para programar mantenimientos

### Mejoras de UX/UI
- **Tema oscuro/claro** personalizable
- **Responsive design** mejorado para móviles
- **Accesibilidad** WCAG 2.1 AA
- **Animaciones** y transiciones suaves
- **PWA** (Progressive Web App)

### Análisis y Reportes
- **Dashboard ejecutivo** con KPIs
- **Análisis predictivo** de incidencias
- **Reportes personalizados** por departamento
- **Métricas de rendimiento** de técnicos
- **Análisis de tendencias** temporales

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 👥 Autor

- **Francisco Javier Sánchez López** - *Desarrollo completo* - [Javityan2](https://github.com/Javityan2)
- **Email:** franciscoj.sanchezl@potoros.itson.edu.mx
- **LinkedIn:** [Francisco Javier Sánchez López](https://www.linkedin.com/in/francisco-javier-sanchez-lopez-881a02357/)

## 💡 Motivación del Proyecto

He desarrollado este sistema de gestión de incidencias con el propósito de aprender todas las tecnologías que estoy utilizando en él. Quiero ser un desarrollador completo y que mis empleadores sepan que soy capaz de aprender cualquier cosa para dar solución a cualquier problema que los clientes enfrenten.

Aunque conocía previamente Spring Boot y Angular, es la primera vez que las utilizo para crear algo completo y funcional. Este proyecto representa mi compromiso con el aprendizaje continuo y la excelencia técnica.

## 🙏 Agradecimientos

- **Miguel Moroyoqui** - Mi profesor de la universidad (ITSON) por ser una fuente de inspiración en el aprendizaje constante de este bello mundo del desarrollo de software
- Spring Boot Team
- Angular Team
- Bootstrap Team
- Comunidad de desarrolladores

---

**¡Disfruta usando el sistema de gestión de incidencias!** 🎉
