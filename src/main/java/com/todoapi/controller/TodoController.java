package com.todoapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todoapi.dto.TodoDto;
import com.todoapi.model.Todo;
import com.todoapi.repository.TodoRepository;
import com.todoapi.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CRUD REST APIs for Todo Resource", description = "TODOS CRUD REST APIs - Create Todo, Update Todo, Get Todo, Get All Todos, Delete Todo")
@RestController
@RequestMapping("/api/v1/todos/")
public class TodoController {

    private final TodoRepository repository;
    private final TodoService service;

    public TodoController(TodoRepository repository, TodoService service) {
        this.repository = repository;
        this.service = service;
    }

    @Autowired
    private ModelMapper modelMapper;

    // Get all Todos Rest Api
    // http://localhost:8081/api/v1/todos
    @Operation(summary = "Get all todos", description = "Retrieve a list of all todos")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/")
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = service.getAllTodos();
        return ResponseEntity.ok(todos);
    }
    //

    // Get single Todo Rest Api (get id by url)
    // http://localhost:8081/api/v1/todos/1
    @Operation(summary = "Get Todo By ID REST API", description = "Get Todo By ID REST API is used to get a single Todo from the database, get id by url")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getById(@PathVariable Integer id) {
        Todo todo = service.getTodoById(id);
        if (todo != null) {
            TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
            return new ResponseEntity<>(todoDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //

    // Get single Todo Rest Api (get id by querystring)
    // http://localhost:8081/api/v1/todo?id=1
    @Operation(summary = "Get Todo By ID REST API", description = "Get Todo By ID REST API is used to get a single Todo from the database, get id by querystring")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/todo")
    public Todo getByIdQs(@RequestParam Integer id) {
        return service.getTodoById(id);
    }
    //

    // Add new Todo Rest Api
    // http://localhost:8081/api/v1/todos/add
    @Operation(summary = "Create new  Todo REST API", description = "Save new Todo on database")
    @ApiResponse(responseCode = "201", description = "HTTP Status 201 Created")
    @PostMapping("/add")
    public ResponseEntity<TodoDto> add(@RequestBody TodoDto todoDto) {
        TodoDto savedTodo = service.addTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }
    //

    // Update Todo Rest Api
    // http://localhost:8081/api/v1/todos/1
    @Operation(summary = "Update Todo REST API", description = "Update Todo on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Todo updatedTodo) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>("Todo not found", HttpStatus.NOT_FOUND);
        }
        return service.updateTodo(id, updatedTodo);
    }
    //

    // Delete Todo Rest Api
    // http://localhost:8081/api/v1/todos/1
    @Operation(summary = "Delete Todo REST API", description = "Delete Todo on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.deleteTodo(id);
        return ResponseEntity.ok("Todo deleted successfully");
    }
    //

    // Search Todo Rest Api
    // http://localhost:8081/api/v1/todos/search
    @Operation(summary = "Search Todo REST API", description = "Search Todo on database by filter")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/search")
    public ResponseEntity<List<TodoDto>> searchTodos(@RequestParam("keyword") String keyword) {
        List<Todo> todos = service.searchTodos(keyword);
        List<TodoDto> todosDto = todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(todosDto);
    }
    //

    // Search Todo by Category Rest Api
    // http://localhost:8081/api/v1/todos/searchByCategoryId
    @Operation(summary = "Search Todo by Category Api REST API", description = "Search Todo by Category Api on database by id")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/searchByCategoryId")
    public ResponseEntity<List<TodoDto>> searchTodosByCategoryId(@RequestParam int categoryId) {
        List<Todo> todos = service.searchTodosByCategoryId(categoryId);
        List<TodoDto> todosDto = todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(todosDto);
    }
    //

    // Set a todo to complete
    // http://localhost:8081/api/v1/todos/1/complete
    @PatchMapping("{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") int todoId) {
        TodoDto updatedTodo = service.completeTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    // Set a todo to in progress
    // http://localhost:8081/api/v1/todos/1/inprogress
    @PatchMapping("{id}/inprogress")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") int todoId) {
        TodoDto updatedTodo = service.inProgressTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }

}
