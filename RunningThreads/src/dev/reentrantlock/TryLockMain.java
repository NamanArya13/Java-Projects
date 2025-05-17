package dev.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockMain {

    private static final ReentrantLock lock1 = new ReentrantLock();

    private static final ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Runnable task = ()->{
            boolean res1 = lock1.tryLock();
            try{
                if(res1){
                    System.out.println("Lock acquired "+lock1.getHoldCount());
                    System.out.println(Thread.currentThread().getName()+" acquired first locks");
                    boolean res2 = lock2.tryLock(1000, TimeUnit.MILLISECONDS);
                    try{
                        if(res2) System.out.println(Thread.currentThread().getName()+" acquired both locks");
                        Thread.sleep(500);
                    }finally {
                       if (res2) lock2.unlock();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
               if (res1) lock1.unlock();
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    };
    }

