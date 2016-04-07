
package GUI;

import Main.Constants;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import CourseData.Profile;

/**
 * This panel one of the content windows that get switched by the Main GUI. Its
 * only purpose is to display simple text.
 * 
 * @author Nate
 * 
 */
@SuppressWarnings("serial")
public class ProfileGUI extends JPanel {
	
	public String semester, major, uniqueId, curYear;
	public int hours;
	private JTextField uniqueIDTextField;
	private JComboBox<String> majorBox, statusBox, currentSemesterBox;
	private JSpinner spinner;
	private Label hoursLabel;
	private JLabel profileTitleLabel;
	/**
	 * Creates the profile panel
	 */
	public ProfileGUI(Profile profile) {
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		//Create a blank profile panel
		setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		setLayout(null);
		
		uniqueIDTextField = new JTextField();
		uniqueIDTextField.setText((profile.getUniqueID().equals(""))? "Unique ID" : profile.getUniqueID());
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
		
		majorBox = new JComboBox<>();
		majorBox.setBackground(Color.WHITE);
		majorBox.setToolTipText("");
		majorBox.setBounds(39, 147, 203, 27);
		majorBox.addItem("Select Your Major");
		majorBox.addItem("Computer Science");
		majorBox.addItem("Software Engineering");
		majorBox.setSelectedItem(profile.getMajor().equals("") ? "Select Your Major" : profile.getMajor());
		add(majorBox);
		
		statusBox = new JComboBox<>();
		statusBox.setBounds(400, 147, 140, 27);
		statusBox.addItem("Current Year");
		statusBox.addItem("First Year");
		statusBox.addItem("Second Year");
		statusBox.addItem("Thrid Year");
		statusBox.addItem("Fourth Year +");
		statusBox.setSelectedItem(profile.getCurYear().equals("") ? "Current Year" : profile.getCurYear());
		add(statusBox);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner.setBounds(409, 233, 131, 28);
		spinner.setValue(profile.getHours());
		add(spinner);
		
		hoursLabel = new Label("Hours:");
		hoursLabel.setAlignment(Label.RIGHT);
		hoursLabel.setFont(new Font("Arial", Font.PLAIN, 17));
		
		hoursLabel.setBounds(329, 237, 70, 28);
		add(hoursLabel);
		
		currentSemesterBox = new JComboBox<>();
		currentSemesterBox.setBounds(39, 233, 203, 28);
		currentSemesterBox.addItem("Select Current Semester");
		currentSemesterBox.addItem("Spring 2016");
		add(currentSemesterBox);
		
		profileTitleLabel = new JLabel("Create Your Own Profile");
		profileTitleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		profileTitleLabel.setBounds(169, 20, 341, 64);
		add(profileTitleLabel);
	}
	
	public boolean allSelected(){
		uniqueId = uniqueIDTextField.getText();
		major = (String) majorBox.getSelectedItem();
		curYear = (String) statusBox.getSelectedItem();
		semester = (String) currentSemesterBox.getSelectedItem();
		hours = (int)spinner.getValue();
		
		if (uniqueId.equals("") || uniqueId.equals("uniqueID") || major.equals(majorBox.getItemAt(0)) ||
				curYear.equals(statusBox.getItemAt(0)) || semester.equals(currentSemesterBox.getItemAt(0))) 
			return false;
		else {
			semester = "CSE_Course_Schedule_" + semester.replace(" ", "_");
			return true; 
		}
	}
}


