package com.fluxapp.todoflux.repository;


import com.fluxapp.todoflux.models.FluxUser;
import com.fluxapp.todoflux.models.TodoItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {


    List<TodoItem> getAllTodoItemsByUser(FluxUser user);

    @Query("SELECT e FROM TodoItem e WHERE e.user = :user")
    List<TodoItem> findByUser(@Param("user") FluxUser user);

    @Modifying
    @Query("UPDATE TodoItem t SET t.title = :title WHERE t.id = :id")
    void updateTodoTitle(@Param("id") Long id, @Param("title") String title);

    @Modifying
    @Query("DELETE FROM TodoItem t WHERE t.id = :id")
    int deleteTodoItemById(@Param("id") Long id);

}
