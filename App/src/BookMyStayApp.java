import java.util.*;

// Custom exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private int reservationId;
    private String customerName;
    private String roomType;
    private int nights;

    private static final List<String> VALID_ROOM_TYPES = Arrays.asList("Standard", "Deluxe", "Suite");

    public Reservation(int reservationId, String customerName, String roomType, int nights) throws InvalidBookingException {
        // Validate input
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new InvalidBookingException("Customer name cannot be empty.");
        }
        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        if (nights <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than 0.");
        }

        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.nights = nights;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Customer: " + customerName +
                ", Room Type: " + roomType +
                ", Nights: " + nights;
    }
}

// Booking manager class
class BookingManager {
    private List<Reservation> reservations = new ArrayList<>();
    private Map<String, Integer> roomInventory = new HashMap<>();

    public BookingManager() {
        // Initialize inventory
        roomInventory.put("Standard", 5);
        roomInventory.put("Deluxe", 3);
        roomInventory.put("Suite", 2);
    }

    // Attempt to add a reservation
    public void addReservation(Reservation res) throws InvalidBookingException {
        String room = res.toString().contains("Standard") ? "Standard"
                : res.toString().contains("Deluxe") ? "Deluxe" : "Suite";

        int available = roomInventory.getOrDefault(room, 0);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + room);
        }

        // Reduce inventory and add reservation
        roomInventory.put(room, available - 1);
        reservations.add(res);
        System.out.println("Booking successful: " + res);
    }

    public void generateReport() {
        System.out.println("\n--- Current Reservations ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
        System.out.println("--- End of Report ---\n");
    }

    public void showInventory() {
        System.out.println("Current Room Inventory: " + roomInventory);
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();

        try {
            Reservation r1 = new Reservation(101, "Alice", "Deluxe", 3);
            manager.addReservation(r1);

            Reservation r2 = new Reservation(102, "Bob", "Standard", 2);
            manager.addReservation(r2);

            // Invalid room type
            Reservation r3 = new Reservation(103, "Charlie", "Penthouse", 1);
            manager.addReservation(r3);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        // Another booking with invalid nights
        try {
            Reservation r4 = new Reservation(104, "Daisy", "Suite", 0);
            manager.addReservation(r4);
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        // Display report and inventory
        manager.generateReport();
        manager.showInventory();
    }
}