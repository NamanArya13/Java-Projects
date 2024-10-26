import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


public class Main {
    public static void main(String[] args) {
        List<String> bingoPool = new ArrayList<>(75);
        int start=1;

        for(Character c:"BINGO".toCharArray()){
            for(int i=start;i<(start+15);i++){
                bingoPool.add(""+c+i);
            }
            start+=15;
        }

        Collections.shuffle(bingoPool);
        for(int i=0;i<15;i++){
            System.out.println(bingoPool.get(i));
        }
        System.out.println("-".repeat(100));

//        List<String> firstOnes=bingoPool.subList(0,15);
        List<String> firstOnes=new ArrayList<>(bingoPool.subList(0,15));
        firstOnes.sort(String::compareTo);
        firstOnes.replaceAll(c->{
            if(c.indexOf('G')==0 || c.indexOf("O")==0){
                String updated=c.charAt(0)+"-"+c.substring(1);
                System.out.println(updated);
                return updated;
            }
            return c;
        });

        System.out.println("\n"+"-".repeat(100));


        for(int i=0;i<15;i++){
            System.out.println(bingoPool.get(i));
        }

//        bingoPool.stream()
//                .limit(15)
//                .filter(s->s.indexOf('G')==0 || s.indexOf("O")==0)
//                .map(s->s.charAt(0)+"-"+s.substring(1))
//                .sorted()
//                .forEach(s-> System.out.print(s+" "));

        var tempStream=bingoPool.stream()
                .limit(15)
                .filter(s->s.indexOf('G')==0 || s.indexOf("O")==0)
                .peek(s-> System.out.println("The filtered is "+s))
                .map(s->s.charAt(0)+"-"+s.substring(1))
                .peek(s-> System.out.println("After map "+s))
                .sorted();
//                .forEach(s-> System.out.print(s+" "));

//        tempStream.forEach(System.out::println);
        System.out.println("THE TEMPSTREAM");
        tempStream.forEach(s-> System.out.println(s.toLowerCase()+" "));
        System.out.println("\n--------------------------");

        Integer abc=print("ABC",(a)->a.substring(0,3).length(),"ABDEFHDJWI");
        System.out.println(abc);

    }


    public static <T extends String,R> R print(String abc, Function<T,R> lambda, T input){
        return lambda.apply(input);
    }
}