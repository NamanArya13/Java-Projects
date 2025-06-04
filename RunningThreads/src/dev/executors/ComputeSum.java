package dev.executors;

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
        for(int i = this.start;i<=this.end;i++) sum+=i;
        return sum;
    }
}

public class ComputeSum {
    public static void main(String[] args) {

        SumCalculator calculator = new SumCalculator(-1000000000,1000000000);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Integer> result = executorService.submit(calculator);

        try{
            System.out.println(result.get(100,TimeUnit.MILLISECONDS));
        }catch (InterruptedException e){
            System.out.println("Interrupted");
        }catch (ExecutionException e){
            System.out.println("Execution exception");
        }catch (TimeoutException e){
            System.out.println("Timed Out");
        }

    }
}
