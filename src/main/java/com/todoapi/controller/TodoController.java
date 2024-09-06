package com.todoapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.multipart.MultipartFile;

import com.todoapi.dto.TodoCreateDto;
import com.todoapi.dto.TodoDto;
import com.todoapi.model.Todo;
import com.todoapi.repository.TodoRepository;
import com.todoapi.service.TodoService;
import com.todoapi.utility.FormatResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

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

    @Value("${upload.path}")
    private String uploadPath;

    // Get all Todos Rest Api
    // http://localhost:8081/api/v1/todos
    @Operation(summary = "Get all todos", description = "Retrieve a list of all todos")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer tagId,
            @RequestParam(required = false) Integer statusId) {

        Pageable pageable = PageRequest.of(page, size);


        if (keyword == null) keyword = ""; 
        if (categoryId == null) categoryId = 0;
        if (tagId == null) tagId = 0;
        if (statusId == null) statusId = 0;

        Iterable<Todo> todos = repository.searchTodos(keyword, categoryId, tagId, statusId, pageable);

        List<TodoDto> todosDto = new ArrayList<>();
        for (Todo todo : todos) {
            TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
            todosDto.add(todoDto);
        }

        Page<Todo> todosPage = repository.findAll(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("todos", todosDto);
        response.put("totalItems", todosPage.getTotalElements());

        return ResponseEntity.ok(response);
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
    public ResponseEntity<TodoDto> add(@RequestBody TodoCreateDto todoDto) {
        TodoDto savedTodo = service.addTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }
    //

    // Update Todo Rest Api
    // http://localhost:8081/api/v1/todos/1
    @Operation(summary = "Update Todo REST API", description = "Update Todo on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PatchMapping("/{id}")
    public ResponseEntity<FormatResponse> update(@PathVariable Integer id, @RequestBody TodoCreateDto updatedTodo) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Todo not found"), HttpStatus.NOT_FOUND);
        }
        return service.updateTodo(id, updatedTodo);
    }
    //

    // Delete Todo Rest Api
    // http://localhost:8081/api/v1/todos/1
    @Operation(summary = "Delete Todo REST API", description = "Delete Todo on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FormatResponse> delete(@PathVariable Integer id) {
        service.deleteTodo(id);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Todo deleted successfully!"), HttpStatus.OK);
    }
    //

    // Search Todo by Category Rest Api
    // http://localhost:8081/api/v1/todos/searchByCategoryId
    @Operation(summary = "Search Todo by Category Api REST API", description = "Search Todo by Category Api on database by id")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/searchByCategoryId")
    public ResponseEntity<Map<String, Object>> searchTodosByCategoryId(@RequestParam int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Todo> todos = service.searchTodosByCategoryId(categoryId, pageable);
        List<TodoDto> todosDto = todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("todos", todosDto);
        response.put("totalItems", todos.size());

        return ResponseEntity.ok(response);
    }
    //

    // Set a todo to complete
    // http://localhost:8081/api/v1/todos/1/complete
    @PatchMapping("{id}/complete")
    public ResponseEntity<FormatResponse> completeTodo(@PathVariable("id") int todoId) {
        return service.completeTodo(todoId, true);
    }

    // Set a todo to in progress
    // http://localhost:8081/api/v1/todos/1/inprogress
    @PatchMapping("{id}/inprogress")
    public ResponseEntity<FormatResponse> inCompleteTodo(@PathVariable("id") int todoId) {
        return service.completeTodo(todoId, false);
    }

    @Operation(summary = "Upload todo image", description = "Upload photo of todo")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<FormatResponse> uploadImage(@PathVariable int id,
            @RequestParam("image") MultipartFile multipartFile) {
        try {
            String fileDownloadUri = service.uploadImage(id, multipartFile, uploadPath);
            return new ResponseEntity<>(new FormatResponse(fileDownloadUri), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new FormatResponse("Error uploading image"), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Download todo image", description = "Download photo of todo")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName, HttpServletRequest request) {
        try {
            return service.downloadImage(fileName, request, uploadPath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
