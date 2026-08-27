package dev.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class ToggleMain {
    public static void main(String[] args) throws InterruptedException {

        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1);

        System.out.println(atomicIntegerArray.get(0));
        atomicIntegerArray.compareAndSet(0,0,1);

        Runnable toggleTask = ()->{
            for(int i = 0;i<1000;i++){
                boolean success;
                do{
                    int current = atomicIntegerArray.get(0);
                    int newVal = (current == 0)?1:0;
                    success = atomicIntegerArray.compareAndSet(0,current,newVal);
                }while (!success);
            }
        };

        Thread t1 = new Thread(toggleTask);
        Thread t2 = new Thread(toggleTask);
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Final switch state = " + atomicIntegerArray.get(0));

    }
}
