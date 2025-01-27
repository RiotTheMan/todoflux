package com.fluxapp.todoflux.service;

import com.fluxapp.todoflux.controller.AuthController;
import com.fluxapp.todoflux.controller.TodoController;
import com.fluxapp.todoflux.models.CheckItem;
import com.fluxapp.todoflux.models.TodoItem;
import com.fluxapp.todoflux.repository.CheckItemRepository;
import com.fluxapp.todoflux.repository.TodoItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TodoItemService {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    private final TodoItemRepository todoItemRepository;
    private final CheckItemRepository checkItemRepository;

    public TodoItemService(UserService userService, TodoItemRepository todoItemRepository, CheckItemRepository checkItemRepository) {
        this.userService = userService;
        this.todoItemRepository = todoItemRepository;
        this.checkItemRepository = checkItemRepository;
    }

// Api Data Calls
//    public List<TodoItem>  getAll() {
//        return (List<TodoItem>) todoItemRepository.findAll();
//    }
    public List<TodoItem>  getAll() {
        return (List<TodoItem>) todoItemRepository.findAll();
    }

    public List<TodoItem> getAllTodoItemsByUsername() {
        return todoItemRepository.findByUser(userService.getCurrentAuthenticatedUser());
    }

    public int createTodoItem(String title) {
        try {
            TodoItem todoItem = new TodoItem(userService.getCurrentAuthenticatedUser(), title);
            todoItemRepository.save(todoItem);
            return 1;
        } catch (Exception e) {
            log.error("Data Persistence/Integrity issue: {}", e.getMessage());
            return 0;
        }
    }

    @Transactional
    public int updateTodoTitle(Long id, String title) {
        try {
            todoItemRepository.updateTodoTitle(id, title);
            return 1;
        } catch (Exception e) {
            log.error("Data Persistence/Integrity issue Todo Item: {}", e.getMessage());
            return 0;
        }
    }

    @Transactional
    public int deleteTodoItem(String checkItemId) {
        try {
            Long id = Long.valueOf(checkItemId);
            Optional<TodoItem> todoItem = todoItemRepository.findById(id);
            List<CheckItem> checks = checkItemRepository.findAllByTodoItem(todoItem);
            checkItemRepository.deleteAll(checks);
            todoItemRepository.deleteById(id);
            return 1;
//            return todoItemRepository.deleteTodoItemById(id);
        } catch (NumberFormatException | DataIntegrityViolationException e) {
            log.error("Error deleting check item: {}", e.getMessage());
            return 0;
        }
    }

}
