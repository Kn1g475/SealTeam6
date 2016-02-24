package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import CourseData.Category;
import CourseData.Class;
import CourseData.Data;
import Main.Constants;

/**
 * This panel represents a generated report and allows for navigation within the
 * report
 * 
 * @author matt
 * 
 */
public class Report extends JPanel {

	Report report = this;

	CardLayout contentSwitcher;

	/**
	 * A new report is generated from a given data object. Any changes to the
	 * data object require that the report be recreated
	 * 
	 * @param dataObject
	 */
	public Report(Data dataObject) {

		this.setBackground(Constants.CONTENT_BACKGROUND_COLOR);

		if (dataObject.classList.isEmpty()) {
			this.add(new JLabel("No data to report on"));
			return;
		}

		// Processes the data object to ensure all data is up to date for the
		// report
		try {
			dataObject.setCategories();
			dataObject.findConflicts();
		} catch (Exception e) {

			this.add(new JLabel(
					"An Error occured searching for conflicts within the file"));
			return;
		}

		// create report content
		contentSwitcher = new CardLayout();
		this.setLayout(contentSwitcher);

		JPanel mainReport = new JPanel();
		mainReport.setLayout(new BoxLayout(mainReport, BoxLayout.PAGE_AXIS));
		mainReport.setBackground(Constants.CONTENT_BACKGROUND_COLOR);

		JPanel reportLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		reportLabel.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		reportLabel.add(new JLabel(Constants.MAIN_REPORT_LABEL));
		mainReport.add(reportLabel);

		String[] days = new String[] { "M", "T", "W", "R", "F" };
		String[] longDays = new String[] { "Monday", "Tuesday", "Wendesday",
				"Thursday", "Friday" };

		// For each day of the week, get all the finals for that day and add
		// them as time buttons. This generates the "home page" for the report
		for (int i = 0; i < days.length; i++) {

			JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
			header.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
			JLabel dayOfWeek = new JLabel(longDays[i]);
			dayOfWeek.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
			dayOfWeek.setFont(Constants.MAIN_FONT);
			header.add(dayOfWeek);
			mainReport.add(header);

			JPanel finalsOfWeek = new JPanel(new FlowLayout(FlowLayout.LEFT));
			finalsOfWeek.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
			for (Category cat : dataObject.finalsCategories) {

				if (cat.finalDay.equalsIgnoreCase(days[i])) {

					JButton day = new JButton(
							Constants.timeToString(cat.finalTime)
									+ ((cat.hasConflicts) ? "**" : ""));
					if (cat.hasConflicts) {
						day.setBackground(Constants.FINAL_CONTAINS_CONFLICT_WARNING);
					}
					day.addActionListener(new ReportSwitcher());
					day.setActionCommand(cat.toString());

					finalsOfWeek.add(day);

				}

			}

			mainReport.add(finalsOfWeek);

		}

		this.add(mainReport, "MAIN");

		// make a panel for each final exam time and add them to the content switcher.
		for (Category cat : dataObject.finalsCategories) {

			JTextPane catPanel = new JTextPane();
			catPanel.setEditable(false);
			catPanel.setContentType("text/html");
			catPanel.setBackground(Constants.CONTENT_BACKGROUND_COLOR);

			String content = "<html>" + "<h1>" + cat.toString()
					+ "</h1> <table>";

			Collections.sort(cat.classesInThisCategory);

			for (Class c : cat.classesInThisCategory) {

				content += c.toStringReportHTMLRow();

			}

			content += "</table></html>";

			catPanel.setText(content);

			JScrollPane scrollWrapper = new JScrollPane(catPanel);
			scrollWrapper.setBackground(Constants.CONTENT_BACKGROUND_COLOR);

			JPanel paneWrapper = new JPanel(new BorderLayout());
			paneWrapper.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
			paneWrapper.add(scrollWrapper, BorderLayout.CENTER);

			JPanel backButtonHolder = new JPanel();
			backButtonHolder.setLayout(new BoxLayout(backButtonHolder,
					BoxLayout.LINE_AXIS));
			backButtonHolder.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
			JButton backButton = new JButton("Back");
			backButton.addActionListener(new BackButtonListener());
			backButtonHolder.add(Box.createHorizontalGlue());
			backButtonHolder.add(backButton);

			paneWrapper.add(backButtonHolder, BorderLayout.SOUTH);

			this.add(paneWrapper, cat.toString());

		}

	}

	/**
	 * Switches the report to display content based on which button was clicked
	 * 
	 * @author matt
	 * 
	 */
	public class ReportSwitcher implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			contentSwitcher.show(report, e.getActionCommand());

		}

	}

	/**
	 * Switches to the main report when a back button is clicked
	 * 
	 * @author matt
	 * 
	 */
	public class BackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			contentSwitcher.show(report, "MAIN");

		}

	}

}
