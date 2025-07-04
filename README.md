# Helpdesk de Incidencias

Sistema de gestión de incidencias corporativas desarrollado con Java 17 (Spring Boot) y Angular.

## 🚀 Tecnologías Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL/MySQL**
- **Maven**

### Frontend
- **Angular 17**
- **TypeScript**
- **Angular Material**
- **RxJS**

## 📁 Estructura del Proyecto

```
helpdesk-incidencias/
├── backend/                 # API REST con Spring Boot
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── frontend/               # Aplicación Angular
│   ├── src/
│   ├── package.json
│   ├── angular.json
│   └── README.md
├── docs/                   # Documentación del proyecto
├── .gitignore
├── LICENSE
└── README.md
```

## 🛠️ Instalación y Configuración

### Prerrequisitos
- Java 17 o superior
- Node.js 18 o superior
- Angular CLI
- Maven
- Base de datos (PostgreSQL/MySQL)

### Backend

1. Navega al directorio del backend:
```bash
cd backend
```

2. Instala las dependencias:
```bash
mvn clean install
```

3. Configura la base de datos en `application.properties`

4. Ejecuta la aplicación:
```bash
mvn spring-boot:run
```

### Frontend

1. Navega al directorio del frontend:
```bash
cd frontend
```

2. Instala las dependencias:
```bash
npm install
```

3. Ejecuta la aplicación en modo desarrollo:
```bash
ng serve
```

La aplicación estará disponible en `http://localhost:4200`

## 📋 Funcionalidades

- [ ] Gestión de usuarios y roles
- [ ] Creación y seguimiento de incidencias
- [ ] Sistema de tickets
- [ ] Dashboard de estadísticas
- [ ] Notificaciones en tiempo real
- [ ] Reportes y exportación
- [ ] API REST documentada

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👨‍💻 Autor

**Javityan2**

## 📞 Contacto

- GitHub: [@Javityan2](https://github.com/Javityan2)

---

⭐ Si este proyecto te ha sido útil, ¡dale una estrella!
