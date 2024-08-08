package com.todoapi.dto;

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
    private int categoryId;
    private Tag tag;
    private int tagId;
    private Status status;
    private int statusId;
    private boolean completed;
    private String imageUrl;
}