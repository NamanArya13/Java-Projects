package dev.threadfinal;

import java.util.Deque;
import java.util.LinkedList;

class BlockingQueue{

    private final Deque<Integer> queue;
    private final int capacity;

    public BlockingQueue( int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public void enqueue(int item) {
        synchronized (this) {
            while (this.queue.size() == capacity) {
                try {
                    System.out.println("Queue full, waiting...");
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Item inserted - " + item);
            this.queue.addLast(item);
            this.notifyAll();
        }
    }

    public void dequeue() {
        synchronized (this) {
            while (this.queue.isEmpty()) {
                try {
                    System.out.println("Queue empty, waiting...");
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            int item = this.queue.removeFirst();
            System.out.println("Item removed - " + item);
            this.notifyAll();
        }
    }

    public int size(){
        synchronized (this) {
            return queue.size();
        }
    }
}

public class BlockingQueueMain {

    public static void main(String[] args) {

        BlockingQueue queue = new BlockingQueue(3);

        // Producer 1
        Thread producer1 = new Thread(() -> {
            for (int i = 1; i <= 5000; i++) {
                queue.enqueue(i);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Producer-1");


        // Producer 2
        Thread producer2 = new Thread(() -> {
            for (int i = 5000; i <= 10000; i++) {
                queue.enqueue(i);

                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Producer-2");


        // Consumer 1
        Thread consumer1 = new Thread(() -> {
            for (int i = 1; i <= 5000; i++) {
                queue.dequeue();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Consumer-1");


        // Consumer 2
        Thread consumer2 = new Thread(() -> {
            for (int i = 1; i <= 5000; i++) {
                queue.dequeue();

                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Consumer-2");


        // Start all threads
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        // Wait for all threads to finish
        try {
            producer1.join();
            producer2.join();
            consumer1.join();
            consumer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final queue size: " + queue.size());
        System.out.println("All threads finished.");
    }
}
