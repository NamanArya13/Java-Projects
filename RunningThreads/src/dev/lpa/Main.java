package dev.lpa;


import dev.multipleThreads.ThreadColor;

import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        var multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> taskList = List.of(
                ()->Main.sum(1,10,1,"red"),
                ()->Main.sum(10,100,10,"blue"),
                ()->Main.sum(2,20,2,"green"));
        try{
            var results = multiExecutor.invokeAll(taskList);
            for(var result: results){
                System.out.println(result.get(500,TimeUnit.MILLISECONDS));
            }
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    public static void cachedmain(String[] args) {
        var multiExecutor = Executors.newCachedThreadPool();
        try{
//            multiExecutor.execute(()->Main.sum(1,10,1,"red"));
//            multiExecutor.execute(()->Main.sum(10,100,10,"blue"));
//            multiExecutor.execute(()->Main.sum(2,20,2,"green"));

            var redValue = multiExecutor.submit(()->Main.sum(1,10,1,"red"));
            var blueValue = multiExecutor.submit(()->Main.sum(10,100,10,"blue"));
            var greenValue = multiExecutor.submit(()->Main.sum(2,20,2,"green"));

            try{
                System.out.println(redValue.get(5000,TimeUnit.SECONDS));
                System.out.println(blueValue.get(5000,TimeUnit.SECONDS));
                System.out.println(greenValue.get(5000 ,TimeUnit.SECONDS));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
//            multiExecutor.execute(()->Main.sum(1,10,1,"yellow"));
//            multiExecutor.execute(()->Main.sum(10,100,10,"cyan"));
//            multiExecutor.execute(()->Main.sum(2,20,2,"purple"));
//            try{
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Next Tasks will get executed");
//            for(var color: new String[]{"red","blue","green","yellow","purple","cyan","black"}){
//                multiExecutor.execute(()->Main.sum(1,10,1,color));
//            }
        }finally {
            multiExecutor.shutdown();
        }
    }

    public static void fixedmain(String[] args) {

        System.out.println("Main thread running");
        try {
            System.out.println("Main thread paused for one second");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> {
            String tname = Thread.currentThread().getName();
            System.out.println(tname + " should take 10 dots to run.");
            for (int i = 0; i < 10; i++) {
                System.out.print(". ");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("\nWhoops!! " + tname + " interrupted.");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("\n" + tname + " completed.");
        });

        Thread installThread = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(250);
                    System.out.println("Installation Step " + (i + 1) +
                            " is completed.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "InstallThread");

        Thread threadMonitor = new Thread(() -> {
            long now = System.currentTimeMillis();

            while (thread.isAlive()) {
                try {
                    Thread.sleep(1000);

                    if (System.currentTimeMillis() - now > 3000) {
                        thread.interrupt();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });

        System.out.println(thread.getName() + " starting");
        thread.start();
        threadMonitor.start();

        try {
            thread.join();
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (!thread.isInterrupted()) {
            installThread.start();
        } else {
            System.out.println("Previous thread was interrupted, " +
                    installThread.getName() + " can't run.");
        }
    }

    private static int sum(int start,int end, int delta, String colorString){
        var threadColor = ThreadColor.ANSI_RESET;
        try{
            threadColor = ThreadColor.valueOf("ANSI_"+colorString.toUpperCase());
        }catch(IllegalArgumentException ignore){}
        String color = threadColor.color();
        int sum = 0;
        for(int i=start;i<=end;i+=delta){
            sum+=i;
        }
        System.out.println(color+Thread.currentThread().getName()+", "+colorString+", "+colorString+" "+sum);
        return sum;
    }
}
