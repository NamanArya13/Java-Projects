package dev.threads;

public class Application {

    private static int counter = 0;
    public static void main(String[] args) throws InterruptedException {


        Runnable task = ()->{
            for(int i = 0;i<100000;i++){
                counter++;
            }
        };

        Thread thread1 = new Thread(new ThreadsFirst());
        Thread thread2 = new Thread(new ThreadsFirst());

        thread1.start();
        thread2.start();

        Thread thread3 = new Thread(task);
        Thread thread4 = new Thread(task);

        thread3.start();
        thread4.start();
        thread3.join();
        thread4.join();
        System.out.println("Shared Counter = "+counter);
    }
}
