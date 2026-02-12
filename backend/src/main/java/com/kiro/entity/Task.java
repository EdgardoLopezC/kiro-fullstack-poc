package com.kiro.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
public class Task extends PanacheEntity {
    
    @Column(nullable = false)
    public String title;
    
    public String description;
    
    @Column(nullable = false)
    public Boolean completed = false;
    
    @Column(nullable = false)
    public LocalDateTime createdAt = LocalDateTime.now();
}
