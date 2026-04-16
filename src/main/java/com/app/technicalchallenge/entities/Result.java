package com.app.technicalchallenge.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private long totalWords;

    private long totalLines;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> mostFrequentWords;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> fileProccesed;
}
