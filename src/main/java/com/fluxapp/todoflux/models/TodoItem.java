package com.fluxapp.todoflux.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private FluxUser user;

    @OneToMany(mappedBy = "todoItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CheckItem> items;

    @Transient
    private int totalItemsCount;

    // Constructors
    public TodoItem() {}

    public TodoItem(FluxUser user, String title) {
        this.user = user;
        this.title = title;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public FluxUser getUser() { return user; }
    public void setUser(FluxUser user) { this.user = user; }

    public List<CheckItem> getItems() { return items; }
    public void setItems(List<CheckItem> items) { this.items = items; }

    public int getTotalItemsCount() { return (items != null) ? items.size() : 0; }
}
