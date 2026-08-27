package dev.executors;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskRetry {

    private static boolean downloadFile(){
        String urlString = "https://www.digitalcean.co.in/robots.txt"; // Replace with the actual URL
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
            System.out.println("Task failed");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

        final int maxAttempts = 3;
        final int[] attemptCount = {0};

        Runnable task = () -> {
            attemptCount[0]++;
            System.out.println("Attempt " + attemptCount[0] + " - " + Thread.currentThread().getName());

            boolean success = downloadFile();

            if (success || attemptCount[0] >= maxAttempts) {
                service.shutdown(); // Stop further retries
                System.out.println(success ? "Download succeeded, stopping retries." : "Max retries reached. Giving up.");
            }
        };

        service.scheduleWithFixedDelay(task, 0, 5, TimeUnit.SECONDS);
    }
}
