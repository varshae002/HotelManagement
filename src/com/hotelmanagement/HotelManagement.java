package com.hotelmanagement;
import java.sql.*;
import java.util.*;

public class HotelManagement {
    private List<Room> rooms = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private Connection connection;

    public HotelManagement() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Initialize database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hoteldb", "root", "1234");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a room
    public void addRoom(Room room) {
        rooms.add(room);
        try {
            String query = "INSERT INTO rooms (room_number, room_type, price, is_available) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, room.getRoomNumber());
            stmt.setString(2, room.getRoomType());
            stmt.setDouble(3, room.getPrice());
            stmt.setBoolean(4, room.isAvailable());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a customer
    public void addCustomer(Customer customer) {
        customers.add(customer);
        try {
            // Check if the customer ID already exists
            String checkQuery = "SELECT COUNT(*) FROM customers WHERE customer_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setInt(1, customer.getCustomerId());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.println("Customer ID already exists. Please enter a unique customer ID.");
                return;
            }

            String query = "INSERT INTO customers (customer_id, name, contact) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, customer.getCustomerId());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getContact());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to make a booking
    public void makeBooking(Booking booking) {
        bookings.add(booking);
        try {
            String query = "INSERT INTO bookings (booking_id, customer_id, room_number, booking_date) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, booking.getBookingId());
            stmt.setInt(2, booking.getCustomer().getCustomerId());
            stmt.setInt(3, booking.getRoom().getRoomNumber());
            stmt.setString(4, booking.getBookingDate());
            stmt.executeUpdate();

            // Update room availability
            String updateRoomQuery = "UPDATE rooms SET is_available = ? WHERE room_number = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateRoomQuery);
            updateStmt.setBoolean(1, false);
            updateStmt.setInt(2, booking.getRoom().getRoomNumber());
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch all rooms
    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();
        try {
            String query = "SELECT * FROM rooms";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Room room = new Room(rs.getInt("room_number"), rs.getString("room_type"), rs.getDouble("price"));
                room.setAvailable(rs.getBoolean("is_available"));
                roomList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    // Method to fetch all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        try {
            String query = "SELECT * FROM customers";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("contact"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    // Method to fetch all bookings
    public List<Booking> getAllBookings() {
        List<Booking> bookingList = new ArrayList<>();
        try {
            String query = "SELECT * FROM bookings";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int customerId = rs.getInt("customer_id");
                int roomNumber = rs.getInt("room_number");
                String bookingDate = rs.getString("booking_date");

                // Fetching Customer and Room objects from the database
                Customer customer = getCustomerById(customerId);
                Room room = getRoomByNumber(roomNumber);

                Booking booking = new Booking(bookingId, customer, room, bookingDate);
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingList;
    }

    // Fetch customer by ID from the database
    private Customer getCustomerById(int customerId) {
        try {
            String query = "SELECT * FROM customers WHERE customer_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fetch room by number from the database
    private Room getRoomByNumber(int roomNumber) {
        try {
            String query = "SELECT * FROM rooms WHERE room_number = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, roomNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Room room = new Room(rs.getInt("room_number"), rs.getString("room_type"), rs.getDouble("price"));
                room.setAvailable(rs.getBoolean("is_available"));
                return room;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}