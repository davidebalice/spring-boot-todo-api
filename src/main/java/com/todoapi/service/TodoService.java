package com.todoapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todoapi.dto.TodoDto;
import com.todoapi.model.Todo;

@Service
public interface TodoService {
    Todo getTodoById(int todoId);

    List<TodoDto> getAllTodos();

    TodoDto addTodo(TodoDto newTodo);

    ResponseEntity<String> updateTodo(int id, Todo updatedTodo);

    ResponseEntity<String> deleteTodo(Integer idTodo);

    List<Todo> searchTodos(String keyword);

    List<Todo> searchTodosByCategoryId(int categoryId);

}
