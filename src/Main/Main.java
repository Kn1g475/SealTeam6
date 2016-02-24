package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CourseData.Data;
import GUI.About;
import GUI.Instructions;
import GUI.Report;
import GUI.SideBar;
import GUI.TopBar;

/**
 * <p>
 * The main class of the program. Includes the primary container of the program
 * GUI, contains the primary data structure object, and handles action that
 * drives the program forward
 * </p>
 * 
 * @author matt
 * 
 */
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

	/**
	 * The mian contructor. Creates the GUI and initializes the program.
	 * 
	 * @throws Exception
	 */
	public Main() throws Exception {

		dataObject = new Data();

		this.setTitle(Constants.WINDOW_TITLE);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// =========== Make and add frame and main panel ===============

		// moves the window to the middle of the screen because it's annoying
		// that it pops up in the corner
		this.setBounds(
				(int) ((java.awt.Toolkit.getDefaultToolkit().getScreenSize()
						.getWidth() / 2) - (Constants.MAIN_GUI_WIDTH / 2)),
				100, 0, 0);

		// set window size
		this.setPreferredSize(new Dimension(Constants.MAIN_GUI_WIDTH,
				Constants.MAIN_GUI_HEIGHT));

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		this.add(mainPanel);

		// =================== Make and add top row ======================
		topBar = new TopBar(new ButtonListener());
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
		aboutPanel = new About();
		instructionsPanel = new Instructions();

		// Add initial blank panel when program first starts
		JPanel blank = new JPanel();
		blank.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		contentPanel.add(blank, "BLANK");
		contentPanel.add(aboutPanel, "ABOUT");
		contentPanel.add(instructionsPanel, "INSTRUCTIONS");
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// pack and display the main GUI
		this.pack();
		this.setVisible(true);

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
	 * 
	 * @author matt
	 * 
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
		 * 
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

					alert("No file was selected", false);
				}

			}
			// delete butotn selected, remove file and reset buttons
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

				String result = dataObject.readNewCourseData(selectedFile);

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
	 * 
	 * @author matt
	 * 
	 */
	public class SideButtonListener extends MouseAdapter {

		/**
		 * Takes the action of a mouse click on a side bar button. Includes
		 * logic to determine which label was clicked and respond appropriately.
		 */
		public void mouseClicked(MouseEvent e) {

			// reset error label before every action
			clearAlert();

			if (e.getSource() == sideBar.aboutButton) {

				contentSwitcher.show(contentPanel, "ABOUT");

			}
			if (e.getSource() == sideBar.instructionsButton) {

				contentSwitcher.show(contentPanel, "INSTRUCTIONS");

			}
			if (e.getSource() == sideBar.reportButton) {

				contentSwitcher.show(contentPanel, "REPORT");

			}
		}

	}

	public static void main(String[] args) throws Exception {

		new Main();

	}// end main
}
