package model;

import java.io.Serializable;

public class Luxury extends Room implements Serializable {

    private static int PRICE = 200;

    public Luxury(int id) {
	super(id, PRICE);
    }

    public static int getPRICE() {
	return PRICE;
    }

    public static void setPRICE(int PRICE) {
	Luxury.PRICE = PRICE;
    }

}
