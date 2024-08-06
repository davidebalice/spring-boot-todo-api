package com.todoapi.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todoapi.model.Status;
@Service
public interface StatusService {
    Status getStatusById(int categoryId);
    ResponseEntity<String> updateStatus(int id, Status updateStatus);
    ResponseEntity<String> deleteStatus(Integer idStatus);
}
