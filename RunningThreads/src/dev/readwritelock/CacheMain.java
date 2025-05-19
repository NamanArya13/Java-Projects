package dev.readwritelock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Cache<T,U>{

    private final Map<T,U> cache;

    private final ReadWriteLock lock;

    public Cache(ReadWriteLock lock){
        cache = new HashMap<>();
        this.lock = lock;
    }

    public void put(T key, U value){
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+" is writing to cache ");
            cache.put(key, value);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public U get(T key){
        lock.readLock().lock();
        U val;
        try {
            System.out.println(Thread.currentThread().getName()+" is reading from cache ");
            Thread.sleep(1000);
            val = cache.get(key);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
        }
        return val;
    }
}

public class CacheMain {
    public static void main(String[] args) {
        Cache<String,Integer> cache = new Cache<>(new ReentrantReadWriteLock());
        Set<String> values = new HashSet<>();

        for(int i = 0;i<5;i++){
            String key1 = "Val"+i;
            String key2 = key1+"New";
            int val1= i*100;
            values.add(key1);

            Runnable task = ()->cache.put(key1,val1);
            Runnable task2 = ()->cache.put(key2,val1+50);
            values.add(key2);
            new Thread(task).start();
            for(int j = 0;j<10;j++){
                new Thread(task2).start();
                for(String s:values){
                    new Thread(()->cache.get(s)).start();
                }
            }
        }
    }
    public static void main2(String[] args) throws InterruptedException {

        Cache<String,Integer> cache = new Cache<>(new ReentrantReadWriteLock());

        Thread work1 = new Thread(()->cache.put("Val1",100));
        Thread work2 = new Thread(()->cache.put("Val2",200));
        Thread work3 = new Thread(()->cache.put("Val3",300));

        work1.start();
        work2.start();
        work3.start();

        work1.join();
        work2.join();
        work3.join();

        Thread.sleep(2000);

        Thread t1 = new Thread(()->{
            Integer val = cache.get("Val1");
            System.out.println(val);
        });

        Thread t2 = new Thread(()->{
            Integer val = cache.get("Val2");
            System.out.println(val);
        });

        Thread t3 = new Thread(()->{
            Integer val = cache.get("Val3");
            System.out.println(val);
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
