package dev.threadfinal;

class PingPong{

    private int count;

    public PingPong(int count){
        this.count = count;
    }

    public void ping(){
        int i = 0;
        while (i!=count){
            synchronized (this){
                try {
                    System.out.println("Ping");
                    Thread.sleep(1000);
                    i++;
                    this.notify();
                    if (i!=count)
                        this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void pong(){
        int i = 0;
        while (i!=count){
            synchronized (this){
                try {
                    System.out.println("Pong");
                    Thread.sleep(1000);
                    i++;
                    this.notify();
                    if (i!=count)
                        this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

public class PingPongMain {
    public static void main(String[] args) throws InterruptedException {
        PingPong pingPong = new PingPong(10);
        Thread ping = new Thread(pingPong::ping);
        Thread pong = new Thread(pingPong::pong);
        ping.start();
        Thread.sleep(50);
        pong.start();
        ping.join();
        pong.join();
    }
}
