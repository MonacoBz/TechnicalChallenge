package com.app.technicalchallenge.dto;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Status;

import java.time.LocalDateTime;

public record ProcessResponseDto(
        String UUID,
        Status status,
        ProgressResponseDto progress,
        LocalDateTime started_at,
        ResultResponseDto result
) {
    public ProcessResponseDto(Process process){
        this(
                process.getUuid().toString(),
                process.getStatus(),
                new ProgressResponseDto(process.getProgress()),
                process.getStarted_at(),
                new ResultResponseDto(process.getResults())
        );
    }
}
