package dev.synch;

class SharedResource {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void workOne() {
        synchronized (lock1) {
            System.out.println("Thread-1 acquired lock1");

            try { Thread.sleep(100); } catch (InterruptedException e) {}

            synchronized (lock2) {
                System.out.println("Thread-1 acquired lock2");
            }
        }
    }

    public void workTwo() {
        synchronized (lock2) {
            System.out.println("Thread-2 acquired lock2");

            try { Thread.sleep(100); } catch (InterruptedException e) {}

            synchronized (lock1) {
                System.out.println("Thread-2 acquired lock1");
            }
        }
    }
}

public class SimulateDeadlock {
    public static void main(String[] args) {
        SharedResource shared = new SharedResource();

        Thread t1 = new Thread(shared::workOne);
        Thread t2 = new Thread(shared::workTwo);

        t1.start();
        t2.start();
    }
}
