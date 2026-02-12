package com.kiro.repository;

import com.kiro.entity.Task;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {
    
    public List<Task> findByCompleted(Boolean completed) {
        return list("completed", completed);
    }
    
    public List<Task> findAllOrderedByCreatedAt() {
        return list("ORDER BY createdAt DESC");
    }
}
