package com.example.todoapi.service;

import com.example.todoapi.dto.TodoRequest;
import com.example.todoapi.model.Todo;
import com.example.todoapi.model.User;
import com.example.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Todo createTodo(User user, TodoRequest request) {
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.isCompleted())
                .user(user)
                .build();
        return todoRepository.save(todo);
    }

    public List<Todo> getUserTodos(User user) {
        return todoRepository.findByUser(user);
    }

    public List<Todo> getUserTodosByStatus(User user, boolean completed) {
        return todoRepository.findByUserAndCompleted(user, completed);
    }

    public Todo getTodoById(Long id, User user) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("У вас нет доступа к этой задаче");
        }
        return todo;
    }

    public Todo updateTodo(Long id, User user, TodoRequest request) {
        Todo todo = getTodoById(id, user);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id, User user) {
        Todo todo = getTodoById(id, user);
        todoRepository.delete(todo);
    }
}