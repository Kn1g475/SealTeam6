package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import CourseData.Category;
import CourseData.Profile;
import Exceptions.InvalidClassException;
import CourseData.Class;
import Main.Constants;

/**
 * This panel represents a generated report and allows for navigation within the
 * report
 * @author Sam Levy
 */
@SuppressWarnings("serial")
public class Report extends JPanel {
	private Report report = this;
	private CardLayout contentSwitcher;
	/**
	 * A new report is generated from a given profile. Any changes to the
	 * data object require that the report be recreated
	 * 
	 * @param profile
	 */
	public Report(Profile profile) {
		setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		if (profile.getSchedule().isEmpty()) {
			this.add(new JLabel("No data to report on"));
			return;
		}
		// Processes the data object to ensure all data is up to date for the
		// report
		try {
			profile.findConflicts();
		} catch (InvalidClassException e) {
			this.add(new JLabel("An Error occured searching for conflicts within the file"));
			return;
		}
		String[] longDays = { "Monday", "Tuesday", "Wendesday", "Thursday", "Friday" };
		// create report content
		contentSwitcher = new CardLayout();
		setLayout(contentSwitcher);
		
		// create schedule window
		JPanel mainReport = new JPanel();
		mainReport.setLayout(null);
		mainReport.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		
		// make week at a glance calendar
		TableModel weekModel = new DefaultTableModel(29,6);
		WeekTable weekSchedule = new WeekTable(weekModel, profile.getSchedule());
		weekSchedule.setBounds(0, 30, 630, 460);
		weekSchedule.setCellSelectionEnabled(false);
		weekSchedule.setBorder(new LineBorder(Color.BLACK,1));
		for (int i = 0; i < weekSchedule.getRowCount(); i++) {
				weekSchedule.setRowHeight(i, 20);
		}
		weekSchedule.getColumnModel().getColumn(0).setPreferredWidth(35);
		for (int i = 0; i < weekSchedule.getColumnCount(); i++)
			if(i == 0)
				weekSchedule.getColumnModel().getColumn(i).setHeaderValue("");
			else
				weekSchedule.getColumnModel().getColumn(i).setHeaderValue(longDays[i - 1]);
		
		JScrollPane calanderScroll = new JScrollPane(weekSchedule);
		calanderScroll.setBounds(0, 30, 630, 460);
		mainReport.add(calanderScroll);

		JButton mainLeft = new JButton("<--");
		mainLeft.setBounds(0, 0, 50, 30);
		mainLeft.addActionListener(new Switcher());
		mainReport.add(mainLeft);

		JButton mainRight = new JButton("-->");
		mainRight.setBounds(580, 0, 50, 30);
		mainRight.addActionListener(new Switcher());
		mainReport.add(mainRight);

		JLabel mainTitle = new JLabel("Week At a Glance");
		mainTitle.setBounds(290, 0,300,30);
		mainReport.add(mainTitle);

		JPanel reportLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		reportLabel.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		reportLabel.add(new JLabel(Constants.MAIN_REPORT_LABEL));
		mainReport.add(reportLabel);
		add(mainReport, "WEEK SCHEDULE");

		// make a panel for each final exam time and add them to the content switcher.
		JPanel conflictReport = new JPanel();
		conflictReport.setLayout(null);
		conflictReport.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		// make finals week calendar
		TableModel conflictModel = new DefaultTableModel(13,6);
		ConflictTable finalSchedule = new ConflictTable(conflictModel, profile.finalsCategories);
		finalSchedule.setBounds(0, 30, 630, 460);
		finalSchedule.setCellSelectionEnabled(false);
		finalSchedule.setBorder(new LineBorder(Color.BLACK,1));

		for (int i = 0; i < finalSchedule.getRowCount(); i++) {
			if( i == 0)
				finalSchedule.setRowHeight(i, 20);
			else 
				finalSchedule.setRowHeight(i, 440 / 12);
		}
		finalSchedule.getColumnModel().getColumn(0).setPreferredWidth(20);
		for (int i = 1; i < finalSchedule.getColumnCount(); i++)
			finalSchedule.getModel().setValueAt(longDays[i - 1], 0, i);
		conflictReport.add(finalSchedule);

		JButton conflictLeft = new JButton("<--");
		conflictLeft.setBounds(0, 0, 50, 30);
		conflictLeft.addActionListener(new Switcher());
		conflictReport.add(conflictLeft);

		JButton conflictRight = new JButton("-->");
		conflictRight.setBounds(580, 0, 50, 30);
		conflictRight.addActionListener(new Switcher());
		conflictReport.add(conflictRight);

		JLabel conflictTitle = new JLabel("Finals Schedule");
		conflictTitle.setBounds(290, 0,300,30);
		conflictReport.add(conflictTitle);

		add(conflictReport,"CONFLICT");
		
		// make information panel
		JPanel infoPanel = new JPanel(null);
		infoPanel.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		
		JTextPane catPanel = new JTextPane();
		catPanel.setBounds(0, 30, 630, 460);
		catPanel.setEditable(false);
		catPanel.setContentType("text/html");
		catPanel.setBackground(Constants.CONTENT_BACKGROUND_COLOR);
		// get warnings
		String content = "<html><h1>Info</h1></br><h3>Warnings:</h3><table>";
		for (String warn : profile.Warnings) {
			content += "<tr><td>" + warn + "</td></tr>";
		}
		//get course information
		content += "</table></br><h3>Class Infomation: </h3></br><table>";
		for (Category cat : profile.finalsCategories) {
			Collections.sort(cat.classesInThisCategory);
			for (Class c : cat.classesInThisCategory) {
				content += c.toStringReportHTMLRow();
			}
		}
		content += "</table></html>";
		catPanel.setText(content);
		
		JScrollPane scrollWrapper = new JScrollPane(catPanel);
		scrollWrapper.setBounds(0, 30, 630, 460);
		infoPanel.add(scrollWrapper);
		
		JButton infoLeft = new JButton("<--");
		infoLeft.setBounds(0, 0, 50, 30);
		infoLeft.addActionListener(new Switcher());
		infoPanel.add(infoLeft);

		JButton infoRight = new JButton("-->");
		infoRight.setBounds(580, 0, 50, 30);
		infoRight.addActionListener(new Switcher());
		infoPanel.add(infoRight);
		
		add(infoPanel,"INFO");
	}
	
	private class Switcher implements ActionListener {
		@Override
		/**
		 * switches the panels back and forth
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("-->")) {
				contentSwitcher.next(report);
			}
			if (e.getActionCommand().equals("<--")) {
				contentSwitcher.previous(report);
			}
		}
	}
}
