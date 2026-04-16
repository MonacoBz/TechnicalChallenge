package com.app.technicalchallenge.dto;

import com.app.technicalchallenge.entities.Status;

public record ProcessResponseDto(
        String UUID,
        Status status
) {

}
