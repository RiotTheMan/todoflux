package com.fluxapp.todoflux.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Checklist {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Transient
    private int totalItemsCount;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<CheckItem> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(int totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }

    public List<CheckItem> getItems() {
        return items;
    }

    public void setItems(List<CheckItem> items) {
        this.items = items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
