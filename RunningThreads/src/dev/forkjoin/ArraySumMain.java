package dev.forkjoin;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class ArraySum extends RecursiveTask<Long>{

    private final int[] array;
    private final int start;
    private final int end;

    public ArraySum(int[] arr,int start,int end){
        this.array = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end-start<=1000){
            long sum = 0;
            for(int i = start;i<end;i++){
                sum = sum+array[i];
            }
            return sum;
        }else{
            int mid = (start+end)/2;
            ArraySum left = new ArraySum(array,start,mid);
            ArraySum right = new ArraySum(array,mid,end);

            left.fork();
            right.fork();

            Long leftResult = left.join();
            Long rightResult = right.join();
            return leftResult+rightResult;
        }
    }
}

public class ArraySumMain {
    public static void main(String[] args) {
        int[] largeArray = new Random().ints(10_000_000_00, 100, 1000).toArray();
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        ArraySum task = new ArraySum(largeArray,0,largeArray.length);
        long start = System.currentTimeMillis();
        long result = forkJoinPool.invoke(task);
        long end = System.currentTimeMillis();

        System.out.println("Sum "+result);
        System.out.println("Time taken = "+(end-start)+" ms");

        long startNonThread = System.currentTimeMillis();
        long sum = 0;
        for(int i = 0;i<largeArray.length;i++){
            sum = sum+largeArray[i];
        }
        long endNonThread = System.currentTimeMillis();
        System.out.println("Sum "+sum);
        System.out.println("Time taken = "+(endNonThread-startNonThread)+" ms");

    }
}
