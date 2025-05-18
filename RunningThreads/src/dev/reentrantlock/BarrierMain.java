package dev.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ReusableBarrier{

    private final int totalThreads;
    private int waitingThreads;
    private int generation;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public ReusableBarrier(int totalThreads){
        this.totalThreads = totalThreads;
    }

    public void await(){
        lock.lock();
        try{
            int currentGen = generation;
            waitingThreads++;

            if (waitingThreads == totalThreads){
                System.out.println("All threads reached barrier - signalling all");
                waitingThreads = 0;
                generation++;
                condition.signalAll();
            }else{
                while (generation == currentGen){
                    condition.await();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
}

public class BarrierMain {
    public static void main(String[] args) {
        ReusableBarrier barrier = new ReusableBarrier(3); // 3 threads must reach before proceeding

        Runnable task = () -> {
            for (int i = 1; i <= 2; i++) {
                System.out.println(Thread.currentThread().getName() + " reached barrier, round " + i);
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " passed barrier, round " + i);
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        Thread t3 = new Thread(task, "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}
