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

import com.todoapi.model.Status;
import com.todoapi.repository.StatusRepository;
import com.todoapi.service.StatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "CRUD REST APIs for Status Resource", description = "CATEGORIES CRUD REST APIs - Create Status, Update Status, Get Status, Get All Categories, Delete Status")
@RestController
@RequestMapping("/api/v1/status/")
public class StatusController {

    private final StatusRepository repository;
    private final StatusService service;

    public StatusController(StatusRepository repository, StatusService service) {
        this.repository = repository;
        this.service = service;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DemoMode demoMode;

    // Get all Categories Rest Api
    // http://localhost:8081/api/v1/status
    @Operation(summary = "Get all status", description = "Retrieve a list of all status")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/")
    public ResponseEntity<List<Status>> list() {
        List<Status> status = (List<Status>) repository.findAll();

        List<Status> statusArray = status.stream()
                .map(category -> modelMapper.map(category, Status.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(statusArray);
    }
    //

    // Get single Status Rest Api (get id by url)
    // http://localhost:8081/api/v1/status/1
    @Operation(summary = "Get Status By ID REST API", description = "Get Status By ID REST API is used to get a single Status from the database, get id by url")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/{id}")
    public Status getById(@PathVariable Integer id) {
        return service.getStatusById(id);
    }
    //

    // Add new Status Rest Api
    // http://localhost:8081/api/v1/status/add
    @Operation(summary = "Crate new Status REST API", description = "Save new Status on database")
    @ApiResponse(responseCode = "201", description = "HTTP Status 201 Created")
    @PostMapping("/add")
    public ResponseEntity<String> add(@Valid @RequestBody Status p) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        repository.save(p);
        return new ResponseEntity<>("Status addedd successfully!", HttpStatus.OK);
    }
    //

    // Update Status Rest Api
    // http://localhost:8081/api/v1/status/1
    @Operation(summary = "Update Status REST API", description = "Update Status on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Status updatedStatus) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        return service.updateStatus(id, updatedStatus);
    }
    //

    // Delete Status Rest Api
    // http://localhost:8081/api/v1/status/1
    @Operation(summary = "Delete Status REST API", description = "Delete Status on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        service.deleteStatus(id);
        return new ResponseEntity<>("Status deleted successfully!", HttpStatus.OK);
    }
    //

   

}
