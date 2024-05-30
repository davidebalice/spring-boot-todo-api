package com.todoapi.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todoapi.dto.CategoryDto;
import com.todoapi.model.Category;
@Service
public interface CategoryService {
    CategoryDto getCategoryById(int categoryId);
    ResponseEntity<String> updateCategory(int id, Category updateCategory);
    ResponseEntity<String> deleteCategory(Integer idCategory);
    List<Category> searchCategories(String keyword);
}
