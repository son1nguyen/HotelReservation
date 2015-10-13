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
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.HotelManager;
import model.Reservation;
import model.Room;

public class AdminGUI extends JFrame implements ChangeListener {

    private HotelManager hotelManager;
    private Date viewingDate = new Date(new Date().getYear(), new Date().getMonth(), 15);
    private JPanel roomsPanel = new JPanel(new GridLayout(0, 1));
    private boolean available = false;

    public AdminGUI(HotelManager hotelManager) {
	this.hotelManager = hotelManager;
	this.hotelManager.addListener(this);
	setSize(550, 270);
	setLayout(new GridLayout(1, 2));

	makeRoomsPanel();

	CalendarGUI calendarGUI = new CalendarGUI(viewingDate, new JTextField());
	calendarGUI.attach(this);

	JPanel adminJPanel = new JPanel(new GridLayout(1, 3));
	final JToggleButton toggleButton = new JToggleButton("Available Room");
	toggleButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (toggleButton.isEnabled()) {
		    available = !available;
		    makeRoomsPanel();
		}
	    }
	});
	JButton saveButton = new JButton("Save");
	saveButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		hotelManager.save("data.txt", hotelManager);
	    }
	});
	JButton quitButton = new JButton("Quit");
	quitButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
	    }
	});
	adminJPanel.add(toggleButton);
	adminJPanel.add(saveButton);
	adminJPanel.add(quitButton);

	JPanel rightJPanel = new JPanel(new BorderLayout());
	JScrollPane scrollPane = new JScrollPane(roomsPanel);
	rightJPanel.add(adminJPanel, BorderLayout.NORTH);
	rightJPanel.add(scrollPane, BorderLayout.CENTER);

	add(calendarGUI);
	add(rightJPanel);

	setResizable(false);
	setVisible(true);
    }

    public void makeRoomsPanel() {
	roomsPanel.removeAll();
	if (!available) {
	    ArrayList<Reservation> rooms = hotelManager.getReservationsByDate(viewingDate);
	    for (final Reservation room : rooms) {
		roomsPanel.add(new JLabel("Room number: " + room.getRoom().getId() + ": "
			+ room.getCustomer().getFirst() + " " + room.getCustomer().getLast()
			+ " $" + room.getRoom().getPrice()));
	    }
	} else {
	    ArrayList<Room> rooms = hotelManager.getAvailableRooms(viewingDate, viewingDate);
	    for (final Room room : rooms) {
		roomsPanel.add(new JLabel("Room Number: " + room.getId() + "   $" + room.getPrice()));
	    }
	}

	revalidate();
	repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
	makeRoomsPanel();
    }

}
