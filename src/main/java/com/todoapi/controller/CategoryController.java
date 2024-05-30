package com.todoapi.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.todoapi.dto.CategoryDto;
import com.todoapi.mapper.CategoryMapper;
import com.todoapi.model.Category;
import com.todoapi.repository.CategoryRepository;
import com.todoapi.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
        name = "CRUD REST APIs for Category Resource",
        description = "CATEGORIES CRUD REST APIs - Create Category, Update Category, Get Category, Get All Categories, Delete Category"
)
@RestController
@RequestMapping("/api/v1/categories/")
public class CategoryController {

    private final CategoryRepository repository;
    private final CategoryService service;

    public CategoryController(CategoryRepository repository, CategoryService service) {
        this.repository = repository;
        this.service = service;
    }


    //Get all Categories Rest Api
    //http://localhost:8081/api/v1/categories
    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories")
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> list() {
        List<Category> categories = (List<Category>) repository.findAll();
        List<CategoryDto> categoriesDto = categories.stream()
                .map(CategoryMapper::mapToCategoryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriesDto);
    }
    //


    //Get single Category Rest Api (get id by url)
    //http://localhost:8081/api/v1/categories/1
    @Operation(
        summary = "Get Category By ID REST API",
        description = "Get Category By ID REST API is used to get a single Category from the database, get id by url"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable Integer id) {
        return service.getCategoryById(id);
    }
    //


    //Add new Category Rest Api
    //http://localhost:8081/api/v1/categories/add
    @Operation(
        summary = "Crate new Category REST API",
        description = "Save new Category on database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 Created"
    )
    @PostMapping("/add")
    public ResponseEntity<String> add(@Valid @RequestBody Category p) {
        repository.save(p);
        return new ResponseEntity<>("Category addedd successfully!", HttpStatus.OK);
    }
    //


    //Update Category Rest Api
    //http://localhost:8081/api/v1/categories/1
    @Operation(
        summary = "Update Category REST API",
        description = "Update Category on database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Category updatedCategory) {
        return service.updateCategory(id, updatedCategory);
    }
    //


    //Delete Category Rest Api
    //http://localhost:8081/api/v1/categories/1
    @Operation(
        summary = "Delete Category REST API",
        description = "Delete Category on database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
    }
    //


    //Search Category Rest Api
    //http://localhost:8081/api/v1/categories/search
    @Operation(
        summary = "Search Category REST API",
        description = "Search Category on database by filter"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/search")
    public List<Category> searchCategories(@RequestParam("keyword") String keyword) {
        List<Category> categories = service.searchCategories(keyword);
        return categories;
    }
    //

}
