
package GUI;


import java.awt.ComponentOrientation;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import java.awt.*;

import com.sun.prism.paint.Color;

import Main.AddCourseState;
import Main.Constants;

/**
 * This panel one of the content windows that get switched by the Main GUI. Its
 * only purpose is to display simple text.
 * 
 * @author Nate
 * 
 */
@SuppressWarnings("serial")
public class Profile extends JPanel {
	private AddCourseState state = AddCourseState.TAKEN;
	private JTextField uniqueIDTextField;
	JComboBox majorBox, statusBox, courseAddWindow, campusBox;
	JSpinner spinner;
	Label hoursLabel;
	List listAddWindow;
	Scrollbar scrollbarAddWindow;
	Button acceptWindowButton, cancelWindowButton;
	
	
	/**
	 * Creates the profile panel
	 */
	public Profile() {
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		//Create a blank profile panel
		setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		setLayout(null);
		
		uniqueIDTextField = new JTextField();
		uniqueIDTextField.setText("Unique ID");
		uniqueIDTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				uniqueIDTextField.setText("");
			}
			public void focusLost(FocusEvent e) {
				uniqueIDTextField.setText(uniqueIDTextField.getText().equals("") ? "Unique ID" : uniqueIDTextField.getText());
				revalidate();
			}

		});
		uniqueIDTextField.setBounds(250, 96, 150, 28);
		add(uniqueIDTextField);
		
		uniqueIDTextField.setColumns(10);
		
		majorBox = new JComboBox();
		majorBox.setToolTipText("");
		majorBox.setBounds(39, 147, 203, 27);
		majorBox.addItem("Select Your Major");
		majorBox.addItem("Computer Science");
		majorBox.addItem("Software Engineering");
		add(majorBox);
		
		statusBox = new JComboBox();
		statusBox.setBounds(400, 147, 140, 27);
		statusBox.addItem("Current Year");
		statusBox.addItem("First Year");
		statusBox.addItem("Second Year");
		statusBox.addItem("Thrid Year");
		statusBox.addItem("Fourth Year +");
		add(statusBox);
		
		spinner = new JSpinner();
		spinner.setBounds(409, 233, 131, 28);
		add(spinner);
		
		hoursLabel = new Label("Hours:");
		hoursLabel.setAlignment(Label.RIGHT);
		hoursLabel.setFont(new Font("Arial", Font.PLAIN, 17));
		
		hoursLabel.setBounds(329, 237, 70, 28);
		add(hoursLabel);
		
		JComboBox currentSemesterBox = new JComboBox();
		currentSemesterBox.setBounds(39, 233, 203, 28);
		currentSemesterBox.addItem("Select Current Semester");
		add(currentSemesterBox);
		
		JLabel lblNewLabel = new JLabel("Create Your Own Profile");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		lblNewLabel.setBounds(169, 20, 341, 64);
		add(lblNewLabel);

		// ========================!addButtonWindow!====================================
		AddButtonWindow.PARENT.getContentPane().setPreferredSize(new Dimension(300, 300));
		AddButtonWindow.PARENT.setResizable(false);
		AddButtonWindow.PARENT.getContentPane().setLayout(null);


		courseAddWindow = new JComboBox();
		courseAddWindow.setBounds(6, 6, 120, 27);

		//HARD CODE For courses===========================
		courseAddWindow.addItem("Course");
		courseAddWindow.addItem("CSE 102");
		courseAddWindow.addItem("CSE 174");
		courseAddWindow.addItem("CSE 201");
		courseAddWindow.addItem("CSE 271");
		courseAddWindow.addItem("CSE 274");
		courseAddWindow.addItem("CSE 278");
		AddButtonWindow.PARENT.getContentPane().add(courseAddWindow);
		//================================

		//HARD CODE for the location box=============
		campusBox = new JComboBox();
		campusBox.setBounds(335, 6, 140, 27);
		campusBox.addItem("Location");
		campusBox.addItem("Oxford");
		campusBox.addItem("Hamilton");
		campusBox.addItem("MiddleTown");
		campusBox.addItem("All");
		AddButtonWindow.PARENT.getContentPane().add(campusBox);
		//============================================

		//HARD CODE for the course box=============================
		listAddWindow = new List();
		listAddWindow.setBounds(10, 39, 480, 170);
		listAddWindow.add("Location |  CRN   | CourseTitle | Section | Professor | Time");
		listAddWindow.add("   O     |  57409 |   CSE 201   |    B    | Ann Sobel | 11:30A.M - 12:50P.M");
		AddButtonWindow.PARENT.getContentPane().add(listAddWindow);
		//==========================================================

		//Add the scroll bar for the courses box
		scrollbarAddWindow = new Scrollbar();
		scrollbarAddWindow.setBounds(250, 39, 15, 170);
		AddButtonWindow.PARENT.getContentPane().add(scrollbarAddWindow);

		//Accept button in the pop up window
		acceptWindowButton = new Button("Accept");
		acceptWindowButton.setBounds(9, 215, 84, 28);
		acceptWindowButton.setActionCommand("acceptWindowButton");//Exits out of the add button window
		acceptWindowButton.addActionListener(new ButtonEvent());
		AddButtonWindow.PARENT.getContentPane().add(acceptWindowButton);

		// Cancel button in pop up window
		cancelWindowButton = new Button("Cancel");
		cancelWindowButton.setBounds(408, 215, 84, 28);
		cancelWindowButton.setActionCommand("cancelWindowButton");
		cancelWindowButton.addActionListener(new ButtonEvent());
		AddButtonWindow.PARENT.getContentPane().add(cancelWindowButton);

		Label courseLabel = new Label("Full Course Name");
		courseLabel.setBounds(190, 10, 117, 17);
		AddButtonWindow.PARENT.getContentPane().add(courseLabel);

		//END PROFILE PANEL===================================

	}

	private class ButtonEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equalsIgnoreCase("cancelWindowButton")){
				AddButtonWindow.PARENT.setVisible(false);
			}
			if(e.getActionCommand().equalsIgnoreCase("acceptWindowButton")){
				AddButtonWindow.PARENT.setVisible(false);
			}
			if(e.getActionCommand().equalsIgnoreCase("addWindowButton")){
				String result = "";
				switch (state) {

				case TAKEN : 
					//add code
					break;
				case DESIRED :
					//add code
					break;
				case NULL :
					//add code	
					break;
				case ERROR :
					//add code	
					break;
				}
			}	
		}
	}
}


