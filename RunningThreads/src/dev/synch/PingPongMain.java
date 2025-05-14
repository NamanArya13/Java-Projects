package dev.synch;

class SharedObject{

    private boolean ping = true;

    private final Object lock = new Object();

    public void changeLock(){
        ping = !ping;
    }

    public void printPing(){
        for(int i = 0;i<10;i++) {
            synchronized (lock) {
                while (!ping) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Ping");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.notify();
                changeLock();
            }

        }
    }

    public void printPong(){
        for(int i = 0;i<10;i++) {
            synchronized (lock) {
                while (ping) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Pong");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.notify();
                changeLock();
            }
        }
    }
}


public class PingPongMain {
    public static void main(String[] args) throws InterruptedException {
        SharedObject sharedObject = new SharedObject();
        Thread t1 = new Thread(sharedObject::printPing);
        Thread t2 = new Thread(sharedObject::printPong);

        t1.start();
        Thread.sleep(50);
        t2.start();
    }
}
