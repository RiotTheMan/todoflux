package com.fluxapp.todoflux.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class CheckItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "todo_item_id", nullable = false)
    private TodoItem todoItem;

    // Constructors
    public CheckItem() {}

    public CheckItem(String description, TodoItem todoItem) {
        this.description = description;
        this.todoItem = todoItem;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TodoItem getTodoItem() { return todoItem; }
    public void setTodoItem(TodoItem todoItem) { this.todoItem = todoItem; }
}