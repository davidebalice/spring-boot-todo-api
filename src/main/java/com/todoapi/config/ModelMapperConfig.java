package com.todoapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.todoapi.dto.TodoDto;
import com.todoapi.model.Category;
import com.todoapi.model.Status;
import com.todoapi.model.Tag;
import com.todoapi.model.Todo;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Todo.class, TodoDto.class).addMappings(mapper -> {
            mapper.skip(TodoDto::setCategoryId);
            mapper.skip(TodoDto::setStatusId);
            mapper.skip(TodoDto::setTagId);
        }).setPostConverter(context -> {
            Todo source = context.getSource();
            TodoDto destination = context.getDestination();
            if (source.getCategory() != null) {
                destination.setCategoryId(source.getCategory().getId());
            }
            if (source.getStatus() != null) {
                destination.setStatusId(source.getStatus().getId());
            }
            if (source.getTag() != null) {
                destination.setTagId(source.getTag().getId());
            }
            return context.getDestination();
        });

        modelMapper.createTypeMap(TodoDto.class, Todo.class).addMappings(mapper -> {
            mapper.skip(Todo::setCategory);
            mapper.skip(Todo::setStatus);
            mapper.skip(Todo::setTag);
        }).setPostConverter(context -> {
            TodoDto source = context.getSource();
            Todo destination = context.getDestination();
            if (source.getCategoryId() != 0) {
                Category category = new Category();
                category.setId(source.getCategoryId());
                destination.setCategory(category);
            }
            if (source.getStatusId() != 0) {
                Status status = new Status();
                status.setId(source.getStatusId());
                destination.setStatus(status);
            }
            if (source.getTagId() != 0) {
                Tag tag = new Tag();
                tag.setId(source.getTagId());
                destination.setTag(tag);
            }
            return context.getDestination();
        });

        return modelMapper;
    }
}
