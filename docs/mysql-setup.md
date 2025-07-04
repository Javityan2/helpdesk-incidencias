# Guía Completa de Configuración MySQL - Helpdesk Incidencias

## 📋 Prerrequisitos

1. **MySQL Server** instalado (versión 8.0 o superior)
2. **MySQL Workbench** instalado
3. **Java 17** instalado
4. **Maven** instalado

## 🚀 Paso 1: Instalar MySQL Server

### Opción A: Descarga Directa
1. Ve a [MySQL Downloads](https://dev.mysql.com/downloads/mysql/)
2. Descarga **MySQL Community Server** para Windows
3. Ejecuta el instalador
4. **Configuración recomendada**:
   - **Port**: 3306 (por defecto)
   - **Root Password**: `password` (para desarrollo)
   - **Service Name**: MySQL80

### Opción B: XAMPP (Alternativa)
1. Descarga [XAMPP](https://www.apachefriends.org/)
2. Instala XAMPP
3. Inicia MySQL desde el panel de control

## 🛠️ Paso 2: Instalar MySQL Workbench

1. Ve a [MySQL Workbench Downloads](https://dev.mysql.com/downloads/workbench/)
2. Descarga la versión para Windows
3. Instala MySQL Workbench

## 🔧 Paso 3: Configurar MySQL Workbench

### 3.1 Conectar a MySQL Server

1. **Abre MySQL Workbench**
2. **Crea una nueva conexión**:
   - Hostname: `localhost`
   - Port: `3306`
   - Username: `root`
   - Password: `password` (o la que configuraste)
   - Connection Name: `Helpdesk Local`

3. **Test Connection** para verificar que funciona

### 3.2 Crear la Base de Datos

1. **Conecta a la base de datos**
2. **Ejecuta el siguiente script SQL**:

```sql
-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS helpdesk_incidencias
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE helpdesk_incidencias;

-- Verificar que se creó correctamente
SHOW DATABASES;
```

### 3.3 Crear Usuario de Desarrollo (Opcional)

```sql
-- Crear usuario específico para la aplicación
CREATE USER 'helpdesk_user'@'localhost' IDENTIFIED BY 'helpdesk_password';

-- Otorgar permisos
GRANT ALL PRIVILEGES ON helpdesk_incidencias.* TO 'helpdesk_user'@'localhost';

-- Aplicar cambios
FLUSH PRIVILEGES;
```

## 🗃️ Paso 4: Verificar Configuración

### 4.1 Verificar Conexión desde Java

1. **Ejecuta el backend**:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

2. **Verifica en los logs** que se conecte correctamente

### 4.2 Verificar Tablas Creadas

En MySQL Workbench, ejecuta:

```sql
USE helpdesk_incidencias;

-- Ver todas las tablas
SHOW TABLES;

-- Ver estructura de la tabla usuarios
DESCRIBE usuarios;

-- Ver datos de prueba
SELECT * FROM usuarios;
```

## 🔍 Paso 5: Troubleshooting

### Problema: "Access denied for user 'root'@'localhost'"
**Solución**:
```sql
-- Conectar como root y cambiar password
ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;
```

### Problema: "Connection refused"
**Solución**:
1. Verificar que MySQL esté ejecutándose
2. Verificar puerto 3306
3. Verificar firewall

### Problema: "Database doesn't exist"
**Solución**:
```sql
CREATE DATABASE helpdesk_incidencias;
```

## 📊 Paso 6: Datos de Prueba

Los datos de prueba se cargan automáticamente desde `data.sql`, pero puedes verificar:

```sql
-- Verificar usuarios creados
SELECT id, empleado_id, nombre, apellido, email, rol, activo 
FROM usuarios;

-- Verificar que las contraseñas estén encriptadas
SELECT empleado_id, password FROM usuarios;
```

## 🎯 Paso 7: Configuración Final

### 7.1 Verificar application.properties

Asegúrate de que tu `application.properties` tenga:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/helpdesk_incidencias?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=password
```

### 7.2 Probar Conexión

1. **Ejecuta el backend**
2. **Verifica en los logs**:
   ```
   Hibernate: create table usuarios...
   Hibernate: insert into usuarios...
   ```

## 📝 Checklist de Verificación

- [ ] MySQL Server instalado y ejecutándose
- [ ] MySQL Workbench instalado
- [ ] Conexión creada en Workbench
- [ ] Base de datos `helpdesk_incidencias` creada
- [ ] Backend se conecta correctamente
- [ ] Tablas creadas automáticamente
- [ ] Datos de prueba cargados
- [ ] Puedes hacer login con `admin@empresa.com` / `password123`

## 🚀 Próximos Pasos

Una vez que la base de datos esté configurada:

1. **Probar con Postman** (usando la colección que creamos)
2. **Implementar controladores de incidencias**
3. **Implementar controladores de comentarios**
4. **Desarrollar frontend Angular**

¡Base de datos lista para desarrollo! 🎉 