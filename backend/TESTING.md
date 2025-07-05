# 🧪 Pruebas del Backend - Helpdesk Incidencias

## 📋 Descripción

Este documento describe las pruebas implementadas para el backend del sistema Helpdesk Incidencias.

## 🏗️ Arquitectura de Pruebas

### Estructura de Pruebas
```
src/test/
├── java/com/helpdesk/incidencias/
│   ├── HelpdeskIncidenciasApplicationTests.java    # Pruebas principales
│   ├── application/services/
│   │   ├── UsuarioServiceTest.java                # Pruebas de servicios
│   │   └── IncidenciaServiceTest.java
│   └── infrastructure/
│       ├── config/
│       │   └── TestConfig.java                    # Configuración de pruebas
│       └── controllers/
│           ├── AuthControllerTest.java             # Pruebas de controladores
│           └── UsuarioControllerTest.java
└── resources/
    └── application-test.properties                # Configuración de pruebas
```

## 🧪 Tipos de Pruebas

### 1. Pruebas Unitarias
- **UsuarioServiceTest**: Pruebas del servicio de usuarios
- **IncidenciaServiceTest**: Pruebas del servicio de incidencias

### 2. Pruebas de Integración
- **AuthControllerTest**: Pruebas del controlador de autenticación
- **UsuarioControllerTest**: Pruebas del controlador de usuarios

### 3. Pruebas de Configuración
- **TestConfig**: Configuración específica para pruebas
- **application-test.properties**: Configuración de base de datos H2

## 🚀 Ejecutar Pruebas

### Opción 1: Usando Maven
```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas con reporte de cobertura
mvn clean test jacoco:report

# Ejecutar pruebas específicas
mvn test -Dtest=UsuarioServiceTest
```

### Opción 2: Usando el Script
```bash
# En Windows
run-tests.bat

# En Linux/Mac
./run-tests.sh
```

### Opción 3: Desde IDE
- Ejecutar `HelpdeskIncidenciasApplicationTests` como prueba principal
- Ejecutar clases individuales de prueba

## 📊 Cobertura de Código

### JaCoCo Configuration
- **Cobertura de Líneas**: Mínimo 80%
- **Cobertura de Ramas**: Mínimo 70%
- **Cobertura de Métodos**: Mínimo 85%

### Verificar Cobertura
```bash
# Generar reporte
mvn jacoco:report

# Ver reporte en: target/site/jacoco/index.html
```

## 🧪 Casos de Prueba Implementados

### UsuarioService
- ✅ Crear usuario
- ✅ Actualizar usuario
- ✅ Eliminar usuario
- ✅ Obtener usuario por ID
- ✅ Obtener usuario por empleadoId
- ✅ Autenticar usuario
- ✅ Validar existencia de usuario

### IncidenciaService
- ✅ Obtener todas las incidencias
- ✅ Obtener incidencia por ID
- ✅ Obtener incidencias por estado
- ✅ Obtener incidencias por prioridad
- ✅ Eliminar incidencia
- ✅ Obtener incidencias por usuario

### AuthController
- ✅ Login exitoso
- ✅ Login con credenciales inválidas
- ✅ Validación de token
- ✅ Refresh de token

### UsuarioController
- ✅ Obtener todos los usuarios
- ✅ Obtener usuario por ID
- ✅ Obtener usuario por empleadoId
- ✅ Obtener usuarios activos
- ✅ Eliminar usuario
- ✅ Obtener estadísticas

## 🔧 Configuración de Base de Datos

### Base de Datos de Pruebas (H2)
```properties
# Configuración H2 para pruebas
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
```

### Ventajas de H2
- ✅ Base de datos en memoria
- ✅ Inicialización automática
- ✅ Aislamiento de pruebas
- ✅ Velocidad de ejecución

## 📝 Convenciones de Pruebas

### Nomenclatura
```java
@Test
void testNombreDelMetodo_Condicion_ResultadoEsperado() {
    // Arrange
    // Act
    // Assert
}
```

### Estructura AAA
- **Arrange**: Preparar datos y mocks
- **Act**: Ejecutar el método a probar
- **Assert**: Verificar resultados

### Mocks
```java
@Mock
private UsuarioRepository usuarioRepository;

@InjectMocks
private UsuarioService usuarioService;
```

## 🚨 Manejo de Errores

### Excepciones Esperadas
```java
@Test
void testCrearUsuario_EmpleadoIdDuplicado_ThrowsException() {
    // Arrange
    when(usuarioRepository.existsByEmpleadoId("EMP001")).thenReturn(true);
    
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
        usuarioService.crearUsuario(usuario);
    });
}
```

## 📈 Métricas de Calidad

### Objetivos de Cobertura
- **Servicios**: 90%+
- **Controladores**: 85%+
- **Repositorios**: 80%+
- **Utilidades**: 95%+

### Pruebas por Método
- **Casos Exitosos**: 100%
- **Casos de Error**: 100%
- **Casos Límite**: 80%+

## 🔄 Integración Continua

### GitHub Actions (Futuro)
```yaml
- name: Run Tests
  run: mvn test

- name: Generate Coverage Report
  run: mvn jacoco:report

- name: Upload Coverage
  uses: codecov/codecov-action@v3
```

## 📚 Recursos Adicionales

### Documentación
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing)

### Herramientas
- **JUnit 5**: Framework de pruebas
- **Mockito**: Framework de mocking
- **JaCoCo**: Cobertura de código
- **H2**: Base de datos de pruebas

---

## ✅ Checklist de Pruebas

- [x] Configuración de pruebas
- [x] Pruebas unitarias de servicios
- [x] Pruebas de integración de controladores
- [x] Configuración de base de datos H2
- [x] Reportes de cobertura
- [x] Scripts de ejecución
- [ ] Pruebas de seguridad
- [ ] Pruebas de rendimiento
- [ ] Pruebas de carga

**Estado**: 🟡 En Progreso (80% completado) 