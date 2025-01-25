package com.fluxapp.todoflux.models;

import jakarta.persistence.*;

@Entity
public class CheckItem {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "checklist_id")
    @ManyToOne
    private Checklist checklist;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
