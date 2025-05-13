package dev.synch;

class VolatileClass{

    public volatile boolean flag = true;

    public void run(){

        while (flag){}
    }

    public void stop(){
        flag = false;
    }

}

public class VolatileMain {

    public static void main(String[] args) throws InterruptedException {

        VolatileClass volatileClass = new VolatileClass();

        Thread runThread = new Thread(volatileClass::run);

        runThread.start();

        Thread.sleep(2000);

        volatileClass.stop();

        runThread.join();

        System.out.println("Thread stopped "+runThread.getState());
    }
}
