# 🎯 DEMO ONLINE - HELPDESK INCIDENCIAS

## 🌐 Acceso a la Demo

### **URLs de la Aplicación**
- **Frontend (Angular):** [URL_PENDIENTE] - Interfaz de usuario
- **Backend API:** [URL_PENDIENTE] - API REST
- **Swagger Documentation:** [URL_PENDIENTE]/swagger-ui.html - Documentación de la API

---

## 🔑 Credenciales de Acceso

### **Usuarios de Demo Disponibles**

| Rol | Email | Contraseña | Descripción |
|-----|-------|------------|-------------|
| **ADMIN** | `admin@helpdesk.com` | `password` | Acceso completo al sistema |
| **TÉCNICO** | `maria.garcia@empresa.com` | `password` | Gestión de incidencias y soporte |
| **SUPERVISOR** | `carlos.lopez@empresa.com` | `password` | Supervisión y reportes |
| **USUARIO 1** | `ana.martinez@empresa.com` | `password` | Crear y gestionar propias incidencias |
| **USUARIO 2** | `luis.rodriguez@empresa.com` | `password` | Crear y gestionar propias incidencias |

---

## 🚀 Funcionalidades Disponibles para Probar

### **1. Autenticación y Roles**
- ✅ **Login seguro** con email y contraseña
- ✅ **Control de acceso** basado en roles (ADMIN, TECNICO, SUPERVISOR, USUARIO)
- ✅ **Protección de rutas** según permisos del usuario
- ✅ **Logout automático** al expirar el token JWT

### **2. Gestión de Incidencias**
- ✅ **Crear incidencias** con título, descripción, prioridad y categoría
- ✅ **Editar incidencias** existentes
- ✅ **Cambiar estado** (ABIERTA, EN_PROCESO, CERRADA)
- ✅ **Asignar técnicos** a incidencias
- ✅ **Filtros avanzados** por estado, prioridad, categoría, fecha
- ✅ **Búsqueda** por título o descripción
- ✅ **Paginación** de resultados

### **3. Sistema de Comentarios**
- ✅ **Comentarios externos** (visibles para todos)
- ✅ **Comentarios internos** (solo técnicos y admin)
- ✅ **Historial completo** de comentarios por incidencia
- ✅ **Notificaciones** cuando se agregan comentarios

### **4. Notificaciones**
- ✅ **Notificaciones en tiempo real** para:
  - Nuevas incidencias asignadas
  - Comentarios en incidencias
  - Cambios de estado
  - Aprobaciones de solicitudes
- ✅ **Marcar como leída** las notificaciones
- ✅ **Contador de no leídas** en tiempo real

### **5. Favoritos y Borradores**
- ✅ **Agregar incidencias a favoritos** para seguimiento rápido
- ✅ **Contador de favoritos** en tiempo real
- ✅ **Guardar borradores** de incidencias en proceso
- ✅ **Recuperar borradores** para completar incidencias

### **6. Herramientas de Análisis Visual**
- ✅ **Diagrama de Flujo** del proceso de resolución
- ✅ **Análisis Ishikawa** (Diagrama de Espina de Pescado)
- ✅ **Lluvia de Ideas** para soluciones
- ✅ **Análisis de los 5 Porqués** para causas raíz
- ✅ **Análisis de Pareto** para priorización

### **7. Dashboard y Estadísticas**
- ✅ **Estadísticas generales** del sistema
- ✅ **Gráficos de incidencias** por estado y prioridad
- ✅ **Métricas de rendimiento** de técnicos
- ✅ **Tendencias temporales** de incidencias

---

## 📋 Guía de Pruebas Recomendadas

### **Paso 1: Explorar con Usuario Regular**
1. **Inicia sesión** con `ana.martinez@empresa.com` / `password`
2. **Crea una nueva incidencia** con alta prioridad
3. **Explora el dashboard** y las estadísticas
4. **Prueba los filtros** en la lista de incidencias
5. **Agrega comentarios** a tus incidencias

