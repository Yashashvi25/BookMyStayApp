import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Demonstrates centralized room inventory management using HashMap.
 *
 * @author YourName
 * @version 3.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Welcome to Book My Stay");
        System.out.println("Hotel Booking System v3.1");
        System.out.println("=================================\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display room details with availability
        single.displayRoom();
        System.out.println("Available: " + inventory.getAvailability("Single Room"));
        System.out.println();

        doubleRoom.displayRoom();
        System.out.println("Available: " + inventory.getAvailability("Double Room"));
        System.out.println();

        suite.displayRoom();
        System.out.println("Available: " + inventory.getAvailability("Suite Room"));
        System.out.println();

        // Example inventory update
        inventory.updateAvailability("Single Room", -1);

        System.out.println("Updated Single Room Availability: "
                + inventory.getAvailability("Single Room"));
    }
}


/**
 * Abstract class representing a generic Room.
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
}


/**
 * Single Room implementation
 * @version 2.0
 */
class SingleRoom extends Room {

    SingleRoom() {
        super("Single Room", 1, 200, 2500);
    }
}


/**
 * Double Room implementation
 * @version 2.0
 */
class DoubleRoom extends Room {

    DoubleRoom() {
        super("Double Room", 2, 350, 4000);
    }
}


/**
 * Suite Room implementation
 * @version 2.0
 */
class SuiteRoom extends Room {

    SuiteRoom() {
        super("Suite Room", 3, 600, 7500);
    }
}


/**
 * RoomInventory manages centralized room availability.
 * Uses HashMap to store room type and available count.
 *
 * @version 3.0
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes inventory
    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);
    }

    // Get availability of a room type
    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (positive or negative)
    void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    // Display full inventory
    void displayInventory() {
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}