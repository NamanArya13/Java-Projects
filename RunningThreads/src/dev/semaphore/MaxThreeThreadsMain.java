package dev.semaphore;

import java.util.concurrent.Semaphore;

public class MaxThreeThreadsMain {

    private static final Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
        Runnable task = ()->{
            try {
                semaphore.acquire();
                System.out.println("Name - " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                semaphore.release();
            }
        };

        for(int i = 0;i<100;i++){
            new Thread(task).start();
        }
    }
}
