package dev.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockMain {

    private static final ReentrantLock lock1 = new ReentrantLock();

    private static final ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Runnable task1 = ()->{
            boolean res1 = lock1.tryLock();
            try{
                if(res1){
                    System.out.println("Lock acquired "+lock1.getHoldCount());
                    System.out.println(Thread.currentThread().getName()+" acquired first lock");
                    boolean res2 = lock2.tryLock(5000, TimeUnit.MILLISECONDS);
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

        Runnable task2 = ()->{
            boolean res1 = lock2.tryLock();
            try{
                if(res1){
                    System.out.println("Lock acquired "+lock2.getHoldCount());
                    System.out.println(Thread.currentThread().getName()+" acquired second lock");
                    boolean res2 = lock1.tryLock(3000, TimeUnit.MILLISECONDS);
                    try{
                        if(res2) System.out.println(Thread.currentThread().getName()+" acquired both locks");
                        Thread.sleep(500);
                    }finally {
                        if (res2) lock1.unlock();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if (res1) lock2.unlock();
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    };
    }

