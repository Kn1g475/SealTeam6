
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
public class Profile extends JPanel {
	private CourseData.Profile prof;
	private JTextField uniqueIDTextField;
	JComboBox majorBox, statusBox, currentSemesterBox;
	JSpinner spinner;
	Label hoursLabel;
	JLabel profileTitleLabel;
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
		add(currentSemesterBox);
		
		profileTitleLabel = new JLabel("Create Your Own Profile!");
		profileTitleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		profileTitleLabel.setBounds(169, 20, 341, 64);
		add(profileTitleLabel);

		
	}
	public CourseData.Profile getProfile() {
		return prof;
	}
}


