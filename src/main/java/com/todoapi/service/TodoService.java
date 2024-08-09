package com.todoapi.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.todoapi.dto.TodoCreateDto;
import com.todoapi.dto.TodoDto;
import com.todoapi.model.Todo;
import com.todoapi.utility.FormatResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface TodoService {
    Todo getTodoById(int todoId);

    List<TodoDto> getAllTodos();

    TodoDto addTodo(TodoCreateDto newTodo);

    ResponseEntity<FormatResponse> updateTodo(int id, TodoCreateDto updatedTodo);

    ResponseEntity<FormatResponse> deleteTodo(Integer idTodo);

    List<Todo> searchTodos(String keyword, Pageable pageable);

    List<Todo> searchTodosByCategoryId(int categoryId, Pageable pageable);

    ResponseEntity<FormatResponse> completeTodo(Integer idTodo, boolean value);

    String uploadImage(int id, MultipartFile multipartFile, String uploadPath) throws IOException;

    ResponseEntity<Resource> downloadImage(String fileName, HttpServletRequest request, String uploadPath)
            throws IOException;

}
