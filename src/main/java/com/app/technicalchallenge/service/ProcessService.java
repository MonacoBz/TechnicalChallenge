package com.app.technicalchallenge.service;

import com.app.technicalchallenge.async.ProcessAsync;
import com.app.technicalchallenge.dto.ProcessResponseDto;
import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Progress;
import com.app.technicalchallenge.entities.Status;
import com.app.technicalchallenge.io.FileAnalyzer;
import com.app.technicalchallenge.io.FileScanner;
import com.app.technicalchallenge.repository.ProcessRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class ProcessService {

    private final ProcessRepository repository;

    private final ExecutorService executor;

    private final FileScanner fileScanner;

    private final FileAnalyzer fileAnalyzer;
    public ProcessService(
            ProcessRepository repository,
            ExecutorService executor,
            FileScanner fileScanner,
            FileAnalyzer fileAnalyzer
    ){
        this.repository = repository;
        this.executor = executor;
        this.fileScanner = fileScanner;
        this.fileAnalyzer = fileAnalyzer;
    }

    public ProcessResponseDto createProcess(){
        var process = new Process(null, UUID.randomUUID(),Status.PENDIG,new Progress(0,0,0));
        repository.save(process);
        executor.submit(new ProcessAsync(this,process,fileScanner.getFiles(),fileAnalyzer));
        return new ProcessResponseDto(process);
    }

    @Transactional
    public synchronized Process updateProcess(Process process){
        if(!repository.existsById(process.getId()))throw new RuntimeException("There's not a process with id: " + process.getId());
        return repository.save(process);
    }

}
