 package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import CourseData.Data;
import GUI.About;
import GUI.AddButtonWindow;
import GUI.Instructions;
import GUI.Profile;
import GUI.Report;
import GUI.SideBar;
import GUI.TopBar;

import javax.swing.JRadioButton;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.ScrollPane;

import javax.swing.JTextField;

import java.awt.Choice;

import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.List;
import java.awt.Scrollbar;
import java.awt.Button;

/**
 * <p>
 * The main class of the program. Includes the primary container of the program
 * GUI, contains the primary data structure object, and handles action that
 * drives the program forward
 * </p>
 * @author matt
 */
@SuppressWarnings("serial")
public class Main extends JFrame {

	File selectedFile;
	Data dataObject;

	private JPanel mainPanel;

	private CardLayout contentSwitcher;
	private JPanel contentPanel;

	TopBar topBar;
	SideBar sideBar;

	JPanel aboutPanel;
	JPanel instructionsPanel;
	JPanel reportPanel;
	JPanel profilePanel;
	JRadioButton rdbtnTaken, rdbtnDesired;
	private JTextField txtUniqueId;

	public static AddCourseState state = AddCourseState.TAKEN;

	/**
	 * The main constructor. Creates the GUI and initializes the program.
	 * 
	 * @throws Exception
	 */
	public Main() {
		dataObject = new Data();

		setTitle(Constants.WINDOW_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// =========== Make and add frame and main panel ===============

		// moves the window to the middle of the screen because it's annoying
		// that it pops up in the corner
		setBounds(
				(int) ((java.awt.Toolkit.getDefaultToolkit().getScreenSize()
						.getWidth() / 2) - (Constants.MAIN_GUI_WIDTH / 2)),
						100, 0, 0);

		// set window size
		//setSize(Constants.MAIN_GUI_WIDTH, Constants.MAIN_GUI_HEIGHT);
		setPreferredSize(new Dimension(Constants.MAIN_GUI_WIDTH, Constants.MAIN_GUI_HEIGHT));
		setResizable(false);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		getContentPane().add(mainPanel);

		// =================== Make and add top row ======================
		topBar = new TopBar(new ButtonListener());
		topBar.addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		topBar.submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		topBar.deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		topBar.addButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		mainPanel.add(topBar, BorderLayout.NORTH);

		// =================== Make and add side bar =====================
		sideBar = new SideBar(new SideButtonListener());
		mainPanel.add(sideBar, BorderLayout.WEST);

		// set up Content Switcher to allow side bar to rotate through
		// content panels
		contentSwitcher = new CardLayout();
		contentPanel = new JPanel(contentSwitcher);
		contentPanel.setBorder(BorderFactory.createLineBorder(
				Constants.CONTENT_BACKGROUND_COLOR, 10));

		profilePanel = new Profile();
		aboutPanel = new About();
		instructionsPanel = new Instructions();


		// Add initial blank panel when program first starts
		JPanel newProfilePanel = new JPanel();
		// Profile Panel===================================================
		newProfilePanel.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		contentPanel.add(profilePanel,"PROFILE");
		profilePanel.setLayout(null);

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
		profilePanel.add(txtUniqueId);
		txtUniqueId.setColumns(10);

		// List to choose courses taken
		List list = new List();
		list.setBounds(94, 112, 414, 89);
		list.add("CSE 174 ");
		list.add("CSE 271");
		profilePanel.add(list);

		// Scroll Bar for courses taken
		Scrollbar scrollbar = new Scrollbar();
		scrollbar.setBounds(493, 112, 15, 89);
		profilePanel.add(scrollbar);

		// Label for the list of classes taken
		JLabel classesTakenLabel = new JLabel("Classes Taken");
		classesTakenLabel.setBounds(235, 90, 95, 16);
		profilePanel.add(classesTakenLabel);
		
		// List for Desired Classes
		List desiredClassesList = new List();
		desiredClassesList.setBounds(94, 266, 414, 89);
		desiredClassesList.add("CSE 102");
		desiredClassesList.add("CSE 278");
		profilePanel.add(desiredClassesList);
		
		// Drop down box for Major
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Major");
		comboBox.addItem("Computer Science");
		comboBox.addItem("Software Engineering");
		comboBox.setName("Major");
		comboBox.setBounds(374, 45, 134, 26);
		comboBox.revalidate();
		profilePanel.add(comboBox);

		//scroll bar for desired classes
		Scrollbar scrollbarDesiredClasses = new Scrollbar();
		scrollbarDesiredClasses.setBounds(493, 266, 15, 89);
		profilePanel.add(scrollbarDesiredClasses);

		//Label for desired classes list
		JLabel desiredClassesLabel = new JLabel("Desired Classes");
		desiredClassesLabel.setBounds(235, 251, 106, 16);
		profilePanel.add(desiredClassesLabel);

		// Radio buttons ================================================
		rdbtnTaken = new JRadioButton("Taken");
		rdbtnTaken.setBounds(61, 368, 78, 28);
		rdbtnTaken.setActionCommand("rdbtnTaken");
		rdbtnTaken.setSelected(true);
		rdbtnTaken.addActionListener(new RadioEvent());
		profilePanel.add(rdbtnTaken);


		rdbtnDesired = new JRadioButton("Desired");
		rdbtnDesired.setBounds(61, 408, 80, 26);
		rdbtnDesired.setActionCommand("rdbtnDesired");
		rdbtnDesired.setSelected(false);
		rdbtnDesired.addActionListener(new RadioEvent());
		profilePanel.add(rdbtnDesired);

		// addButtonWindow ================================================
		AddButtonWindow.PARENT.getContentPane().setPreferredSize(new Dimension(300, 300));
		AddButtonWindow.PARENT.setResizable(false);
		AddButtonWindow.PARENT.getContentPane().setLayout(null);

		JComboBox courseAddWindow = new JComboBox();
		courseAddWindow.setBounds(6, 6, 120, 27);
		//campusBox.setBounds(x, y, width, height);
		courseAddWindow.addItem("Course");
		courseAddWindow.addItem("CSE 102");
		courseAddWindow.addItem("CSE 174");
		courseAddWindow.addItem("CSE 201");
		courseAddWindow.addItem("CSE 271");
		courseAddWindow.addItem("CSE 274");
		courseAddWindow.addItem("CSE 278");
		AddButtonWindow.PARENT.getContentPane().add(courseAddWindow);

		JComboBox campusBox = new JComboBox();
		campusBox.setBounds(335, 6, 140, 27);
		campusBox.addItem("Location");
		campusBox.addItem("Oxford");
		campusBox.addItem("Hamilton");
		campusBox.addItem("MiddleTown");
		campusBox.addItem("All");
		AddButtonWindow.PARENT.getContentPane().add(campusBox);

		List listAddWindow = new List();
		listAddWindow.setBounds(10, 39, 480, 170);
		listAddWindow.add("Location |  CRN   | CourseTitle | Section | Professor | Time");
		listAddWindow.add("   O     |  57409 |   CSE 201   |    B    | Ann Sobel | 11:30A.M - 12:50P.M");
		AddButtonWindow.PARENT.getContentPane().add(listAddWindow);

		Scrollbar scrollbarAddWindow = new Scrollbar();
		scrollbarAddWindow.setBounds(250, 39, 15, 170);
		AddButtonWindow.PARENT.getContentPane().add(scrollbarAddWindow);

		//Accept button in the pop up window
		Button acceptWindowButton = new Button("Accept");
		acceptWindowButton.setBounds(9, 215, 84, 28);
		AddButtonWindow.PARENT.getContentPane().add(acceptWindowButton);

		// Cancel button in pop up window
		Button cancelWindowButton = new Button("Cancel");
		cancelWindowButton.setBounds(408, 215, 84, 28);
		cancelWindowButton.setActionCommand("cancelWindowButton");
		cancelWindowButton.addActionListener(new ButtonListener());
		AddButtonWindow.PARENT.getContentPane().add(cancelWindowButton);

		Label courseLabel = new Label("Full Course Name");
		courseLabel.setBounds(190, 10, 117, 17);
		AddButtonWindow.PARENT.getContentPane().add(courseLabel);

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
		profilePanel.add(addButton);

		Button removeButton = new Button("Remove");
		removeButton.setBackground(Color.WHITE);
		removeButton.setBounds(228, 408, 102, 28);
		profilePanel.add(removeButton);

		Button checkButton = new Button("Check");
		checkButton.setBounds(413, 368, 117, 28);
		profilePanel.add(checkButton);
		
		JButton profileSaveButton = new JButton("Save Profile");
		profileSaveButton.setBounds(413, 408, 117, 29);
		profilePanel.add(profileSaveButton);
		contentPanel.add(newProfilePanel, "BLANK");
		contentPanel.add(aboutPanel, "ABOUT");
		contentPanel.add(instructionsPanel, "INSTRUCTIONS");
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// pack and display the main GUI
		pack();
		setVisible(true);

		// initialize the report with our un-populated data object,
		// but don't display it
		fileInitialReport();
	}

	/**
	 * <p>
	 * Sets the alert text at the top of the GUI
	 * </p>
	 * <p>
	 * type == true: Black Text<br/>
	 * type == false: Red Text
	 * </p>
	 */
	private void alert(String text, boolean type) {
		if (type)
			topBar.errorLabel.setForeground(Color.BLACK);
		else
			topBar.errorLabel.setForeground(Color.RED);
		topBar.errorLabel.setText(text);
	}

	/**
	 * Clears the alert text at the top of the GUI
	 */
	private void clearAlert() {
		topBar.errorLabel.setText("");
	}
	/**
	 * <p>
	 * Files a new report from the current data object and displays it to the
	 * main GUI
	 * </p>
	 */
	private void fileReport() {
		if (reportPanel != null)
			contentSwitcher.removeLayoutComponent(reportPanel);
		reportPanel = new Report(dataObject);
		contentPanel.add(reportPanel, "REPORT");
		contentSwitcher.show(contentPanel, "REPORT");
	}

	/**
	 * initializes the report (to set associations) but doesn't display it
	 */
	private void fileInitialReport() {
		if (reportPanel != null)
			contentSwitcher.removeLayoutComponent(reportPanel);
		reportPanel = new Report(dataObject);
		contentPanel.add(reportPanel, "REPORT");
	}

	/**
	 * Listens for top bar button actions
	 * @author matt
	 */
	public class ButtonListener implements ActionListener {
		/**
		 * <p>
		 * Takes a button click from the top bar
		 * </p>
		 * <p>
		 * Includes logic to determine which button was clicked and respond
		 * appropriately
		 * </p>
		 * @Override
		 */
		public void actionPerformed(ActionEvent e) {
			// reset error label before every action
			clearAlert();
			// add button clicked, find add file to file loader

			if(e.getActionCommand().equalsIgnoreCase("cancelWindowButton")){
			
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

			if (e.getActionCommand().equalsIgnoreCase("addButton")) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					topBar.fileLabel.setText(selectedFile.getName());
					topBar.deleteButton.setEnabled(true);
					topBar.submitButton.setEnabled(true);
				} else
					alert("No file was selected", false);
			}
			// delete button selected, remove file and reset buttons

			if (e.getActionCommand().equalsIgnoreCase("deleteButton")) {
				selectedFile = null;
				topBar.fileLabel.setText("");
				topBar.deleteButton.setEnabled(false);
				topBar.submitButton.setEnabled(false);
			}
			// submit button selected, check file, process file, and run report
			if (e.getActionCommand().equalsIgnoreCase("submitButton")) {
				if (selectedFile == null || !selectedFile.canRead()) {
					alert("No file selected or cannot read file", false);
					return;
				}
				String result = "";
				switch (TopBar.state) {

				case COURSE : 

					result = dataObject.readNewCourseData(selectedFile);
					break;
				case REQUIREMENT :
					//add code
					break;
				case SCHEDULE : 
					//add code
					break;
				case NULL :
					//add code	
					break;
				case ERROR :
					//add code	
					break;

				}

				if (!result.replace("Error", "").equals(result))
					alert(result, false);
				else {
					fileReport();
					alert(result, true);
				}
				selectedFile = null;
				topBar.fileLabel.setText("");
				topBar.deleteButton.setEnabled(false);
				topBar.submitButton.setEnabled(false);
			}
		}
	}

	/**
	 * Listens for clicks on the side bar labels
	 * @author matt
	 */

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
	public class SideButtonListener extends MouseAdapter {
		/**
		 * Takes the action of a mouse click on a side bar button. Includes
		 * logic to determine which label was clicked and respond appropriately.
		 */
		public void mouseClicked(MouseEvent e) {
			// reset error label before every action
			clearAlert();
			if (e.getSource() == sideBar.profileButton)
				contentSwitcher.show(contentPanel, "PROFILE");
			if (e.getSource() == sideBar.aboutButton)
				contentSwitcher.show(contentPanel, "ABOUT");
			if (e.getSource() == sideBar.instructionsButton)
				contentSwitcher.show(contentPanel, "INSTRUCTIONS");
			if (e.getSource() == sideBar.reportButton)
				contentSwitcher.show(contentPanel, "REPORT");
		}
	}

	public static void main(String[] args) {
		new Main();
	}// end main
}
