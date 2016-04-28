package CourseData;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import CourseData.Class;
import Main.Constants;
/**
 * Handles getting data from certain sources
 * @author Sam Levy
 */
public class Data {
	/**
	 * Connects to the Database and gets the Major Requirements for the specified major
	 * @param major
	 * @return
	 */
	public static Map<Course, Requirement> readNewRequirementData(String major) {
		//create storage for requirements
		Map<Course, Requirement> degreeRequirements = new HashMap<>();
		//determine the Major table
		String[] split = major.split(" ");
		String use = Character.toString(split[0].charAt(0)) + Character.toString(split[1].charAt(0));
		try{
			//Connect to database
			DatabaseConnector connector= new DatabaseConnector();
			//Get all the requirements information from the table
			degreeRequirements.putAll(connector.getRequrements("SELECT " + Constants.COLUMNS_IN_REQUIREMENTS + "FROM " + use + "_Requirements_2015_2016 WHERE 1"));
			connector.close();
		} catch(SQLTimeoutException e){
			e.printStackTrace();
			System.err.println("Error: Connection Timeout!!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error: Could not connect to Database!!");
		}
		return degreeRequirements;
	}
	/**
	 * Gets the Class information from the Specified Table
	 * @param table
	 * @return
	 */
	public static List<Class> readNewCourseData(String table) {
		//Create Storage for the class information
		List<Class> allClassList = new ArrayList<>();
		try{
			//connect to database
			DatabaseConnector connector= new DatabaseConnector();
			//get class information from the specified table
			allClassList.addAll(connector.getClasses("SELECT " + Constants.COLUMNS_IN_DATABASE + "FROM " + table + " WHERE 1"));
			connector.close();
		} catch(SQLTimeoutException e){
			e.printStackTrace();
			System.err.println("Error: Connection Timeout!!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error: Could not connect to Database!!");
		}
		return allClassList;
	}
	/**
	 * Gets all the Courses available 
	 * @param classes
	 * @return
	 */
	public static List<Course> getCourses(List<Class> classes, Set<Course> course) {
		List<Course> courses = new ArrayList<>();
		for (Course c : course)
			courses.add(c);
		for (Class c : classes)
			if (!courses.contains(c.getCourse()))
				courses.add(c.getCourse());
		return courses;
	}
	
	/**
	 * Hard coded way of setting the final exam slots
	 * @param allClassList
	 * @param cats
	 */
	public static void setCategories(List<Class> classList, List<Category> cats) {
		if (cats.size() == 0) {
			/*
			 * +---------------------------------------------+ | Hard coding
			 * categories for testing purposes |
			 * +---------------------------------------------+
			 */
			Category setCat = new Category(0, 1000, 800, "T", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1000, 1130, 800, "R", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1130, 1300, 1245, "T", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1300, 1430, 1245, "R", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1430, 1600, 1500, "T", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1600, 1730, 1500, "R", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1730, "T", "TR");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1945, "T", "T");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1945, "R", "R");
			cats.add(setCat);

			setCat = new Category(0, 1000, 800, "W", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1000, 1130, 1015, "F", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1130, 1300, 1015, "W", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1300, 1430, 1245, "M", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1430, 1600, 1500, "W", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1600, 1730, 1500, "M", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1730, "W", "MW");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1945, "M", "M");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1945, "W", "W");
			cats.add(setCat);
		}
		// loop through classes and set categories
		for (int i = 0; i < classList.size(); i++) {
			for (int j = 0; j < cats.size(); j++) {
				if (cats.get(j).matches(classList.get(i)))
					classList.get(i).setCategory(cats.get(j));
			}
		}
	}
}