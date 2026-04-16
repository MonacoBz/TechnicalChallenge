package com.app.technicalchallenge.dto;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Status;

public record ProcessResponseDto(
        String UUID,
        Status status
) {
    public ProcessResponseDto(Process process){
        this(process.getUuid().toString(),process.getStatus());
    }
}
