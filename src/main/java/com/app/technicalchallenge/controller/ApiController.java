package com.app.technicalchallenge.controller;

import com.app.technicalchallenge.dto.ProcessResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
public class ApiController {

    @PostMapping("/start")
    public ResponseEntity<ProcessResponseDto> startProcess() {

    }
}
