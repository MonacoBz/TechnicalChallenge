package com.app.technicalchallenge.io;

import org.springframework.core.io.Resource;

import java.util.Queue;

public interface ResourceScanner {


    Queue<Resource> getFiles();
}
