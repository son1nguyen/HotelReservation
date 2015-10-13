package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HotelManager implements Serializable {

    private final int NUM_OF_ROOM = 20;
    private RoomsManager roomsManager;
    private CustomersManager customersManager;
    private ArrayList<Reservation> reservations;
    private transient ArrayList<ChangeListener> listeners;

    public HotelManager() {
	roomsManager = new RoomsManager();
	customersManager = new CustomersManager();
	reservations = new ArrayList<>();
	listeners = new ArrayList<>();

	Room room = null;
	for (int i = 0; i < NUM_OF_ROOM; i++) {
	    if (i < NUM_OF_ROOM / 2) {
		room = new Economy(i + 1);
	    } else {
		room = new Luxury(i + 1);
	    }
	    roomsManager.addRooms(room);
	}
    }

    public boolean containsCustomer(int customerID) {
	return customersManager.contains(customerID);
    }

    public Room getRoom(int roomID) {
	return roomsManager.getRoom(roomID);
    }

    public RoomsManager getRoomsInHotel() {
	return roomsManager;
    }

    public boolean addCustomer(int customerID, String firstName, String lastName) {
	return customersManager.addCustomer(new Customer(customerID, lastName, firstName));
    }

    public void makeReservation(int customerID, int roomID, Date fromDate, Date toDate) {
	reservations.add(new Reservation(customersManager.getCustomerInfo(customerID),
		roomsManager.getRoom(roomID), fromDate, toDate));
	update();
    }

    public void cancelReservation(Reservation canceledReservation) {
	reservations.remove(canceledReservation);
	update();
    }

    public ArrayList<Room> getAvailableRooms(Date fromDate, Date toDate) {
	return roomsManager.getAvailableRooms(getReservedRooms(fromDate, toDate));
    }

    private ArrayList<Integer> getReservedRooms(Date fromDate, Date toDate) {
	ArrayList<Integer> reservedRooms = new ArrayList<>();
	for (Reservation reservation : reservations) {
	    if (reservation.isReserved(fromDate, toDate)) {
		reservedRooms.add(reservation.getRoom().getId());
	    }
	}
	return reservedRooms;
    }

    public ArrayList<Room> getAvailableRoomsByPrice(Date fromDate, Date toDate, int price) {
	return roomsManager.getAvailableRoomsByPrice(getReservedRooms(fromDate, toDate), price);
    }

    public ArrayList<Reservation> getReservedRoomsByCustomer(int customerID) {
	ArrayList<Reservation> rooms = new ArrayList<>();
	for (Reservation reservation : reservations) {
	    if (reservation.getCustomer().getId() == customerID) {
		rooms.add(reservation);
	    }
	}
	return rooms;
    }

    public ArrayList<Reservation> getReservationsByDate(Date date) {
	ArrayList<Reservation> temp = new ArrayList<>();
	for (Reservation reservation : reservations) {
	    if (reservation.isReserved(date, date)) {
		temp.add(reservation);
	    }
	}
	return temp;
    }

    public String printCustomers() {
	return customersManager.print();
    }

    public String printRooms() {
	return roomsManager.print();
    }

    public String printReservations() {
	StringBuffer buffer = new StringBuffer();
	for (Reservation reservation : reservations) {
	    buffer.append(reservation.print() + "\n");
	}
	return buffer.toString();
    }

    public void save(String fileName, HotelManager hotelManager) {
	try {
	    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
	    out.writeObject(hotelManager);
	    System.out.println("Saved successfully");
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }

    public void addListener(ChangeListener c) {
	if (listeners == null) {
	    listeners = new ArrayList<>();
	}
	listeners.add(c);
    }

    public void update() {
	for (ChangeListener l : listeners) {
	    l.stateChanged(new ChangeEvent(this));
	}
    }
}
