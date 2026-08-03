package com.example.todoapi.repository;

import com.example.todoapi.model.User;
import com.example.todoapi.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByUser(User user);
    List<Todo> findByUserAndCompleted(User user, boolean completed);
}
