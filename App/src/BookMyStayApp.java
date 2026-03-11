import java.util.*;

/**
 * Book My Stay Application
 * Demonstrates booking request intake using FIFO queue.
 *
 * @author YourName
 * @version 5.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Welcome to Book My Stay");
        System.out.println("Hotel Booking System v5.1");
        System.out.println("=================================\n");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        bookingQueue.addRequest(new Reservation("Arun", "Single Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Double Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Suite Room"));

        // Display queued booking requests
        bookingQueue.displayRequests();
    }
}


/**
 * Reservation represents a guest booking request.
 *
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}


/**
 * BookingRequestQueue manages incoming booking requests
 * using FIFO queue behavior.
 *
 * @version 5.0
 */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Request received from " + reservation.getGuestName());
    }

    // Display all requests in arrival order
    public void displayRequests() {

        System.out.println("\nBooking Requests in Queue:\n");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}