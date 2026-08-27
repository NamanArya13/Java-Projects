package dev.threadfinal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class BankAccount{

    private int balance;

    public BankAccount(int balance){
        this.balance = balance;
    }

    public void deposit(int amount){
        synchronized (this) {
            this.balance += amount;
        }
    }

    public void withdraw(int amount){
        synchronized (this) {
            this.balance -= amount;
       }
    }

    public int getBalance(){
        return this.balance;
    }
}

public class BankAccountMain {

    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount(100000);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100_000; i++) {
            executor.submit(() -> bankAccount.deposit(1000));
            executor.submit(() -> bankAccount.withdraw(1000));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println(bankAccount.getBalance());
    }
}
