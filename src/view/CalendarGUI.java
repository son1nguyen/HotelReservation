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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarGUI extends JPanel {

    private Date selectedDate;
    private JTextField textField;
    private ArrayList<ChangeListener> listeners;
    private JComboBox<String> monthBox;
    private JComboBox<Integer> yearBox;
    private String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May",
	"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private int numberOfDays;
    private JPanel dateJPanel = new JPanel(new GridLayout(0, 7));

    public CalendarGUI(Date dateToSet, JTextField fieldToSet) {
	this.selectedDate = dateToSet;
	this.textField = fieldToSet;
	this.listeners = new ArrayList<>();
	this.textField.setText(selectedDate.getDate() + " - "
		+ MONTH[selectedDate.getMonth()] + " - "
		+ (selectedDate.getYear() + 1900));
	setLayout(new BorderLayout());

	monthBox = new JComboBox<>(MONTH);
	yearBox = makeYearBox();
	setDefaultDate();

	monthBox.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e
	    ) {
		JComboBox cb = (JComboBox) e.getSource();
		int selectedMonth = cb.getSelectedIndex();
		selectedDate.setMonth(selectedMonth);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(selectedDate);
		numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		makeDateJPanel();
	    }
	}
	);
	yearBox.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e
	    ) {
		JComboBox cb = (JComboBox) e.getSource();
		int selectedYear = yearBox.getItemAt(cb.getSelectedIndex());
		selectedDate.setYear(selectedYear - 1900);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(selectedDate);
		numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		makeDateJPanel();
	    }
	}
	);

	JPanel controlPanel = new JPanel(new GridLayout(1, 2));
	controlPanel.add(monthBox);
	controlPanel.add(yearBox);

	makeDateJPanel();
	add(controlPanel, BorderLayout.NORTH);
	add(dateJPanel, BorderLayout.CENTER);
    }

    public void makeDateJPanel() {
	dateJPanel.removeAll();
	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(selectedDate);

	numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	int firstDayOfMonth = getStartDate(selectedDate);

	dateJPanel.add(new JLabel("Sun", SwingConstants.CENTER));
	dateJPanel.add(new JLabel("Mon", SwingConstants.CENTER));
	dateJPanel.add(new JLabel("Tue", SwingConstants.CENTER));
	dateJPanel.add(new JLabel("Wed", SwingConstants.CENTER));
	dateJPanel.add(new JLabel("Thu", SwingConstants.CENTER));
	dateJPanel.add(new JLabel("Fri", SwingConstants.CENTER));
	dateJPanel.add(new JLabel("Sat", SwingConstants.CENTER));

	int weekdayIndex = 0; // reset index of weekday
	for (int day = 0; day < firstDayOfMonth; day++) {
	    dateJPanel.add(new JLabel());
	    weekdayIndex++;
	}

	for (int day = 1; day <= numberOfDays; day++) {
	    JButton button = new JButton(String.valueOf(day));
	    button.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    int chosen = Integer.parseInt(((JButton) e.getSource()).getText());
		    selectedDate.setYear(yearBox.getItemAt(yearBox.getSelectedIndex()) - 1900);
		    selectedDate.setMonth(monthBox.getSelectedIndex());
		    selectedDate.setDate(chosen);
		    textField.setText(selectedDate.getDate() + " - "
			    + MONTH[selectedDate.getMonth()] + " - "
			    + (selectedDate.getYear() + 1900));
		    if (listeners != null) {
			for (ChangeListener listener : listeners) {
			    listener.stateChanged(new ChangeEvent(this));
			}
		    }
		}
	    });
	    dateJPanel.add(button);
	}

	for (int i = firstDayOfMonth + numberOfDays; i < 42; i++) {
	    dateJPanel.add(new JLabel());
	}
	revalidate();
	repaint();
    }

    private int getStartDate(Date aDate) {
	Date temp = new Date(aDate.getYear(), aDate.getMonth(), 1);
	switch (temp.toString().substring(0, 3)) {
	    case "Sun":
		return 0;
	    case "Mon":
		return 1;
	    case "Tue":
		return 2;
	    case "Wed":
		return 3;
	    case "Thu":
		return 4;
	    case "Fri":
		return 5;
	    case "Sat":
		return 6;
	    default:
		return -1;
	}
    }

    public JComboBox<Integer> makeYearBox() {
	JComboBox<Integer> yearBox = new JComboBox<>();
	for (int i = 0; i < 100; i++) {
	    yearBox.addItem(new Date().getYear() + 1900 + i);
	}
	return yearBox;
    }

    public void setDefaultDate() {
	monthBox.setSelectedIndex(selectedDate.getMonth());
	yearBox.setSelectedIndex(selectedDate.getYear() + 1900 - yearBox.getItemAt(0));
    }

    public void attach(ChangeListener c) {
	listeners.add(c);
    }
}
