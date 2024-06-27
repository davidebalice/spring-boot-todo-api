package com.todoapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.todoapi.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

    void deleteById(Optional<Todo> p);

    @Query("SELECT p FROM Todo p " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Todo> searchTodos(@Param("keyword") String keyword, Pageable pageable);

    List<Todo> findByCategoryId(int categoryId, Pageable pageable);

}
