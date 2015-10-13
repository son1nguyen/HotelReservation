package model;

import java.io.Serializable;

public class Customer implements Serializable {

    private int id;
    private String last;
    private String first;

    public Customer(int id, String last, String first) {
	this.id = id;
	this.last = last;
	this.first = first;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getLast() {
	return last;
    }

    public void setLast(String last) {
	this.last = last;
    }

    public String getFirst() {
	return first;
    }

    public void setFirst(String first) {
	this.first = first;
    }

    public String print() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("User ID: " + this.id);
	buffer.append("\nLast name: " + this.last);
	buffer.append("\nFirst name: " + this.first);
	return buffer.toString();
    }
}
