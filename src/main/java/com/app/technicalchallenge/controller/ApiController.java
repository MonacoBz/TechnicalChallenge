package com.app.technicalchallenge.controller;

import com.app.technicalchallenge.dto.ProcessCreationResponseDto;
import com.app.technicalchallenge.dto.ProcessResponseDto;
import com.app.technicalchallenge.dto.ResultResponseDto;
import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.service.ProcessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process")
public class ApiController {

    private final ProcessService service;

    public ApiController(ProcessService service){
        this.service = service;
    }

    @PostMapping("/start")
    public ResponseEntity<ProcessCreationResponseDto> startProcess(){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(service.startProcess());
    }

    @PostMapping("/stop/{process_id}")
    public ResponseEntity stopProcess(@PathVariable long process_id){
        service.stopProcess(process_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{process_id}")
    public ResponseEntity<ProcessResponseDto> statusProcess(@PathVariable long process_id){
        return ResponseEntity.ok(service.statusProcess(process_id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProcessResponseDto>> stateProcesses(){
        return ResponseEntity.ok(service.stateProcesses());
    }

    @GetMapping("/results/{process_id}")
    public ResponseEntity<ResultResponseDto> resultProcess(@PathVariable long process_id){
        return ResponseEntity.ok(service.resultProcess(process_id));
    }

}
