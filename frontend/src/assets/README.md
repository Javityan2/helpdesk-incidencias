# 📁 Assets

Este directorio contiene los recursos estáticos de la aplicación.

## 📋 Contenido

- **favicon.ico**: Icono de la aplicación
- **images/**: Imágenes y gráficos
- **icons/**: Iconos personalizados
- **fonts/**: Fuentes tipográficas (si aplica)

## 🎨 Favicon

El favicon actual es un placeholder. Para producción, se recomienda:

1. Crear un favicon personalizado
2. Incluir múltiples tamaños (16x16, 32x32, 48x48)
3. Generar favicon.ico con herramientas como favicon.io

## 📱 Imágenes

### Optimización
- Usar formatos modernos (WebP, AVIF)
- Comprimir imágenes para web
- Implementar lazy loading

### Responsive Images
```html
<img src="image.jpg" 
     srcset="image-300.jpg 300w, image-600.jpg 600w, image-900.jpg 900w"
     sizes="(max-width: 600px) 300px, (max-width: 900px) 600px, 900px"
     alt="Descripción">
```

## 🔧 Configuración

### Angular.json
```json
"assets": [
  "src/favicon.ico",
  "src/assets"
]
```

### Importación en Componentes
```typescript
// En componentes
const imageUrl = 'assets/images/logo.png';
``` 