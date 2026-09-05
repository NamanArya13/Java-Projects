package dev.threadfinal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureSumMain {

    static class SumTask implements Callable<Long> {

        private final int start;
        private final int end;

        public SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Long call() {
            long sum = 0;

            for (int i = start; i <= end; i++) {
                sum += i;
            }

            System.out.println(
                    "Range " + start + " - " + end +
                            " processed by " + Thread.currentThread().getName()
            );

            return sum;
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Callable<Long>> tasks = new ArrayList<>();

        // Create 5 tasks
        for (int i = 0; i < 5; i++) {
            int start = i * 100 + 1;
            int end = start + 99;

            tasks.add(new SumTask(start, end));
        }

        List<Future<Long>> futures = new ArrayList<>();

        // Submit all tasks
        for (Callable<Long> task : tasks) {
            futures.add(executorService.submit(task));
        }

        long finalSum = 0;

        // Collect results
        for (Future<Long> future : futures) {
            try {
                finalSum += future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();

        System.out.println("Final combined sum = " + finalSum);
    }
}