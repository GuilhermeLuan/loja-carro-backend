
# Car Shop BackEnd 💻

This project is a vehicle management API that enables vehicle registration, updating, retrieval, and deletion, with authentication and authorization for access control. Designed for a vehicle inventory administration environment, it allows administrators to authenticate in the system and manage vehicle records, including details such as photo, type, brand, model, year, and price.



## 💻 Technologies
- Java
- Spring Boot
- Docker
- PostgresSQL
- Flyway migrations
- Spring Validation
## 🚀 Deploy

### Prerequisites
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/products/docker-desktop/)

### Cloning


```bash
git clone https://github.com/GuilhermeLuan/loja-carro-backend

```

### Starting

```bash
cd loja-carro-backend
docker compose up
./mvnw clean install
./mvnw spring-boot:run

```


## 📍API Endpoints

#### Retorna todos os itens

| route                              | description                                                                                   |
|------------------------------------|-----------------------------------------------------------------------------------------------|
| GET /api/v1/vehicles               | retrieves a list of all vehicles.                                                             |
| GET /api//v1/vehicles?model={name} | Retrieves a list of all vehicles, optionally filtering by vehicle model name (e.g., "Civic"). |
| GET /api/v1/vehicles/{Long}        | retrieves a specific vehicles by its unique identifier (Long).                                |
| POST /api/v1/vehicles              | creates a vehicles in the database.                                                           |
| PUT /api/v1/vehicles               | modifies a specific vehicles by its unique identifier on body (Long).                         |
| DELETE /api/v1/vehicles/{Long}     | deletes a specific vehicles by its unique identifier (Long).                                  |


### GET /api/v1/vehicles

**RESPONSE**
```json
{
  "content": [
    {
      "id": 14,
      "type": "AUTOMOVEL",
      "model": "Jett",
      "brand": "VOLKSWAGEN",
      "color": "White",
      "price": 25000,
      "year": 2023,
      "imageLink": "https://img.freepik.com/free-vector/red-sedan-car-isolated-white-vector_53876-64366.jpg"
    }
  ],
  "pageable": {}
}
```

### GET /api//v1/vehicles?model={name}

**RESPONSE**
```json
{
  "content": [
    {
      "id": 26,
      "type": "AUTOMOVEL",
      "model": "Civic",
      "brand": "HONDA",
      "color": "Blue",
      "price": 30000,
      "year": 2022,
      "imageLink": "https://example.com/car.jpg"
    }
  ],
  "pageable": {}
}
```

### GET /api/v1/vehicles/{Long}

**RESPONSE**
```json
{
  "id": 26,
  "type": "AUTOMOVEL",
  "model": "Civic",
  "color": "Blue",
  "brand": "HONDA",
  "price": 30000,
  "year": 2022,
  "imageLink": "https://example.com/car.jpg"
}
```

### POST /api/v1/vehicles
**REQUEST**

```json
{
  "type": "AUTOMOVEL",
  "model": "Civic",
  "color": "Blue",
  "brand": "HONDA",
  "price": 30000.00,
  "year": 2022,
  "imageLink": "https://example.com/car.jpg"
}
```

**RESPONSE**

```json
{
  "id": 35,
  "type": "AUTOMOVEL",
  "model": "Civic",
  "brand": "HONDA",
  "color": "Blue",
  "price": 30000,
  "year": 2022,
  "imageLink": "https://example.com/car.jpg"
}
```

### PUT /api/v1/vehicles

**REQUEST**

```json
{
  "id": 35,
  "type": "AUTOMOVEL",
  "model": "Civic",
  "brand": "HONDA",
  "color": "Red",
  "price": 30000,
  "year": 2022,
  "imageLink": "https://example.com/car.jpg"
}
```

**RESPONSE**
- **Status Code:** 204 No Content
- **Description:** The vehicle was successfully updated, but no response body is returned.

### DELETE /api/v1/vehicles/{id}

**REQUEST**
- **Request Body:** None

**RESPONSE**
- **Status Code:** 204 No Content
- **Description:** The vehicle was successfully deleted, but no response body is returned.