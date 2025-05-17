package dev.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantPrinter {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        lock.lock();
        try{
            printA();
            System.out.println(lock.getHoldCount());
            lock.lock();
            try{
                printB();
                System.out.println(lock.getHoldCount());
                lock.lock();
                try{
                    printC();
                    System.out.println(lock.getHoldCount());
                }finally {
                    lock.unlock();
                    System.out.println(lock.getHoldCount());
                }
            }finally {
                lock.unlock();
                System.out.println(lock.getHoldCount());
            }
        }finally {
            lock.unlock();
            System.out.println(lock.getHoldCount());
        }
    }

    public static void printA(){
        System.out.println("A");
    }

    public static void printB(){
        System.out.println("B");
    }

    public static void printC(){
        System.out.println("C");
    }
}
