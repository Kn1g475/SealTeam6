
package GUI;


import javax.swing.*;

import java.awt.*;

import Main.AddCourseState;
import Main.Constants;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * This panel one of the content windows that get switched by the Main GUI. Its
 * only purpose is to display simple text.
 * 
 * @author Nate
 * 
 */
@SuppressWarnings("serial")
public class ProfileGUI extends JPanel {
	private JTextField uniqueIDTextField;
	JComboBox majorBox, statusBox, currentSemesterBox;
	JSpinner spinner;
	Label hoursLabel;
	JLabel profileTitleLabel;
	/**
	 * Creates the profile panel
	 */
	public ProfileGUI() {
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
		majorBox.setBackground(Color.WHITE);
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
		spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner.setBounds(409, 233, 131, 28);
		add(spinner);
		
		hoursLabel = new Label("Hours:");
		hoursLabel.setAlignment(Label.RIGHT);
		hoursLabel.setFont(new Font("Arial", Font.PLAIN, 17));
		
		hoursLabel.setBounds(329, 237, 70, 28);
		add(hoursLabel);
		
		currentSemesterBox = new JComboBox();
		currentSemesterBox.setBounds(39, 233, 203, 28);
		currentSemesterBox.addItem("Select Current Semester");
		currentSemesterBox.addItem("Fall 2016");
		add(currentSemesterBox);
		
		profileTitleLabel = new JLabel("Create Your Own ProfileGUI");
		profileTitleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		profileTitleLabel.setBounds(169, 20, 341, 64);
		add(profileTitleLabel);

		
	}
	
	public boolean allSelected(){
		
		
		String uniqueID = uniqueIDTextField.getText();
		String major =  (String) majorBox.getSelectedItem();
		String status = (String) statusBox.getSelectedItem();
		String currentSemester = (String) currentSemesterBox.getSelectedItem();
		
		
		if(uniqueID.equals("") || uniqueID.equals("uniqueID")) return false;
		if(major.equals(majorBox.getItemAt(0))) return false;
		if(status.equals(statusBox.getItemAt(0))) return false;
		if(currentSemester.equals(currentSemesterBox.getItemAt(0))) return false;
		else return true; 
		
	}
}


