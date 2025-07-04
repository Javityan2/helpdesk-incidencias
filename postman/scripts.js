// Script para el endpoint de Login
// Se ejecuta después de recibir la respuesta
if (pm.response.code === 200) {
    const response = pm.response.json();
    if (response.success && response.data && response.data.token) {
        // Guardar el token en la variable de entorno
        pm.environment.set("authToken", response.data.token);
        console.log("Token guardado exitosamente");
    }
}

// Script para validar respuestas de error
if (pm.response.code >= 400) {
    const response = pm.response.json();
    console.log("Error:", response.message || response.error);
}

// Script para validar estructura de respuesta
if (pm.response.code === 200) {
    const response = pm.response.json();
    pm.test("Respuesta exitosa", function () {
        pm.expect(response.success).to.be.true;
    });
    
    pm.test("Mensaje presente", function () {
        pm.expect(response.message).to.be.a('string');
    });
    
    pm.test("Timestamp presente", function () {
        pm.expect(response.timestamp).to.be.a('string');
    });
}

// Script para validar estructura de AuthResponse
if (pm.response.code === 200 && pm.request.url.path.includes("/auth/login")) {
    const response = pm.response.json();
    if (response.data) {
        pm.test("Token presente", function () {
            pm.expect(response.data.token).to.be.a('string');
        });
        
        pm.test("Tipo de token", function () {
            pm.expect(response.data.tipo).to.eql("Bearer");
        });
        
        pm.test("Datos de usuario", function () {
            pm.expect(response.data.usuarioId).to.be.a('number');
            pm.expect(response.data.empleadoId).to.be.a('string');
            pm.expect(response.data.nombre).to.be.a('string');
            pm.expect(response.data.apellido).to.be.a('string');
            pm.expect(response.data.email).to.be.a('string');
            pm.expect(response.data.rol).to.be.a('string');
        });
    }
}

// Script para validar estructura de Usuario
if (pm.response.code === 200 && pm.request.url.path.includes("/usuarios")) {
    const response = pm.response.json();
    if (response.data && Array.isArray(response.data)) {
        pm.test("Lista de usuarios", function () {
            pm.expect(response.data).to.be.an('array');
        });
        
        if (response.data.length > 0) {
            const usuario = response.data[0];
            pm.test("Estructura de usuario", function () {
                pm.expect(usuario).to.have.property('id');
                pm.expect(usuario).to.have.property('empleadoId');
                pm.expect(usuario).to.have.property('nombre');
                pm.expect(usuario).to.have.property('apellido');
                pm.expect(usuario).to.have.property('email');
                pm.expect(usuario).to.have.property('rol');
            });
        }
    }
} 