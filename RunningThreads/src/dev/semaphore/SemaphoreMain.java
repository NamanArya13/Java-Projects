package dev.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreMain {

    static Semaphore semaphore = new Semaphore(3);
    static int taskCounter = 1;

    public static void main(String[] args) {
        Runnable task = ()-> {
            while (true){
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " acquired permit, working...");
                    Thread.sleep(1000); // work
                    System.out.println(Thread.currentThread().getName() + " finished ");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
        }
        };

        for(int i = 0;i<5;i++){
            new Thread(task).start();
        }
    }
}
