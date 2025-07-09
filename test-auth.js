// Script de prueba para verificar autenticación
const axios = require('axios');

const API_BASE_URL = 'http://localhost:8080/api';

async function testAuth() {
    try {
        // 1. Login
        console.log('1. Probando login...');
        const loginResponse = await axios.post(`${API_BASE_URL}/auth/login`, {
            identificador: 'admin@helpdesk.com',
            password: 'admin123'
        });
        
        console.log('Login exitoso:', loginResponse.data.success);
        const token = loginResponse.data.data.token;
        console.log('Token recibido:', token ? `SÍ (${token.length} chars)` : 'NO');
        
        // 2. Probar endpoint de debug
        console.log('\n2. Probando endpoint de debug...');
        const debugResponse = await axios.get(`${API_BASE_URL}/auth/debug-token`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        console.log('Debug exitoso:', debugResponse.data.success);
        console.log('Mensaje:', debugResponse.data.message);
        
        // 3. Probar endpoint de test-auth
        console.log('\n3. Probando endpoint de test-auth...');
        const testAuthResponse = await axios.get(`${API_BASE_URL}/auth/test-auth`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        console.log('Test auth exitoso:', testAuthResponse.data.success);
        console.log('Mensaje:', testAuthResponse.data.message);
        
        // 4. Probar endpoint de notificaciones
        console.log('\n4. Probando endpoint de notificaciones...');
        const notificationsResponse = await axios.get(`${API_BASE_URL}/notificaciones`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        console.log('Notificaciones exitoso:', notificationsResponse.status === 200);
        
    } catch (error) {
        console.error('Error en prueba:', error.response?.data || error.message);
    }
}

testAuth(); 