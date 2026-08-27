package dev.atomic;

import java.util.concurrent.atomic.AtomicInteger;

class AtomicLikeCounter{

    private final AtomicInteger likes = new AtomicInteger(0);

    public void like(){
        likes.incrementAndGet();
    }

    public void unlike(){
        likes.updateAndGet(val->val>0?val-1:0);
    }


    public int getLikes(){
        return likes.get();
    }
}
public class LikeCounter {
    public static void main(String[] args) throws InterruptedException {

        AtomicLikeCounter atomicLikeCounter = new AtomicLikeCounter();

        System.out.println(atomicLikeCounter.getLikes());

        for(int i = 0;i<1000;i++){
            new Thread(atomicLikeCounter::like).start();
            new Thread(atomicLikeCounter::unlike).start();
        }

        Thread.sleep(5000);

        System.out.println(atomicLikeCounter.getLikes());
    }
}
