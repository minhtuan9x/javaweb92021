package com.laptrinhjavaweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class DateFormatConfig {
    @Bean
    public DateFormat dateFormat(){
        return new SimpleDateFormat("dd/MM/yyyy");
    }
}
