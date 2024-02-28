package com.chukurs.database.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    //need this class to work with modelMapper
    //it is available in context as its a BEAN
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

