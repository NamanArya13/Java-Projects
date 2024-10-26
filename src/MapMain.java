import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MapMain {
    public static void main(String[] args) {
        Map<Character, int[]> myMap = new LinkedHashMap<>();

        int bingoIndex=1;
        for( char c : "BINGO".toCharArray()){
            int[] numbers = new int[15];
            int labelNo = bingoIndex;
            Arrays.setAll(numbers,i->i+labelNo);
            myMap.put(c,numbers);
            bingoIndex+=15;
        }

        myMap.entrySet()
                .stream()
//                .limit(2)
                .map(e->e.getKey()+ " has range: "+e.getValue()[0]+" - "+e.getValue()[e.getValue().length-1])
                .forEach(System.out::println);


        Random random = new Random();
        Stream.generate(()->random.nextInt(2))
                .limit(10)
                .forEach(System.out::print);

        System.out.println();

        System.out.println("-".repeat(100));
        IntStream.iterate(1,(n)->n+1)
                .limit(20)
                .forEach(s-> System.out.print(s+" "));

    }
}
