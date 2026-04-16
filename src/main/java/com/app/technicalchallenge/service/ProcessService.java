package com.app.technicalchallenge.service;

import com.app.technicalchallenge.async.ProcessAsync;
import com.app.technicalchallenge.dto.ProcessResponseDto;
import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Status;
import com.app.technicalchallenge.repository.ProcessRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class ProcessService {

    private final ProcessRepository repository;

    private final ExecutorService executor;

    public ProcessService(
            ProcessRepository repository,
            ExecutorService executor){
        this.repository = repository;
        this.executor = executor;
    }

    public ProcessResponseDto createProcess(){
        var process = new Process(null, UUID.randomUUID(),Status.PENDIG);
        repository.save(process);
        executor.submit(new ProcessAsync(this,process));
        return new ProcessResponseDto(process.getUuid().toString(),process.getStatus());
    }
}
