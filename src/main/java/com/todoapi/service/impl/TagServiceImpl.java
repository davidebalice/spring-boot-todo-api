package com.todoapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todoapi.exception.ResourceNotFoundException;
import com.todoapi.model.Tag;
import com.todoapi.repository.TagRepository;
import com.todoapi.service.TagService;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tag getTagById(int tagId) {
        Tag tag = repository.findById(tagId).orElseThrow(
                () -> new ResourceNotFoundException("Tag", "id", tagId));
        return modelMapper.map(tag, Tag.class);
    }

    @Override
    public ResponseEntity<String> updateTag(int tagId, Tag updateTag) {
        try {
            if (!repository.existsById(tagId)) {
                throw new ResourceNotFoundException("Tag", "id", tagId);
            }

            Tag existingTag = repository.findById(tagId).orElse(null);

            if (updateTag.getName() != null) {
                existingTag.setName(updateTag.getName());
            }

            repository.save(existingTag);

            return new ResponseEntity<>("Tag updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating tag", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteTag(Integer tagId) {
        Optional<Tag> pOptional = repository.findById(tagId);
        if (pOptional.isPresent()) {
            Tag c = pOptional.get();
            repository.delete(c);
            return new ResponseEntity<>("Tag deleted successfully", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Tag", "id", tagId);
        }
    }
    

  
}
