package dev.threads;

import java.util.concurrent.TimeUnit;

public class ThreadStates implements Runnable{
    @Override
    public void run() {

        System.out.println("Thread State - "+Thread.currentThread().getState());
        for(int i = 0;i<10;i++){
            System.out.println(i);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
