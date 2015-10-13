/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.HotelManager;

/**
 *
 * @author sonnguyen
 */
class RegisterGUI extends JFrame {

    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;

    public RegisterGUI(HotelManager hotelManager) {
	setSize(220, 180);
	setLayout(new FlowLayout());

	JLabel idLabel = new JLabel("Enter your ID");
	JLabel firstNameLabel = new JLabel("First name");
	JLabel lastNameLabel = new JLabel("Last name");

	idField = new JTextField(5);
	firstNameField = new JTextField(10);
	lastNameField = new JTextField(10);

	JButton registerButton = new JButton("Register !");
	registerButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    int customerID = Integer.parseInt(idField.getText());
		    String firstName = firstNameField.getText();
		    String lastName = lastNameField.getText();

		    if (firstName.isEmpty() || lastName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "First name or last name cannot be blank");
		    } else if (!hotelManager.containsCustomer(customerID)) {
			dispose();
			hotelManager.addCustomer(customerID, firstName, lastName);
			new ReservationGUI(hotelManager, customerID);
		    } else {
			JOptionPane.showMessageDialog(null, "This ID aleady exists, please choose another one");
			idField.setText("");
		    }
		} catch (NumberFormatException numberFormatException) {
		    JOptionPane.showMessageDialog(null, "ID has to be a number");
		    idField.setText("");
		}

	    }
	});

	add(idLabel);
	add(idField);
	add(firstNameLabel);
	add(firstNameField);
	add(lastNameLabel);
	add(lastNameField);
	add(registerButton);

	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
