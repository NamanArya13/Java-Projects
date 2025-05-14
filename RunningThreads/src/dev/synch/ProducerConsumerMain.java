package dev.synch;

import java.util.ArrayList;
import java.util.List;

class BlockingQueue{

    private int size;

    private int data = 0;

    private List<String> arrayList;

    private final Object lock = new Object();

    public BlockingQueue(int size){
        this.size = size;
        arrayList = new ArrayList<>(size);
    }

    public void producer(){
        while (true){
            synchronized (lock){
                while (arrayList.size()>=size){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                while (arrayList.size()<size){
                    System.out.println(" Producing Data - "+data);
                    arrayList.add("Data - "+data++);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.notifyAll();
            }
        }
    }

    public void consumer(){
        while (true){
            synchronized (lock){
                while (arrayList.isEmpty()){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                while (!arrayList.isEmpty()){
                    System.out.println("Consuming Data - "+arrayList.get(arrayList.size()-1));
                    arrayList.remove(arrayList.size()-1);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.notifyAll();
            }
        }
    }
}

public class ProducerConsumerMain {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue = new BlockingQueue(5);

        Thread producer = new Thread(queue::producer);

        Thread consumer = new Thread(queue::consumer);

        producer.start();
        Thread.sleep(50);
        consumer.start();
    }


}
