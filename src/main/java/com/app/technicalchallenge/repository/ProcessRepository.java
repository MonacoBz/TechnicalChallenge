package com.app.technicalchallenge.repository;

import com.app.technicalchallenge.entities.Process;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process,Long> {
}
