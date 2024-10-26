import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamIntermediate {
    public static void main(String[] args) {

        IntStream.iterate((int) 'A', i->i<=(int)'z',i->i+1)
//                .limit(50)
                .filter(Character::isAlphabetic)
                .skip(5)
//                .filter(i->Character.toUpperCase(i)>'E')
                .forEach(s-> System.out.printf(" %c ",s));

        System.out.println();
        System.out.println();
        IntStream.iterate((int) 'A', i->i<=(int)'z',i->i+1)
                .filter(Character::isAlphabetic)
                .dropWhile(i->Character.toUpperCase(i)<='E')
                .forEach(s-> System.out.printf(" %c ",s));

        System.out.println();
        System.out.println();
        IntStream.iterate((int) 'A', i->i<=(int)'z',i->i+1)
                .filter(Character::isAlphabetic)
                .dropWhile(i->Character.toUpperCase(i)<='E')
                .takeWhile(i->i<'a')
                .forEach(s-> System.out.printf(" %c ",s));


        System.out.println();
        System.out.println();
        IntStream.iterate((int) 'A', i->i<=(int)'z',i->i+1)
                .filter(Character::isAlphabetic)
                .map(Character::toUpperCase)
                .distinct()
                .forEach(s-> System.out.printf(" %c ",s));

        System.out.println();
        System.out.println();
        Random random = new Random();
        Stream.generate(()-> random.nextInt((int)'A',(int)'Z'+1))
                .limit(50)
                .distinct()
                .sorted()
                .forEach(s-> System.out.printf( "%c ",s));
    }
}
