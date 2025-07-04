# Configuración MySQL con XAMPP - Helpdesk Incidencias

## 🚀 Instalación y Configuración con XAMPP

### Paso 1: Instalar XAMPP

1. **Descargar XAMPP**:
   - Ve a [https://www.apachefriends.org/](https://www.apachefriends.org/)
   - Descarga la versión para Windows
   - Instala XAMPP (recomendado: `C:\xampp`)

2. **Iniciar XAMPP**:
   - Abre XAMPP Control Panel
   - Haz clic en "Start" para **MySQL**
   - Verifica que el puerto 3306 esté disponible

### Paso 2: Configurar MySQL

1. **Acceder a phpMyAdmin**:
   - Abre tu navegador
   - Ve a `http://localhost/phpmyadmin`
   - Usuario: `root`
   - Contraseña: (vacía por defecto)

2. **Crear la base de datos**:
   ```sql
   CREATE DATABASE helpdesk_incidencias
   CHARACTER SET utf8mb4
   COLLATE utf8mb4_unicode_ci;
   ```

3. **Crear usuario de desarrollo**:
   ```sql
   CREATE USER 'helpdesk_user'@'localhost' IDENTIFIED BY 'helpdesk_password';
   GRANT ALL PRIVILEGES ON helpdesk_incidencias.* TO 'helpdesk_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

### Paso 3: Configurar MySQL Workbench

1. **Crear nueva conexión**:
   - Hostname: `localhost`
   - Port: `3306`
   - Username: `root`
   - Password: (vacía)
   - Connection Name: `Helpdesk XAMPP`

2. **Probar conexión**

### Paso 4: Actualizar application.properties

```properties
# Configuración para XAMPP
spring.datasource.url=jdbc:mysql://localhost:3306/helpdesk_incidencias?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=
```

### Paso 5: Verificar Configuración

1. **Ejecutar el backend**:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

2. **Verificar en phpMyAdmin**:
   - Ve a `http://localhost/phpmyadmin`
   - Selecciona la base de datos `helpdesk_incidencias`
   - Verifica que las tablas se crearon
   - Verifica que los datos de prueba se cargaron

## 🔧 Troubleshooting XAMPP

### Problema: "MySQL no inicia"
**Solución**:
1. Verificar que el puerto 3306 no esté en uso
2. Cambiar puerto en `C:\xampp\mysql\bin\my.ini`
3. Reiniciar XAMPP

### Problema: "Access denied"
**Solución**:
1. En phpMyAdmin, ve a "User accounts"
2. Edita el usuario `root@localhost`
3. Cambia la contraseña o déjala vacía

### Problema: "Connection refused"
**Solución**:
1. Verificar que MySQL esté ejecutándose en XAMPP
2. Verificar puerto 3306
3. Reiniciar XAMPP

## 📊 Ventajas de XAMPP

- ✅ **Fácil instalación**
- ✅ **Incluye phpMyAdmin**
- ✅ **No requiere configuración compleja**
- ✅ **Ideal para desarrollo**

## 🎯 Próximos Pasos

1. **Probar conexión desde Java**
2. **Verificar tablas creadas**
3. **Probar con Postman**
4. **Continuar con desarrollo**

¡XAMPP configurado correctamente! 🎉 