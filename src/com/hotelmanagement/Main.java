package com.hotelmanagement;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HotelManagement hotelManagement = new HotelManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Management System");
            System.out.println("1. Add Room");
            System.out.println("2. Add Customer");
            System.out.println("3. Make Booking");
            System.out.println("4. View All Rooms");
            System.out.println("5. View All Customers");
            System.out.println("6. View All Bookings");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline

            switch (choice) {
                case 1:
                    System.out.println("Enter room number:");
                    int roomNumber = scanner.nextInt();
                    scanner.nextLine();  // consume the newline

                    System.out.println("Enter room type:");
                    String roomType = scanner.nextLine();

                    System.out.println("Enter price:");
                    double price = scanner.nextDouble();
                    scanner.nextLine();  // consume the newline

                    hotelManagement.addRoom(new Room(roomNumber, roomType, price));
                    System.out.println("Room added successfully.");
                    break;

                case 2:
                    System.out.println("Enter customer ID:");
                    int customerId = scanner.nextInt();
                    scanner.nextLine();  // consume the newline

                    System.out.println("Enter customer name:");
                    String name = scanner.nextLine();

                    System.out.println("Enter contact number:");
                    String contact = scanner.nextLine();

                    hotelManagement.addCustomer(new Customer(customerId, name, contact));
                    System.out.println("Customer added successfully.");
                    break;

                case 3:
                    System.out.println("Enter booking ID:");
                    int bookingId = scanner.nextInt();
                    scanner.nextLine();  // consume the newline

                    System.out.println("Enter customer ID for booking:");
                    customerId = scanner.nextInt();
                    scanner.nextLine();  // consume the newline

                    System.out.println("Enter room number for booking:");
                    roomNumber = scanner.nextInt();
                    scanner.nextLine();  // consume the newline

                    System.out.println("Enter booking date (YYYY-MM-DD):");
                    String bookingDate = scanner.nextLine();

                    Customer customer = hotelManagement.getAllCustomers().stream()
                            .filter(c -> c.getCustomerId() == customerId)
                            .findFirst()
                            .orElse(null);

                    Room room = hotelManagement.getAllRooms().stream()
                            .filter(r -> r.getRoomNumber() == roomNumber)
                            .findFirst()
                            .orElse(null);

                    if (customer != null && room != null) {
                        hotelManagement.makeBooking(new Booking(bookingId, customer, room, bookingDate));
                        System.out.println("Booking made successfully.");
                    } else {
                        System.out.println("Invalid customer ID or room number. Booking not made.");
                    }
                    break;

                case 4:
                    List<Room> rooms = hotelManagement.getAllRooms();
                    System.out.println("Rooms in the hotel:");
                    for (Room r : rooms) {
                        System.out.println("Room Number: " + r.getRoomNumber() + ", Room Type: " + r.getRoomType() + ", Price: " + r.getPrice() + ", Available: " + r.isAvailable());
                    }
                    break;

                case 5:
                    List<Customer> customers = hotelManagement.getAllCustomers();
                    System.out.println("Customers in the hotel:");
                    for (Customer c : customers) {
                        System.out.println("Customer ID: " + c.getCustomerId() + ", Name: " + c.getName() + ", Contact: " + c.getContact());
                    }
                    break;

                case 6:
                    List<Booking> bookings = hotelManagement.getAllBookings();
                    System.out.println("Bookings in the hotel:");
                    for (Booking b : bookings) {
                        Customer customer1 = b.getCustomer();
                        Room room1 = b.getRoom();
                        if (customer1 != null && room1 != null) {
                            System.out.println("Booking ID: " + b.getBookingId() + ", Customer Name: " + customer1.getName() + ", Room Number: " + room1.getRoomNumber() + ", Booking Date: " + b.getBookingDate());
                        } else {
                            System.out.println("Booking ID: " + b.getBookingId() + ", Customer Name: Not Found, Room Number: Not Found, Booking Date: " + b.getBookingDate());
                        }
                    }
                    break;

                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}