package dev.threadfinal;

public class NumberPrinter extends Thread{

    private final int start;
    private final int end;

    public NumberPrinter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run(){
        for(int i = start;i<=end;i++){
            System.out.println(i);
        }
    }
}

class Solution {
    public static void main(String[] args) throws InterruptedException {
        NumberPrinter first = new NumberPrinter(1,5);
        NumberPrinter second = new NumberPrinter(6,10);
        NumberPrinter third = new NumberPrinter(11,15);

        first.start();
        first.join();
        second.start();
        second.join();
        third.start();
        third.join();

    }
}
