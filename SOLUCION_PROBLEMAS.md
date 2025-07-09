# 🔧 SOLUCIÓN DE PROBLEMAS - HELPDESK INCIDENCIAS

## 🚨 **PROBLEMAS COMUNES Y SOLUCIONES**

### **1. Error 401 - No Autorizado**

**Síntomas:**
- Errores 401 en el navegador
- Redirecciones a `/error`
- Token válido pero peticiones fallan

**Causas:**
- Roles sin prefijo `ROLE_` en Spring Security
- Configuración incorrecta de CORS
- Token expirado o inválido

**Soluciones:**
1. **Verificar token**: Usar el endpoint `/api/auth/debug-token`
2. **Reiniciar aplicación**: Detener y volver a iniciar backend
3. **Limpiar localStorage**: Borrar token del navegador
4. **Verificar roles**: Asegurar que los roles tengan prefijo `ROLE_`

### **2. Error de Conexión a Base de Datos**

**Síntomas:**
- Error al iniciar Spring Boot
- Mensajes de conexión fallida
- Tablas no encontradas

**Soluciones:**
1. **Verificar MySQL**: `mysql --version`
2. **Verificar base de datos**: Ejecutar `database/verify-setup.sql`
3. **Recrear base de datos**: Ejecutar `database/setup.sql`
4. **Verificar credenciales**: Revisar `application.properties`

### **3. Error de Compilación Frontend**

**Síntomas:**
- Errores de TypeScript
- Módulos no encontrados
- Dependencias faltantes

**Soluciones:**
1. **Reinstalar dependencias**: `npm install`
2. **Limpiar cache**: `npm cache clean --force`
3. **Verificar Node.js**: `node --version`
4. **Recompilar**: `npm run build`

### **4. Error de CORS**

**Síntomas:**
- Errores de CORS en el navegador
- Peticiones bloqueadas
- Problemas de comunicación frontend-backend

**Soluciones:**
1. **Verificar configuración CORS**: Revisar `SecurityConfig.java`
2. **Verificar proxy**: Revisar `proxy.conf.json`
3. **Reiniciar servidores**: Detener y volver a iniciar
4. **Limpiar cache del navegador**

## 🔍 **DIAGNÓSTICO PASO A PASO**

### **Paso 1: Verificar Backend**
```bash
cd backend
mvn clean compile
mvn spring-boot:run
```

**Verificar:**
- ✅ Compilación exitosa
- ✅ Servidor iniciado en puerto 8080
- ✅ Sin errores en consola

### **Paso 2: Verificar Base de Datos**
```sql
-- Ejecutar en MySQL Workbench
USE helpdesk_incidencias;
SHOW TABLES;
SELECT COUNT(*) FROM usuarios;
```

**Verificar:**
- ✅ Base de datos existe
- ✅ Tablas creadas
- ✅ Datos de usuarios presentes

### **Paso 3: Verificar Frontend**
```bash
cd frontend
npm install
npm run build
ng serve
```

**Verificar:**
- ✅ Compilación exitosa
- ✅ Servidor iniciado en puerto 4200
- ✅ Sin errores en consola

### **Paso 4: Probar Autenticación**
1. Ir a `http://localhost:4200`
2. Intentar login con credenciales de prueba
3. Verificar que el token se guarda
4. Probar navegación entre páginas

## 🛠️ **HERRAMIENTAS DE DIAGNÓSTICO**

### **Endpoints de Debug Backend**
- `GET /api/auth/debug-token` - Verificar token
- `GET /api/auth/test-auth` - Probar autenticación
- `GET /api/auth/test-token` - Probar token

### **Comandos de Verificación**
```bash
# Verificar Java
java -version

# Verificar Node.js
node --version

# Verificar MySQL
mysql --version

# Verificar Maven
mvn --version

# Verificar Angular CLI
ng version
```

### **Logs Importantes**
- **Backend**: `backend/logs/application.log`
- **Frontend**: Consola del navegador (F12)
- **Base de datos**: MySQL Workbench logs

## 📋 **CHECKLIST DE VERIFICACIÓN**

### **Antes de Iniciar**
- [ ] Java 23 instalado
- [ ] Node.js 18+ instalado
- [ ] MySQL 8.0+ instalado y funcionando
- [ ] Maven instalado
- [ ] Base de datos creada
- [ ] Dependencias instaladas

### **Durante la Ejecución**
- [ ] Backend inicia sin errores
- [ ] Frontend compila correctamente
- [ ] Base de datos conecta
- [ ] Login funciona
- [ ] Navegación funciona
- [ ] API responde correctamente

### **Después de la Ejecución**
- [ ] Aplicación accesible en `http://localhost:4200`
- [ ] API accesible en `http://localhost:8080`
- [ ] Swagger accesible en `http://localhost:8080/swagger-ui.html`
- [ ] Sin errores en consolas
- [ ] Funcionalidades principales funcionando

## 🚀 **COMANDOS DE INICIO RÁPIDO**

### **Inicio Completo**
```bash
# Ejecutar script de inicio
start-full-stack.bat
```

### **Inicio Manual**
```bash
# Terminal 1 - Backend
cd backend
mvn spring-boot:run

# Terminal 2 - Frontend
cd frontend
ng serve
```

### **Verificación Rápida**
```bash
# Verificar que todo funciona
verify-setup.bat
```

## 📞 **CONTACTO Y SOPORTE**

Si los problemas persisten:

1. **Revisar logs**: Buscar errores específicos
2. **Verificar configuración**: Comparar con archivos de ejemplo
3. **Reiniciar servicios**: Detener y volver a iniciar
4. **Limpiar cache**: Borrar archivos temporales
5. **Reinstalar dependencias**: Eliminar y reinstalar node_modules

---

**Estado del proyecto**: ✅ **FUNCIONAL** con correcciones aplicadas 