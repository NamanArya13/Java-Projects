import java.util.stream.IntStream;

public class streamMain {
    public static void main(String[] args) {

        IntStream.iterate(1,(n)->n+1)
                .limit(20)
                .filter(streamMain::isPrime)
                .forEach(s-> System.out.print(s+" "));
        System.out.println();
        System.out.println("-".repeat(40));
        IntStream.iterate(1,(n)->n+1)
                .limit(100)
                .filter(streamMain::isPrime)
                .forEach(s-> System.out.print(s+" "));

        System.out.println();
        System.out.println("-".repeat(40));

        IntStream.iterate(1,(n)->n<=100,n->n+1)
                .filter(streamMain::isPrime)
                .forEach(s-> System.out.print(s+" "));


        System.out.println();
        System.out.println("-".repeat(40));

        IntStream.range(1,100)
                .filter(streamMain::isPrime)
                .forEach(s-> System.out.print(s+" "));

        IntStream.rangeClosed(1,100)
                .filter(streamMain::isPrime)
                .forEach(s-> System.out.print(s+" "));



    }

    public static boolean isPrime(int wholeNumber){
        if(wholeNumber<=2){
            return (wholeNumber==2);
        }

        for(int divisor=2;divisor<wholeNumber;divisor++){
            if(wholeNumber%divisor==0){
                return false;
            }
        }

        return true;
    }
}
