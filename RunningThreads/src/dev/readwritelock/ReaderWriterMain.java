package dev.readwritelock;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SharedData{

    private final int[] data = new int[]{1,2,3,4,5};
    Random random  = new Random();

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    public void readData(){
        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+" is reading the data ");
            System.out.println("Array Data - "+Arrays.toString(data));
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+" read the data");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void writeData(){
        readWriteLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+" is writing the data ");
            int idx = random.nextInt(0,5);
            data[idx]++;
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+" has written to the data");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
}

class Reader implements Runnable{

    private final SharedData sharedData;

    Reader(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        sharedData.readData();
    }
}

class Writer implements Runnable{

    private final SharedData sharedData;

    Writer(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        sharedData.writeData();
    }
}

public class ReaderWriterMain {
    public static void main(String[] args) {

        SharedData sharedData = new SharedData();

        for(int i = 0;i<5;i++){
            new Thread(new Reader(sharedData)).start();
            new Thread(new Writer(sharedData)).start();
        }
    }
}
