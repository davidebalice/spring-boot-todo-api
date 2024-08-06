package com.todoapi.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todoapi.model.Tag;
@Service
public interface TagService {
    Tag getTagById(int categoryId);
    ResponseEntity<String> updateTag(int id, Tag updateTag);
    ResponseEntity<String> deleteTag(Integer idTag);
}
