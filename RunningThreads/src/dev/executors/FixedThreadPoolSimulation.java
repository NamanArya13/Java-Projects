package dev.executors;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class CustomThreadFactory implements ThreadFactory{

    private static int count = 0;

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("File Download worker - "+Thread.currentThread().getName()+"- "+count++);
        thread.setDaemon(false);
        return thread;
    }
}

public class FixedThreadPoolSimulation {



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
                System.out.println("Downloading file by "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                    downloadFile();
                    System.out.println("File downloaded by "+Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(5,new CustomThreadFactory());

        for(int i = 0;i<100;i++){
            executorService.submit(task);
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt(); // good practice
        }


        System.out.println("Executor has shutdown");
    }
}
