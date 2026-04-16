package com.app.technicalchallenge.io;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class FileScanner {

    private final ResourceLoader resourceLoader;

    private Queue<Resource> filesData = new ArrayDeque<>();

    public FileScanner(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    public Queue<Resource> getFiles(){
        findFiles();
        return new ArrayDeque<>(filesData);
    }

    private void findFiles(){
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        try{
            Resource[] resources = resolver.getResources("classpath:archivos/*.txt");
            filesData.addAll(Arrays.asList(resources));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
