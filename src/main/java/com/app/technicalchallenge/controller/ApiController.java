package com.app.technicalchallenge.controller;

import com.app.technicalchallenge.dto.ProcessResponseDto;
import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.service.ProcessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
public class ApiController {

    private final ProcessService service;

    public ApiController(ProcessService service){
        this.service = service;
    }

    @PostMapping("/start")
    public ResponseEntity<ProcessResponseDto> startProcess() {
        return ResponseEntity.ok(service.startProcess());
    }

    @PostMapping("/stop/{process_id}")
    public ResponseEntity<ProcessResponseDto> stopProcess(@PathVariable long process_id){
        return ResponseEntity.ok(service.stopProcess(process_id));
    }


}
