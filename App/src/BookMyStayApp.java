import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 * Demonstrates adding optional services to reservations.
 */
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - UC7 =====");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests
        queue.addRequest(new Reservation("Arun", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Double Room"));

        // Process bookings
        List<String> reservationIds = bookingService.processRequests(queue);

        // Add-on service manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Add services to reservations
        serviceManager.addService(reservationIds.get(0), new AddOnService("Breakfast", 200));
        serviceManager.addService(reservationIds.get(0), new AddOnService("WiFi", 100));
        serviceManager.addService(reservationIds.get(1), new AddOnService("Spa", 500));

        // Display total cost
        for (String resId : reservationIds) {
            int cost = serviceManager.calculateTotalCost(resId);
            System.out.println("Total add-on cost for " + resId + ": ₹" + cost);
        }
    }
}

/**
 * Reservation class
 */
class Reservation {

    private static int counter = 1;
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = "RES" + counter++;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * Queue (FIFO)
 */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

/**
 * Inventory
 */
class RoomInventory {

    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single Room", 2);
        rooms.put("Double Room", 2);
    }

    public int getAvailability(String type) {
        return rooms.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }
}

/**
 * Booking Service
 */
class BookingService {

    private RoomInventory inventory;
    private Set<String> allocated = new HashSet<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public List<String> processRequests(BookingRequestQueue queue) {

        List<String> reservationIds = new ArrayList<>();

        while (queue.hasRequests()) {

            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                String roomId = generateRoomId(type);
                inventory.decrement(type);

                System.out.println("Booked for " + r.getGuestName() + " Room ID: " + roomId);

                reservationIds.add(r.getReservationId());

            } else {
                System.out.println("No rooms available for " + r.getGuestName());
            }
        }

        return reservationIds;
    }

    private String generateRoomId(String type) {

        String id;

        do {
            id = type.substring(0, 2).toUpperCase() + (100 + new Random().nextInt(900));
        } while (allocated.contains(id));

        allocated.add(id);
        return id;
    }
}

/**
 * Add-On Service
 */
class AddOnService {

    private String name;
    private int cost;

    public AddOnService(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}

/**
 * Add-On Service Manager
 */
class AddOnServiceManager {

    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {

        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service " + service.getName() + " to " + reservationId);
    }

    public int calculateTotalCost(String reservationId) {

        List<AddOnService> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());

        int total = 0;

        for (AddOnService s : services) {
            total += s.getCost();
        }

        return total;
    }
}