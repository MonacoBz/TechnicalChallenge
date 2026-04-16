package com.app.technicalchallenge.async;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.io.FileAnalyzer;
import com.app.technicalchallenge.service.ProcessService;
import org.springframework.core.io.Resource;


import java.sql.Time;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ProcessAsync implements Runnable{

    private final ProcessService service;

    private Process process;

    private final Queue<Resource> files;

    private Resource lastFile;

    private final FileAnalyzer analyzer;

    public ProcessAsync(
            ProcessService service,
            Process process,
            Queue<Resource> files,
            FileAnalyzer analyzer
    ){
        this.service = service;
        this.process = process;
        this.files = files;
        this.analyzer = analyzer;
    }

    @Override
    public void run() {
        try{
            int count = 0;
            while(!files.isEmpty()){
                if(count == 2){}
                lastFile = files.poll();
                analyzer.analyze(process);
                count++;
                TimeUnit.SECONDS.sleep(10);
            }
        }catch (InterruptedException e){
            System.out.println("ERROR");
        }
    }
}
