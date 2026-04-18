package com.app.technicalchallenge.async;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Status;
import com.app.technicalchallenge.exception.AnalyzerException;
import com.app.technicalchallenge.exception.InternalServerException;
import com.app.technicalchallenge.io.ResourceAnalyzer;
import com.app.technicalchallenge.service.ProcessService;
import org.springframework.core.io.Resource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ProcessAsync implements Runnable{

    private final ProcessService service;

    private final Process process;

    private final Queue<Resource> files;

    private Resource lastFile;

    private final ResourceAnalyzer analyzer;

    private Thread thread;

    private Map<String,Integer> words;

    public ProcessAsync(
            ProcessService service,
            Process process,
            Queue<Resource> files,
            ResourceAnalyzer analyzer
    ){
        this.service = service;
        this.process = process;
        this.files = files;
        this.analyzer = analyzer;
        this.words = new HashMap<>();
    }

    @Override
    public void run() {
        try {
        thread = Thread.currentThread();
        int count = 0;
        process.setStatus(Status.RUNNING);
        service.updateProcess(process);
        var start = LocalDateTime.now();
        while(!files.isEmpty()){
            if(Thread.currentThread().isInterrupted())break;
            if(count == 2){
                calculateTime(start);
                service.updateProcess(process);
                count = 0;
            }

            lastFile = files.poll();
            analyzer.analyze(process,lastFile,words);
            setProgress();
            count++;
            try{
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
        process.setStatus((Thread.currentThread().isInterrupted())?Status.STOPPED:Status.COMPLETED);
        service.updateProcess(process);
        } catch (AnalyzerException e){
            process.setStatus(Status.FAILED);
            service.updateProcess(process);
            throw new InternalServerException(e.getMessage());
        }
    }

    private void setProgress(){
        var progess = process.getProgress();
        var processedFiles = progess.getProccesedFiles() + 1;
        var percentage = processedFiles * 10;
        progess.setPorcentage((int) percentage);
        progess.setProccesedFiles(processedFiles);
    }

    private void calculateTime(LocalDateTime start){
        Duration elapsed = Duration.between(start, LocalDateTime.now());
        double secondsPerFile = (double) elapsed.toSeconds() / process.getProgress().getProccesedFiles();
        long secondsRemaining = (long) (secondsPerFile * files.size());
        process.setEstimated_completion(LocalDateTime.now().plusSeconds(secondsRemaining));
    }

    public void stopThread(){
        thread.interrupt();
    }

    public long getProcessId(){
        return process.getId();
    }

}
