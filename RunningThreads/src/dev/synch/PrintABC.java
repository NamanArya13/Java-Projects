package dev.synch;

class ABC{

    boolean isA = true;

    boolean isB = false;

    boolean isC = false;

    private final Object lock = new Object();

    void printA(){
        while (true){
            synchronized (lock){
                while (isB || isC) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print("A");
                try {
                    Thread.sleep(300);
                    isA=false;
                    isB=true;
                    lock.notifyAll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    void printB(){
        while (true){
            synchronized (lock){
                while (isA || isC) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print("B");
                try {
                    Thread.sleep(300);
                    isB=false;
                    isC=true;
                    lock.notifyAll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    void printC(){
        while (true){
            synchronized (lock){
                while (isA || isB) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print("C");
                try {
                    Thread.sleep(300);
                    isC=false;
                    isA=true;
                    lock.notifyAll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

public class PrintABC {
    public static void main(String[] args) {
        ABC abc = new ABC();

        Thread aPrint = new Thread(abc::printA);
        Thread bPrint = new Thread(abc::printB);
        Thread cPrint = new Thread(abc::printC);

        aPrint.start();
        bPrint.start();
        cPrint.start();
    }
}
