package dev.atomic;

import java.util.concurrent.atomic.AtomicReference;

class Account{

    private int balance;

    public Account(int balance){
        this.balance = balance;
    }

    public int getBalance(){
        return this.balance;
    }
}

public class BankAccountMain {
    public static void main(String[] args) throws InterruptedException {

        AtomicReference<Account> accountAtomicReference = new AtomicReference<>(new Account(1000));

        Runnable deposit = ()->{
            for(int i = 0;i<1000;i++){
                while (true){
                    Account acc = accountAtomicReference.get();
                    Account updatedAcc = new Account(acc.getBalance()+10);
                    if(accountAtomicReference.compareAndSet(acc,updatedAcc)) break;
                }
            }
        };

        Runnable withdraw = () -> {
            for (int i = 0; i < 1000; i++) {
                while (true) {
                    Account acc = accountAtomicReference.get();
                    if (acc.getBalance() >= 10) {
                        Account updated = new Account(acc.getBalance() - 10);
                        if (accountAtomicReference.compareAndSet(acc, updated)) break;
                    } else break;
                }
            }
        };

        Thread t1 = new Thread(deposit);
        Thread t2 = new Thread(withdraw);
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Final balance = " + accountAtomicReference.get().getBalance());

    }
}
