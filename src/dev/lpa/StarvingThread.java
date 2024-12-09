package dev.lpa;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StarvingThread {

    private static final Lock lock = new ReentrantLock(true);
    public static void main(String[] args) {
        Callable<Boolean> thread = ()->{
            String threadName = Thread.currentThread().getName();
            int threadNo = Integer.parseInt(threadName.replaceAll(".*-[1-9]*-.*-",""));
            boolean keepGoing = true;
            Date date;
            while (keepGoing){
                lock.lock();
                try{
                    System.out.printf("%d acquired the lock.%n",threadNo);
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    date = new Date(System.currentTimeMillis());
                    System.out.println("After - "+date);
                    System.out.printf("Shutting down %d%n",threadNo);
                    Thread.currentThread().interrupt();
                    return false;
                }finally {
                    System.out.printf("%d is unlocking.%n",threadNo);
                    lock.unlock();
                }
            }
            return true;
        };

        List<Callable<Boolean>> listCallable = Collections.nCopies(3,thread);

        var executor = Executors.newFixedThreadPool(3);
        Date date = new Date(System.currentTimeMillis());
        System.out.println("Before - "+date);
        try{
            var futures = executor.invokeAll(listCallable,10,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executor.shutdownNow();

    }
}
