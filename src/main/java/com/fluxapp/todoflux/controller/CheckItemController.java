package com.fluxapp.todoflux.controller;

import com.fluxapp.todoflux.models.CheckItem;
import com.fluxapp.todoflux.service.CheckItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check")
public class CheckItemController {

    private final CheckItemService checkItemService;

    public CheckItemController(CheckItemService checkItemService) {
        this.checkItemService = checkItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCheckItem(@RequestParam String todoId) {
        int success = checkItemService.createCheckItem(todoId);
        if (success > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/updateCheckItemTitle")
    public ResponseEntity<?> updateCheckItemTitle(
            @RequestParam Long id,
            @RequestParam String title
    ) {

        int success = checkItemService.updateCheckItemTitle(id, title);
        if (success > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCheckItem(@RequestParam String checkItemId) {
        int success = checkItemService.deleteCheckItem(checkItemId);
        if (success > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CheckItem not found or invalid ID");
        }
    }

    @GetMapping("/getAllCheckItemsByTodoId")
    public ResponseEntity<List<CheckItem>> getAllCheckItemsByTodoId(@RequestParam String todoId) {
        List<CheckItem> items = checkItemService.getCheckItemsByTodoId(todoId);
        return ResponseEntity.ok(items);
    }

}
