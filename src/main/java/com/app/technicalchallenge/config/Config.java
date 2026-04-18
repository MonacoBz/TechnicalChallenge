package com.app.technicalchallenge.config;

import com.app.technicalchallenge.io.FileAnalyzer;
import com.app.technicalchallenge.io.FileScanner;
import com.app.technicalchallenge.io.ResourceAnalyzer;
import com.app.technicalchallenge.io.ResourceScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class Config {

    @Value("${app.files.path}")
    private String path;
    @Bean
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public ResourceScanner fileScanner(ResourceLoader resourceLoader){
        return new FileScanner(resourceLoader,path);
    }

    @Bean
    public ResourceAnalyzer fileAnalyzer(){return new FileAnalyzer();}
}
