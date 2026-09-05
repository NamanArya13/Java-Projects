package dev.threadfinal;

class OddEven{

    private final int counter;
    private boolean evenFlag;

    public OddEven(int counter){
        this.counter = counter;
        this.evenFlag = true;
    }

    public void printEven(){
        for(int i = 0;i<=counter;i+=2){
            synchronized (this) {
                while (!evenFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(i);
                evenFlag = !evenFlag;
                this.notify();
            }
        }
    }

    public void printOdd(){
        for(int i = 1;i<=counter;i+=2){
            synchronized (this) {
                while (evenFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(i);
                evenFlag = !evenFlag;
                this.notify();
            }
        }
    }
}
public class OddEvenMain {
    public static void main(String[] args) throws InterruptedException {
        OddEven oddEven = new OddEven(20);
        Thread even = new Thread(oddEven::printEven);
        Thread odd = new Thread(oddEven::printOdd);

        odd.start();
        even.start();

        even.join();
        odd.join();
    }
}
