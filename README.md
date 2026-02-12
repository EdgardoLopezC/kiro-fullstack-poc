# Kiro Fullstack POC

Proyecto de prueba técnica con stack completo: Angular + Quarkus + MySQL.

## 📋 Stack Tecnológico

- **Frontend**: Angular 18 (Standalone Components)
- **Backend**: Quarkus 3.6.4 (Java 17)
- **Base de Datos**: MySQL 8.x
- **Documentación API**: OpenAPI/Swagger

## 📁 Estructura del Proyecto

```
/frontend   - Aplicación Angular
/backend    - API Quarkus
/docs       - Documentación y diagramas
```

## 🚀 Inicio Rápido

### Prerrequisitos

- Node.js 18+ y npm
- Java 17+
- Maven 3.8+
- MySQL 8.x

### 1. Configurar Base de Datos

```sql
CREATE DATABASE todo_db;
```

### 2. Backend (Quarkus)

```bash
cd backend
mvn quarkus:dev -s .mvn/settings.xml
```

El backend estará disponible en: http://localhost:8080

**Swagger UI:** http://localhost:8080/swagger-ui

### 3. Frontend (Angular)

```bash
cd frontend
npm install
npm start
```

El frontend estará disponible en: http://localhost:4200

## ⚙️ Configuración

### Variables de Entorno (Backend)

Puedes configurar la conexión a MySQL usando variables de entorno:

```bash
set DB_URL=jdbc:mysql://localhost:3306/todo_db
set DB_USER=root
set DB_PASSWORD=tu_password
```

O editar directamente `backend/src/main/resources/application.properties`

## 📚 Documentación

- **Arquitectura y Diagramas:** [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)
- **API Documentation:** http://localhost:8080/swagger-ui (cuando el backend esté corriendo)

## 🔗 Endpoints API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/tasks` | Listar todas las tareas |
| GET | `/api/tasks/{id}` | Obtener tarea por ID |
| POST | `/api/tasks` | Crear nueva tarea |
| PUT | `/api/tasks/{id}` | Actualizar tarea |
| DELETE | `/api/tasks/{id}` | Eliminar tarea |
| GET | `/api/tasks/completed/{status}` | Filtrar por estado |

## 🎯 Características

### Frontend
- Componentes Standalone de Angular
- CRUD completo de tareas
- Interfaz responsive
- Validación de formularios

### Backend
- API REST con Quarkus
- Hibernate Panache para ORM
- Documentación OpenAPI/Swagger
- CORS configurado
- Hot reload en desarrollo

## 🛠️ Desarrollo

Este proyecto sigue Git Flow estándar con commits en la rama `main`.

## 📝 Licencia

Este es un proyecto de prueba técnica.

