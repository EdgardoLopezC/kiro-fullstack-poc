import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../../services/task.service';
import { Task } from '../../models/task.model';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];
  newTask: Task = { title: '', description: '', completed: false };
  editingTask: Task | null = null;

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.taskService.getAllTasks().subscribe({
      next: (tasks) => this.tasks = tasks,
      error: (err) => console.error('Error loading tasks:', err)
    });
  }

  createTask(): void {
    if (this.newTask.title.trim()) {
      this.taskService.createTask(this.newTask).subscribe({
        next: () => {
          this.loadTasks();
          this.newTask = { title: '', description: '', completed: false };
        },
        error: (err) => console.error('Error creating task:', err)
      });
    }
  }

  editTask(task: Task): void {
    this.editingTask = { ...task };
  }

  updateTask(): void {
    if (this.editingTask && this.editingTask.id) {
      this.taskService.updateTask(this.editingTask.id, this.editingTask).subscribe({
        next: () => {
          this.loadTasks();
          this.editingTask = null;
        },
        error: (err) => console.error('Error updating task:', err)
      });
    }
  }

  deleteTask(id: number | undefined): void {
    if (id && confirm('¿Estás seguro de eliminar esta tarea?')) {
      this.taskService.deleteTask(id).subscribe({
        next: () => this.loadTasks(),
        error: (err) => console.error('Error deleting task:', err)
      });
    }
  }

  toggleComplete(task: Task): void {
    if (task.id) {
      const updatedTask = { ...task, completed: !task.completed };
      this.taskService.updateTask(task.id, updatedTask).subscribe({
        next: () => this.loadTasks(),
        error: (err) => console.error('Error toggling task:', err)
      });
    }
  }

  cancelEdit(): void {
    this.editingTask = null;
  }
}
