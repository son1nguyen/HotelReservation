/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.HotelManager;
import model.Reservation;

public class ReceiptGUI extends JFrame {

    public ReceiptGUI(HotelManager hotelManager, int customerID) {
	setLayout(new BorderLayout());
	ArrayList<Reservation> reservations;
	reservations = hotelManager.getReservedRoomsByCustomer(customerID);

	JPanel reservationPanel = new JPanel(new GridLayout(0, 1));
	JScrollPane scrollPane = new JScrollPane(reservationPanel);
	double total = 0;
	for (final Reservation reservation : reservations) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    JLabel label = new JLabel("Room " + reservation.getRoom().getId()
		    + ": $" + reservation.getRoom().getPrice() + " ** "
		    + dateFormat.format(reservation.getCheckIn()) + " To "
		    + dateFormat.format(reservation.getCheckOut()));
	    reservationPanel.add(label);
	    total += reservation.getRoom().getPrice();
	}

	reservationPanel.add(new JLabel("-------------------------------------"));
	reservationPanel.add(new JLabel("The total is $" + total));

	JButton done = new JButton("DONE !!");
	done.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		new GUI_Interaction(hotelManager);
	    }
	});

	add(scrollPane, BorderLayout.CENTER);
	add(done, BorderLayout.SOUTH);

	setSize(360, 300);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
    }
}
