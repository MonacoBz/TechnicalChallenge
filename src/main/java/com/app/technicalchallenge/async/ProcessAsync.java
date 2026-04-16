package com.app.technicalchallenge.async;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.service.ProcessService;

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

    }
}
