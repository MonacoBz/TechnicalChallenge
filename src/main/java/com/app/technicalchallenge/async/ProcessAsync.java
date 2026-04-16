package com.app.technicalchallenge.async;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.service.ProcessService;
import org.springframework.core.io.Resource;


import java.sql.Time;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ProcessAsync implements Runnable{

    public static volatile long STOPP = 0;

    private final ProcessService service;

    private final Process process;

    private final Queue<Resource> files;

    public ProcessAsync(
            ProcessService service,
            Process process,
            Queue<Resource> files
    ){
        this.service = service;
        this.process = process;
        this.files = files;
    }

    @Override
    public void run() {
        try{
            while(!files.isEmpty()){
                var file = files.poll();
                System.out.println("I'm thread " + process.getId() + " and I'm reading file: " + file.getFilename());
                TimeUnit.SECONDS.sleep(10);
            }
        }catch (InterruptedException e){
            System.out.println("ERROR");
        }
    }
}
