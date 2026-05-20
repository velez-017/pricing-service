# Pricing Service API

Microservicio REST desarrollado con Spring Boot para la gestión de precios de productos por región.

## Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Docker
- Docker Compose
- Maven
- Hibernate
- Lombok
- Validation API

---

# Arquitectura del proyecto

```text
Controller -> Service -> Repository -> PostgreSQL
```

---

# Funcionalidades

- Crear precios
- Listar precios
- Buscar precio por ID
- Actualizar precios
- Eliminar precios
- Listar regiones
- Validaciones
- Manejo global de excepciones
- Dockerización completa

---

# Estructura del proyecto

```text
src/main/java/com/techplanner/pricingservice
│
├── controllers
├── dto
├── entities
├── exceptions
├── repositories
├── services
└── PricingServiceApplication
```

---

# Variables y puertos

| Servicio | Puerto |
|---|---|
| API | 8081 |
| Actuator | 8082 |
| PostgreSQL | 5433 |

---

# Ejecutar el proyecto

## 1. Clonar repositorio

```bash
git clone https://github.com/velez-017/pricing-service.git
```

## 2. Entrar al proyecto

```bash
cd pricing-service
```

## 3. Levantar Docker

```bash
docker compose up -d
```

---

# Verificar contenedores

```bash
docker ps
```

---

# Health Check

```bash
curl http://localhost:8082/actuator/health
```

---

# Endpoints disponibles

## Obtener todos los precios

```http
GET /api/v1/pricing-service/prices
```

Ejemplo:

```bash
curl http://localhost:8081/api/v1/pricing-service/prices
```

---

## Obtener precio por ID

```http
GET /api/v1/pricing-service/prices/{id}
```

Ejemplo:

```bash
curl http://localhost:8081/api/v1/pricing-service/prices/1
```

---

## Crear precio

```http
POST /api/v1/pricing-service/prices
```

Body:

```json
{
  "productName": "Laptop Dell",
  "amount": 3500,
  "regionId": 1
}
```

Ejemplo PowerShell:

```powershell
curl -Method POST `
  -Uri "http://localhost:8081/api/v1/pricing-service/prices" `
  -ContentType "application/json" `
  -Body '{
    "productName":"Laptop Dell",
    "amount":3500,
    "regionId":1
  }'
```

---

## Actualizar precio

```http
PUT /api/v1/pricing-service/prices/{id}
```

---

## Eliminar precio

```http
DELETE /api/v1/pricing-service/prices/{id}
```

---

## Obtener regiones

```http
GET /api/v1/pricing-service/prices/regions
```

Ejemplo:

```bash
curl http://localhost:8081/api/v1/pricing-service/prices/regions
```

---

# Docker

## Construir imágenes

```bash
docker compose build
```

## Levantar servicios

```bash
docker compose up -d
```

## Ver logs

```bash
docker compose logs app
```

## Detener servicios

```bash
docker compose down
```

---

# Base de datos

PostgreSQL se ejecuta en Docker.

## Configuración

| Variable | Valor |
|---|---|
| Database | pricing_db |
| User | postgres |
| Password | postgres |
| Puerto | 5433 |

---

# Validaciones implementadas

- Nombre obligatorio
- Longitud mínima y máxima
- Amount obligatorio
- Amount positivo
- Región obligatoria

---

# Manejo de errores

Se implementó un `GlobalExceptionHandler` para:

- errores de validación
- recursos no encontrados
- errores internos del servidor

---

# Estado del proyecto

Proyecto funcional y dockerizado.

Características implementadas:

- CRUD REST
- PostgreSQL
- Docker Compose
- Validaciones
- Exception Handling
- Actuator
- Arquitectura por capas

---

# Autor

Desarrollado por Jhon Velez como proyecto backend profesional con Spring Boot y Docker.