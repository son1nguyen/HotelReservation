
import view.GUI_Interaction;
import model.HotelManager;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
	HotelManager hotelManager = read("data.txt");
	if (hotelManager == null) {
	    hotelManager = new HotelManager();
	    System.out.println("The object is null");
	}

	new GUI_Interaction(hotelManager);
    }

    public static HotelManager read(String fileName) {
	try {
	    ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
	    return (HotelManager) in.readObject();
	} catch (Exception ex) {
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	    return null;
	}
    }
}
