package com.app.technicalchallenge.async;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Status;
import com.app.technicalchallenge.exception.AnalyzerException;
import com.app.technicalchallenge.exception.InternalServerException;
import com.app.technicalchallenge.io.ResourceAnalyzer;
import com.app.technicalchallenge.service.ProcessService;
import org.springframework.core.io.Resource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProcessAsync implements Runnable{

    private final ProcessService service;

    private final Process process;

    private final Queue<Resource> files;

    private Resource lastFile;

    private final ResourceAnalyzer analyzer;

    private Thread thread;

    private Map<String,Integer> words;

    private final ReentrantLock lock = new ReentrantLock();

    public final Condition PAUSED = lock.newCondition();

    private volatile boolean isPaused = false;

    public ProcessAsync(
            ProcessService service,
            Process process,
            Queue<Resource> files,
            ResourceAnalyzer analyzer
    ){
        this.service = service;
        this.process = process;
        this.files = files;
        this.analyzer = analyzer;
        this.words = new HashMap<>();
    }

    @Override
    public void run() {
        try {
            thread = Thread.currentThread();
            int count = 0;
            process.setStatus(Status.RUNNING);
            updateProcess();
            var start = LocalDateTime.now();
            while(!files.isEmpty()){
                checkLock();
                if (thread.isInterrupted()) break;
                if (count == 2) {
                    batchProcess(start);
                    count = 0;
                }
                analyze();
                count++;
                try{
                    TimeUnit.SECONDS.sleep(10);
                }catch (InterruptedException e){
                    thread.interrupt();
                }
            }
            process.setStatus((Thread.currentThread().isInterrupted())?Status.STOPPED:Status.COMPLETED);
        } catch (AnalyzerException e){
            process.setStatus(Status.FAILED);
        }finally {
            updateProcess();
        }
    }

    private void checkLock(){
        lock.lock();
        try {
            if(isPaused){
            while (isPaused) {
                PAUSED.await();
            }
            process.setStatus(Status.RUNNING);
            updateProcess();
            }
        }catch (InterruptedException e){
            thread.interrupt();
        }finally {
            lock.unlock();
        }
    }

    private void batchProcess(LocalDateTime start){
        calculateTime(start);
        updateProcess();
    }

    private void analyze(){
        lastFile = files.poll();
        analyzer.analyze(process, lastFile, words);
        setProgress();
    }

    private void updateProcess(){
        service.updateProcess(process);
    }

    private void setProgress(){
        var progess = process.getProgress();
        var processedFiles = progess.getProccesedFiles() + 1;
        var percentage = processedFiles * 10;
        progess.setPorcentage((int) percentage);
        progess.setProccesedFiles(processedFiles);
    }

    private void calculateTime(LocalDateTime start){
        Duration elapsed = Duration.between(start, LocalDateTime.now());
        double secondsPerFile = (double) elapsed.toSeconds() / process.getProgress().getProccesedFiles();
        long secondsRemaining = (long) (secondsPerFile * files.size());
        process.setEstimated_completion(LocalDateTime.now().plusSeconds(secondsRemaining));
    }


    public void stopThread(){
        thread.interrupt();
    }

    public void pauseThread(){
        isPaused = true;
    }

    public void resumeThread(){
        lock.lock();
        try {
            isPaused = false;
            PAUSED.signalAll();
            lock.unlock();
        }finally {
            lock.unlock();
        }
    }
    public long getProcessId(){
        return process.getId();
    }

    public Status getProcessStatus(){
        return process.getStatus();
    }

}
