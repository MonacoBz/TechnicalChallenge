package com.app.technicalchallenge.io;

import com.app.technicalchallenge.exception.ScannerException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class FileScanner implements ResourceScanner{

    private final ResourceLoader resourceLoader;

    private final String path;
    public FileScanner(
            ResourceLoader resourceLoader,
            String path
    ){
        this.path = path;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Queue<Resource> getFiles(){
        return findFiles();
    }

    private Queue<Resource> findFiles(){
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        Queue<Resource> filesData = new ArrayDeque<>();
        try{
            Resource[] resources = resolver.getResources(path);
            filesData.addAll(Arrays.asList(resources));
            return filesData;
        } catch (IOException e) {
            throw new ScannerException(e.getMessage());
        }
    }

}
