package com.example.todoapi.controller;

import com.example.todoapi.dto.TodoRequest;
import com.example.todoapi.model.Todo;
import com.example.todoapi.model.User;
import com.example.todoapi.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody TodoRequest request) {
        User user = getCurrentUser();
        return ResponseEntity.ok(todoService.createTodo(user, request));
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(
            @RequestParam(required = false) Boolean completed
    ) {
        User user = getCurrentUser();
        if (completed != null) {
            return ResponseEntity.ok(todoService.getUserTodosByStatus(user, completed));
        }
        return ResponseEntity.ok(todoService.getUserTodos(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long id) {
        User user = getCurrentUser();
        return ResponseEntity.ok(todoService.getTodoById(id, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequest request
    ) {
        User user = getCurrentUser();
        return ResponseEntity.ok(todoService.updateTodo(id, user, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        User user = getCurrentUser();
        todoService.deleteTodo(id, user);
        return ResponseEntity.noContent().build();
    }
}
