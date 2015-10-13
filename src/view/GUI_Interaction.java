package view;

import model.HotelManager;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sonnguyen
 */
public class GUI_Interaction extends JFrame {

    public GUI_Interaction(HotelManager hotelManager) {
	setSize(300, 100);
	setLayout(new FlowLayout());

	JLabel userLabel = new JLabel("Enter your user ID");
	JTextField userField = new JTextField();
	userField.setColumns(10);

	JButton loginButton = new JButton("Login");
	loginButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		String userID = userField.getText();

		try {
		    int customerID = Integer.parseInt(userID);
		    if (hotelManager.containsCustomer(customerID)) {
			dispose();
			new ReservationGUI(hotelManager, customerID);
		    } else {
			dispose();
			new RegisterGUI(hotelManager);
		    }
		} catch (NumberFormatException numberFormatException) {
		    userField.setText("");
		}
	    }
	});

	JButton registerButton = new JButton("Register");
	registerButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		new RegisterGUI(hotelManager);
	    }
	});

	JButton adminButton = new JButton("Admin");
	adminButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		new AdminGUI(hotelManager);
	    }
	});

	add(userLabel);
	add(userField);
	add(loginButton);
	add(registerButton);
	add(adminButton);
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
