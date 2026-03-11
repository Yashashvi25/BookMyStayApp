import java.util.*;

/**
 * Book My Stay Application
 * Demonstrates reservation confirmation and room allocation.
 *
 * @author YourName
 * @version 6.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Welcome to Book My Stay");
        System.out.println("Hotel Booking System v6.1");
        System.out.println("=================================\n");

        // Initialize services
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue requestQueue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        // Guests submit booking requests
        requestQueue.addRequest(new Reservation("Arun", "Single Room"));
        requestQueue.addRequest(new Reservation("Priya", "Double Room"));
        requestQueue.addRequest(new Reservation("Rahul", "Single Room"));

        // Process booking requests
        bookingService.processRequests(requestQueue);
    }
}


/**
 * Reservation represents a guest booking request.
 * @version 5.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/**
 * Booking request queue (FIFO).
 * @version 5.0
 */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request received from " + reservation.getGuestName());
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}


/**
 * Centralized Room Inventory
 * @version 3.0
 */
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}


/**
 * BookingService processes booking requests
 * and allocates rooms safely.
 *
 * @version 6.0
 */
class BookingService {

    private RoomInventory inventory;

    // Prevent duplicate room IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type to assigned rooms
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processRequests(BookingRequestQueue queue) {

        while (queue.hasRequests()) {

            Reservation reservation = queue.getNextRequest();
            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing reservation for " + reservation.getGuestName());

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Store allocation
                roomAllocations
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                allocatedRoomIds.add(roomId);

                // Update inventory
                inventory.decrementRoom(roomType);

                System.out.println("Reservation confirmed!");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Room ID: " + roomId);

            } else {
                System.out.println("Sorry, no rooms available for " + roomType);
            }
        }
    }

    private String generateRoomId(String roomType) {

        String prefix = roomType.substring(0, 2).toUpperCase();
        String roomId;

        do {
            roomId = prefix + (100 + new Random().nextInt(900));
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}