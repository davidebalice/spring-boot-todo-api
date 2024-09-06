package com.todoapi.dto;

import java.time.LocalDate;

import com.todoapi.model.Category;
import com.todoapi.model.Status;
import com.todoapi.model.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private int id;
    private String title;
    private String description;
    private Category category;
    private Tag tag;
    private Status status;
    private UserTodoDto user;
    private boolean completed;
    private String imageUrl;
    private LocalDate date;
}