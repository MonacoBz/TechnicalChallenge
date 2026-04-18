package com.app.technicalchallenge.dto;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProcessCreationResponseDto(
        Long process_id,
        UUID process_uuid,
        LocalDateTime started_at,
        Status status
) {

    public ProcessCreationResponseDto(Process process){
        this(
                process.getId(),
                process.getUuid(),
                process.getStarted_at(),
                process.getStatus()
        );
    }
}
