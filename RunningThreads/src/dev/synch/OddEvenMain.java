package dev.synch;

class OddEvenPrint{

    private final int limit;
    private boolean oddFlag;

    public OddEvenPrint(int limit){
        this.limit = limit;
        oddFlag = false;
    }

    public void OddPrint(){
        for(int i = 1;i<=limit;i+=2) {
            synchronized (this) {
                while (!oddFlag) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print(i+" , ");
                oddFlag = false;
                notify();
            }
        }
    }

    public void evenPrint(){
        for(int i = 0;i<=limit;i+=2) {
            synchronized (this) {
                while (oddFlag) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print(i+" , ");
                oddFlag = true;
                notify();
            }
        }
    }

}
public class OddEvenMain {
    public static void main(String[] args) throws InterruptedException {

        OddEvenPrint oddEvenPrint = new OddEvenPrint(100);
        Runnable oddTask = oddEvenPrint::OddPrint;
        Runnable evenTask = oddEvenPrint::evenPrint;

        Thread oddThread = new Thread(oddTask);
        Thread evenThread = new Thread(evenTask);

        evenThread.start();
        Thread.sleep(100);
        oddThread.start();
    }
}
