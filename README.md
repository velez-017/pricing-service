# Pricing Service API

Microservicio REST desarrollado con Spring Boot para la gestión de precios de productos, incluyendo reglas de negocio para impuestos, descuentos y tipos de cliente.

---

# Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- Swagger / OpenAPI
- JUnit 5
- Mockito
- GitHub Actions (CI/CD)
- Maven

---

# Arquitectura del proyecto

```text
pricing-service
│
├── controllers
├── services
├── repositories
├── entities
├── dto
├── enums
├── exceptions
├── tests
└── resources

# Estructura típica de un proyecto Spring Boot con capas bien definidas
```
Funcionalidades
CRUD completo de precios
Cálculo automático de:
descuentos
impuestos
precio final
Tipos de cliente:
REGULAR
EXECUTIVE
ADMINISTRATIVE
Validaciones con Jakarta Validation
Paginación
Swagger UI
Dockerización completa
Integración continua con GitHub Actions
Reglas de negocio
Tipo Cliente	Descuento
REGULAR	0%
EXECUTIVE	10%
ADMINISTRATIVE	5%

Impuesto aplicado:

19%
Endpoints disponibles
Obtener todos los precios
GET /api/v1/pricing-service/prices
Obtener precio por ID
GET /api/v1/pricing-service/prices/{id}
Crear precio
POST /api/v1/pricing-service/prices
Body
{
  "productName": "Macbook Pro M4",
  "amount": 5000,
  "customerType": "EXECUTIVE"
}
Actualizar precio
PUT /api/v1/pricing-service/prices/{id}
Eliminar precio
DELETE /api/v1/pricing-service/prices/{id}
Paginación
GET /api/v1/pricing-service/prices/page/{page}
Swagger UI

Disponible en:

http://localhost:8081/swagger-ui/index.html
Ejecutar el proyecto con Docker
Clonar repositorio
git clone https://github.com/velez-017/pricing-service.git
Entrar al proyecto
cd pricing-service
Levantar contenedores
docker compose up --build
Base de datos
PostgreSQL
Inicialización automática mediante import.sql
Ejecutar tests
mvn test
Pipeline CI/CD

El proyecto incluye GitHub Actions para:

compilación automática
ejecución de tests
validación Docker
integración continua
Ejemplo de respuesta API
[
  {
    "id": 1,
    "productName": "Laptop Dell",
    "basePrice": 2000,
    "customerType": "REGULAR",
    "discountPercentage": 0,
    "taxPercentage": 19,
    "finalPrice": 2380,
    "createdAt": "2026-05-22"
  }
]
Autor

Desarrollado por:

Jhon Velez

GitHub:

https://github.com/velez-017