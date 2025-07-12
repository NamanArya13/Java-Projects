package dev.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

class Counter{

    private final AtomicLong atomicLong;

    public Counter(long value){
        atomicLong = new AtomicLong(value);
    }

    public Long getValue(){
        return atomicLong.get();
    }

    public void increment(){
        atomicLong.incrementAndGet();
    }

    public void decrement(){
        atomicLong.decrementAndGet();
    }

}

public class AtomicCounterMain {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Counter counter = new Counter(15L);

        Runnable incrementTask = counter::increment;
        Runnable decrementTask = counter::decrement;

        for (int i = 0; i < 100000000; i++) {
            executorService.submit(incrementTask);
            executorService.submit(decrementTask);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES); // ✅ Wait for all tasks

        System.out.println("Final Counter Value: " + counter.getValue());
    }
}
