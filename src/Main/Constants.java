package Main;

import java.awt.Color;
import java.awt.Font;

/**
 * This is the configuration class of the program. It contains constants that
 * control many different elements of the program, such as window size, various
 * strings, and colors/design
 * 
 * @author matt
 * 
 */
public class Constants {
	
	public static final String TEAM_NAME = "Seal Team 6";

	public static final String COLUMNS_OF_DATABASE = "CRN,SUBJ,CRSE,SECTION,TITLE,DAYS,START_TIME,END_TIME,DAYS,INSTRUCTOR,MEETING_CATEGORY";
	public static final String COLUMNS_IN_DATABASE = "`CRN`,`SUBJ`,`CRSE`,`SECTION`,`TITLE`,`DAYS`,`START_TIME`,`END_TIME`,`DAYS`,`INSTRUCTOR`,`MEETING_CATEGORY`";
	public static final String COLUMNS_OF_REQUIREMENTS = "UniqueID,SUBJ,CRSE,TITLE,PREREQ,SNR,HRS,INSTR,TYPE";
	public static final String COLUMNS_IN_REQUIREMENTS = "`UniqueID`,`SUBJ`,`CRSE`,`TITLE`,`PREREQ`,`SNR`,`HRS`,`INSTR`,`TYPE`";
	
	public static final int MAIN_GUI_WIDTH = 800;
	public static final int MAIN_GUI_HEIGHT = 600;

	public static final int TOP_ROW_HEIGHT = 55;
	public static final int TOP_ROW_BORDER_THICKNESS = 3;
	public static final Color TOP_ROW_BORDER_COLOR = Color.BLACK;

	public static final Color MENU_BACKGROUND_COLOR = Color.LIGHT_GRAY;
	public static final Color CONTENT_BACKGROUND_COLOR = Color.WHITE;

	// TITLE INFORMATION
	public static final String TITLE = "Schedule Builder";
	public static final Font TITLE_FONT = new Font("Arial", Font.PLAIN,
			TOP_ROW_HEIGHT / 2);;
	public static final String MIAMI_ICON_NAME = "img/PlanB.png";

	public static final Font MAIN_FONT = new Font("Arial", Font.PLAIN,
			TOP_ROW_HEIGHT / 3);
	public static final Font SMALL_FONT = new Font("Arial", Font.PLAIN,
			TOP_ROW_HEIGHT / 4);
	public static final Font SMALL_ITALIC_FONT = new Font("Arial", Font.ITALIC,
			TOP_ROW_HEIGHT / 4);

	public static final String ABOUT_ICON_NAME = "img/about.png";
	public static final String INSTRUCTIONS_ICON_NAME = "img/instructions.png";
	public static final String EDIT_ICON_NAME = "img/edit.png";
	public static final String SEARCH_ICON_NAME = "img/search.png";
	public static final String REPORT_ICON_NAME = "img/report.png";

	public static final String MAIN_REPORT_LABEL = "<html>Exam times listed below.<br/>**Indicates exam slots with potential conflcits</html>";

	public static final Color FINAL_CONTAINS_CONFLICT_WARNING = Color.RED;

	public static final int SIDE_BAR_SPACING = 20;

	public static final String WINDOW_TITLE = TEAM_NAME + " - " + TITLE;

	public static final String PROFILE = "<html><h1>Profile</h1>"; // 
	
	public static final String ABOUT = "<html><h1>About</h1>"
			+ "<p>This program allows users at Miami University to insert a .csv file with course information "
			+ "and will generate a final report showing when the final exam times are scheduled."
			+ " The report will also show any possible conflicts where exam times overlap "
			+ "and cannot be taken at the same time.</p>"
			+ "<br><br><p><b>Creators:</b><br>Matt DePero<br>Brandon Bales<br>"
			+ "Emma Belanger<br>Ryan Potts</p>" + "<br><br><p>Fall 2015</p>"
			+ "</html>";

	public static final String INSTRUCTIONS = "<html><h1>Instructions</h1>"
			+ "<p>To see Full Report:<br><br>"
			+ "Click 'Add File' button and choose file.<br>"
			+ "To add a new file, click 'Add File' again.<br>"
			+ "To remove a file, click 'Remove'.<br>"
			+ "When files have been chosen click 'Submit'.<br><br>"
			+ "A report will be generated and will show the exam times.<br>"
			+ "To see the classes at that time, click on the button with the given time.<br>"
			+ "Any time with '***' next to it indicates an overlap in classes with the same exam time.</p>"
			+ "</html>";

	/**
	 * This is a static method that makes it easy to convert an integer based
	 * time value to a 12 hour type time string
	 * 
	 * @param time
	 * @return
	 */
	public static String timeToString(int time) {
		String amPM = "am";
		// handle am/pm
		if (time >= 1200) 
			amPM = "pm";
		// handle 12 hour time format
		if (time >= 1300)
			time -= 1200;
		// handle 12am (example, 0015 needs translated to 12:15am)
		if (time < 100)
			time += 1200;

		String minutes;
		if ((time - ((time / 100) * 100)) < 10)
			minutes = "0" + (time - ((time / 100) * 100));
		else 
			minutes = Integer.toString((time - ((time / 100) * 100)));

		return (time / 100) + ":" + minutes + amPM;
	}
}
