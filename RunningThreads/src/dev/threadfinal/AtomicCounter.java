package dev.threadfinal;

import java.util.concurrent.atomic.AtomicInteger;

class AtomicIntegerCounter{

    private AtomicInteger counter;
    private int counterInt;

    public AtomicIntegerCounter(){
        counter = new AtomicInteger(0);
        counterInt = 0;
    }

    public void increment(){
        for(int i = 0;i<1000000;i++){
            counter.incrementAndGet();
            counterInt++;
        }
    }

    public int getAtomicValue(){
        return counter.get();
    }

    public int getCounter(){
        return counterInt;
    }
}

public class AtomicCounter {
    public static void main(String[] args) {

        AtomicIntegerCounter counter = new AtomicIntegerCounter();

        Thread[] threads = new Thread[10];
        for(int i = 0;i<threads.length;i++){
            threads[i] = new Thread(counter::increment);
        }

        for(Thread thread: threads){
            thread.start();
        }

        for(Thread thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(counter.getAtomicValue());
        System.out.println(counter.getCounter());

    }
}
