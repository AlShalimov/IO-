package com.shalimov.fileanalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileAnalyzer {

    private static final Pattern SENTENCE_PATTERN = Pattern.compile("((?<=[.!?]))");

    public void analyzers(String path, String word) {
        if (word == null) {
            throw new NullPointerException("The word is blank. Provide a valid word");
        }
        if (path==null){
            throw new NullPointerException("Provide s valid path");
        }
        String content = readContent(path);
        int wordCount = countWords(content, word);
        List<String> sentences = splitSentence(content);
        List<String> filteredSentences = filter(sentences, word);
        System.out.println(result(wordCount, word, filteredSentences));
    }

    public int countWords(String content, String searchWord) {
        List<String> listWord = List.of(content.split("\\W+"));
        int count = 0;
        for (String word : listWord) {
            if (word.equalsIgnoreCase(searchWord)) {
                count++;
            }
        }
        return count;
    }

    public String readContent(String path) {
        String content;
        File file = new File(path);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            content = new String(fileInputStream.readAllBytes());
        } catch (IOException exception) {
            throw new RuntimeException("Cant read file by path",exception);
        }
        return content;
    }

    public List<String> splitSentence(String content) {
        List<String> trimmedSentences = new ArrayList<>();
        String[] sentences = SENTENCE_PATTERN.split(content);
        for (String sentence : sentences) {
            trimmedSentences.add(sentence);
        }
        return trimmedSentences;
    }

    public List<String> filter(List<String> sentences, String word) {
        List<String> resultList = new ArrayList<>();
        for (String string : sentences) {
            if (string.contains(word) || string.contains(word.toLowerCase())) {//IgnoreCase
                resultList.add(string);
            }
        }
        return resultList;
    }

    private String result(int wordCount, String word, List<String> filteredSentences) {
        StringBuilder result = new StringBuilder();
        result.append("In the text, the word ");
        result.append(word);
        result.append(" occurs ");
        result.append(wordCount);
        result.append(" times.\n");
        result.append("All the sentences with this word:\n");
        for (String filteredSentence : filteredSentences) {
            result.append(filteredSentence);
        }
        return result.toString();
    }
}
