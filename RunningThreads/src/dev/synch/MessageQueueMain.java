package dev.synch;

import java.util.ArrayList;
import java.util.List;

class Queue<T>{

    private final int size;

    private final List<T> sharedList;

    private final Object lock = new Object();

    public Queue(int size){
        this.size = size;
        sharedList = new ArrayList<>(this.size);
    }

    public void addMessage(T message) throws InterruptedException {
        while (true){
            synchronized (lock){
                while (sharedList.size()>=this.size){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Producing message - "+message);
                sharedList.add(message);
                Thread.sleep(500);
                lock.notifyAll();
            }
        }
    }

    public void getMessage() {
        while (true){
            synchronized (lock){
                while (sharedList.isEmpty()){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Consuming message - "+sharedList.get(0));
                sharedList.remove(0);
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                lock.notifyAll();
            }
        }
    }

}

public class MessageQueueMain {
    public static void main(String[] args) throws InterruptedException{
        Queue<String> queue = new Queue<>(10);

        for(int i = 0;i<10;i++){
            final int idx = i;
            new Thread(()-> {
                try {
                    queue.addMessage("Message - "+idx);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        for(int i = 0;i<3;i++){
            new Thread(queue::getMessage).start();
        }
    }
}
