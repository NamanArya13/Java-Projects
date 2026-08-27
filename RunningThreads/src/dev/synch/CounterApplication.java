package dev.synch;

class Counter{

    private int counter = 0;

    public void increment(){
        synchronized (this) {
            counter++;
        }
    }

    public synchronized void decrement(){
        synchronized (this) {
            counter--;
        }
    }

    public void display(){
        System.out.println("Counter = "+this.counter);
    }
}

public class CounterApplication {
    public static void main(String[] args) throws InterruptedException {

        Counter counter1 = new Counter();
        Counter counter2 = new Counter();

        Thread t1 = new Thread(()->{
            for(int i = 0;i<100000;i++) counter2.increment();
        });

        Thread t2 = new Thread(()->{
           for(int i = 0;i<100000;i++) counter2.decrement();
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        counter2.display();



        for(int i = 0;i<10000;i++){
            new Thread(counter1::increment).start();
            new Thread(counter1::decrement).start();
        }

        Thread.sleep(3000);

        counter1.display();
    }
}
