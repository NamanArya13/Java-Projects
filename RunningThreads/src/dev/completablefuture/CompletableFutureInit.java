package dev.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureInit {
    public static void main(String[] args){

        System.out.println(Runtime.getRuntime().availableProcessors());


        Runnable runnable = ()->{
            System.out.println("Hello");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(2);

        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(runnable,service)
                .thenRunAsync(runnable,service).thenAcceptAsync((b)-> System.out.println("done"),service);

        System.out.println("Runnable running");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Sleep over");
        completableFuture.join(); // Wait for async chain to complete

        service.shutdown();
        try{
            if(!service.awaitTermination(5, TimeUnit.SECONDS)){
                service.shutdownNow();
            }
        }catch (InterruptedException e) {
            service.shutdownNow();
            System.out.println("Shutting down");
        }

    }
}
