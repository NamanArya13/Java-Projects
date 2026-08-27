package dev.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AcquireTimeoutMain {

    private static final Semaphore semaphore = new Semaphore(1);
    public static void main(String[] args) {

        Runnable task = ()->{
            try{
                boolean acquired = semaphore.tryAcquire(2000,TimeUnit.MILLISECONDS);
                if (acquired) {
                    System.out.println("Thread acquired - " + Thread.currentThread().getName());
                    Thread.sleep(5000);
                }else{
                    System.out.println("Thread time out - "+Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                semaphore.release();
            }
        };

        for(int i = 0;i<10;i++){
            new Thread(task).start();
        }
    }
}
