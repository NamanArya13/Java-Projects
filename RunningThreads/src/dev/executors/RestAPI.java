package dev.executors;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class RestAPI {

    private static Random random = new Random();
    private static String downloadFile() throws InterruptedException {
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
                Thread.sleep(random.nextInt(1000,20000));
                return "Download successful";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Thread.sleep(1000,20000);
            return "Download Failed";
        }
    }
    public static void main(String[] args) {

        Callable<String> task = ()->{
            System.out.println("Calling api - "+Thread.currentThread().getName());
            String response = downloadFile();
            System.out.println("Called api - "+Thread.currentThread().getName());
            return response;
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future<String>> futureList = new ArrayList<>();

        for(int i = 0;i<5;i++){
            futureList.add(executorService.submit(task));
        }

        futureList.forEach(s-> {
            try {
                s.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Got the result for 5th call");
        Future<String> future = executorService.submit(task);
        try{
            future.get(20,TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Interrupted exception");
        } catch (TimeoutException e) {
            System.out.println("Request Timed out");
        }
    }
}
