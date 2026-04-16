package com.app.technicalchallenge.async;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Status;
import com.app.technicalchallenge.io.FileAnalyzer;
import com.app.technicalchallenge.service.ProcessService;
import org.springframework.core.io.Resource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Queue;

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
        var start = LocalDateTime.now();
        while(!files.isEmpty()){
            if(count == 2){
                calculateTime(start);
                service.updateProcess(process);
                count = 0;
            }
            lastFile = files.poll();
            analyzer.analyze(process,lastFile);
            setProgress();
            count++;
        }
        process.setStatus(Status.COMPLETED);
        service.updateProcess(process);
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
}
