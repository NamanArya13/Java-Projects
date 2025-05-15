package dev.atomic;

import java.util.concurrent.atomic.AtomicInteger;

class AtomicCounter{

    private final AtomicInteger integer = new AtomicInteger(0);

    public void increment(){
        integer.incrementAndGet();
    }

    public void decrement(){
        integer.decrementAndGet();
    }

    public void display(){
        System.out.println("Value = "+integer.get());
    }
}
public class AtomicMain {
    public static void main(String[] args) throws InterruptedException {

        AtomicCounter counter = new AtomicCounter();

        for(int i = 0;i<10000;i++){
            new Thread(counter::increment).start();
            new Thread(counter::decrement).start();
        }
        Thread.sleep(5000);
        counter.display();

        AtomicCounter counter1 = new AtomicCounter();

        Thread t1 = new Thread(()->{
            for(int i = 0;i<10000000;i++) counter1.increment();
        });

        Thread t2 = new Thread(()->{
            for(int i = 0;i<10000000;i++) counter1.decrement();
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        counter1.display();
    }
}
