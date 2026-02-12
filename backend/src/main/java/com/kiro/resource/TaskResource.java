package com.kiro.resource;

import com.kiro.entity.Task;
import com.kiro.repository.TaskRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {
    
    @Inject
    TaskRepository taskRepository;
    
    @GET
    public List<Task> getAllTasks() {
        return taskRepository.findAllOrderedByCreatedAt();
    }
    
    @GET
    @Path("/{id}")
    public Response getTaskById(@PathParam("id") Long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(task).build();
    }
    
    @POST
    @Transactional
    public Response createTask(Task task) {
        taskRepository.persist(task);
        return Response.status(Response.Status.CREATED).entity(task).build();
    }
    
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateTask(@PathParam("id") Long id, Task updatedTask) {
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
    public Response deleteTask(@PathParam("id") Long id) {
        boolean deleted = taskRepository.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
    
    @GET
    @Path("/completed/{completed}")
    public List<Task> getTasksByStatus(@PathParam("completed") Boolean completed) {
        return taskRepository.findByCompleted(completed);
    }
}
