package dev.threadfinal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ProducerConsumerQueue{

    private final BlockingQueue<Integer> blockingQueue;

    public ProducerConsumerQueue(int capacity){
        this.blockingQueue = new ArrayBlockingQueue<>(capacity);
    }

    public void addTask(int item){
        try {
            System.out.println("Trying to add item - "+item);
            blockingQueue.put(item);
            System.out.println("Item added");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void completeTask(){
        int item = 0;
        try {
            item = blockingQueue.take();
            System.out.println("Task completed - "+item);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class BlockingQueueDSMain {
    public static void main(String[] args) throws InterruptedException {
        ProducerConsumerQueue producerConsumerQueue = new ProducerConsumerQueue(5);
        Thread producer = new Thread(()->{
           for(int i = 0;i<10000;i++){
               producerConsumerQueue.addTask(i);
           }
        });

        Thread consumer = new Thread(()->{
            for(int i = 0;i<10000;i++){
                producerConsumerQueue.completeTask();
            }
        });

        producer.start();
        Thread.sleep(2000);
        consumer.start();

        producer.join();
        consumer.join();
    }
}
