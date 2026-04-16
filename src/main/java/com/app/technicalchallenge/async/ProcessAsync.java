package com.app.technicalchallenge.async;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Status;
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
        int count = 0;
        process.setStatus(Status.RUNNING);
        service.updateProcess(process);
        while(!files.isEmpty()){
            if(count == 2){
                System.out.println("Guardando cambios");
                service.updateProcess(process);
                count = 0;
            }
            System.out.println("Leyendo archivo");
            lastFile = files.poll();
            analyzer.analyze(process,lastFile);
            setProgress();
            count++;
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        process.setStatus(Status.COMPLETED);
        service.updateProcess(process);
    }

    public void setProgress(){
        var progess = process.getProgress();
        var processedFiles = progess.getProccesedFiles() + 1;
        var percentage = processedFiles * 10;
        progess.setPorcentage((int) percentage);
        progess.setProccesedFiles(processedFiles);
    }
}
