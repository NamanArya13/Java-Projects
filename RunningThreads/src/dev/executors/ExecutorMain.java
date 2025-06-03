package dev.executors;

import java.util.concurrent.*;

public class ExecutorMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Void> task = ()->{
            System.out.println("Hello");
            return null;
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Void> future = executor.submit(task);
        System.out.println(future.get());
        executor.shutdownNow();

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,20,500,TimeUnit.SECONDS,new ArrayBlockingQueue<>(20));
        poolExecutor.submit(task);

        poolExecutor.shutdownNow();

    }
    public static void main2(String[] args) {

        Runnable task = ()->{
            System.out.println("Hello");
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,1000, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));

        Executor service = Executors.newFixedThreadPool(5);

        service.execute(task);
        executor.execute(task);
    }
}
