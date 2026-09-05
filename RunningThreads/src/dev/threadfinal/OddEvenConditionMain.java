package dev.threadfinal;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class OddEvenCondition {

    private final Lock lock = new ReentrantLock();
    private final Condition oddCondition = lock.newCondition();
    private final Condition evenCondition = lock.newCondition();

    private boolean evenTurn = true;

    public void printEven() {
        for (int i = 0; i <= 100; i += 2) {
            lock.lock();

            try {
                while (!evenTurn) {
                    evenCondition.await();
                }

                System.out.println(i);

                evenTurn = false;
                oddCondition.signal();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } finally {
                lock.unlock();
            }
        }
    }

    public void printOdd() {
        for (int i = 1; i <= 99; i += 2) {
            lock.lock();

            try {
                while (evenTurn) {
                    oddCondition.await();
                }

                System.out.println(i);

                evenTurn = true;
                evenCondition.signal();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } finally {
                lock.unlock();
            }
        }
    }

    // demo methods
    public String getUser(){
        try {
            Thread.sleep(2000); // simulate User call
            System.out.println("Got User A");
            return "User A - MALIK SAHAB";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserOrders(){
        try {
            Thread.sleep(1000); // simulate User Order call
            System.out.println("Got Order A");
            return "Order A - MALIK SAHAB";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class OddEvenConditionMain {
    public static void main(String[] args) throws InterruptedException {
        OddEvenCondition oddEven = new OddEvenCondition();
        Thread even = new Thread(oddEven::printEven);
        Thread odd = new Thread(oddEven::printOdd);

        odd.start();
        even.start();

        even.join();
        odd.join();

    }
}
