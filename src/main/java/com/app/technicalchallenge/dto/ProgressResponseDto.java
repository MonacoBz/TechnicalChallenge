package com.app.technicalchallenge.dto;

import com.app.technicalchallenge.entities.Progress;

public record ProgressResponseDto(
        long totalFiles,
        long proccesedFiles,
        int porcentage
) {
    public ProgressResponseDto(Progress progress){
        this(
                progress.getTotalFiles(),
                progress.getProccesedFiles(),
                progress.getPorcentage()
        );
    }
}
