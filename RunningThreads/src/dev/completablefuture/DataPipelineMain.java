package dev.completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataPipelineMain {

    static class User{
        private final String name;
        private final int age;

        public User(String name,int age){
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
    public static void main(String[] args) {

        Random random = new Random();

        ExecutorService service = Executors.newCachedThreadPool();

        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("Fetching user id - "+Thread.currentThread().getName());
                    try {
                        Thread.sleep(random.nextInt(1000,10000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return random.nextInt(1000,10000);
                },service
        ).thenApplyAsync((a)->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("1 complete");
            switch (a){
                case 1: return new User("Abhi",23);
                case 2: return new User("Boman",45);
                case 3: return new User("Carry",12);
                case 4: return new User("Dick",10);
                default: return new User("Logan",57);
            }
        },service).thenAcceptAsync(System.out::println,service);

        CompletableFuture<Void> completableFuture2 = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("Fetching user id - "+Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return random.nextInt(1,5);
                },service
        ).thenApplyAsync((a)->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("2 complete");
            switch (a){
                case 1: return new User("Abhi",23);
                case 2: return new User("Boman",45);
                case 3: return new User("Carry",12);
                case 4: return new User("Dick",10);
                default: return new User("Logan",57);
            }
        },service).thenAcceptAsync(System.out::println,service);
        completableFuture.join();
        completableFuture2.join();
        service.shutdown();

    }
}
