/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.Economy;
import model.HotelManager;
import model.Luxury;
import model.Reservation;
import model.Room;

public class ReservationGUI extends JFrame implements ChangeListener, WindowFocusListener {

    private static final int WIDTH = 720;
    private static final int HEIGHT = 300;
    private HotelManager hotelManager;
    private int customerID;
    private Date fromDate = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate());
    private Date toDate = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate());
    private int roomPrice = 0;
    private int numberOfDays = 0;

    private JTextField fromField = new JTextField(10);
    private JTextField toField = new JTextField(10);

    private JPanel roomsAvaiPanel = new JPanel(new GridLayout(0, 2));
    private JPanel reservationsPanel = new JPanel(new GridLayout(0, 1));

    private ArrayList<Room> roomsSelected = new ArrayList<>();
    private ArrayList<Reservation> reservationsSelected = new ArrayList<>();

    private JFrame temp;

    public ReservationGUI(HotelManager hotelManager, int customerID) {
	this.hotelManager = hotelManager;
	this.hotelManager.addListener(this);
	this.customerID = customerID;
	setLayout(new GridLayout(1, 2));
	setSize(WIDTH, HEIGHT);

	add(makeLeftJPanel());
	add(makeRightJPanel());
	addWindowFocusListener(this);

	setResizable(false);
	setVisible(true);
    }

    public void getDateFrame(int x, int y, Date dateToSet, JTextField fieldToSet) {

	temp = new JFrame();
	temp.setSize(300, 216);
	temp.setLocation(x, y);
	temp.setLayout(new BorderLayout());

	JButton doneButton = new JButton("Done");
	doneButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		temp.dispose();
	    }
	});
	CalendarGUI calendarGUI = new CalendarGUI(dateToSet, fieldToSet);
	calendarGUI.attach(this);

	temp.add(calendarGUI, BorderLayout.CENTER);
	temp.add(doneButton, BorderLayout.SOUTH);
	temp.setResizable(false);
	temp.setUndecorated(true);
	temp.setAlwaysOnTop(true);
	temp.setVisible(true);
    }

    public JPanel makeLeftJPanel() {
	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(fromDate);
	numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	int year = cal.get(Calendar.YEAR);

	fromField.setEditable(false);
	toField.setEditable(false);

	fromField.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		Point currentPos = new Point(fromField.getLocation());
		SwingUtilities.convertPointToScreen(currentPos, fromField);
		getDateFrame((int) currentPos.getX() - 140, (int) currentPos.getY() + 20, fromDate, fromField);
	    }
	});

	toField.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		Point currentPos = new Point(fromField.getLocation());
		SwingUtilities.convertPointToScreen(currentPos, fromField);
		getDateFrame((int) currentPos.getX() - 136, (int) currentPos.getY() + 60, toDate, toField);
	    }
	});

	JPanel fromJPanel = new JPanel(new FlowLayout());
	fromJPanel.add(new JLabel("Check-in"));
	fromJPanel.add(fromField);

	JPanel toJPanel = new JPanel(new FlowLayout());
	toJPanel.add(new JLabel("Check-out"));
	toJPanel.add(toField);

	JPanel roomChoice = new JPanel(new FlowLayout());
	JRadioButton price1 = new JRadioButton("Economy ($" + Economy.getPRICE() + ")");
	JRadioButton price2 = new JRadioButton("Luxury ($" + Luxury.getPRICE() + ")");

	price1.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		roomPrice = Economy.getPRICE();
		if (!fromField.getText().isEmpty() && !toField.getText().isEmpty()) {
		    roomsSelected = new ArrayList<>();
		    makeRoomsAvailPanel();
		}
	    }
	});

	price2.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		roomPrice = Luxury.getPRICE();
		if (!fromField.getText().isEmpty() && !toField.getText().isEmpty()) {
		    roomsSelected = new ArrayList<>();
		    makeRoomsAvailPanel();
		}
	    }
	});

	JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
	buttonPanel.add(price1);
	buttonPanel.add(price2);

	ButtonGroup buttonGroup = new ButtonGroup();
	buttonGroup.add(price1);
	buttonGroup.add(price2);

	roomChoice.add(new JLabel("Room type"));
	roomChoice.add(buttonPanel);

	JButton reserveButton = new JButton("Done and Reserve !");
	reserveButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		for (Room room : roomsSelected) {
		    hotelManager.makeReservation(customerID, room.getId(), (Date) fromDate.clone(), (Date) toDate.clone());
		}
		roomsSelected = new ArrayList<>();
	    }
	});

	JPanel leftJPanel = new JPanel(new BorderLayout());
	leftJPanel.setSize(WIDTH / 3 * 2, HEIGHT);

	JPanel choicePanel = new JPanel(new GridLayout(4, 1));
	choicePanel.add(fromJPanel);
	choicePanel.add(toJPanel);
	choicePanel.add(roomChoice);
	choicePanel.add(reserveButton);

	JScrollPane roomsJScrollPane = new JScrollPane(roomsAvaiPanel);
	leftJPanel.add(choicePanel, BorderLayout.NORTH);
	leftJPanel.add(roomsJScrollPane, BorderLayout.CENTER);
	return leftJPanel;
    }

    public JPanel makeRightJPanel() {
	JPanel rightJPanel = new JPanel(new BorderLayout());
	rightJPanel.setSize(WIDTH / 3, HEIGHT);

	JPanel buttonJPanel = new JPanel(new GridLayout(1, 2));
	JButton removeButton = new JButton("Remove !");
	removeButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		for (Reservation reservation : reservationsSelected) {
		    hotelManager.cancelReservation(reservation);
		}
	    }
	});
	JButton finishedButton = new JButton("Transaction Done");
	finishedButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		new ReceiptGUI(hotelManager, customerID);
	    }
	});

	buttonJPanel.add(removeButton);
	buttonJPanel.add(finishedButton);

	makeReservationPanel();
	JScrollPane roomsJScrollPane = new JScrollPane(reservationsPanel);

	JLabel receipJLabel = new JLabel("RESERVATION LIST", SwingConstants.CENTER);
	receipJLabel.setForeground(Color.red);
	receipJLabel.setFont(new Font(null, Font.BOLD, 14));
	rightJPanel.add(receipJLabel, BorderLayout.NORTH);
	rightJPanel.add(roomsJScrollPane, BorderLayout.CENTER);
	rightJPanel.add(buttonJPanel, BorderLayout.SOUTH);

	return rightJPanel;
    }

    public void makeRoomsAvailPanel() {
	roomsAvaiPanel.removeAll();
	ArrayList<Room> rooms;
	if (roomPrice == 0) {
	    rooms = hotelManager.getAvailableRooms(fromDate, toDate);
	} else {
	    rooms = hotelManager.getAvailableRoomsByPrice(fromDate, toDate, roomPrice);
	}
	for (final Room room : rooms) {
	    JRadioButton button = new JRadioButton("Room " + room.getId() + " : $" + room.getPrice());
	    button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    JRadioButton tempButton = (JRadioButton) e.getSource();
		    if (tempButton.isSelected()) {
			roomsSelected.add(room);
		    } else if (!tempButton.isSelected()) {
			roomsSelected.remove(room);
		    }
		}
	    });
	    roomsAvaiPanel.add(button);
	}
	revalidate();
	repaint();
    }

    public void makeReservationPanel() {
	reservationsPanel.removeAll();
	ArrayList<Reservation> reservations;
	reservations = hotelManager.getReservedRoomsByCustomer(customerID);
	for (final Reservation reservation : reservations) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    JRadioButton button = new JRadioButton("Room " + reservation.getRoom().getId()
		    + ": $" + reservation.getRoom().getPrice() + " ** "
		    + dateFormat.format(reservation.getCheckIn()) + " To "
		    + dateFormat.format(reservation.getCheckOut()));
	    button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    JRadioButton tempButton = (JRadioButton) e.getSource();
		    if (tempButton.isSelected()) {
			reservationsSelected.add(reservation);
		    } else if (!tempButton.isSelected()) {
			reservationsSelected.remove(reservation);
		    }
		}
	    });
	    reservationsPanel.add(button);
	}
	revalidate();
	repaint();
    }

    public int countDays() {
	int count = 1;
	Date tempDate = new Date(fromDate.getYear(), fromDate.getMonth(), fromDate.getDate());
	while (tempDate.before(toDate)) {
	    count++;
	    tempDate.setDate(tempDate.getDate() + 1);
	}
	return count;
    }

    public boolean checkValidDate() {
	if (fromDate.before(new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate())) || fromDate.after(toDate)) {
	    roomsAvaiPanel.removeAll();
	    roomsAvaiPanel.add(new JLabel("Please pick a valid date"));
	    revalidate();
	    repaint();
	    return false;
	} else if (countDays() > 60) {
	    roomsAvaiPanel.removeAll();
	    roomsAvaiPanel.add(new JLabel("Please pick less than 60 days"));
	    revalidate();
	    repaint();
	    return false;
	} else {
	    makeRoomsAvailPanel();
	    return true;
	}
    }

    @Override
    public void stateChanged(ChangeEvent e) {
	if (checkValidDate() && roomsAvaiPanel.getComponentCount() != 0) {
	    makeRoomsAvailPanel();
	}
	makeReservationPanel();
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
	if (temp != null) {
	    temp.dispose();
	}
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
    }

}
