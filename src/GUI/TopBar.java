package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Constants;
import Main.Main.ButtonListener;

import javax.swing.JRadioButton;

import Main.FileState;
import java.awt.Cursor;

/**
 * The top bar of the GUI.
 * 
 * @author matt
 * 
 */
@SuppressWarnings("serial")
public class TopBar extends JPanel {
	
	public static FileState state = FileState.COURSE;
	
	public JLabel fileLabel, errorLabel;
	public JButton addButton, submitButton, deleteButton;
	private JRadioButton scheduleRadioButton;
	private JRadioButton requirementsRadioButton;
	private JRadioButton courseRadioButton;

	/**
	 * Creates a new top bar object, takes in a listener class so that this
	 * object can communicate with the main class
	 * 
	 * @param buttonListener
	 */
	public TopBar(ButtonListener buttonListener) {

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setPreferredSize(new Dimension(795, 55));
		setBackground(Constants.MENU_BACKGROUND_COLOR);
		setBorder(BorderFactory.createMatteBorder(0, 0,
				Constants.TOP_ROW_BORDER_THICKNESS, 0,
				Constants.TOP_ROW_BORDER_COLOR));

		// set up Icon and Program Title in top left corner
		JLabel title = new JLabel();
		Image img = new ImageIcon(Constants.MIAMI_ICON_NAME).getImage();
		img = img.getScaledInstance(Constants.TOP_ROW_HEIGHT,
				Constants.TOP_ROW_HEIGHT, Image.SCALE_SMOOTH);
		title.setIcon(new ImageIcon(img));
		title.setFont(Constants.TITLE_FONT);
		title.setText(Constants.TITLE);
		add(title);

		add(Box.createHorizontalGlue());

		// set up all buttons and labels
		JPanel buttonsGroup = new JPanel();
		buttonsGroup.setLayout(new BoxLayout(buttonsGroup, BoxLayout.PAGE_AXIS));
		buttonsGroup.setBackground(Constants.MENU_BACKGROUND_COLOR);
		JPanel fileButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		fileButtons.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		addButton = new JButton("Add File");
		addButton.setActionCommand("addButton");
		addButton.addActionListener(buttonListener);

		deleteButton = new JButton("Remove");
		deleteButton.setActionCommand("deleteButton");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(buttonListener);

		submitButton = new JButton("Submit");
		submitButton.setActionCommand("submitButton");
		submitButton.setEnabled(false);
		submitButton.addActionListener(buttonListener);

		// Radio button =========================================
		courseRadioButton = new JRadioButton("Course");
		courseRadioButton.setActionCommand("courseRadioButton");
		courseRadioButton.setSelected(true);
		courseRadioButton.addActionListener(new RadioEvent());
		fileButtons.add(courseRadioButton);

		requirementsRadioButton = new JRadioButton("Requirements");
		requirementsRadioButton.setActionCommand("requirementsRadioButton");
		requirementsRadioButton.setSelected(false);
		requirementsRadioButton.addActionListener(new RadioEvent());
		fileButtons.add(requirementsRadioButton);
		
		scheduleRadioButton = new JRadioButton("Schedule");
		scheduleRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		scheduleRadioButton.setActionCommand("scheduleRadioButton");
		scheduleRadioButton.setSelected(false);
		scheduleRadioButton.addActionListener(new RadioEvent());
		fileButtons.add(scheduleRadioButton);


		fileButtons.add(addButton);
		fileButtons.add(deleteButton);
		fileButtons.add(submitButton);
		fileButtons.setBackground(Constants.MENU_BACKGROUND_COLOR);

		errorLabel = new JLabel();
		errorLabel.setFont(Constants.SMALL_FONT);
		errorLabel.setForeground(Color.RED);
		errorLabel.setBackground(Constants.MENU_BACKGROUND_COLOR);

		buttonsGroup.add(fileButtons);
		buttonsGroup.add(errorLabel);

		add(buttonsGroup);
		fileLabel = new JLabel();
		buttonsGroup.add(fileLabel);
		fileLabel.setFont(Constants.SMALL_FONT);
		fileLabel.setLocation(getX(), 50);
	}
	// Add functionality to the radio buttons =============================
	private class RadioEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equalsIgnoreCase("courseRadioButton")){
				state = FileState.COURSE;
				courseRadioButton.setSelected(true);
				requirementsRadioButton.setSelected(false);
				scheduleRadioButton.setSelected(false);
			}
			
			if(e.getActionCommand().equalsIgnoreCase("requirementsRadioButton")){
				state = FileState.REQUIREMENT;
				requirementsRadioButton.setSelected(true);
				courseRadioButton.setSelected(false);
				scheduleRadioButton.setSelected(false);
				
			}
			if(e.getActionCommand().equalsIgnoreCase("scheduleRadioButton")){
				state = FileState.SCHEDULE;
				scheduleRadioButton.setSelected(true);
				courseRadioButton.setSelected(false);
				requirementsRadioButton.setSelected(false);
			}
		}
		
	}
}
