package com.todoapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoCreateDto {
    private int id;
    private String title;
    private String description;
    private int categoryId;
    private int tagId;
    private int statusId;
    private int userId;
    private boolean completed;
    private String imageUrl;
    private LocalDateTime createdAt;
}