package dev.synch;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class BoundedBuffer<T>{

    private final T[] buffer;
    private final int capacity;
    private int index;

    public BoundedBuffer(int capacity){
        this.capacity = capacity;
        buffer = (T[]) new Object[capacity];
        index = 0;
    }

    public void put(T item){
        synchronized (buffer){
            while (index == capacity){
                try {
                    System.out.println("Producer "+Thread.currentThread().getName()+" waiting");
                    buffer.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Adding item "+item);
            buffer[index++] = item;
            buffer.notifyAll();
        }
    }

    public T take(){
        synchronized (buffer){
            while (index == 0){
                try {
                    System.out.println("Consumer waiting");
                    buffer.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            buffer.notifyAll();
            index--;
            System.out.println("Consuming item = " + buffer[index]);
            T item = buffer[index];
            buffer[index] = null; // optional, avoids memory leak
            return item;
        }
    }
}

class ProducerTask<T> implements Runnable{

    private final T value;
    private final BoundedBuffer<T> boundedBuffer;

    public ProducerTask(T value,BoundedBuffer<T> boundedBuffer){
        this.value = value;
        this.boundedBuffer = boundedBuffer;
    }


    @Override
    public void run() {
        boundedBuffer.put(value);
    }
}

class ConsumerTask<T> implements Callable<T> {

    private final BoundedBuffer<T> boundedBuffer;

    public ConsumerTask(BoundedBuffer<T> boundedBuffer){
        this.boundedBuffer = boundedBuffer;
    }



    @Override
    public T call() throws Exception {
        return boundedBuffer.take();
    }
}

public class BoundedBufferMain {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService producer = Executors.newFixedThreadPool(3);
        ExecutorService consumer = Executors.newFixedThreadPool(4);

        BoundedBuffer<String> boundedBuffer = new BoundedBuffer<>(6);

        for(int i = 0;i<100;i++){
            ProducerTask<String> producerTask = new ProducerTask<>("Item - "+i,boundedBuffer);
            producer.submit(producerTask);
        }
        Thread.sleep(2000);

        for(int i = 0;i<100;i++){
            ConsumerTask<String> consumerTask = new ConsumerTask<>(boundedBuffer);
            Thread.sleep(500);
            Future<String> future = consumer.submit(consumerTask);
        }



    }
}
