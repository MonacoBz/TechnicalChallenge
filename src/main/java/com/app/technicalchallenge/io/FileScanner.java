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

    public FileScanner(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    public Queue<Resource> getFiles(){
        return findFiles();
    }

    private Queue<Resource> findFiles(){
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        Queue<Resource> filesData = new ArrayDeque<>();
        try{
            Resource[] resources = resolver.getResources("classpath:archivos/*.txt");
            filesData.addAll(Arrays.asList(resources));
            return filesData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
