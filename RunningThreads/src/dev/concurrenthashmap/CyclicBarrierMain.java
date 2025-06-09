package dev.concurrenthashmap;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierMain {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Random random = new Random();

        CountDownLatch countDownLatch = new CountDownLatch(4);

        AtomicInteger atomicInteger = new AtomicInteger(0);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(4,()->
                System.out.println("Game starting Now"));

        Runnable task = ()->{
            int playerValue = atomicInteger.addAndGet(1);
            System.out.println("Player "+playerValue+" getting ready");
            try {
                Thread.sleep(random.nextInt(1000,10000));
                System.out.println("Player "+playerValue+" is ready");
                cyclicBarrier.await(5,TimeUnit.SECONDS);
                countDownLatch.countDown();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                System.out.println("Player timed out");
                throw new RuntimeException(e);
            }
        };

        for(int i = 0;i<4;i++){
            executorService.submit(task);
        }
        try {
            countDownLatch.await();
            System.out.println("Game ended");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();

    }
}
