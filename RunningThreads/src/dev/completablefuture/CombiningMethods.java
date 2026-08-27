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

public class CombiningMethods {

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
        List<Supplier<String>> supplierList = new ArrayList<>(List.of(()->{
            System.out.println("Starting Weather Service - "+Thread.currentThread().getName());
            downloadFile();
            System.out.println("Completing Weather Service - "+Thread.currentThread().getName());
            return "Weather Done";
        },()->{
            System.out.println("Starting News Service - "+Thread.currentThread().getName());
            downloadFile();
            System.out.println("Completing News Service - "+Thread.currentThread().getName());
            return "News Done";
        },()->{
            System.out.println("Starting Breakfast Service - "+Thread.currentThread().getName());
            downloadFile();
            System.out.println("Completing Breakfast Service - "+Thread.currentThread().getName());
            return "Breakfast done";
        }));

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(supplierList.get(0));
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(supplierList.get(1));
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(supplierList.get(2));

        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3);
            completableFuture.join();
        }catch (RuntimeException e){
            System.out.println("Exception thrown");
        }


    }

    public static void main3(String[] args) {
        Random random = new Random();

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(random.nextInt(1000,10000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Completed 1");
            return random.nextInt(100,200);
        });

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(random.nextInt(1000,10000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Completed 2");
            return random.nextInt(100,200);
        });

        CompletableFuture<Integer> completableFuture = completableFuture1.thenCombineAsync(completableFuture2, Integer::sum);
        completableFuture.join();
        try {
            System.out.println(completableFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main2(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Supplier<String> callWeatherService = ()->{
            System.out.println("Starting Weather Service - "+Thread.currentThread().getName());
            downloadFile();
            System.out.println("Completing Weather Service - "+Thread.currentThread().getName());
            return "Weather Done";
        };

        Supplier<String> callNewsService = ()->{
            System.out.println("Starting News Service - "+Thread.currentThread().getName());
            downloadFile();
            System.out.println("Completing News Service - "+Thread.currentThread().getName());
            return "News Done";
        };

        CompletableFuture<String> completableFutureWeather = CompletableFuture.supplyAsync(callWeatherService,executorService);
        CompletableFuture<String> completableFutureNews = CompletableFuture.supplyAsync(callNewsService,executorService);

        CompletableFuture<Void> completableFuture = completableFutureWeather.thenCombineAsync(completableFutureNews,(a,b)->a+b,executorService).thenAcceptAsync(System.out::println,executorService);
        completableFuture.join();


    }
}
