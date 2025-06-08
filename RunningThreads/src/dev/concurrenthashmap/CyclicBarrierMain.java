package dev.concurrenthashmap;

import java.util.Random;
import java.util.concurrent.*;

public class CyclicBarrierMain {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int round = i + 1;

            // Barrier action: will be executed once all 10 players reach the barrier
            Runnable barrierAction = () -> System.out.println("Game starts Now - Round " + round);

            CyclicBarrier barrier = new CyclicBarrier(10, barrierAction);

            // Submit 10 players
            for (int j = 0; j < 10; j++) {
                executorService.submit(() -> {
                    int playerNumber = random.nextInt(1, 100);
                    System.out.println("Player " + playerNumber + " getting ready");

                    try {
                        Thread.sleep(random.nextInt(1000, 3000));
                        System.out.println("Player " + playerNumber + " is ready");
                        barrier.await(); // Wait for all 10 players
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                });
            }

            // Wait before submitting next round (long enough for this round to finish)
            try {
                Thread.sleep(4000); // You can increase this if needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Round " + round + " submitted\n");
        }

        executorService.shutdown();
    }
}
