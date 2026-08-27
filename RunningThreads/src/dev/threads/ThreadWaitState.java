package dev.threads;

import java.util.ArrayList;
import java.util.List;

class Thread1 implements Runnable {
    private final List<Integer> sharedList;

    public Thread1(List<Integer> sharedList) {
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (sharedList) {
                while (sharedList.size() >= 5) {
                    try {
                        sharedList.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                int i = 0;
                while (sharedList.size() < 5) {
                    sharedList.add(i++);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Thread1 added: " + (i - 1));
                }
                sharedList.notify();
            }
        }
    }
}

class Thread2 implements Runnable {
    private final List<Integer> sharedList;

    public Thread2(List<Integer> sharedList) {
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (sharedList) {
                try {
                    while (sharedList.size() < 5) {
                        sharedList.wait(); // Wait until Thread1 fills the list
                    }
                    Thread.sleep(5000);
                    sharedList.clear();
                    System.out.println("Thread2 cleared the list");
                    sharedList.notify(); // Notify Thread1 that list has been cleared
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class ThreadWaitState {
    static final List<Integer> sharedList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Thread1(sharedList));
        Thread thread2 = new Thread(new Thread2(sharedList));

        thread2.start(); // Start the waiting thread first
        Thread.sleep(1000);
        System.out.println("State - "+thread2.getState());
        thread1.start(); // Then start the producer thread
    }
}
