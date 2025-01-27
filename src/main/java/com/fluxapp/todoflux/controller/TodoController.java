package com.fluxapp.todoflux.controller;

import com.fluxapp.todoflux.models.FluxUser;
import com.fluxapp.todoflux.models.TodoItem;
import com.fluxapp.todoflux.repository.TodoItemRepository;
import com.fluxapp.todoflux.repository.UserRepository;
import com.fluxapp.todoflux.service.TodoItemService;
import com.fluxapp.todoflux.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoItemService todoItemService;

    public TodoController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @GetMapping("/getAll")
    public List<TodoItem> getAll() {
        return  todoItemService.getAll();
    }

    // Reluctant to pass the user ID willy nilly,
    // I do an extra data call to get the flux user
    //  from the authenticated User in this Context
    @GetMapping("/getAllToDoByUser")
    public ResponseEntity<?> getAllTodoItemsByUsername() {
        return ResponseEntity.accepted().body(todoItemService.getAllTodoItemsByUsername());
    }

    @PostMapping("/createTodo")
    public ResponseEntity<?> createTodoItem(@RequestParam String title) {
        int success = todoItemService.createTodoItem(title);
        if (success > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/updateTodoTitle")
    public ResponseEntity<?> updateTodoTitle(
            @RequestParam Long id,
            @RequestParam String title
    ) {

        int success = todoItemService.updateTodoTitle(id, title);
        if (success > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCheckItem(@RequestParam String checkItemId) {
        int success = todoItemService.deleteTodoItem(checkItemId);
        if (success > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CheckItem not found or invalid ID");
        }
    }



}
