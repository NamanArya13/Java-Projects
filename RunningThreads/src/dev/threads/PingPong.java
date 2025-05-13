package dev.threads;


public class PingPong {

    public static void main(String[] args) throws InterruptedException {

        Runnable ping = ()->{
            System.out.println("Ping");
        };

        Runnable pong = ()->{
            System.out.println("Pong");
        };


        for(int i = 0;i<100;i++){
            new Thread(ping).start();
            Thread.sleep(200);
            new Thread(pong).start();
        }
    }
}
