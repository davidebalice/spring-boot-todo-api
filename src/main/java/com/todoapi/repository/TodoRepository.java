package com.todoapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.todoapi.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

    void deleteById(Optional<Todo> p);
    /*
     * @Query("SELECT t FROM Todo t WHERE " +
     * "LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
     * 
     * " AND t.category.id = :categoryId  " +
     * " AND t.tag.id = :tagId  " +
     * " AND t.status.id = :statusId ")
     * 
     * 
     * 
     * List<Todo> searchTodos(@Param("keyword") String keyword, @Param("categoryId")
     * int categoryId,
     * 
     * @Param("tagId") int tagId, @Param("statusId") int statusId,
     * Pageable pageable);
     */

    @Query("SELECT t FROM Todo t WHERE " +
            "(:keyword IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            " AND (:categoryId = 0 OR t.category.id = :categoryId)  " +
            " AND (:tagId = 0 OR t.tag.id = :tagId)  " +
            " AND (:statusId = 0 OR t.status.id = :statusId) ")
            List<Todo> searchTodos(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId,@Param("tagId") Integer tagId, @Param("statusId") Integer statusId,
            Pageable pageable);

    /*
     * @Query("SELECT p FROM Todo p " +
     * "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
     * "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
     * List<Todo> searchTodos(@Param("keyword") String keyword, Pageable pageable);
     */

    List<Todo> findByCategoryId(int categoryId, Pageable pageable);

}
