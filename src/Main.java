import dev.lpa.BankAccount;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BankAccount companyAccount = new BankAccount("Tom",10000);

        Thread thread1 = new Thread(()->{
            companyAccount.withdrawal(2500);
        },"Thread1");

        Thread thread2 = new Thread(()->{companyAccount.deposit(5000);},"Thread2");

        Thread thread3 = new Thread(()->{companyAccount.setName("Tim");},"Thread3");

        Thread thread4 = new Thread(()->{companyAccount.withdrawal(5000);},"Thread4");

        thread1.start();
        thread2.start();
        try{
            Thread.sleep(500);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        thread3.start();
        thread4.start();

        try{
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Final Balance: "+companyAccount.getBalance());
    }
}