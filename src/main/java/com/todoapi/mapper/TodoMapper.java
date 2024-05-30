package com.todoapi.mapper;

import com.todoapi.dto.CategoryDto;
import com.todoapi.dto.TodoDto;
import com.todoapi.model.Todo;

public class TodoMapper {

    // Convert Todo JPA Entity into TodoDto
    public static TodoDto mapToTodoDto(Todo todo) {
        TodoDto todoDto = new TodoDto();
        todoDto.setId(todo.getId());
        todoDto.setTitle(todo.getTitle());
        todoDto.setDescription(todo.getDescription());
        todoDto.setCompleted(todo.isCompleted());

        // Convert Category entity to CategoryDto
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(todo.getCategory().getId());
        categoryDto.setName(todo.getCategory().getName());
        categoryDto.setDescription(todo.getCategory().getDescription());

        todoDto.setCategory(categoryDto);

        return todoDto;
    }

    // Convert ProductDto into Product JPA Entity
    public static Todo mapToTodo(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setId(todoDto.getId());
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(todo.getCategory().getId());
        categoryDto.setName(todo.getCategory().getName());
        categoryDto.setDescription(todo.getCategory().getDescription());
        todoDto.setCategory(categoryDto);

        return todo;
    }
}
