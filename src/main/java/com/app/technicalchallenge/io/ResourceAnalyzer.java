package com.app.technicalchallenge.io;

import com.app.technicalchallenge.entities.Process;
import org.springframework.core.io.Resource;

import java.util.Map;

public interface ResourceAnalyzer {

    public void analyze(Process procees, Resource resource, Map<String,Integer> frequence);
}
