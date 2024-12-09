package dev.lpa;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        File resourceA = new File("inputData.csv");
        File resourceB = new File("outputData.json");

        Thread threadA = new Thread(()->{
           String threadName = Thread.currentThread().getName();
            System.out.println(threadName+" attempting to lock resourceA (csv");
            synchronized (resourceA){
                System.out.println(threadName+" has lock on resourceA (csv)");
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(threadName+" NEXT attempting to lock resourceB (json),"+"still has lock on resource A (csv) ");
                synchronized (resourceB){
                    System.out.println(threadName+ " has lock on resourceB (json) ");
                }
                System.out.println(threadName+" has released lock on resourceB ");
            }
            System.out.println(threadName+" has released lock on resourceA");
        },"THREAD-A");


        // This code produces deadlock
//        Thread threadB = new Thread(()->{
//            String threadName = Thread.currentThread().getName();
//            System.out.println(threadName+" attempting to lock resourceB (csv)");
//            synchronized (resourceB){
//                System.out.println(threadName+" has lock on resourceB (csv)");
//                try{
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println(threadName+" NEXT attempting to lock resourceA (json),"+"still has lock on resource B (csv) ");
//                synchronized (resourceA){
//                    System.out.println(threadName+ " has lock on resourceA (json) ");
//                }
//                System.out.println(threadName+" has released lock on resourceA ");
//            }
//            System.out.println(threadName+" has released lock on resourceB");
//        },"THREAD-B");

        Thread threadB = new Thread(()->{
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName+" attempting to lock resourceA (csv");
            synchronized (resourceA){
                System.out.println(threadName+" has lock on resourceA (csv)");
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(threadName+" NEXT attempting to lock resourceB (json),"+"still has lock on resource A (csv) ");
                synchronized (resourceB){
                    System.out.println(threadName+ " has lock on resourceB (json) ");
                }
                System.out.println(threadName+" has released lock on resourceB ");
            }
            System.out.println(threadName+" has released lock on resourceA");
        },"THREAD-B");

        threadA.start();
        threadB.start();

        try{
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
