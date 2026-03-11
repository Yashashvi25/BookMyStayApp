import java.util.*;

/**
 * Book My Stay Application
 * Demonstrates room search and availability check.
 *
 * @author YourName
 * @version 4.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Welcome to Book My Stay");
        System.out.println("Hotel Booking System v4.1");
        System.out.println("=================================\n");

        // Create room objects
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Search service
        SearchService searchService = new SearchService(inventory);

        // Perform search
        searchService.searchAvailableRooms(rooms);
    }
}


/**
 * Abstract class representing a Room.
 * @version 2.0
 */
abstract class Room {

    String type;
    int beds;
    int size;
    double price;

    Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    void displayRoom() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price per night: ₹" + price);
    }

    String getType() {
        return type;
    }
}


/**
 * Single Room
 * @version 2.0
 */
class SingleRoom extends Room {

    SingleRoom() {
        super("Single Room", 1, 200, 2500);
    }
}


/**
 * Double Room
 * @version 2.0
 */
class DoubleRoom extends Room {

    DoubleRoom() {
        super("Double Room", 2, 350, 4000);
    }
}


/**
 * Suite Room
 * @version 2.0
 */
class SuiteRoom extends Room {

    SuiteRoom() {
        super("Suite Room", 3, 600, 7500);
    }
}


/**
 * Centralized Room Inventory
 * @version 3.0
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 0); // Example: unavailable
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


/**
 * SearchService handles read-only room searches.
 *
 * @version 4.0
 */
class SearchService {

    private RoomInventory inventory;

    SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    void searchAvailableRooms(List<Room> rooms) {

        System.out.println("Available Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Filter unavailable rooms
            if (available > 0) {

                room.displayRoom();
                System.out.println("Available: " + available);
                System.out.println("----------------------------");
            }
        }
    }
}