package dev.threads;

public class ThreadsFirst implements Runnable {

    private int counter = 0;
    @Override
    public void run() {
        for(int i = 0;i<1000;i++){
            counter++;
        }
        System.out.println("Counter = "+counter);
    }

}
