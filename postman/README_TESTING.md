# Guía de Testing con Postman - Helpdesk Incidencias

## 📋 Prerrequisitos

1. **Postman instalado** en tu computadora
2. **Backend ejecutándose** en `http://localhost:8080`
3. **Base de datos configurada** y funcionando
4. **Datos de prueba** cargados en la base de datos

## 🚀 Pasos para Configurar Postman

### Paso 1: Importar la Colección

1. Abre Postman
2. Haz clic en **"Import"** en la esquina superior izquierda
3. Selecciona el archivo `Helpdesk-Incidencias.postman_collection.json`
4. La colección se importará automáticamente

### Paso 2: Configurar Variables de Entorno

1. En Postman, ve a **"Environments"** (icono de engranaje)
2. Crea un nuevo entorno llamado **"Helpdesk Local"**
3. Agrega las siguientes variables:

| Variable | Valor Inicial | Descripción |
|----------|---------------|-------------|
| `baseUrl` | `http://localhost:8080` | URL base de la API |
| `authToken` | (vacío) | Token JWT para autenticación |

### Paso 3: Seleccionar el Entorno

1. En la esquina superior derecha de Postman
2. Selecciona **"Helpdesk Local"** del dropdown

## 🧪 Secuencia de Testing

### 1. Testing de Autenticación

#### 1.1 Login (Obligatorio primero)
- **Endpoint**: `POST /api/auth/login`
- **Body**:
```json
{
    "identificador": "admin@empresa.com",
    "password": "admin123"
}
```
- **Resultado esperado**: 
  - Status: 200
  - Token JWT en la respuesta
  - Token se guarda automáticamente en la variable `authToken`

#### 1.2 Validar Token
- **Endpoint**: `GET /api/auth/validar`
- **Headers**: `Authorization: Bearer {{authToken}}`
- **Resultado esperado**: Status 200

#### 1.3 Obtener Perfil
- **Endpoint**: `GET /api/auth/perfil`
- **Headers**: `Authorization: Bearer {{authToken}}`
- **Resultado esperado**: Datos del usuario actual

#### 1.4 Registro (Opcional)
- **Endpoint**: `POST /api/auth/registro`
- **Body**:
```json
{
    "empleadoId": "EMP001",
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@empresa.com",
    "password": "password123",
    "rol": "USUARIO",
    "departamento": "IT",
    "cargo": "Desarrollador"
}
```

### 2. Testing de Usuarios

#### 2.1 Obtener Todos los Usuarios
- **Endpoint**: `GET /api/usuarios`
- **Headers**: `Authorization: Bearer {{authToken}}`
- **Resultado esperado**: Lista de usuarios

#### 2.2 Crear Usuario
- **Endpoint**: `POST /api/usuarios`
- **Headers**: 
  - `Content-Type: application/json`
  - `Authorization: Bearer {{authToken}}`
- **Body**:
```json
{
    "empleadoId": "EMP002",
    "nombre": "María",
    "apellido": "García",
    "email": "maria.garcia@empresa.com",
    "password": "password123",
    "rol": "TECNICO",
    "departamento": "Soporte",
    "cargo": "Técnico de Soporte"
}
```

#### 2.3 Obtener Usuario por ID
- **Endpoint**: `GET /api/usuarios/{id}`
- **Headers**: `Authorization: Bearer {{authToken}}`

#### 2.4 Actualizar Usuario
- **Endpoint**: `PUT /api/usuarios/{id}`
- **Headers**: 
  - `Content-Type: application/json`
  - `Authorization: Bearer {{authToken}}`
- **Body**:
```json
{
    "empleadoId": "EMP002",
    "nombre": "María",
    "apellido": "García López",
    "email": "maria.garcia@empresa.com",
    "rol": "TECNICO",
    "departamento": "Soporte Técnico",
    "cargo": "Técnico Senior"
}
```

