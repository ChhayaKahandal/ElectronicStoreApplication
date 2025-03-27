package com.electroni.store.ElectronicStoreApp.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig
{
    //here we declaring the bean
    @Bean
    public ModelMapper mapper()
    {
        return new ModelMapper();
    }
}
