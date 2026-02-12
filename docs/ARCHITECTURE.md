# Arquitectura del Sistema - Todo List

## Diagrama de Arquitectura General

```mermaid
graph TB
    subgraph "Frontend - Angular"
        A[Browser] --> B[Angular App]
        B --> C[TaskListComponent]
        C --> D[TaskService]
        D --> E[HttpClient]
    end
    
    subgraph "Backend - Quarkus"
        F[TaskResource<br/>REST API] --> G[TaskRepository]
        G --> H[Task Entity]
        H --> I[(MySQL Database)]
    end
    
    E -->|HTTP/JSON| F
    
    style A fill:#e1f5ff
    style B fill:#bbdefb
    style C fill:#90caf9
    style D fill:#64b5f6
    style E fill:#42a5f5
    style F fill:#ffccbc
    style G fill:#ffab91
    style H fill:#ff8a65
    style I fill:#ff7043
```

## Diagrama de Componentes Frontend

```mermaid
graph LR
    subgraph "Angular Standalone App"
        A[app.component.ts] --> B[TaskListComponent]
        B --> C[task.service.ts]
        C --> D[task.model.ts]
        
        B --> E[task-list.component.html]
        B --> F[task-list.component.css]
        
        C --> G[HttpClient]
        G --> H[Backend API]
    end
    
    style A fill:#4caf50
    style B fill:#66bb6a
    style C fill:#81c784
    style D fill:#a5d6a7
    style E fill:#c8e6c9
    style F fill:#c8e6c9
    style G fill:#e8f5e9
    style H fill:#f1f8e9
```

## Diagrama de Capas Backend

```mermaid
graph TB
    subgraph "Presentation Layer"
        A[TaskResource<br/>@Path /api/tasks]
    end
    
    subgraph "Business Layer"
        B[TaskRepository<br/>PanacheRepository]
    end
    
    subgraph "Data Layer"
        C[Task Entity<br/>@Entity]
        D[(MySQL<br/>todo_db)]
    end
    
    A -->|Inject| B
    B -->|Persist/Query| C
    C -->|Hibernate ORM| D
    
    style A fill:#ff9800
    style B fill:#ffa726
    style C fill:#ffb74d
    style D fill:#ffcc80
```

## Diagrama de Flujo - Crear Tarea

```mermaid
sequenceDiagram
    participant U as Usuario
    participant C as TaskListComponent
    participant S as TaskService
    participant API as TaskResource
    participant R as TaskRepository
    participant DB as MySQL

    U->>C: Click "Agregar Tarea"
    C->>C: Validar datos
    C->>S: createTask(task)
    S->>API: POST /api/tasks
    API->>R: persist(task)
    R->>DB: INSERT INTO task
    DB-->>R: Task guardada
    R-->>API: Task entity
    API-->>S: 201 Created + Task
    S-->>C: Observable<Task>
    C->>C: Actualizar lista
    C-->>U: Mostrar tarea creada
```

## Diagrama de Flujo - Actualizar Tarea

```mermaid
sequenceDiagram
    participant U as Usuario
    participant C as TaskListComponent
    participant S as TaskService
    participant API as TaskResource
    participant R as TaskRepository
    participant DB as MySQL

    U->>C: Click "Editar"
    C->>C: Mostrar formulario
    U->>C: Modificar y guardar
    C->>S: updateTask(id, task)
    S->>API: PUT /api/tasks/{id}
    API->>R: findById(id)
    R->>DB: SELECT * FROM task WHERE id=?
    DB-->>R: Task encontrada
    R-->>API: Task entity
    API->>API: Actualizar campos
    API->>DB: UPDATE task SET...
    DB-->>API: Task actualizada
    API-->>S: 200 OK + Task
    S-->>C: Observable<Task>
    C->>C: Actualizar lista
    C-->>U: Mostrar cambios
```

## Estructura de Directorios

### Backend (Quarkus)
```
backend/
├── src/
│   └── main/
│       ├── java/com/kiro/
│       │   ├── entity/
│       │   │   └── Task.java
│       │   ├── repository/
│       │   │   └── TaskRepository.java
│       │   └── resource/
│       │       └── TaskResource.java
│       └── resources/
│           └── application.properties
├── pom.xml
└── .mvn/
    └── settings.xml
```

### Frontend (Angular)
```
frontend/
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   └── task-list/
│   │   │       ├── task-list.component.ts
│   │   │       ├── task-list.component.html
│   │   │       └── task-list.component.css
│   │   ├── models/
│   │   │   └── task.model.ts
│   │   ├── services/
│   │   │   └── task.service.ts
│   │   ├── app.component.ts
│   │   └── app.config.ts
│   ├── index.html
│   └── main.ts
├── angular.json
├── package.json
└── tsconfig.json
```

## Tecnologías Utilizadas

### Frontend
- **Framework:** Angular 18 (Standalone Components)
- **HTTP Client:** Angular HttpClient
- **Estilos:** CSS puro
- **Build:** Angular CLI + Vite

### Backend
- **Framework:** Quarkus 3.6.4
- **Java:** 17
- **ORM:** Hibernate Panache
- **API:** JAX-RS (RESTEasy Reactive)
- **Documentación:** OpenAPI/Swagger
- **Base de Datos:** MySQL 8.x

### Comunicación
- **Protocolo:** HTTP/REST
- **Formato:** JSON
- **CORS:** Habilitado para localhost:4200

## Endpoints API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/tasks` | Listar todas las tareas |
| GET | `/api/tasks/{id}` | Obtener tarea por ID |
| POST | `/api/tasks` | Crear nueva tarea |
| PUT | `/api/tasks/{id}` | Actualizar tarea |
| DELETE | `/api/tasks/{id}` | Eliminar tarea |
| GET | `/api/tasks/completed/{status}` | Filtrar por estado |

## Acceso a Swagger UI

Una vez que el backend esté corriendo, puedes acceder a la documentación interactiva en:

**URL:** http://localhost:8080/swagger-ui

Desde ahí podrás:
- Ver todos los endpoints disponibles
- Probar las APIs directamente
- Ver los modelos de datos
- Descargar la especificación OpenAPI
