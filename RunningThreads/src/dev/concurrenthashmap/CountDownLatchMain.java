package dev.concurrenthashmap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchMain {

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

        CountDownLatch latch = new CountDownLatch(200);
        Runnable task = ()->{
            System.out.println("Starting task - "+Thread.currentThread().getName());
            downloadFile();
            latch.countDown();
            System.out.println("Task completed - "+Thread.currentThread().getName());
        };

        ExecutorService service = Executors.newFixedThreadPool(5);

        for(int i = 0;i<200;i++){
            service.submit(task);
        }

        try {
            latch.await();
            System.out.println("Task completed");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main2(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(3);

        ExecutorService service = Executors.newFixedThreadPool(2);

        Random random = new Random();

        Runnable databaseService = ()->{
            try {
                Thread.sleep(random.nextInt(1000,10000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Started Database service");
            countDownLatch.countDown();
        };

        Runnable cacheService = ()->{
            try {
                Thread.sleep(random.nextInt(1000,10000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Started cache service");
            countDownLatch.countDown();
        };

        Runnable authService = ()->{
            try {
                Thread.sleep(random.nextInt(1000,10000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Started Auth service");
            countDownLatch.countDown();
        };

        service.submit(databaseService);
        service.submit(authService);
        service.submit(cacheService);

        try {
            countDownLatch.await();
            System.out.println("System is up");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        service.shutdown();
    }
}
