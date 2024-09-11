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


import com.todoapi.config.DemoMode;

import com.todoapi.exception.DemoModeException;

import com.todoapi.model.Tag;
import com.todoapi.repository.TagRepository;
import com.todoapi.service.TagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

//@Tag(name = "CRUD REST APIs for Tag Resource", description = "CATEGORIES CRUD REST APIs - Create Tag, Update Tag, Get Tag, Get All Categories, Delete Tag")
@RestController
@RequestMapping("/api/v1/tags/")
public class TagController {

    private final TagRepository repository;
    private final TagService service;

    public TagController(TagRepository repository, TagService service) {
        this.repository = repository;
        this.service = service;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DemoMode demoMode;

    // Get all Categories Rest Api
    // http://localhost:8081/api/v1/tags
    @Operation(summary = "Get all tags", description = "Retrieve a list of all tags")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/")
    public ResponseEntity<List<Tag>> list() {
        List<Tag> tags = (List<Tag>) repository.findAll();

        List<Tag> tagsArray = tags.stream()
                .map(category -> modelMapper.map(category, Tag.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(tagsArray);
    }
    //

    // Get single Tag Rest Api (get id by url)
    // http://localhost:8081/api/v1/tags/1
    @Operation(summary = "Get Tag By ID REST API", description = "Get Tag By ID REST API is used to get a single Tag from the database, get id by url")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/{id}")
    public Tag getById(@PathVariable Integer id) {
        return service.getTagById(id);
    }
    //

    // Add new Tag Rest Api
    // http://localhost:8081/api/v1/tags/add
    @Operation(summary = "Crate new Tag REST API", description = "Save new Tag on database")
    @ApiResponse(responseCode = "201", description = "HTTP Status 201 Created")
    @PostMapping("/add")
    public ResponseEntity<String> add(@Valid @RequestBody Tag p) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        repository.save(p);
        return new ResponseEntity<>("Tag addedd successfully!", HttpStatus.OK);
    }
    //

    // Update Tag Rest Api
    // http://localhost:8081/api/v1/tags/1
    @Operation(summary = "Update Tag REST API", description = "Update Tag on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Tag updatedTag) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        return service.updateTag(id, updatedTag);
    }
    //

    // Delete Tag Rest Api
    // http://localhost:8081/api/v1/tags/1
    @Operation(summary = "Delete Tag REST API", description = "Delete Tag on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        service.deleteTag(id);
        return new ResponseEntity<>("Tag deleted successfully!", HttpStatus.OK);
    }
    //

   

}
