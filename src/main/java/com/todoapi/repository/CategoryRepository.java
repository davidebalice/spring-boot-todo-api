package com.todoapi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.todoapi.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

    void deleteById(Optional<Category> p);

     @Query("SELECT c FROM Category c " +
            "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " )
    List<Category> searchCategories(@Param("keyword") String keyword);

}
