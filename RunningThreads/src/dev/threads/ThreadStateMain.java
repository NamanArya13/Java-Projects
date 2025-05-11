package dev.threads;

public class ThreadStateMain {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new ThreadStates());
        System.out.println("Thread State - "+thread.getState());

        thread.start();
        Thread.sleep(1000);
        System.out.println("Thread state - "+thread.getState());
    }
}
