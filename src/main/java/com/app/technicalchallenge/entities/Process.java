package com.app.technicalchallenge.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    private Status status;

    @Embedded
    private Progress progress;

    private LocalDateTime started_at;

    private LocalDateTime estimated_completion;

    @Embedded
    private Result results;



}
