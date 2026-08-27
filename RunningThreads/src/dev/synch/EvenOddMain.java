package dev.synch;

class EvenOdd{

    private boolean evenTurn = true;

    private final Object lock = new Object();

    public void printEven(){
        for(int i = 0;i<=100;i+=2){
            synchronized (lock){
                while (!evenTurn){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                    lock.notifyAll();
                    evenTurn = false;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void printOdd(){
        for(int i = 1;i<=100;i+=2){
            synchronized (lock){
                while (evenTurn){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                    lock.notifyAll();
                    evenTurn = true;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
public class EvenOddMain {
    public static void main(String[] args) throws InterruptedException {
        EvenOdd evenOdd = new EvenOdd();

        Thread even = new Thread(evenOdd::printEven);
        Thread odd = new Thread(evenOdd::printOdd);

        even.start();
        Thread.sleep(50);
        odd.start();
    }
}
