package com.todoapi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.todoapi.model.Status;

public interface StatusRepository extends JpaRepository<Status,Integer>{

    void deleteById(Optional<Status> p);
}
