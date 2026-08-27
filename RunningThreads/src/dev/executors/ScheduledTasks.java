package dev.executors;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.*;

public class ScheduledTasks {

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

        Runnable task = ()->{
            System.out.println("Starting download - "+Thread.currentThread().getName());
            downloadFile();
            System.out.println("Download complete");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        };

        Callable<String> callable = ()->{
            System.out.println("Starting download - "+Thread.currentThread().getName());
            downloadFile();
            System.out.println("Download complete");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
            return "Download Complete";
        };

        ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(3);

        for(int i = 0;i<5;i++) {
            scheduledService.scheduleWithFixedDelay(task, 1, 3, TimeUnit.SECONDS);
        }


    }

    public static void main2(String[] args) {

        Runnable task = ()->{
            System.out.println("Starting download - "+Thread.currentThread().getName());
            downloadFile();
            System.out.println("Download complete");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        };

        ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(5);

        scheduledService.scheduleAtFixedRate(task,2,3,TimeUnit.SECONDS);

        try{
            Thread.sleep(10000);
            scheduledService.shutdown();
            if(!scheduledService.awaitTermination(20,TimeUnit.SECONDS)){
                scheduledService.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduledService.shutdownNow();
            System.out.println("Shutting down");
        }
        System.out.println("Service was shutdown");
    }
}
