package com.todoapi.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todoapi.dto.TodoDto;
import com.todoapi.exception.ResourceNotFoundException;
import com.todoapi.model.Category;
import com.todoapi.model.Status;
import com.todoapi.model.Tag;
import com.todoapi.model.Todo;
import com.todoapi.repository.CategoryRepository;
import com.todoapi.repository.StatusRepository;
import com.todoapi.repository.TagRepository;
import com.todoapi.repository.TodoRepository;
import com.todoapi.service.TodoService;
import com.todoapi.utility.FileUploadUtil;
import com.todoapi.utility.FormatResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final StatusRepository statusRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TodoServiceImpl(TodoRepository repository, CategoryRepository categoryRepository,
            TagRepository tagRepository, StatusRepository statusRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.statusRepository = statusRepository;
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
    public ResponseEntity<FormatResponse> updateTodo(int id, TodoDto updatedTodo) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<FormatResponse>(new FormatResponse("Todo not found"),
                        HttpStatus.NOT_FOUND);
            }

            Todo existingTodo = repository.findById(id).get();

            if (updatedTodo.getTitle() != null) {
                existingTodo.setTitle(updatedTodo.getTitle());
            }
            if (updatedTodo.getDescription() != null) {
                existingTodo.setDescription(updatedTodo.getDescription());
            }
            if (updatedTodo.getCategoryId() >= 1) {
                Category category = categoryRepository.findById(updatedTodo.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));
                existingTodo.setCategory(category);
            }
            if (updatedTodo.getCategoryId() >= 1) {
                Category category = categoryRepository.findById(updatedTodo.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));
                existingTodo.setCategory(category);
            }
            if (updatedTodo.getTagId() >= 1) {
                Tag tag = tagRepository.findById(updatedTodo.getTagId())
                        .orElseThrow(() -> new RuntimeException("Tag not found"));
                existingTodo.setTag(tag);
            }
            if (updatedTodo.getStatusId() >= 1) {
                Status status = statusRepository.findById(updatedTodo.getStatusId())
                        .orElseThrow(() -> new RuntimeException("Tag not found"));
                existingTodo.setStatus(status);
            }
            if (updatedTodo.isCompleted() == true) {
                existingTodo.setCompleted(true);
            } else {
                existingTodo.setCompleted(false);
            }

            if (updatedTodo.getImageUrl() != null) {
                existingTodo.setImageUrl(updatedTodo.getImageUrl());
            }

            repository.save(existingTodo);

            return new ResponseEntity<FormatResponse>(new FormatResponse("Todo updated successfully!"),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Error updating"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<FormatResponse> deleteTodo(Integer todoId) {
        Optional<Todo> pOptional = repository.findById(todoId);
        if (pOptional.isPresent()) {
            Todo p = pOptional.get();
            repository.delete(p);
            return new ResponseEntity<FormatResponse>(new FormatResponse("Todo deleted successfully"),
                    HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Todo", "id", todoId);
        }
    }

    @Override
    public List<Todo> searchTodos(String keyword, Pageable pageable) {
        return repository.searchTodos(keyword, pageable);
    }

    @Override
    public List<Todo> searchTodosByCategoryId(int categoryId, Pageable pageable) {
        return repository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public ResponseEntity<FormatResponse> completeTodo(Integer id, boolean value) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<FormatResponse>(new FormatResponse("Todo not found"),
                        HttpStatus.NOT_FOUND);
            }

            Todo existingTodo = repository.findById(id).get();

            existingTodo.setCompleted(value);

            repository.save(existingTodo);
            if (value) {
                return new ResponseEntity<FormatResponse>(new FormatResponse("Todo set to complete!"),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<FormatResponse>(new FormatResponse("Todo set to in progress!"),
                        HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Error updating"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String uploadImage(int id, MultipartFile multipartFile, String uploadPath) throws IOException {

        if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
            throw new IllegalArgumentException("Invalid file");
        }

        String fileName = id + "_" + StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = uploadPath + "/image";
        Path filePath = Paths.get(uploadDir, fileName);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        Todo recipe = getTodoById(id);
        TodoDto todoDto = modelMapper.map(recipe, TodoDto.class);
        todoDto.setImageUrl(fileName);
        updateTodo(id, todoDto);

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/recipes/image/")
                .path(fileName)
                .toUriString();
    }

    @Override
    public ResponseEntity<Resource> downloadImage(String fileName, HttpServletRequest request, String uploadPath)
            throws IOException {
        Path filePath = Paths.get(uploadPath + "/image").resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
