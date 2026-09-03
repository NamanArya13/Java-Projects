package dev.threadfinal;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

class BlockQueue{
    private Semaphore full;

    // Semaphore to track the number of available empty spots in the queue
    private Semaphore empty;

    // A concurrent deque to store the elements of the queue
    private ConcurrentLinkedDeque<Integer> deque;

    // Constructor to initialize the semaphores and the deque
    public BlockQueue(int capacity) {
        // 'full' starts with 0 since the queue is empty initially
        full = new Semaphore(0);
        // 'empty' starts with the capacity of the queue
        empty = new Semaphore(capacity);
        // Initialize the concurrent deque to hold queue elements
        deque = new ConcurrentLinkedDeque<>();
    }

    // Method to add an element to the queue
    public void enqueue(int element) throws InterruptedException {
        // Acquire an empty spot before adding an element
        empty.acquire();
        // Add the element to the front of the deque
        deque.addFirst(element);
        // Release a full spot after adding the element
        full.release();
    }

    // Method to remove and retrieve an element from the queue
    public int dequeue() throws InterruptedException {
        int result = -1;
        // Acquire a full spot before removing an element
        full.acquire();
        // Remove the element from the end of the deque
        result = deque.pollLast();
        // Release an empty spot after removing the element
        empty.release();
        return result;
    }

    // Method to get the current size of the queue
    public int size() throws InterruptedException {
        int result = 0;
        // Retrieve and return the size of the deque
        result = deque.size();
        return result;
    }

}

public class BlockingQueueSemaphoreMain {
    public static void main(String[] args) {

    }
}
