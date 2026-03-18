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

// Thread-safe booking manager
class BookingManager {
    private List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private Map<String, Integer> roomInventory = Collections.synchronizedMap(new HashMap<>());

    public BookingManager() {
        roomInventory.put("Standard", 5);
        roomInventory.put("Deluxe", 3);
        roomInventory.put("Suite", 2);
    }

    // Synchronized booking to ensure thread safety
    public synchronized void addReservation(Reservation res) throws InvalidBookingException {
        String room = res.getRoomType();
        int available = roomInventory.getOrDefault(room, 0);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + room);
        }

        roomInventory.put(room, available - 1);
        reservations.add(res);
        System.out.println(Thread.currentThread().getName() + " booked: " + res);
    }

    public synchronized void generateReport() {
        System.out.println("\n--- Current Reservations ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
        System.out.println("--- End of Report ---\n");
    }

    public synchronized void showInventory() {
        System.out.println("Current Room Inventory: " + roomInventory);
    }
}

// Runnable task representing a guest trying to book a room
class BookingTask implements Runnable {
    private BookingManager manager;
    private Reservation reservation;

    public BookingTask(BookingManager manager, Reservation reservation) {
        this.manager = manager;
        this.reservation = reservation;
    }

    @Override
    public void run() {
        try {
            manager.addReservation(reservation);
        } catch (InvalidBookingException e) {
            System.out.println(Thread.currentThread().getName() + " booking failed: " + e.getMessage());
        }
    }
}

// Main class matching the file name
public class BookMyStayApp {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();

        // Simulate concurrent booking requests
        Thread t1 = new Thread(new BookingTask(manager, createReservation(101, "Alice", "Deluxe", 3)), "Guest-1");
        Thread t2 = new Thread(new BookingTask(manager, createReservation(102, "Bob", "Deluxe", 2)), "Guest-2");
        Thread t3 = new Thread(new BookingTask(manager, createReservation(103, "Charlie", "Standard", 1)), "Guest-3");
        Thread t4 = new Thread(new BookingTask(manager, createReservation(104, "Daisy", "Suite", 2)), "Guest-4");
        Thread t5 = new Thread(new BookingTask(manager, createReservation(105, "Eve", "Deluxe", 1)), "Guest-5");

        // Start all threads simultaneously
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display final state
        manager.generateReport();
        manager.showInventory();
    }

    // Helper method to create reservations safely
    private static Reservation createReservation(int id, String name, String roomType, int nights) {
        try {
            return new Reservation(id, name, roomType, nights);
        } catch (InvalidBookingException e) {
            System.out.println("Failed to create reservation: " + e.getMessage());
            return null;
        }
    }
}