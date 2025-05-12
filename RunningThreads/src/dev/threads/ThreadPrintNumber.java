package dev.threads;

class WorkerThread implements Runnable{

    private final int i;
    public WorkerThread(int i){
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(this.i);
        System.out.println("Thread - "+Thread.currentThread().getName());
    }
}

public class ThreadPrintNumber{
    public static void main(String[] args) throws InterruptedException {

        int i;
        for(i = 0;i<6;i++){
            Thread thread = new Thread(new WorkerThread(i));
            thread.start();
            Thread.sleep(300);
        }
    }
}