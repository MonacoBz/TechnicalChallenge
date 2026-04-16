package com.app.technicalchallenge.config;

import com.app.technicalchallenge.io.FileScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class Config {

    @Bean
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public FileScanner fileScanner(ResourceLoader resourceLoader){
        return new FileScanner(resourceLoader);
    }
}
