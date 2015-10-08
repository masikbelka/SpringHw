package com.epam.cdp.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tasks {

    public static final String REGEX = "[^a-zA-Z]";
    private static final Pattern ALPHABETIC_RPL = Pattern.compile(REGEX);
    public static final int ARRAY_MAX_LENGTH = 70000;
    public static final int MIN_WORD_LENGTH = 5;
    public static final int MAX_WORD_LENGTH = 25;
    // ALPHABETIC_RPL.matcher(w).replaceAll("")

    public static List<String> task1Java7(List<String> words) {
        List<String> inputWords = new ArrayList<>(words);
        List<String> resultWords = new ArrayList<>();

        for (String word : inputWords) {
            word = ALPHABETIC_RPL.matcher(word).replaceAll("");
            int length = word.length();
            if (length >= MIN_WORD_LENGTH && length <= MAX_WORD_LENGTH) {
                resultWords.add(word.toLowerCase());
            }
        }
        return resultWords;
    }

    public static List<String> task1Java8(List<String> words, boolean isParallel) {
        Stream<String> stream = isParallel ? words.parallelStream() : words.stream();

        return stream.map(word -> ALPHABETIC_RPL.matcher(word).replaceAll(""))
                .filter(word -> word.length() > 4)
                .filter(word -> word.length() < 26)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public static List<String> task2Java7(List<String> words) {
        List<String> inputWords = new ArrayList<>(words);
        List<String> resultWords = new ArrayList<>();

        for (String word : inputWords) {
            word = ALPHABETIC_RPL.matcher(word).replaceAll("");
            int length = word.length();
            if (length >= MIN_WORD_LENGTH && length <= MAX_WORD_LENGTH) {
                resultWords.add(word.toLowerCase());
            }
        }
        resultWords.sort(new ReversedOrderComparator());
        resultWords.sort(new NaturalOrderComparator());
        if (resultWords.size() <= ARRAY_MAX_LENGTH) {
            return resultWords.subList(0, ARRAY_MAX_LENGTH);
        }
        return resultWords;
    }

    public static List<String> task2Java8(List<String> words, boolean isParallel) {
        Stream<String> stream = isParallel ? words.parallelStream() : words.stream();

        return stream.map(word -> ALPHABETIC_RPL.matcher(word).replaceAll(""))
                .filter(word -> word.length() >= MIN_WORD_LENGTH)
                .filter(word -> word.length() <= MAX_WORD_LENGTH)
                .map(String::toLowerCase)
                .limit(ARRAY_MAX_LENGTH)
                .sorted((o1, o2) -> o2.length() - o1.length())
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<String> task3(Path... paths) {
        List<String> result;

        result = Arrays.stream(paths)
                .map(Tasks::getLines)
                .flatMap(lines -> lines)
                .flatMap(word -> Arrays.stream(word.split(REGEX)))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()))
                .entrySet().stream()
                .sorted((o1, o2) -> ((int) (o2.getValue() - o1.getValue())))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return result;
    }

    private static Stream<String> getLines(Path path) {
        Stream<String> resultStream = null;
        try {
            resultStream = Files.lines(path);
        } catch (IOException e) {
            System.err.println(e);
        }
        return resultStream;
    }

    static class ReversedOrderComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o2.length() - o1.length();
        }
    }
    static class NaturalOrderComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.length() - o2.length();
        }
    }
}

