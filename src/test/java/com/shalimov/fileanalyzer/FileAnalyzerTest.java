package com.shalimov.fileanalyzer;


import java.io.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FileAnalyzerTest {
    private final String CONTENT = "Yesterday, all my troubles seemed so far away." +
            "Now it looks as though they're here to stay." +
            "Oh, I believe in yesterday.";

    private final String WORD = "Yesterday";
    private FileAnalyzer fileAnalyzer;
    private File path;

    @Before
   public void before() {
        fileAnalyzer = new FileAnalyzer();
        path = new File(new File("Yesterday.txt").getAbsolutePath());
    }

    @Test
    public void testWhenPathIsNullThemNullPointerException() {
     assertThrows(NullPointerException.class, () -> {
            fileAnalyzer.analyzers(null, "Yesterday");
        });
    }

    @Test
    public void testWhenWordIsNullThemNullPointerException() {
    assertThrows(NullPointerException.class, () -> {
            fileAnalyzer.analyzers(path.getPath(), null);
        });
    }

    @Test
    public void testCalculateWordWorkCorrectly() {
        int expect = 2;
        int actual = fileAnalyzer.countWords(CONTENT, WORD);
        assertEquals(expect, actual);
    }

    @Test
    public void testReadContentWorkCorrectly() {
        String actualContent = fileAnalyzer.readContent(path.toString());
        assertEquals(CONTENT, actualContent);
    }

    @Test
    public void testSplitSentenceWorkCorrectly() {
        String expected = "[Yesterday, all my troubles seemed so far away., " + "Now it looks as though they're here to stay.," +
                " Oh, I believe in yesterday.]";
        String actualContent = String.valueOf(fileAnalyzer.splitSentence(CONTENT));
        assertEquals(expected, actualContent);
    }

    @Test
    public void testFilterSentenceWorkCorrectly() {
        List<String> sentence = List.of("Yesterday one", "Yesterday two", "suddenly one ", "suddenly one");
        List<String> expected = List.of("Yesterday one", "Yesterday two");
        assertEquals(expected, fileAnalyzer.filter(sentence, WORD));
    }
}
