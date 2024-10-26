import java.util.Random;

public record Seat2(char rowMarker, int seatNumber, boolean isReserved) {

    public Seat2(char rowMarker, int seatNumber) {
        this(rowMarker, seatNumber, new Random().nextBoolean());
    }


}
