package com.todoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todoapi.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    void deleteById(Optional<Tag> p);
}
