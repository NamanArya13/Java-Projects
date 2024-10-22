package dev.lpa;

public class ThreadChallengeCustomThread extends Thread{

    @Override
    public void run() {
        String tname = Thread.currentThread().getName();
        for(int i=0;i<11;i++){
            if(i%2==0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread "+tname+" is interrupted");
                        return;
                    }
                System.out.println(i);
            }
        }
    }
}
