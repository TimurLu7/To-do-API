package com.example.todoapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequest {

    @NotBlank(message = "Заголовок задачи обязателен")
    private String title;

    private String description;

    private boolean completed;
}