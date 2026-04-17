package com.app.technicalchallenge.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Progress {

    private long totalFiles;

    private long proccesedFiles;

    private int porcentage;
}
