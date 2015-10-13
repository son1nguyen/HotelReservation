package model;

import java.io.Serializable;

public class Economy extends Room implements Serializable {

    private static int PRICE = 80;

    public Economy(int id) {
	super(id, PRICE);
    }

    public static int getPRICE() {
	return PRICE;
    }

    public static void setPRICE(int PRICE) {
	Economy.PRICE = PRICE;
    }

}
