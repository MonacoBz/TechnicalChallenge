package com.app.technicalchallenge.dto;

import com.app.technicalchallenge.entities.Result;

import java.util.Set;

public record ResultResponseDto(
        long total_words,
        long total_lines,
        Set<String> most_frequent_words,
        Set<String> file_processed
) {
    public ResultResponseDto(Result result){
        this(
                result.getTotalWords(),
                result.getTotalLines(),
                result.getMostFrequentWords(),
                result.getFileProccesed()
        );
    }
}
