package com.todoapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todoapi.exception.ResourceNotFoundException;
import com.todoapi.model.Status;
import com.todoapi.repository.StatusRepository;
import com.todoapi.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public StatusServiceImpl(StatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public Status getStatusById(int statusId) {
        Status status = repository.findById(statusId).orElseThrow(
                () -> new ResourceNotFoundException("Status", "id", statusId));
        return modelMapper.map(status, Status.class);
    }

    @Override
    public ResponseEntity<String> updateStatus(int statusId, Status updateStatus) {
        try {
            if (!repository.existsById(statusId)) {
                throw new ResourceNotFoundException("Status", "id", statusId);
            }

            Status existingStatus = repository.findById(statusId).orElse(null);

            if (updateStatus.getName() != null) {
                existingStatus.setName(updateStatus.getName());
            }

            repository.save(existingStatus);

            return new ResponseEntity<>("Status updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteStatus(Integer statusId) {
        Optional<Status> pOptional = repository.findById(statusId);
        if (pOptional.isPresent()) {
            Status c = pOptional.get();
            repository.delete(c);
            return new ResponseEntity<>("Status deleted successfully", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Status", "id", statusId);
        }
    }
}
