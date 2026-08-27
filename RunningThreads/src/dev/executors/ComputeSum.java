package dev.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class SumCalculator implements Callable<Integer> {

    private final int start;
    private final int end;

    public SumCalculator(int start,int end){
        this.start = start;
        this.end = end;
    }


    @Override
    public Integer call() {
        int sum = 0;
        System.out.println("Computing sum by - "+Thread.currentThread().getName());
        for(int i = this.start;i<=this.end;i++) sum+=i;
        System.out.println("Computed Sum by - "+Thread.currentThread().getName());
        return sum;
    }
}

public class ComputeSum {
    public static void main(String[] args) {


        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Future<Integer>> futureList = new ArrayList<>();
        for(int i = 0;i<15;i++) {
            futureList.add(executorService.submit(new SumCalculator(-1000000,i+10000000)));
        }
        try{
            for(Future<Integer> future: futureList){
                System.out.println(future.get());
            }
        }catch (InterruptedException e){
            System.out.println("Interrupted");
        }catch (ExecutionException e){
            System.out.println("Execution exception");
        }

    }
}
