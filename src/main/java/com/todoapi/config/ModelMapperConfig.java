package com.todoapi.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.todoapi.dto.TodoDto;
import com.todoapi.model.Category;
import com.todoapi.model.Status;
import com.todoapi.model.Tag;
import com.todoapi.model.Todo;
import com.todoapi.model.User;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Todo, TodoDto>() {
            @Override
            protected void configure() {
                //map(source.getCategory().getId(), destination.getCategoryId());
                //map(source.getStatus().getId(), destination.getStatusId());
                //map(source.getTag().getId(), destination.getTagId());
                //map(source.getUser().getId(), destination.getUserId());
            }
        });

        modelMapper.createTypeMap(TodoDto.class, Todo.class).addMappings(mapper -> {
            mapper.skip(Todo::setCategory);
            mapper.skip(Todo::setStatus);
            mapper.skip(Todo::setTag);
            //mapper.skip(Todo::setUser);
        }).setPostConverter(context -> {
            TodoDto source = context.getSource();
            Todo destination = context.getDestination();
           
      /* 
            if (source.getCategoryId() != 0 && source.getCategoryId() > 0) {
                Category category = new Category();
                category.setId(source.getCategoryId());
                destination.setCategory(category);
            }
            if (source.getStatusId() != 0 && source.getStatusId() > 0) {
                Status status = new Status();
                status.setId(source.getStatusId());
                destination.setStatus(status);
            }
            if (source.getTagId() != 0 && source.getTagId() > 0) {
                Tag tag = new Tag();
                tag.setId(source.getTagId());
                destination.setTag(tag);
            }
            
            if (source.getUserId() != 0 && source.getUserId() > 0) {
                User user = new User();
                user.setId(source.getUserId());
                destination.setUser(user);
            }*/
            return context.getDestination();
        });

        return modelMapper;
    }
}
