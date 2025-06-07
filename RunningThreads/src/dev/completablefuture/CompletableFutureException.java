package dev.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class CompletableFutureException {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture<String> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("Running risky task...");
            if (true) throw new RuntimeException("Boom!");
            return "Success";
        }, executor).handle((result, ex) -> {
            if (ex != null) {
                System.out.println("Handled exception: " + ex.getMessage());
                return "Recovered in handle()";
            } else {
                return result;
            }
        });

        System.out.println("Result: " + task.join());
        executor.shutdown();
    }

    public static void main2(String[] args) {

        Supplier<String> task = ()->{
            try {
                Thread.sleep(2000);
                System.out.println("Starting Task");
                throw new RuntimeException("Exception thrown");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(task).exceptionally(ex-> {
            System.out.println("Exception thrown");
            return "Exception was thrown";
        }
        );

        completableFuture.join();
        try {
            System.out.println(completableFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
