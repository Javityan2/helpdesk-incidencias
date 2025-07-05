# 📚 Documentación Helpdesk Incidencias

## 🎯 Descripción del Sistema

El sistema Helpdesk Incidencias es una aplicación web corporativa para la gestión colaborativa de incidencias internas. Permite a los empleados reportar problemas, analizar causas raíz y seguir un proceso estructurado de resolución.

## 🏗️ Arquitectura del Sistema

### Backend (Spring Boot)
- **Arquitectura Hexagonal**: Separación clara entre dominio, aplicación e infraestructura
- **Base de Datos**: MySQL con JPA/Hibernate
- **Seguridad**: JWT con Spring Security
- **API REST**: Documentada con Swagger/OpenAPI

### Frontend (Angular)
- **Framework**: Angular 17 con TypeScript
- **UI**: Angular Material + Bootstrap 5
- **Estado**: RxJS para programación reactiva
- **Autenticación**: JWT con interceptores

## 📊 Funcionalidades Principales

### 🔐 Autenticación
- Login con ID de empleado o email
- JWT para sesiones seguras
- Roles: ADMIN, USER, TECHNICIAN

### 📋 Gestión de Incidencias
- **CRUD completo** de incidencias
- **Estados**: Abierta, En Proceso, Resuelta, Cerrada
- **Prioridades**: Alta, Media, Baja
- **Categorías**: Hardware, Software, Red, Otros

### 🎨 Análisis Visual
- **Diagramas de flujo** para procesos de resolución
- **Análisis de causa-efecto** (Ishikawa)
- **5 porqués** para análisis de raíz
- **Análisis de Pareto** para priorización

### 📈 Dashboard
- Estadísticas en tiempo real
- Gráficos de distribución
- Métricas de rendimiento

## 🚀 Guía de Instalación

### Prerrequisitos
- Java 23 (JDK)
- Node.js 18+
- MySQL 8.0+
- Maven 3.8+

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone [URL_DEL_REPO]
cd helpdesk-incidencias
```

2. **Configurar Base de Datos**
```bash
# Ejecutar en MySQL Workbench
source database/setup.sql
```

3. **Configurar Backend**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

4. **Configurar Frontend**
```bash
cd frontend
npm install
npm start
```

5. **Acceder a la Aplicación**
- Frontend: http://localhost:4200
- Backend API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

## 🔧 Configuración

### Variables de Entorno
```properties
# Backend (application.properties)
spring.datasource.url=jdbc:mysql://localhost:3306/helpdesk_incidencias
spring.datasource.username=root
spring.datasource.password=1234
jwt.secret=helpdeskIncidenciasSecretKey2025
jwt.expiration=86400000

# Frontend (environment.ts)
apiUrl: 'http://localhost:8080/api'
```

## 🧪 Pruebas

### Backend
```bash
cd backend
mvn test                    # Pruebas unitarias
mvn jacoco:report          # Cobertura de código
```

### Frontend
```bash
cd frontend
npm test                    # Pruebas unitarias
npm run test:coverage      # Con cobertura
```

## 📁 Estructura del Proyecto

```
helpdesk-incidencias/
├── backend/                    # API REST (Spring Boot)
│   ├── src/main/java/com/helpdesk/incidencias/
│   │   ├── domain/            # Entidades y repositorios
│   │   ├── application/       # Servicios y DTOs
│   │   └── infrastructure/    # Controladores y config
│   └── src/main/resources/    # Configuración
├── frontend/                   # Aplicación Angular
│   ├── src/app/
│   │   ├── components/        # Componentes UI
│   │   ├── services/          # Servicios HTTP
│   │   └── guards/            # Guards de autenticación
│   └── src/assets/            # Recursos estáticos
├── database/                   # Scripts de base de datos
├── docs/                       # Documentación
└── postman/                    # Colecciones de API
```

## 🔐 Seguridad

### Implementada
- ✅ JWT Authentication
- ✅ CORS Configuration
- ✅ Password Encryption (BCrypt)
- ✅ Role-based Access Control
- ✅ Input Validation

### Recomendaciones para Producción
- 🔒 HTTPS obligatorio
- 🔒 Rate Limiting
- 🔒 Audit Logging
- 🔒 Environment Variables
- 🔒 Database Backup

## 📈 Roadmap

### Versión 1.1 (Próxima)
- [ ] Notificaciones push en tiempo real
- [ ] Adjuntos de archivos en incidencias
- [ ] Reportes avanzados con gráficos
- [ ] Dashboard personalizable
- [ ] Exportación a PDF/Excel

### Versión 1.2
- [ ] API móvil para apps nativas
- [ ] Integración con LDAP/Active Directory
- [ ] Workflow de aprobación
- [ ] SLA y métricas de tiempo
- [ ] Integración con sistemas externos

## 🤝 Contribución

### Guías de Desarrollo
1. Fork el repositorio
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'feat: nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

### Estándares de Código
- **Backend**: Java naming conventions
- **Frontend**: Angular style guide
- **Commits**: Conventional commits
- **Documentación**: Javadoc + JSDoc

## 📞 Soporte

Para soporte técnico o preguntas sobre el proyecto:
- Crear un issue en GitHub
- Contactar al equipo de desarrollo
- Revisar la documentación de la API en Swagger 