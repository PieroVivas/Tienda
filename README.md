# 📘 Store Online API

API RESTful desarrollada con **Spring WebFlux + R2DBC + H2** (Java 17) para la gestión de productos y pedidos de una tienda online.  
Incluye ejemplos de concurrencia con **bloqueo optimista** y manejo reactivo de transacciones.

---

## 🚀 Requisitos previos

- **Java 17**  
- **Maven 3.9+**  
- **Postman** (para probar la API)  
- (Opcional) **IntelliJ IDEA / VS Code** como IDE

---

## ▶️ Cómo ejecutar el proyecto

1. **Compilar y empaquetar**
   ```bash
   mvn clean install
   ```

2. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

3. La API estará disponible en:  
   👉 [http://localhost:8080](http://localhost:8080)

---

## 📂 Estructura del proyecto

```
src/main/java/com/example/store
 ┣ api/               → Controladores REST
 ┣ domain/            → Entidades (Producto, Pedido, ItemPedido)
 ┣ repository/        → Repositorios R2DBC
 ┣ service/           → Lógica de negocio
 ┗ resources/
    ┣ application.yml → Configuración de Spring Boot
    ┣ schema.sql      → Definición de tablas
    ┗ data.sql        → Datos iniciales (UTF-8)
```

---

## 🛠️ Endpoints principales

### 📌 Productos
- **GET** `/api/productos` → listar todos los productos  
- **GET** `/api/productos/{id}` → obtener un producto por ID  
- **POST** `/api/productos` → crear un producto  
- **PUT** `/api/productos/{id}` → actualizar un producto  
- **DELETE** `/api/productos/{id}` → eliminar un producto  

### 📌 Pedidos
- **POST** `/api/pedidos` → confirmar un pedido (actualiza stock y guarda items)  
- **GET** `/api/pedidos` → listar pedidos  

---

## 📑 Ejemplos de uso en Postman

### Crear un pedido
```json
POST http://localhost:8080/api/pedidos
Content-Type: application/json

{
  "items": [
    { "productoId": 1, "cantidad": 2 },
    { "productoId": 3, "cantidad": 1 }
  ]
}
```

✅ Si hay stock suficiente → se confirma.  
❌ Si no hay stock o hay conflicto de concurrencia → error `409` o `500`.

---

## 📘 Swagger / OpenAPI

Documentación interactiva disponible en:  
👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## ⚡ Notas importantes

- La base de datos H2 es **en memoria** → se reinicia en cada arranque.  
- Los datos iniciales están en `data.sql`.  
- El campo `version` en `producto` se usa para **control de concurrencia optimista**.
