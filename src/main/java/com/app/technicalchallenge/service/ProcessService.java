package com.app.technicalchallenge.service;

import com.app.technicalchallenge.async.ProcessAsync;
import com.app.technicalchallenge.dto.ProcessResponseDto;
import com.app.technicalchallenge.dto.ResultResponseDto;
import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Progress;
import com.app.technicalchallenge.entities.Result;
import com.app.technicalchallenge.entities.Status;
import com.app.technicalchallenge.io.FileAnalyzer;
import com.app.technicalchallenge.io.FileScanner;
import com.app.technicalchallenge.repository.ProcessRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class ProcessService {

    private final ProcessRepository repository;

    private final ExecutorService executor;

    private final FileScanner fileScanner;

    private final FileAnalyzer fileAnalyzer;

    private List<ProcessAsync> processes;
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
        processes = new CopyOnWriteArrayList<>();
    }

    public ProcessResponseDto startProcess(){
        var files = fileScanner.getFiles();
        var process = createProcess(files);
        repository.save(process);
        var async = new ProcessAsync(this,process,files,fileAnalyzer);
        executor.submit(async);
        processes.add(async);
        return new ProcessResponseDto(process);
    }

    public ProcessResponseDto stopProcess(long process_id){
        processes.stream()
                .filter(p->p.getProcessId() == process_id)
                .findFirst()
                .get()
                .stopThread();

        return new ProcessResponseDto(repository.findById(process_id).get());
    }

    public ProcessResponseDto statusProcess(long process_id){
        var process =  repository.findById(process_id)
                        .orElseThrow(()->new RuntimeException("There are not a process with id: " + process_id));
        return new ProcessResponseDto(process);
    }

    public List<ProcessResponseDto> stateProcesses(){
        return repository.findAll()
                .stream()
                .map(ProcessResponseDto::new)
                .toList();
    }

    public ResultResponseDto resultProcess(long process_id){
        var p = repository.findById(process_id).get();
        return new ResultResponseDto(p.getResults());
    }

    @Transactional
    public Process updateProcess(Process process){
        if(!repository.existsById(process.getId()))throw new RuntimeException("There's not a process with id: " + process.getId());
        return repository.save(process);
    }

    private Process createProcess(Queue<Resource> files){
        return new Process(
                null,
                UUID.randomUUID(),
                Status.PENDIG,
                new Progress(
                        files.size(),
                        0,
                        0
                ),
                LocalDateTime.now(),
                null,
                new Result(
                        0,
                        0,
                        new HashSet<>(),
                        new HashSet<>())
        );
    }
}
