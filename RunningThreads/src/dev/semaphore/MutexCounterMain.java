package dev.semaphore;

import java.util.concurrent.Semaphore;

class SharedResource{

    private int counter = 0;

    public void increment(){
        counter++;
    }

    public void display(){
        System.out.println("Counter = "+counter);
    }
}

public class MutexCounterMain {

    private static final Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {

        SharedResource resource = new SharedResource();

        Runnable task = ()->{
            try{
                semaphore.acquire();
                resource.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                semaphore.release();
            }
        };

        for(int i = 0;i<10;i++){
            new Thread(task).start();
        }

        Thread.sleep(2000);

        resource.display();
    }
}