### **Paso 2: Probar Rol de Técnico**
1. **Inicia sesión** con `maria.garcia@empresa.com` / `password`
2. **Revisa incidencias asignadas** en el dashboard
3. **Cambia el estado** de una incidencia a "EN_PROCESO"
4. **Agrega comentarios internos** (solo visibles para técnicos)
5. **Prueba las herramientas de análisis visual** en una incidencia

### **Paso 3: Explorar Funcionalidades de Admin**
1. **Inicia sesión** con `admin@helpdesk.com` / `password`
2. **Revisa todas las incidencias** del sistema
3. **Asigna técnicos** a incidencias sin asignar
4. **Explora las notificaciones** del sistema
5. **Prueba todas las herramientas de análisis**

### **Paso 4: Probar Herramientas Avanzadas**
1. **Selecciona una incidencia** con problemas complejos
2. **Genera un diagrama de flujo** del proceso de resolución
3. **Crea un análisis Ishikawa** para identificar causas
4. **Realiza una lluvia de ideas** para soluciones
5. **Aplica el análisis de los 5 Porqués**
6. **Visualiza el análisis de Pareto** para priorización

---

## 🎨 Características de la Interfaz

### **Diseño Moderno y Responsivo**
- ✅ **Interfaz profesional** con gradientes y glassmorphism
- ✅ **Diseño responsive** para desktop, tablet y móvil
- ✅ **Animaciones suaves** y transiciones
- ✅ **Iconografía clara** con FontAwesome
- ✅ **Tema consistente** en toda la aplicación

### **Experiencia de Usuario**
- ✅ **Navegación intuitiva** entre secciones
- ✅ **Feedback visual** para todas las acciones
- ✅ **Estados de carga** apropiados
- ✅ **Manejo de errores** con mensajes claros
- ✅ **Accesibilidad** mejorada

---

## 🔧 Datos de Ejemplo Incluidos

### **Incidencias de Demo**
- **11 incidencias** con diferentes estados y prioridades
- **Problemas realistas** (WiFi, nómina, hardware, etc.)
- **Fechas variadas** para mostrar historial
- **Asignaciones** a diferentes técnicos

### **Comentarios de Demo**
- **Comentarios externos** para comunicación con usuarios
- **Comentarios internos** para coordinación técnica
- **Historial temporal** de conversaciones

### **Notificaciones de Demo**
- **6 notificaciones** de diferentes tipos
- **Algunas leídas** y otras sin leer
- **Variedad de mensajes** para mostrar funcionalidad

### **Favoritos y Borradores**
- **6 favoritos** de diferentes usuarios
- **3 borradores** en proceso de creación

---

## 📊 Métricas de la Demo

| Métrica | Cantidad |
|---------|----------|
| **Usuarios** | 5 (diferentes roles) |
| **Incidencias** | 11 (varios estados) |
| **Comentarios** | 8 (internos y externos) |
| **Notificaciones** | 6 (diferentes tipos) |
| **Favoritos** | 6 |
| **Borradores** | 3 |

---

## 🚨 Notas Importantes

### **Para Reclutadores y Evaluadores**
- **Esta es una demo funcional** de un sistema real
- **Todas las funcionalidades** están implementadas y operativas
- **El código está disponible** en el repositorio GitHub
- **La arquitectura** es escalable y mantenible

### **Para Desarrolladores**
- **Backend:** Spring Boot con arquitectura hexagonal
- **Frontend:** Angular 17 con TypeScript
- **Base de datos:** MySQL con JPA/Hibernate
- **Seguridad:** JWT con Spring Security
- **Herramientas visuales:** Mermaid.js y Chart.js

---

## 📞 Soporte y Contacto

### **Información del Desarrollador**
- **Nombre:** Francisco Javier Sánchez López
- **Email:** franciscoj.sanchezl@potoros.itson.edu.mx
- **GitHub:** [Javityan2](https://github.com/Javityan2)
- **LinkedIn:** [Francisco Javier Sánchez López](https://www.linkedin.com/in/francisco-javier-sanchez-lopez-881a02357/)

### **En caso de problemas**
- Revisar la consola del navegador (F12)
- Verificar la conexión a internet
- Contactar al desarrollador con capturas de pantalla del error

---

**¡Disfruta explorando el sistema de gestión de incidencias!** 🎉 