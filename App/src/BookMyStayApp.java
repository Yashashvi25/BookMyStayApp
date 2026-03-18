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
        // Input validation
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

    public int getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Customer: " + customerName +
                ", Room Type: " + roomType +
                ", Nights: " + nights;
    }
}

// Booking manager with cancellation support
class BookingManager {
    private List<Reservation> reservations = new ArrayList<>();
    private Map<String, Integer> roomInventory = new HashMap<>();
    private Stack<String> releasedRooms = new Stack<>();

    public BookingManager() {
        roomInventory.put("Standard", 5);
        roomInventory.put("Deluxe", 3);
        roomInventory.put("Suite", 2);
    }

    // Add reservation
    public void addReservation(Reservation res) throws InvalidBookingException {
        String room = res.getRoomType();
        int available = roomInventory.getOrDefault(room, 0);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + room);
        }
        roomInventory.put(room, available - 1);
        reservations.add(res);
        System.out.println("Booking successful: " + res);
    }

    // Cancel reservation
    public void cancelReservation(int reservationId) throws InvalidBookingException {
        Reservation toCancel = null;
        for (Reservation r : reservations) {
            if (r.getReservationId() == reservationId) {
                toCancel = r;
                break;
            }
        }
        if (toCancel == null) {
            throw new InvalidBookingException("Reservation ID " + reservationId + " does not exist or already cancelled.");
        }

        // Rollback inventory
        String room = toCancel.getRoomType();
        roomInventory.put(room, roomInventory.get(room) + 1);
        releasedRooms.push(room); // Track rollback LIFO

        reservations.remove(toCancel);
        System.out.println("Cancellation successful: " + toCancel);
    }

    public void generateReport() {
        System.out.println("\n--- Current Reservations ---");
        if (reservations.isEmpty()) {
            System.out.println("No active bookings.");
        } else {
            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }
        System.out.println("--- End of Report ---\n");
    }

    public void showInventory() {
        System.out.println("Current Room Inventory: " + roomInventory);
    }

    public void showReleasedRooms() {
        System.out.println("Recently Released Rooms (LIFO): " + releasedRooms);
    }
}

// Main class matching file name
public class BookMyStayApp {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();

        try {
            Reservation r1 = new Reservation(101, "Alice", "Deluxe", 3);
            Reservation r2 = new Reservation(102, "Bob", "Standard", 2);
            Reservation r3 = new Reservation(103, "Charlie", "Suite", 4);

            manager.addReservation(r1);
            manager.addReservation(r2);
            manager.addReservation(r3);

            // Cancel one booking
            manager.cancelReservation(102);

            // Attempt to cancel non-existent booking
            manager.cancelReservation(999);

        } catch (InvalidBookingException e) {
            System.out.println("Operation failed: " + e.getMessage());
        }

        // Display current state
        manager.generateReport();
        manager.showInventory();
        manager.showReleasedRooms();
    }
}
