package dev.threadfinal;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolMain {
    public static void main(String[] args) {

        RejectedExecutionHandler rejectionHandler =
                (runnable, executor) -> {

                    System.out.println(
                            "REJECTED -> " + runnable
                                    + " | Pool is full and queue is full"
                    );
                };

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,5,60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(), rejectionHandler);
    }
}
