# 🚀 Helpdesk Incidencias - Sistema Completo

## 📋 Descripción

Sistema web corporativo para la gestión colaborativa de incidencias internas con análisis visual y seguimiento estructurado. Desarrollado con arquitectura moderna full-stack.

## 🏗️ Arquitectura

### Backend (Spring Boot)
- **Java 17** con Spring Boot 3.2.0
- **Arquitectura Hexagonal** (Clean Architecture)
- **Base de datos MySQL** con JPA/Hibernate
- **Seguridad JWT** con Spring Security
- **API REST** documentada con Swagger/OpenAPI
- **Pruebas unitarias** con JUnit 5 y Mockito

### Frontend (Angular 17)
- **Angular 17** con TypeScript
- **Angular Material** para UI moderna
- **Bootstrap 5** para responsive design
- **RxJS** para programación reactiva
- **JWT Authentication** con interceptors
- **Componentes modulares** y reutilizables

## 🚀 Inicio Rápido

### Opción 1: Script Automático (Recomendado)
```bash
# Windows
start-full-stack.bat

# Linux/Mac
./start-full-stack.sh
```

### Opción 2: Manual

#### 1. Backend
```bash
cd backend
mvn spring-boot:run
```

#### 2. Frontend
```bash
cd frontend
npm install
npm start
```

### 3. Acceder a la Aplicación
- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8080
- **Documentación API**: http://localhost:8080/swagger-ui.html

## 📊 Características Principales

### 🔐 Autenticación y Autorización
- **Login seguro** con JWT
- **Roles de usuario**: ADMIN, USER, TECHNICIAN
- **Protección de rutas** con guards
- **Interceptores** para manejo automático de tokens

### 📋 Gestión de Incidencias
- **CRUD completo** de incidencias
- **Estados**: Abierta, En Proceso, Resuelta, Cerrada
- **Prioridades**: Alta, Media, Baja
- **Categorías**: Hardware, Software, Red, Otros
- **Asignación** a técnicos
- **Comentarios** y seguimiento

### 📈 Dashboard y Analytics
- **Estadísticas en tiempo real**
- **Gráficos de distribución** por estado y prioridad
- **Métricas de rendimiento**
- **Filtros avanzados**

### 🎨 Interfaz de Usuario
- **Diseño responsivo** para móviles y tablets
- **Tema Material Design** moderno
- **Navegación intuitiva** con sidebar
- **Notificaciones** con toastr
- **Loading states** y feedback visual

## 🛠️ Tecnologías Utilizadas

### Backend Stack
```
Java 17
├── Spring Boot 3.2.0
├── Spring Security
├── Spring Data JPA
├── MySQL 8.0
├── JWT (jjwt 0.12.3)
├── Swagger/OpenAPI
├── JUnit 5 + Mockito
└── JaCoCo (Cobertura)
```

### Frontend Stack
```
Angular 17
├── TypeScript
├── Angular Material
├── Bootstrap 5
├── RxJS
├── ngx-toastr
├── jwt-decode
└── Chart.js
```

## 📁 Estructura del Proyecto

```
helpdesk-incidencias/
├── backend/                    # API REST (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/helpdesk/incidencias/
│   │   │   │   ├── domain/           # Entidades y repositorios
│   │   │   │   ├── application/      # Servicios y DTOs
│   │   │   │   └── infrastructure/   # Controladores y config
│   │   │   └── resources/
│   │   └── test/                     # Pruebas unitarias
│   ├── pom.xml
│   └── TESTING.md
├── frontend/                   # Aplicación Angular
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/           # Componentes UI
│   │   │   ├── services/             # Servicios HTTP
│   │   │   ├── guards/               # Guards de autenticación
│   │   │   └── interceptors/         # Interceptores HTTP
│   │   ├── environments/             # Configuraciones
│   │   └── assets/                   # Recursos estáticos
│   ├── package.json
│   └── README.md
├── docs/                       # Documentación
├── scripts/                    # Scripts de automatización
└── README.md                   # Este archivo
```

## 🔧 Configuración

### Base de Datos
```sql
-- Crear base de datos
CREATE DATABASE helpdesk_incidencias;
```

### Variables de Entorno
```properties
# Backend (application.properties)
spring.datasource.url=jdbc:mysql://localhost:3306/helpdesk_incidencias
spring.datasource.username=root
spring.datasource.password=password
jwt.secret=your-secret-key
jwt.expiration=3600000

# Frontend (environment.ts)
apiUrl: 'http://localhost:8080/api'
```

## 🧪 Pruebas

### Backend
```bash
cd backend
mvn test                    # Ejecutar todas las pruebas
mvn jacoco:report          # Generar reporte de cobertura
```

### Frontend
```bash
cd frontend
npm test                    # Ejecutar pruebas unitarias
npm run test:coverage      # Con cobertura
```

## 📊 Métricas de Calidad

### Backend
- **Cobertura de código**: > 80%
- **Pruebas unitarias**: 100% de servicios críticos
- **Pruebas de integración**: Controladores principales
- **Documentación API**: Swagger completo

### Frontend
- **Cobertura de código**: > 70%
- **Componentes testeados**: Principales
- **Performance**: Lighthouse > 90
- **Accesibilidad**: WCAG 2.1 AA

## 🚀 Despliegue

### Backend (Producción)
```bash
cd backend
mvn clean package
java -jar target/helpdesk-incidencias-1.0.0.jar
```

### Frontend (Producción)
```bash
cd frontend
npm run build:prod
# Servir archivos de dist/helpdesk-incidencias/
```

## 🔐 Seguridad

### Implementada
- ✅ **JWT Authentication**
- ✅ **CORS Configuration**
- ✅ **Password Encryption** (BCrypt)
- ✅ **Role-based Access Control**
- ✅ **Input Validation**
- ✅ **SQL Injection Protection**

### Recomendaciones para Producción
- 🔒 **HTTPS** obligatorio
- 🔒 **Rate Limiting**
- 🔒 **Audit Logging**
- 🔒 **Environment Variables**
- 🔒 **Database Backup**

## 📈 Roadmap

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

## 🤝 Contribución

### Guías de Desarrollo
1. **Fork** el repositorio
2. **Crear** rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. **Commit** cambios (`git commit -am 'feat: nueva funcionalidad'`)
4. **Push** a la rama (`git push origin feature/nueva-funcionalidad`)
5. **Crear** Pull Request

### Estándares de Código
- **Backend**: Java Code Style Guide
- **Frontend**: Angular Style Guide
- **Commits**: Conventional Commits
- **Documentación**: Javadoc + JSDoc

## 📞 Soporte

### Contacto
- **Email**: soporte@helpdesk-incidencias.com
- **Documentación**: [Wiki del proyecto]
- **Issues**: [GitHub Issues]

### Comunidad
- **Discord**: [Canal de desarrollo]
- **Stack Overflow**: [Tag: helpdesk-incidencias]
- **Blog**: [Artículos técnicos]

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

## 🙏 Agradecimientos

- **Spring Team** por el excelente framework
- **Angular Team** por la plataforma web moderna
- **Material Design** por las guías de diseño
- **Comunidad open source** por las librerías utilizadas

---

**Estado del Proyecto**: 🟢 Activo y en desarrollo

**Última Actualización**: Diciembre 2024

**Versión**: 1.0.0
