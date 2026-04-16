package com.app.technicalchallenge.controller;

import com.app.technicalchallenge.dto.ProcessResponseDto;
import com.app.technicalchallenge.service.ProcessService;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok(service.createProcess());
    }

}
