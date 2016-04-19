package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;


import CourseData.Category;
import CourseData.Profile;
import CourseData.TimeInterval;
import Main.Constants;

/**
 * This panel represents a generated report and allows for navigation within the
 * report
 * @author matt
 */
@SuppressWarnings("serial")
public class Report extends JPanel {
	private Report report = this;
	private CardLayout contentSwitcher;
	/**
	 * A new report is generated from a given data object. Any changes to the
	 * data object require that the report be recreated
	 * 
	 * @param dataObject
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
		} catch (Exception e) {
			this.add(new JLabel("An Error occured searching for conflicts within the file"));
			return;
		}

		String[] longDays = { "Monday", "Tuesday", "Wendesday", "Thursday", "Friday" };
		// create report content
		contentSwitcher = new CardLayout();
		setLayout(contentSwitcher);


		JPanel mainReport = new JPanel();
		mainReport.setLayout(null);
		mainReport.setBackground(Constants.CONTENT_BACKGROUND_COLOR);


		TableModel weekModel = new DefaultTableModel(21,6);
		WeekTable weekSchedule = new WeekTable(weekModel, profile.getSchedule());
		weekSchedule.setBounds(0, 30, 630, 460);
		weekSchedule.setCellSelectionEnabled(false);
		weekSchedule.setBorder(new LineBorder(Color.BLACK,1));

		for (int i = 0; i < weekSchedule.getRowCount(); i++) {
			if( i == 0)
				weekSchedule.setRowHeight(i, 20);
			else 
				weekSchedule.setRowHeight(i, 440 / 20);
		}
		weekSchedule.getColumnModel().getColumn(0).setPreferredWidth(35);
		for (int i = 1; i < weekSchedule.getColumnCount(); i++)
			weekSchedule.getModel().setValueAt(longDays[i - 1], 0, i);
		


		mainReport.add(weekSchedule);

		JButton mainLeft = new JButton("<--");
		mainLeft.setBounds(0, 0, 50, 30);
		mainLeft.addActionListener(new Switcher());
		mainReport.add(mainLeft);

		JButton mainRight = new JButton("-->");
		mainRight.setBounds(580, 0, 50, 30);
		mainRight.addActionListener(new Switcher());
		mainReport.add(mainRight);

		JLabel mainTitle = new JLabel("Week At a Glance");
		mainTitle.setBounds(290, 0,100,30);
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

		TableModel conflictModel = new DefaultTableModel(13,6);
		JTable finalSchedule = new JTable(conflictModel) {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
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
		int time = 800;
		for (int i = 1; i < finalSchedule.getRowCount(); i++) {
			finalSchedule.getModel().setValueAt(Constants.timeToString(time), i, 0);
			switch (i) {
			case 1: case 3: case 5: case 7: case 9: case 11:
				time += 200;
				break;
			case 2: case 6: 
				time += 15;
				break;
			case 4: case 8:
				time +=30;
				break;
			}
			if (time % 100 == 60)
				time += 40;
			if (time % 100 == 90)
				time += 10;
		}
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
		conflictTitle.setBounds(290, 0,100,30);
		conflictReport.add(conflictTitle);


		for (Category cat : profile.finalsCategories) {

			Collections.sort(cat.classesInThisCategory);
			String finalsInfo = "";
			for (CourseData.Class c : cat.classesInThisCategory) 
				finalsInfo += c.CoursenSection() + "\n"; 
			finalSchedule.getModel().setValueAt(finalsInfo,categoryToFinalsRow(cat),dayToWeekColumn(cat.finalDay.charAt(0)));
		}
		add(conflictReport,"CONFLICT");
		
		
		JPanel infoReport = new JPanel();
	}
	private int dayToWeekColumn(char day) {
		switch (day) {
		case 'M': return 1;
		case 'T': return 2;
		case 'W': return 3;
		case 'R': return 4;
		case 'F': return 5;
		default: return 0;
		}
	}

	private int categoryToFinalsRow(Category cat) {
		switch (cat.finalTime) {
		case 800: return 1;
		case 1015: return 3;
		case 1245: return 5;
		case 1500: return 7;
		case 1730: return 9;
		case 1945: return 11;
		default: return 0;
		}
	}
	
	private class Switcher implements ActionListener {
		@Override
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
