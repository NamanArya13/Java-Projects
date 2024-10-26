import java.util.Comparator;
import java.util.stream.Stream;

public class StreamIntermediate2 {
    public static void main(String[] args) {
        System.out.println();

        int maxSeats = 100;
        int seatsInRow = 10;
        var stream = Stream.iterate(0,i->i<maxSeats, i->i+1)
//                .limit(maxSeats)
                .map(i -> new Seat((char)('A'+i/seatsInRow),i%seatsInRow))
                        .mapToDouble((Seat::price))
                                .mapToObj("%.2f"::formatted);

//        stream.forEach(System.out::println);
        System.out.println();
        System.out.println();

        var stream2 = Stream.iterate(0,i->i<maxSeats, i->i+1)
                .map(i -> new Seat((char)('A'+i/seatsInRow),i%seatsInRow))
                .sorted(Comparator.comparing(Seat::price).thenComparing(Seat::toString));

//        stream2.forEach(System.out::println );

        var stream3 = Stream.iterate(0,i->i<maxSeats, i->i+1)
                .map(i -> new Seat((char)('A'+i/seatsInRow),i%seatsInRow))
                .skip(5)
                .limit(10)
                .peek(s-> System.out.println("-->"+s))
                .sorted(Comparator.comparing(Seat::price).thenComparing(Seat::toString));

        stream3.forEach(System.out::println );




    }
}
