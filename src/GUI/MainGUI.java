package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import CourseData.Data;
import CourseData.Profile;
import Main.Constants;

/**
 * <p>
 * The main class of the program. Includes the primary container of the program
 * GUI, contains the primary data structure object, and handles action that
 * drives the program forward
 * </p>
 * @author matt
 */
@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	File selectedFile;

	private Profile profile;

	private JPanel mainPanel;

	private CardLayout contentSwitcher;
	private JPanel contentPanel;

	private TopBar topBar;
	private SideBar sideBar;

	private JPanel aboutPanel;
	private Schedule schedulePanel;
	private JPanel instructionsPanel;
	private JPanel reportPanel;
	private ProfileGUI profilePanel;


	/**
	 * The main constructor. Creates the GUI and initializes the program.
	 * 
	 * @throws Exception
	 */
	public MainGUI() {
		profile = new Profile();
		this.setIconImage(new ImageIcon("img/Icon.png").getImage());
		setTitle(Constants.WINDOW_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// =========== Make and add frame and main panel ===============

		// moves the window to the middle of the screen because it's annoying
		// that it pops up in the corner
		setBounds((int)((java.awt.Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth() / 2) - (Constants.MAIN_GUI_WIDTH / 2)), 100, 0, 0);

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

		mainPanel.add(topBar, BorderLayout.NORTH);

		// =================== Make and add side bar =====================
		sideBar = new SideBar(new SideButtonListener());
		mainPanel.add(sideBar, BorderLayout.WEST);

		// set up Content Switcher to allow side bar to rotate through content panels
		contentSwitcher = new CardLayout();
		contentPanel = new JPanel(contentSwitcher);
		contentPanel.setBorder(BorderFactory.createLineBorder(
				Constants.CONTENT_BACKGROUND_COLOR, 10));

		profilePanel = new ProfileGUI(profile, new ToScheduleButtonListener());
		schedulePanel = new Schedule(profile, new CheckButton()); 
		aboutPanel = new About();
		instructionsPanel = new Instructions();

		contentPanel.add(profilePanel,"PROFILE");
		contentPanel.add(schedulePanel,"SCHEDULE"); 
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
		reportPanel = new Report(profile);
		contentPanel.add(reportPanel, "REPORT");
		contentSwitcher.show(contentPanel, "REPORT");
	}

	/**
	 * initializes the report (to set associations) but doesn't display it
	 */
	private void fileInitialReport() {
		if (reportPanel != null)
			contentSwitcher.removeLayoutComponent(reportPanel);
		reportPanel = new Report(profile);
		contentPanel.add(reportPanel, "REPORT");
	}

	private String errorDump(List<String> err) {
		StringBuilder sb = new StringBuilder();
		sb.append("Errors: \n");
		for (String error : err) {
			sb.append(String.format("\t%s\n", error.substring(8)));
		}
		return sb.toString();
	}


	public class CheckButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equalsIgnoreCase("Check")) {
				if (!profile.checkFeasibility())
					JOptionPane.showMessageDialog(schedulePanel.parent, errorDump(profile.Errors), "Oops", JOptionPane.ERROR_MESSAGE);
				else
					fileReport();
			}
		}

	}
	private void clearFile() {
		selectedFile = null;
		topBar.fileLabel.setText("");
		topBar.deleteButton.setEnabled(false);
		topBar.submitButton.setEnabled(false);
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
			if (e.getActionCommand().equalsIgnoreCase("addButton")) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					topBar.fileLabel.setText(selectedFile.getName());
					topBar.deleteButton.setEnabled(true);
					topBar.submitButton.setEnabled(true);
				} else {
					clearFile();
					alert("No file was selected", false);
				}

			}
			// delete button selected, remove file and reset buttons

			if (e.getActionCommand().equalsIgnoreCase("deleteButton")) {
				clearFile();
			}
			// submit button selected, check file, process file, and run report
			if (e.getActionCommand().equalsIgnoreCase("submitButton")) {
				if (selectedFile == null || !selectedFile.canRead()) {
					alert("No file selected or cannot read file", false);
					return;
				}
				String result = "";
				result = profile.readProfile(selectedFile);
				if (!result.replace("Error", "").equals(result))
					alert(result, false);
				else {
					//fileReport();
					profileUpload();
					alert(result, true);
				}
				clearFile();
			}
		}
	}
	private void profileUpload() {
		if (profilePanel != null)
			contentSwitcher.removeLayoutComponent(profilePanel);
		profilePanel = new ProfileGUI(profile, new ToScheduleButtonListener());
		contentPanel.add(profilePanel, "PROFILE");
		if (schedulePanel != null)
			contentSwitcher.removeLayoutComponent(schedulePanel);
		schedulePanel = new Schedule(profile, new CheckButton());
		contentPanel.add(schedulePanel,"SCHEDULE"); 
		contentSwitcher.show(contentPanel, "PROFILE");
	}
	/**
	 * Listens for clicks on the side bar labels
	 * @author matt
	 */
	private class SideButtonListener extends MouseAdapter {
		/**
		 * Takes the action of a mouse click on a side bar button. Includes
		 * logic to determine which label was clicked and respond appropriately.
		 */
		public void mouseClicked(MouseEvent e) {
			// reset error label before every action
			clearAlert();
			if (e.getSource() == sideBar.profileButton)
				contentSwitcher.show(contentPanel, "PROFILE");
			if(e.getSource() == sideBar.scheduleButton) {
				if (profilePanel.allSelected()) {
					profile.setCurYear(profilePanel.curYear);
					profile.setUniqueID(profilePanel.uniqueId);
					profile.setHours(profilePanel.hours);

					if(schedulePanel.classes.isEmpty())
						schedulePanel.classes = Data.readNewCourseData(profilePanel.semester);
					if(profile.majorReq.isEmpty() || !profile.getMajor().equals(profilePanel.major)) {
						profile.setMajor(profilePanel.major);
						profile.majorReq = Data.readNewRequirementData(profile.getMajor());
					}
					schedulePanel.courses = Data.getCourses(schedulePanel.classes, profile.majorReq.keySet());
					contentSwitcher.show(contentPanel, "SCHEDULE");
				} else {
					JOptionPane.showMessageDialog(schedulePanel.parent, "You need to fill out all the profile information", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			} 
			if (e.getSource() == sideBar.aboutButton)
				contentSwitcher.show(contentPanel, "ABOUT");
			if (e.getSource() == sideBar.instructionsButton)
				contentSwitcher.show(contentPanel, "INSTRUCTIONS");
			if (e.getSource() == sideBar.reportButton)
				contentSwitcher.show(contentPanel, "REPORT");
		}
			
		}
	public class ToScheduleButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equalsIgnoreCase("toSchedule")){
				if (profilePanel.allSelected()) {
					profile.setCurYear(profilePanel.curYear);
					profile.setUniqueID(profilePanel.uniqueId);
					profile.setHours(profilePanel.hours);

					if(schedulePanel.classes.isEmpty())
						schedulePanel.classes = Data.readNewCourseData(profilePanel.semester);
					if(profile.majorReq.isEmpty() || !profile.getMajor().equals(profilePanel.major)) {
						profile.setMajor(profilePanel.major);
						profile.majorReq = Data.readNewRequirementData(profile.getMajor());
					}
					schedulePanel.courses = Data.getCourses(schedulePanel.classes, profile.majorReq.keySet());
					contentSwitcher.show(contentPanel, "SCHEDULE");
				} else {
					JOptionPane.showMessageDialog(schedulePanel.parent, "You need to fill out all the profile information", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
}
