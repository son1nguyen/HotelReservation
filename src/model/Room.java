package model;

import java.io.Serializable;

public abstract class Room implements Serializable {

    private int id;
    private double price;

    public Room(int id, double price) {
	this.id = id;
	this.price = price;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public double getPrice() {
	return price;
    }

    public void setPrice(double price) {
	this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
	return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public String print() {
	return "Room ID: " + this.id + "\n" + this.getClass() + "\nCost: $" + this.price;
    }
}
