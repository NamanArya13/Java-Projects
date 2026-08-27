package dev.threads;

import java.util.concurrent.*;

class Ping implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.println("Ping");
        try {
            Thread.sleep(1000);
            return 8;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Pong implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.println("Pong");
        try {
            Thread.sleep(1000);
            return 5;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

public class PingPong {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Callable<Integer> ping = new Ping();

        Callable<Integer> pong = new Pong();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> num1 = null;
        Future<Integer> num2 = null;
        for(int i =0;i<10;i++) {
            num1 = executorService.submit(ping);
            num2 = executorService.submit(pong);


        }
        Thread.sleep(30000);
        if (num1.isDone()){
            System.out.println(num1.get());
        }

        if (num2.isDone()){
            System.out.println(num2.get());
        }

        executorService.shutdown();

    }
}
