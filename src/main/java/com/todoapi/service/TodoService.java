package com.todoapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todoapi.model.Todo;

@Service
public interface TodoService {
    Todo getTodoById(int todoId);

    ResponseEntity<String> updateTodo(int id, Todo updatedTodo);

    ResponseEntity<String> deleteTodo(Integer idTodo);

    List<Todo> searchTodos(String keyword);

    List<Todo> searchTodosByCategoryId(int categoryId);

    List<Todo> getAllTodos();
}
