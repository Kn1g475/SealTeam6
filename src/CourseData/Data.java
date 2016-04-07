package CourseData;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;

import CourseData.Class;
import Main.Constants;
import Exceptions.*;
/**
 * The container class for the data structure that holds all class data
 * @author matt
 */
public class Data {
	public List<Category> finalsCategories;
	/**
	 * calls the static function that flags conflicts in the data
	 * @throws InvlaidClassException
	 */

	public static List<Requirement> readNewRequirementData() {
		List<Requirement> degreeRequirements = new ArrayList<>();;
		try{
			DatabaseConnector connector= new DatabaseConnector();
			degreeRequirements.addAll(connector.getRequrements("SELECT " + Constants.COLUMNS_IN_REQUIREMENTS + "FROM CSE_Requirements WHERE 1"));
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
	 * Processes a file and adds new entries to the data
	 * @param dataFile
	 * @return
	 */
	public static List<Class> readNewCourseData(String table) {
		List<Class> allClassList = new ArrayList<>();
		try{
			DatabaseConnector connector= new DatabaseConnector();
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
	public static List<Course> getCourses(List<Class> classes) {
		List<Course> courses = new ArrayList<>();
		for (Class c : classes)
			if (!courses.contains(c.getCourse()))
				courses.add(c.getCourse());
		return courses;
	}
	/*public List<Requirement> getMajorRequirements(String major) {
		List<Requirement> majorRequirements = new ArrayList<>();
		for (Requirement req : degreeRequirements) {
			if (req.getUniqueId().contains(major))
					majorRequirements.add(req);
		}
		return majorRequirements;
	}*/
	
	/**
	 * Hard coded way of setting the final exam slots
	 * @param allClassList
	 * @param cats
	 * @throws Exception
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
		// loop through data object and set categories
		for (int i = 0; i < classList.size(); i++) {
			for (int j = 0; j < cats.size(); j++) {
				if (cats.get(j).matches(classList.get(i)))
					classList.get(i).setCategory(cats.get(j));
			}
		}
	}
}