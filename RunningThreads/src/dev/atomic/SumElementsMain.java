package dev.atomic;

import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

class AtomicArray{

    private final AtomicIntegerArray atomicIntegerArray;
    private final int size;

    public AtomicArray(int size){
        this.size = size;
        atomicIntegerArray = new AtomicIntegerArray(this.size);
    }

    public void increment(int idx){
        atomicIntegerArray.addAndGet(idx,5);
    }

    public void decrement(int idx){
        atomicIntegerArray.addAndGet(idx,-10);
    }

    public int sum(){
        int sum = 0;
        for(int i = 0;i<this.size;i++){
            sum = sum+atomicIntegerArray.get(i);
        }
        return sum;
    }
}

public class SumElementsMain {
    public static void main(String[] args) throws InterruptedException {
        AtomicArray atomicArray = new AtomicArray(5);

        for(int i = 0;i<10000;i++){
            final int idx = i;
            new Thread(()->atomicArray.increment((idx+1)%5)).start();
            new Thread(()->atomicArray.decrement((idx+3)%5)).start();
        }

        Thread.sleep(10000);
        System.out.println("Sum = "+atomicArray.sum());
    }
}
