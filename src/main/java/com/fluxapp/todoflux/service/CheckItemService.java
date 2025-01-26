package com.fluxapp.todoflux.service;

import com.fluxapp.todoflux.models.CheckItem;
import com.fluxapp.todoflux.models.TodoItem;
import com.fluxapp.todoflux.repository.CheckItemRepository;
import com.fluxapp.todoflux.repository.TodoItemRepository;
import jakarta.persistence.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CheckItemService {


    private static final Logger log = LoggerFactory.getLogger(CheckItemService.class);
    private final CheckItemRepository checkItemRepository;
    private final TodoItemRepository todoItemRepository;

    public CheckItemService(CheckItemRepository checkItemRepository, TodoItemRepository todoItemRepository) {
        this.checkItemRepository = checkItemRepository;
        this.todoItemRepository = todoItemRepository;
    }

    public int createCheckItem(String todoId) {
        try {
            Optional<TodoItem> todo =todoItemRepository.findById(Long.valueOf(todoId));
            CheckItem checkItem = new CheckItem();
            checkItem.setTodoItem(todo.get());
            checkItem.setDescription("New Item");
            checkItemRepository.save(checkItem);
            return 1;
        } catch (Exception e) {
            log.error("Data Persistence/Integrity issue Check Item: {}", e.getMessage());
            return 0;
        }
    }

    @Transactional
    public int updateCheckItemTitle(Long id, String title) {
        try {
            checkItemRepository.updateCheckItemTitle(id, title);
            return 1;
        } catch (Exception e) {
            log.error("Data Persistence/Integrity issue Check Item Name Update: {}", e.getMessage());
            return 0;
        }
    }

    @Transactional
    public int deleteCheckItem(String checkItemId) {
        try {
            Long id = Long.valueOf(checkItemId);
            return checkItemRepository.deleteCheckItemById(id);
        } catch (NumberFormatException | DataIntegrityViolationException e) {
            log.error("Error deleting check item: {}", e.getMessage());
            return 0;
        }
    }

    public List<CheckItem> getCheckItemsByTodoId(String todoId) {
        Optional<TodoItem> todo =todoItemRepository.findById(Long.valueOf(todoId));
        return checkItemRepository.findAllByTodoItem(todo);
    }
}
