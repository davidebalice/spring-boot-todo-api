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
           
      
            return context.getDestination();
        });

        return modelMapper;
    }
}
