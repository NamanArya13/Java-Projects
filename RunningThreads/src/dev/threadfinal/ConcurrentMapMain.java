package dev.threadfinal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapMain {

    private final ConcurrentHashMap<String, Integer> frequencyMap =
            new ConcurrentHashMap<>();

    public void processWords() {
        for (int i = 0; i < 100_000; i++) {
            frequencyMap.merge("apple", 1, Integer::sum);
            frequencyMap.merge("banana", 1, Integer::sum);
            frequencyMap.merge("orange", 1, Integer::sum);
        }
    }

    public void printFrequency() {
        frequencyMap.forEach((word, count) ->
                System.out.println(word + " -> " + count)
        );
    }

    public static void main(String[] args) throws InterruptedException {

        ConcurrentMapMain processor = new ConcurrentMapMain();

        int numberOfThreads = 10;
        int iterationsPerThread = 100_000;

        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(
                    processor::processWords,
                    "Thread-" + (i + 1)
            );
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        processor.printFrequency();

        int expectedCount =
                numberOfThreads * iterationsPerThread;

        System.out.println("\nExpected count for each word: "
                + expectedCount);
    }
}