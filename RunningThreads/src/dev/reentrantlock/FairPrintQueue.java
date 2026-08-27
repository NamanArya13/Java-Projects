package dev.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class FairPrintQueue {

    private static final ReentrantLock lock = new ReentrantLock(true);
    public static void main(String[] args) throws InterruptedException {

        Runnable task = ()->{
            while (true) {
                lock.lock();
                try {
                    System.out.println("Printing - " + Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        };


            for (int i = 0; i < 5; i++) {
                new Thread(task, "Thread " + i).start();
            }

    }
}
