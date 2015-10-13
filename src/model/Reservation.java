package model;


import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {

    private Customer customer;
    private Room room;
    private Date checkIn;
    private Date checkOut;

    public Reservation(Customer customer, Room room, Date checkIn, Date checkOut) {
	this.customer = customer;
	this.room = room;
	this.checkIn = checkIn;
	this.checkOut = checkOut;
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

    public Date getCheckIn() {
	return checkIn;
    }

    public void setCheckIn(Date checkIn) {
	this.checkIn = checkIn;
    }

    public Date getCheckOut() {
	return checkOut;
    }

    public void setCheckOut(Date checkOut) {
	this.checkOut = checkOut;
    }

    /**
     * check the room reserved or not
     *
     * @param fromDate from this date
     * @param toDate to this date
     * @return a boolean value (true is reserved) (false is not reserved)
     */
    public boolean isReserved(Date fromDate, Date toDate) {
	if (fromDate.before(this.checkIn) && toDate.before(this.checkIn)) {
	    return false;
	} else if (fromDate.after(this.checkOut) && toDate.after(this.checkOut)) {
	    return false;
	} else {
	    return true;
	}
    }

    public String print() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("Customer ID: " + customer.getId());
	buffer.append("\nRoom ID: " + room.getId());
	buffer.append("\nCheck in: " + checkIn.toString());
	buffer.append("\nCheck out: " + checkOut.toString());
	return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	} else if (obj instanceof Reservation) {
	    Reservation o = (Reservation) obj;
	    if (this.customer.getId() == o.getCustomer().getId()
		    && this.room.getId() == o.getRoom().getId()
		    && this.checkIn.equals(o.getCheckIn())
		    && this.checkOut.equals(o.getCheckOut())) {
		return true;
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
    }

}
