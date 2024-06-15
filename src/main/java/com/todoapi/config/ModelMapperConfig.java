package com.todoapi.config;



import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.todoapi.dto.TodoDto;
import com.todoapi.model.Todo;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Todo, TodoDto> typeMap = modelMapper.createTypeMap(Todo.class, TodoDto.class);
        typeMap.addMappings(mapper -> mapper.map(src -> src.getCategory(), TodoDto::setCategoryDto));
        return modelMapper;
    }
}
