package dev.executors;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class GracefulShutDown {

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

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable task = ()->{
            System.out.println("Starting download - "+Thread.currentThread().getName());
            downloadFile();
            System.out.println("Download complete");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        };

        for(int i = 0;i<5;i++){
            executorService.execute(task);
        }

        executorService.shutdown();
        try {
            Thread.sleep(1000);
            executorService.execute(task);
        }catch (RejectedExecutionException e){
            System.out.println("Cannot accept tasks after shutdown");
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        List<Runnable> pendingTasks = new ArrayList<>();
        try{
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS))
                pendingTasks = executorService.shutdownNow();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        }

        System.out.println("Executor terminated");
        pendingTasks.forEach(System.out::println);

    }
}
