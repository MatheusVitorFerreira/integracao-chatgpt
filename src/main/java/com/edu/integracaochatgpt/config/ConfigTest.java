package com.edu.integracaochatgpt.config;

import com.edu.integracaochatgpt.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class ConfigTest {

    @Autowired
    private DbService dbService;

    @Bean
    public boolean instantiateDataBase() {
        dbService.instantiateDataBase();
        return true;
    }

}