#### 2.5 Cambiar Password
- **Endpoint**: `PUT /api/usuarios/{id}/password`
- **Headers**: 
  - `Content-Type: application/json`
  - `Authorization: Bearer {{authToken}}`
- **Body**: `"nuevaPassword123"`

#### 2.6 Endpoints Especializados
- **Obtener por Rol**: `GET /api/usuarios/rol/TECNICO`
- **Obtener por Departamento**: `GET /api/usuarios/departamento/IT`
- **Obtener Técnicos**: `GET /api/usuarios/tecnicos`
- **Obtener Supervisores**: `GET /api/usuarios/supervisores`
- **Activar Usuario**: `PUT /api/usuarios/{id}/activar`
- **Desactivar Usuario**: `PUT /api/usuarios/{id}/desactivar`
- **Estadísticas**: `GET /api/usuarios/estadisticas`

## 🔍 Validaciones Automáticas

La colección incluye scripts que validan automáticamente:

### Estructura de Respuesta
- ✅ `success`: boolean
- ✅ `message`: string
- ✅ `timestamp`: string
- ✅ `data`: objeto o array

### Estructura de AuthResponse
- ✅ `token`: string
- ✅ `tipo`: "Bearer"
- ✅ `usuarioId`: number
- ✅ `empleadoId`: string
- ✅ `nombre`: string
- ✅ `apellido`: string
- ✅ `email`: string
- ✅ `rol`: string

### Estructura de Usuario
- ✅ `id`: number
- ✅ `empleadoId`: string
- ✅ `nombre`: string
- ✅ `apellido`: string
- ✅ `email`: string
- ✅ `rol`: string

## 🚨 Casos de Error a Probar

### 1. Autenticación
- **Credenciales inválidas**: Debe retornar 401
- **Token expirado**: Debe retornar 401
- **Token inválido**: Debe retornar 401
- **Sin token**: Debe retornar 401

### 2. Autorización
- **Acceso sin permisos**: Debe retornar 403
- **Rol insuficiente**: Debe retornar 403

### 3. Validación de Datos
- **Campos requeridos faltantes**: Debe retornar 400
- **Formato de email inválido**: Debe retornar 400
- **Password muy corta**: Debe retornar 400

### 4. Recursos No Encontrados
- **Usuario inexistente**: Debe retornar 404
- **ID inválido**: Debe retornar 404

## 📊 Códigos de Estado HTTP

| Código | Descripción | Cuándo se usa |
|--------|-------------|---------------|
| 200 | OK | Operación exitosa |
| 201 | Created | Recurso creado |
| 400 | Bad Request | Datos inválidos |
| 401 | Unauthorized | No autenticado |
| 403 | Forbidden | No autorizado |
| 404 | Not Found | Recurso no encontrado |
| 500 | Internal Server Error | Error del servidor |

## 🔧 Troubleshooting

### Problema: "Connection refused"
- **Solución**: Verificar que el backend esté ejecutándose en puerto 8080

### Problema: "Token inválido"
- **Solución**: Hacer login nuevamente para obtener un token fresco

### Problema: "CORS error"
- **Solución**: Verificar que CORS esté configurado correctamente en el backend

### Problema: "Base de datos no conectada"
- **Solución**: Verificar configuración de base de datos en `application.properties`

## 📝 Notas Importantes

1. **Siempre hacer login primero** para obtener el token
2. **El token se guarda automáticamente** en la variable `authToken`
3. **Los scripts validan automáticamente** la estructura de respuesta
4. **Probar casos de error** para validar manejo de excepciones
5. **Verificar logs del backend** para debugging

## 🎯 Próximos Pasos

Una vez que hayas probado exitosamente estos endpoints:

1. **Implementar controladores de incidencias**
2. **Implementar controladores de comentarios**
3. **Agregar más validaciones**
4. **Implementar frontend Angular**
5. **Configurar CI/CD**

¡Happy Testing! 🚀 