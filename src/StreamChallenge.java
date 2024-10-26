import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamChallenge {
    private static int counter=75;
    public static void main(String[] args) {

        List<String> bingoPool=new ArrayList<>(75);
        int index=1;
        for(Character c: "biNGO".toCharArray()){
            for(int i=index;i<index+15;i++){
            bingoPool.add(""+c+i);
        }
            index+=15;
        }

        System.out.println(bingoPool);
        var stream1=bingoPool.stream()
                .limit(15)
                .map(String::toUpperCase);
//        stream1.forEach(System.out::println);

        var stream2=bingoPool.stream()
                .filter(s->String.valueOf(s.charAt(0)).equals("i"))
                .map(String::toUpperCase);

//        stream2.forEach(System.out::println);

        int seed=31;
        var stream3= Stream.iterate(seed,i->i<=45,i->i+1)
                .map(i->"N"+i);

//        stream3.forEach(System.out::println);

        var stream4=Stream.iterate(46,i->i+1)
                .limit(15)
                .map(i->"G"+i);
//
//        stream4.forEach(System.out::println);

        String[] oLabels = new String[15];
        Arrays.setAll(oLabels,i->String.valueOf(i+61));
        var stream5=Arrays.stream(oLabels)
                .limit(15)
                .map(i->"O"+i);

//        stream5.forEach(System.out::println);


        var stream6 = Stream.concat(stream1,stream2);
        var stream7 = Stream.concat(stream6,stream3);
        var stream8 = Stream.concat(stream7,stream4);
        Stream.concat(stream8,stream5).forEach(s -> System.out.print(" "+s+" "));


        var stream10 = Stream.generate(StreamChallenge::getCounter)
                        .limit(15)
                                .map(i->"M"+i);
        stream10.forEach(System.out::println);


        System.out.println("-".repeat(100));
        Stream.generate(()->new Random().nextInt(100,115))
                .distinct()
                .limit(10)
                .map(i->"MNAH"+i)
                .sorted()
                .forEach(s-> System.out.print(" "+s+" "));
    }

    private static int getCounter(){
        return counter++;
    }

}
