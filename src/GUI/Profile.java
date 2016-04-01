
package GUI;






import Main.AddCourseState;
import Main.Constants;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.List;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
	private JTextField txtUniqueId;
	private JRadioButton rdbtnTaken, rdbtnDesired;
	/**
	 * Creates the profile panel
	 */
	public Profile() {
		//Create a blank profile panel
		setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		setLayout(null);
		JLabel about = new JLabel(Constants.PROFILE);// Constants.ABOUT);
		add(about);

		// textField for students Unique ID
		txtUniqueId = new JTextField();
		txtUniqueId.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				txtUniqueId.setText("");
			}
			public void focusLost(FocusEvent e) {
				txtUniqueId.setText(txtUniqueId.getText().equals("") ? "Unique ID" : txtUniqueId.getText());
			}

		});
		txtUniqueId.setText("Unique ID");
		txtUniqueId.setBounds(94, 43, 134, 28);
		add(txtUniqueId);
		txtUniqueId.setColumns(10);

		// List to choose courses taken
		List list = new List();
		list.setBounds(94, 112, 414, 89);
		list.add("CSE 174 ");
		list.add("CSE 271");
		add(list);

		// Scroll Bar for courses taken
		Scrollbar scrollbar = new Scrollbar();
		scrollbar.setBounds(493, 112, 15, 89);
		add(scrollbar);

		// Label for the list of classes taken
		JLabel classesTakenLabel = new JLabel("Classes Taken");
		classesTakenLabel.setBounds(235, 90, 95, 16);
		add(classesTakenLabel);

		// List for Desired Classes
		List desiredClassesList = new List();
		desiredClassesList.setBounds(94, 266, 414, 89);
		desiredClassesList.add("CSE 102");
		desiredClassesList.add("CSE 278");
		add(desiredClassesList);

		// Drop down box for Major
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Major");
		comboBox.addItem("Computer Science");
		comboBox.addItem("Software Engineering");
		comboBox.setName("Major");
		comboBox.setBounds(374, 45, 134, 26);
		comboBox.revalidate();
		add(comboBox);

		//scroll bar for desired classes
		Scrollbar scrollbarDesiredClasses = new Scrollbar();
		scrollbarDesiredClasses.setBounds(493, 266, 15, 89);
		add(scrollbarDesiredClasses);

		//Label for desired classes list
		JLabel desiredClassesLabel = new JLabel("Desired Classes");
		desiredClassesLabel.setBounds(235, 251, 106, 16);
		add(desiredClassesLabel);

		// Radio buttons ================================================
		rdbtnTaken = new JRadioButton("Taken");
		rdbtnTaken.setBounds(61, 368, 78, 28);
		rdbtnTaken.setActionCommand("rdbtnTaken");
		rdbtnTaken.setSelected(true);
		rdbtnTaken.addActionListener(new RadioEvent());
		add(rdbtnTaken);


		rdbtnDesired = new JRadioButton("Desired");
		rdbtnDesired.setBounds(61, 408, 80, 26);
		rdbtnDesired.setActionCommand("rdbtnDesired");
		rdbtnDesired.setSelected(false);
		rdbtnDesired.addActionListener(new RadioEvent());
		add(rdbtnDesired);

		// ========================!addButtonWindow!====================================
		AddButtonWindow.PARENT.getContentPane().setPreferredSize(new Dimension(300, 300));
		AddButtonWindow.PARENT.setResizable(false);
		AddButtonWindow.PARENT.getContentPane().setLayout(null);

		// Create a new window when the add button is clicked
		Button addButton = new Button("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddButtonWindow.PARENT.setPreferredSize(new Dimension(500, 275));
				AddButtonWindow.PARENT.setTitle("Add Course");
				AddButtonWindow.PARENT.setLocation(450, 275);
				AddButtonWindow.PARENT.pack();
				AddButtonWindow.PARENT.setVisible(true);

			}
		});


		addButton.setBackground(Color.WHITE);
		addButton.setBounds(228, 368, 102, 28);
		add(addButton);


		JComboBox courseAddWindow = new JComboBox();
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
		JComboBox campusBox = new JComboBox();
		campusBox.setBounds(335, 6, 140, 27);
		campusBox.addItem("Location");
		campusBox.addItem("Oxford");
		campusBox.addItem("Hamilton");
		campusBox.addItem("MiddleTown");
		campusBox.addItem("All");
		AddButtonWindow.PARENT.getContentPane().add(campusBox);
		//============================================

		//HARD CODE for the course box=============================
		List listAddWindow = new List();
		listAddWindow.setBounds(10, 39, 480, 170);
		listAddWindow.add("Location |  CRN   | CourseTitle | Section | Professor | Time");
		listAddWindow.add("   O     |  57409 |   CSE 201   |    B    | Ann Sobel | 11:30A.M - 12:50P.M");
		AddButtonWindow.PARENT.getContentPane().add(listAddWindow);
		//==========================================================

		//Add the scroll bar for the courses box
		Scrollbar scrollbarAddWindow = new Scrollbar();
		scrollbarAddWindow.setBounds(250, 39, 15, 170);
		AddButtonWindow.PARENT.getContentPane().add(scrollbarAddWindow);

		//Accept button in the pop up window
		Button acceptWindowButton = new Button("Accept");
		acceptWindowButton.setBounds(9, 215, 84, 28);
		acceptWindowButton.setActionCommand("acceptWindowButton");//Exits out of the add button window
		acceptWindowButton.addActionListener(new ButtonEvent());
		AddButtonWindow.PARENT.getContentPane().add(acceptWindowButton);

		// Cancel button in pop up window
		Button cancelWindowButton = new Button("Cancel");
		cancelWindowButton.setBounds(408, 215, 84, 28);
		cancelWindowButton.setActionCommand("cancelWindowButton");
		cancelWindowButton.addActionListener(new ButtonEvent());
		AddButtonWindow.PARENT.getContentPane().add(cancelWindowButton);

		Label courseLabel = new Label("Full Course Name");
		courseLabel.setBounds(190, 10, 117, 17);
		AddButtonWindow.PARENT.getContentPane().add(courseLabel);



		Button removeButton = new Button("Remove");
		removeButton.setBackground(Color.WHITE);
		removeButton.setBounds(228, 408, 102, 28);
		add(removeButton);

		Button checkButton = new Button("Check");
		checkButton.setBounds(413, 368, 117, 28);
		add(checkButton);

		JButton profileSaveButton = new JButton("Save Profile");
		profileSaveButton.setBounds(413, 408, 117, 29);
		add(profileSaveButton);

		//END PROFILE PANEL===================================

	}

	private class RadioEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equalsIgnoreCase("rdbtnTaken")){
				state = AddCourseState.TAKEN;
				rdbtnTaken.setSelected(true);
				rdbtnDesired.setSelected(false);
			}

			if(e.getActionCommand().equalsIgnoreCase("rdbtnDesired")){
				state = AddCourseState.DESIRED;
				rdbtnDesired.setSelected(true);
				rdbtnTaken.setSelected(false);
			}

		}
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


