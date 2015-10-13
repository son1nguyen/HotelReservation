package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomsManager implements Serializable {

    private HashMap<Integer, Room> rooms;

    public RoomsManager() {
	rooms = new HashMap<>();
    }

    public Room getRoom(int roomID) {
	return this.rooms.get(roomID);
    }

    public boolean addRooms(Room room) {
	if (!this.rooms.containsKey(room.getId())) {
	    this.rooms.put(room.getId(), room);
	    return true;
	} else {
	    return false;
	}
    }

    public boolean contains(int roomID) {
	return this.rooms.containsKey(roomID);
    }

    public ArrayList<Room> getAvailableRooms(ArrayList<Integer> reservedRooms) {
	ArrayList<Room> availableRooms = new ArrayList<>();

	for (Map.Entry<Integer, Room> entrySet : rooms.entrySet()) {
	    Room room = entrySet.getValue();
	    if (!reservedRooms.contains(room.getId())) {
		availableRooms.add(room);
	    }
	}
	return availableRooms;
    }

    public ArrayList<Room> getAvailableRoomsByPrice(ArrayList<Integer> reservedRooms, int roomPrice) {
	ArrayList<Room> availableRooms = new ArrayList<>();

	for (Map.Entry<Integer, Room> entrySet : rooms.entrySet()) {
	    Room room = entrySet.getValue();
	    if (!reservedRooms.contains(room.getId()) && room.getPrice() == roomPrice) {
		availableRooms.add(room);
	    }
	}
	return availableRooms;
    }

    public String print() {
	StringBuffer buffer = new StringBuffer();
	for (Map.Entry<Integer, Room> entrySet : rooms.entrySet()) {
	    Room room = entrySet.getValue();
	    buffer.append(room.print() + "\n");
	}
	return buffer.toString().trim();
    }
}
