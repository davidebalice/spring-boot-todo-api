package com.todoapi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todoapi.dto.TodoDto;
import com.todoapi.exception.ResourceNotFoundException;
import com.todoapi.model.Todo;
import com.todoapi.repository.TodoRepository;
import com.todoapi.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public TodoServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TodoDto> getAllTodos() {

        List<Todo> todos = repository.findAll();

        return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Todo getTodoById(int todoId) {
        return repository.findById(todoId).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", todoId));
    }

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo savedTodo = repository.save(todo);
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
        return savedTodoDto;
    }

    @Override
    public ResponseEntity<String> updateTodo(int id, Todo updatedTodo) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<>("Todo not found", HttpStatus.NOT_FOUND);
            }

            Todo existingTodo = repository.findById(id).get();

            if (updatedTodo.getTitle() != null) {
                existingTodo.setTitle(updatedTodo.getTitle());
            }
            if (updatedTodo.getDescription() != null) {
                existingTodo.setDescription(updatedTodo.getDescription());
            }
            if (updatedTodo.getCategory() != null) {
                existingTodo.setCategory(updatedTodo.getCategory());
            }

            if (updatedTodo.isCompleted() == true) {
                existingTodo.setCompleted(true);
            } else {
                existingTodo.setCompleted(false);
            }

            repository.save(existingTodo);

            return new ResponseEntity<>("Todo updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating todo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteTodo(Integer todoId) {
        Optional<Todo> pOptional = repository.findById(todoId);
        if (pOptional.isPresent()) {
            Todo p = pOptional.get();
            repository.delete(p);
            return new ResponseEntity<>("Todo deleted successfully", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Todo", "id", todoId);
        }
    }

    @Override
    public List<Todo> searchTodos(String keyword) {
        return repository.searchTodos(keyword);
    }

    @Override
    public List<Todo> searchTodosByCategoryId(int categoryId) {
        return repository.findByCategoryId(categoryId);
    }
}
