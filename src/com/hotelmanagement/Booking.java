package com.hotelmanagement;

public class Booking {
	

	

		 private int bookingId;
		    private Customer customer;
		    private Room room;
		    private String bookingDate;

		    public Booking(int bookingId, Customer customer, Room room, String bookingDate) {
		        this.bookingId = bookingId;
		        this.customer = customer;
		        this.room = room;
		        this.bookingDate = bookingDate;
		    }

		    public int getBookingId() {
		        return bookingId;
		    }

		    public void setBookingId(int bookingId) {
		        this.bookingId = bookingId;
		    }

		    public Customer getCustomer() {
		        return customer;
		    }

		    public void setCustomer(Customer customer) {
		        this.customer = customer;
		    }

		    public Room getRoom() {
		        return room;
		    }

		    public void setRoom(Room room) {
		        this.room = room;
		    }

		    public String getBookingDate() {
		        return bookingDate;
		    }

		    public void setBookingDate(String bookingDate) {
		        this.bookingDate = bookingDate;
		    }
		}


