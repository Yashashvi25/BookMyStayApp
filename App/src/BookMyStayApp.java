import java.util.*;

// Reservation class to store booking details
class Reservation {
    private int reservationId;
    private String customerName;
    private String roomType;
    private int nights;

    public Reservation(int reservationId, String customerName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Customer: " + customerName +
                ", Room Type: " + roomType +
                ", Nights: " + nights;
    }
}

// BookingHistory class to store confirmed bookings
class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add a confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation added: " + reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(history);
    }

    // Generate a simple report
    public void generateReport() {
        System.out.println("\n--- Booking History Report ---");
        if (history.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Reservation res : history) {
                System.out.println(res);
            }
        }
        System.out.println("--- End of Report ---\n");
    }
}

// Main class for Use Case 8
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory bookingHistory = new BookingHistory();

        // Simulate booking confirmations
        Reservation r1 = new Reservation(101, "Alice", "Deluxe", 3);
        Reservation r2 = new Reservation(102, "Bob", "Standard", 2);
        Reservation r3 = new Reservation(103, "Charlie", "Suite", 5);

        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);

        // Admin retrieves booking history
        bookingHistory.generateReport();

        // Retrieve reservations separately (if needed)
        List<Reservation> allBookings = bookingHistory.getAllReservations();
        System.out.println("Total confirmed bookings: " + allBookings.size());
    }
}