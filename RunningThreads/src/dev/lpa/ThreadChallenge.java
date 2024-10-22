package dev.lpa;
class ThirdThread implements Runnable{

    @Override
    public void run() {
        System.out.println("Just here for nothing");
    }
}
public class ThreadChallenge {
    public static void main(String[] args) {
        ThreadChallengeCustomThread firstThread= new ThreadChallengeCustomThread();
        long now = System.currentTimeMillis();
        Thread thirdThread = new Thread(new ThirdThread());
        Thread secondThread = new Thread(()->{
            String tname = Thread.currentThread().getName();
            for(int i=0;i<11;i++){
                if (i%2==1){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Thread "+tname+" is interrupted");
                        thirdThread.start();
                        return;
                    }
                    System.out.println(i);
                }
            }
        },"Second Ass Thread");

        firstThread.start();
        secondThread.start();

        while (firstThread.isAlive() || secondThread.isAlive()){
            if(System.currentTimeMillis()-now>2000){
                firstThread.interrupt();
                secondThread.interrupt();
            }
        }


    }




}
