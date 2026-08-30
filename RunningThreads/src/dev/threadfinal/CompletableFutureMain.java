package dev.threadfinal;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureMain {
    public static void main(String[] args) {

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(()->{
            int start = 0;
            int end = 100,sum = 0;
            for(int i = start;i<=end;i++) sum+=i;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return sum;
        }).thenAccept((a)-> System.out.println("The sum is "+a));
        try {
            future.get();
            System.out.println("Task is done");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }


    }
}
