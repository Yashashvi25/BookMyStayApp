/**
 * Book My Stay Application
 * Demonstrates room types and static availability.
 *
 * @author YourName
 * @version 2.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Welcome to Book My Stay");
        System.out.println("Hotel Booking System v2.1");
        System.out.println("=================================\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailable = 10;
        int doubleAvailable = 6;
        int suiteAvailable = 3;

        // Display details
        single.displayRoom();
        System.out.println("Available: " + singleAvailable);
        System.out.println();

        doubleRoom.displayRoom();
        System.out.println("Available: " + doubleAvailable);
        System.out.println();

        suite.displayRoom();
        System.out.println("Available: " + suiteAvailable);
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