package com.app.technicalchallenge.async;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.service.ProcessService;

import java.util.concurrent.TimeUnit;

public class ProcessAsync implements Runnable{

    public static volatile long STOPP = 0;

    private final ProcessService service;

    private final Process process;

    public ProcessAsync(
            ProcessService service,
            Process process
    ){
        this.service = service;
        this.process = process;
    }

    @Override
    public void run() {
        try{
            System.out.println("I'm process with id " + process.getId() + " and i'm gonna sleep 10 seconds");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("I'm process with id " + process.getId() + " and I wake up");
        }catch (InterruptedException e){
            System.out.println("ERROR");
        }
    }
}
