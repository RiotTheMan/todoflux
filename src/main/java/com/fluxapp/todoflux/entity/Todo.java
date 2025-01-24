package com.fluxapp.todoflux.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
@PersistenceContext
public class Todo {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private FluxUser fluxUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FluxUser getUser() {
        return fluxUser;
    }

    public void setUser(FluxUser fluxUser) {
        this.fluxUser = fluxUser;
    }
}
