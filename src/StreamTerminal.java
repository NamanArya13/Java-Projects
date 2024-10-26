import java.util.Arrays;
import java.util.stream.IntStream;

public class StreamTerminal {
    public static void main(String[] args) {
        var result = IntStream.iterate(0,i->i+10)
                .limit(101)
                .summaryStatistics();
        System.out.println("Result = "+result);
        System.out.println(result.getAverage());
        System.out.println(result.getClass().getSimpleName());

        var leapYearData = IntStream.iterate(2000,i->i<=2025,i->i+1)
                .filter(i->i%4==0)
                .peek(s->{
                    System.out.println(s);;
                })
                .summaryStatistics();

        System.out.println("Leap Year Data = "+leapYearData);

        Seat2[] seats = new Seat2[100];
        Arrays.setAll(seats,i->new Seat2((char)('A'+i/10),i%10+1));
//        Arrays.asList(seats).forEach(System.out::println);
        long reservationCount = Arrays
                .stream(seats)
                .filter(Seat2::isReserved)
                .count();

//        long resCount = Arrays.asList(seats).stream().filter(Seat2::isReserved).count();

        System.out.println("reservationCount = "+reservationCount);

        boolean hasBookings = Arrays
                .stream(seats)
                .anyMatch(Seat2::isReserved);

        System.out.println("hasBookings = "+hasBookings);

        boolean fullyBooked = Arrays
                .stream(seats)
                .allMatch(Seat2::isReserved);

        System.out.println("fully Booked = "+fullyBooked);

        boolean eventWashedOut = Arrays
                .stream(seats)
                .noneMatch(Seat2::isReserved);

        System.out.println("eventWashedOut = "+fullyBooked);



    }
}
