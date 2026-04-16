package com.app.technicalchallenge.io;

import com.app.technicalchallenge.entities.Process;
import com.app.technicalchallenge.entities.Result;
import org.springframework.core.io.Resource;

import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class FileAnalyzer {

    private long totalLines = 0;
    private long totalWords = 0;
    Map<String,Integer> frequence;

    public FileAnalyzer(){
        frequence = new HashMap<>();
    }
    public void analyze(Process procees, Resource resource){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))){
            while(reader.ready())prepareResults(reader.readLine(),procees);
            setResults(procees,resource);
        }catch (Exception e){}
    }

    private void prepareResults(String line,Process process){
        var result = process.getResults();
        totalLines++;
        String [] words = line.split(" ");
        totalWords += words.length;
        analyzeFrequenceWords(words);
    }

    private void analyzeFrequenceWords(String [] words){
        for(String word : words)frequence.put(word,frequence.containsKey(word)? frequence.get(word) + 1 : 1);
    }

    private void setResults(Process process, Resource resource){
        int minFrequence = 30;
        Set<String> frequentWords = process.getResults().getMostFrequentWords();
        for(String key : frequence.keySet()){
            if(!frequentWords.contains(key)){
                var value = frequence.get(key);
                if(value >= 100)frequentWords.add(key);
            }
        }
        var result = process.getResults();
        result.setTotalLines(totalLines);
        result.setMostFrequentWords(frequentWords);
        result.setTotalWords(totalWords);
        result.getFileProccesed().add(resource.getFilename());
    }

}
