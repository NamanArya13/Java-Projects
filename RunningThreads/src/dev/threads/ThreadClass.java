package dev.threads;

public class ThreadClass extends Thread{

    private int counter = 0;
    @Override
    public void run(){
        for(int i = 0;i<1000;i++){
            counter++;
        }
        System.out.println("Counter5 = "+counter);
        System.out.println("Thread Name - "+ThreadClass.currentThread().getName());
    }
}
