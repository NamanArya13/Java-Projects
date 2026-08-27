package dev.reentrantlock;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class SharedQueue<T> {

    private final Queue<T> queue;
    private final int size;
    private final ReentrantLock lock;
    private final Condition isFull;
    private final Condition isEmpty;

    static class Data {
        LocalDate timeStamp;
        String message;

        public Data(LocalDate timeStamp, String message) {
            this.timeStamp = timeStamp;
            this.message = message;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "timeStamp=" + timeStamp +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    public SharedQueue(int size, ReentrantLock lock) {
        this.size = size;
        this.queue = new LinkedList<>();
        this.lock = lock;
        this.isFull = lock.newCondition();
        this.isEmpty = lock.newCondition();
    }

    public void enqueue(T item) {
        lock.lock();
        try {
            while (queue.size() == size) {
                isFull.await();
            }
            queue.add(item);
            System.out.println("Added item - " + item);
            isEmpty.signal(); // Wake up one waiting consumer
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void dequeue() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                isEmpty.await();
            }
            T item = queue.poll();
            System.out.println("Removed item - " + item);
            isFull.signal(); // Wake up one waiting producer
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}

public class ProducerConsumerMain {

    private static final Random random = new Random();

    public static void main(String[] args) {
        SharedQueue<SharedQueue.Data> sharedQueue = new SharedQueue<>(5, new ReentrantLock());

        // Start 3 Producer Threads
        for (int i = 0; i < 3; i++) {
            int producerId = i;
            new Thread(() -> {
                while (true) {
                    int val = random.nextInt(10000);
                    SharedQueue.Data data = new SharedQueue.Data(LocalDate.now(), "P" + producerId + " - Msg " + val);
                    sharedQueue.enqueue(data);
                    try {
                        Thread.sleep(500); // simulate work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Producer-" + producerId).start();
        }

        // Start 2 Consumer Threads
        for (int i = 0; i < 2; i++) {
            int consumerId = i;
            new Thread(() -> {
                while (true) {
                    sharedQueue.dequeue();
                    try {
                        Thread.sleep(800); // simulate work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Consumer-" + consumerId).start();
        }
    }
}


