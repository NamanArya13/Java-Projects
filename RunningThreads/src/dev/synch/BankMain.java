package dev.synch;

import java.util.ArrayList;
import java.util.List;

class BankAccount{

    private int balance;
    private final Object lock = new Object();

    public BankAccount(int balance){
        this.balance = balance;
    }

    public void deposit(int amount){
        synchronized (lock) {
            this.balance += amount;
        }
    }

    public void withdraw(int amount){
        synchronized (lock) {
            if (this.balance >= amount)
                this.balance -= amount;
        }
    }

    public void display(){
        System.out.println("Balance = "+this.balance);
    }
}

public class BankMain {

    public static void main2(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount(50000);

        Thread t1 = new Thread(() -> bankAccount.deposit(20000));
        Thread t2 = new Thread(() -> bankAccount.withdraw(10000));
        Thread t3 = new Thread(() -> bankAccount.deposit(60000));
        Thread t4 = new Thread(() -> bankAccount.withdraw(40000));

        t1.start(); t2.start(); t3.start(); t4.start();

        t1.join(); t2.join(); t3.join(); t4.join(); // Wait for all

        bankAccount.display();
    }

    public static void main(String[] args) throws InterruptedException {

        BankAccount bankAccount = new BankAccount(1000);

        List<Thread> threads = new ArrayList<>();

        // Start 1000 deposit and 1000 withdraw threads
        for (int i = 0; i < 10000; i++) {
            Thread t1 = new Thread(() -> bankAccount.deposit(1)); // total deposit: 1000
            Thread t2 = new Thread(() -> bankAccount.withdraw(1)); // total withdraw: 1000
            threads.add(t1);
            threads.add(t2);
            t1.start();
            t2.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        bankAccount.display(); //

    }
}
