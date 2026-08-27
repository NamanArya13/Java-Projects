package dev.completablefuture;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class CompletableFutureMethods {

    private static void downloadFile(){
        String urlString = "https://www.digitalocean.com/robots.txt"; // Replace with the actual URL
        String destination = "downloaded_file.txt"; // Replace with the desired local file path

        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try (InputStream in = connection.getInputStream();
                 FileOutputStream out = new FileOutputStream(destination)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                System.out.println("File downloaded successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(
                () -> System.out.println("Login"), executorService
        );

        // Step 2: thenRunAsync also returns CompletableFuture<Void>
        CompletableFuture<Void> completableFuture2 = completableFuture1.thenRunAsync(
                () -> System.out.println("Fetch User Data"), executorService
        );

        // ✅ Step 3: thenSupplyAsync — this should now be available
        CompletableFuture<Void> completableFuture3 = completableFuture2.thenRunAsync(
                () -> {
                    System.out.println("Returning Result");
                },
                executorService
        );

        try {
            completableFuture3.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();

    }

    public static void main3(String[] args) {

        Random random = new Random();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<CompletableFuture<Double>> completableFutures = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            completableFutures.add(
                    CompletableFuture.supplyAsync(() -> {
                                System.out.println("Thread name - " + Thread.currentThread().getName());
                                return random.nextInt(1, 100);
                            }, executorService)
                            .thenApplyAsync(j -> j * 2,executorService)
                            .thenApplyAsync(k -> Math.pow(k, 2),executorService)
            );
        }

        completableFutures.forEach(s-> {
            try {
                System.out.println(s.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        executorService.shutdown();


    }

    public static void main2(String[] args) {

        // BASIC PRINTING UPPERCASE

        Supplier<String> downloadTask = () -> {
            System.out.println("Starting download - " + Thread.currentThread().getName());
            downloadFile();
            System.out.println("Download complete");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
                return "Incomplete download";
            }
            return "Download Complete";
        };

        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(downloadTask)
                .thenApplyAsync(String::toUpperCase)
                .thenAcceptAsync(System.out::println);

        completableFuture.join();
    }
}
