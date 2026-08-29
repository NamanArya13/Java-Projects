package dev.threadfinal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedThreadPoolMain {
    public static void main(String[] args) throws InterruptedException {

        List<Runnable> tasks = new ArrayList<>();

        for(int i = 0;i<10;i++){
            final int num = i;
            tasks.add(() -> {
                try {
                    System.out.println("Task - "+num+" completed by "+Thread.currentThread().getName());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        });
        }

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(Runnable task: tasks){
            executorService.execute(task);
        }

        executorService.shutdown();
        executorService.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println("All tasks completed");

    }
}
