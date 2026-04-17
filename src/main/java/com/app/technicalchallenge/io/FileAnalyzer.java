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

    public void analyze(Process procees, Resource resource){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))){
            var resultAnalyzer = new ResultAnalyzer();
            while(reader.ready())prepareResults(reader.readLine(),resultAnalyzer);
            setResults(procees,resource,resultAnalyzer);
        }catch (Exception e){}
    }

    private void prepareResults(String line,ResultAnalyzer resultAnalyzer){
        resultAnalyzer.totalLines++;
        String [] words = line.split(" ");
        resultAnalyzer.totalWords += words.length;
        analyzeFrequenceWords(words,resultAnalyzer.frequence);
    }

    private void analyzeFrequenceWords(String [] words,Map<String,Integer> frequence){
        for(String word : words)frequence.put(word,frequence.containsKey(word)? frequence.get(word) + 1 : 1);
    }

    private void setResults(Process process, Resource resource,ResultAnalyzer resultAnalyzer){
        int minFrequence = 50;
        Set<String> frequentWords = process.getResults().getMostFrequentWords();
        for(String key : resultAnalyzer.frequence.keySet()){
            if(!frequentWords.contains(key)){
                var value = resultAnalyzer.frequence.get(key);
                if(value >= 100)frequentWords.add(key);
            }
        }
        var result = process.getResults();
        result.setTotalLines(result.getTotalLines() + resultAnalyzer.totalLines);
        result.setMostFrequentWords(frequentWords);
        result.setTotalWords(result.getTotalWords() + resultAnalyzer.totalWords);
        result.getFileProccesed().add(resource.getFilename());
    }

}

class ResultAnalyzer{
    Map<String,Integer> frequence = new HashMap<>();
    long totalLines;
    long totalWords;
}