package com.kiro.resource;

import com.kiro.entity.Task;
import com.kiro.repository.TaskRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;

@Path("/api/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tasks", description = "Operaciones CRUD para gestión de tareas")
public class TaskResource {
    
    @Inject
    TaskRepository taskRepository;
    
    @GET
    @Operation(summary = "Listar todas las tareas", description = "Obtiene todas las tareas ordenadas por fecha de creación")
    @APIResponse(responseCode = "200", description = "Lista de tareas obtenida exitosamente",
                 content = @Content(schema = @Schema(implementation = Task.class)))
    public List<Task> getAllTasks() {
        return taskRepository.findAllOrderedByCreatedAt();
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener tarea por ID", description = "Obtiene una tarea específica por su identificador")
    @APIResponse(responseCode = "200", description = "Tarea encontrada",
                 content = @Content(schema = @Schema(implementation = Task.class)))
    @APIResponse(responseCode = "404", description = "Tarea no encontrada")
    public Response getTaskById(@Parameter(description = "ID de la tarea") @PathParam("id") Long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(task).build();
    }
    
    @POST
    @Transactional
    @Operation(summary = "Crear nueva tarea", description = "Crea una nueva tarea en el sistema")
    @APIResponse(responseCode = "201", description = "Tarea creada exitosamente",
                 content = @Content(schema = @Schema(implementation = Task.class)))
    public Response createTask(
            @Parameter(description = "Datos de la nueva tarea") Task task) {
        taskRepository.persist(task);
        return Response.status(Response.Status.CREATED).entity(task).build();
    }
    
    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Actualizar tarea", description = "Actualiza una tarea existente")
    @APIResponse(responseCode = "200", description = "Tarea actualizada exitosamente",
                 content = @Content(schema = @Schema(implementation = Task.class)))
    @APIResponse(responseCode = "404", description = "Tarea no encontrada")
    public Response updateTask(
            @Parameter(description = "ID de la tarea") @PathParam("id") Long id,
            @Parameter(description = "Datos actualizados de la tarea") Task updatedTask) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        task.title = updatedTask.title;
        task.description = updatedTask.description;
        task.completed = updatedTask.completed;
        
        return Response.ok(task).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea del sistema")
    @APIResponse(responseCode = "204", description = "Tarea eliminada exitosamente")
    @APIResponse(responseCode = "404", description = "Tarea no encontrada")
    public Response deleteTask(@Parameter(description = "ID de la tarea") @PathParam("id") Long id) {
        boolean deleted = taskRepository.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
    
    @GET
    @Path("/completed/{completed}")
    @Operation(summary = "Filtrar tareas por estado", description = "Obtiene tareas filtradas por estado de completado")
    @APIResponse(responseCode = "200", description = "Lista de tareas filtradas",
                 content = @Content(schema = @Schema(implementation = Task.class)))
    public List<Task> getTasksByStatus(
            @Parameter(description = "Estado de completado (true/false)") @PathParam("completed") Boolean completed) {
        return taskRepository.findByCompleted(completed);
    }
}
