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
	private List<Class> allClassList;
	private List<Class> oxfClassList;
	private List<Class> hamClassList;
	private List<Class> midClassList;
	public List<Class> currentList;
	private List<Requirement> degreeRequirements;
	public List<Category> finalsCategories;
	/**
	 * Creates a new empty data structure
	 */
	public Data() {
		allClassList = new ArrayList<>();
		oxfClassList = new ArrayList<>();
		hamClassList = new ArrayList<>();
		midClassList = new ArrayList<>();
		degreeRequirements = new ArrayList<>();
		updateCurrentList("All");
		finalsCategories = new ArrayList<>();
	}
	/**
	 * Calls the static function that sets the categories for this data
	 * structure
	 */
	public void setCategories() {
		setCategories(currentList, this.finalsCategories);
	}
	/**
	 * calls the static function that flags conflicts in the data
	 * @throws InvlaidClassException
	 */

	public String readNewRequirementData() {
		try{
			DatabaseConnector connector= new DatabaseConnector();
			degreeRequirements.addAll(connector.getRequrements("SELECT " + Constants.COLUMNS_IN_DATABASE + "FROM CSE_Requirements WHERE 1"));
			sortClasses();
			connector.close();
		} catch (InvalidClassException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch(SQLTimeoutException e){
			e.printStackTrace();
			return "Error: Connection Timeout!!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error: Could not connect to Database!!";
		}
		return "Finished Parsing";
	}
	/**
	 * Processes a file and adds new entries to the data
	 * @param dataFile
	 * @return
	 */
	public String readNewCourseData() {
		try{
			DatabaseConnector connector= new DatabaseConnector();
			allClassList.addAll(connector.getClasses("SELECT " + Constants.COLUMNS_IN_DATABASE + "FROM CSE_Course_Schedule_Spring_2016 WHERE 1"));
			sortClasses();
			connector.close();
		} catch (InvalidClassException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch(SQLTimeoutException e){
			e.printStackTrace();
			return "Error: Connection Timeout!!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error: Could not connect to Database!!";
		}
		return "Finished Parsing";
	}
	/**
	 * Sorts the class into <code>ArrayList</code> based on there location
	 * @throws InvalidClassException
	 */
	private void sortClasses() throws InvalidClassException{
		for (Class clas : allClassList) {
			if (clas.getLocation().equals("Oxford")) {
				oxfClassList.add(clas);
			} else if(clas.getLocation().equals("Hamilton")) {
				hamClassList.add(clas);
			} else if(clas.getLocation().equals("MiddleTown")) {
				midClassList.add(clas);
			} else {
				throw new InvalidClassException(String.format("%s: Class does not exist within one of the Miami Campuses", clas.toString()));
			}
		}
	}
	/**
	 * Sets the Current List of classes to a list based on a location of classes
	 * @param place - location of Miami University Campuses
	 * @return true if successful, false if not successful
	 */
	public boolean updateCurrentList(String place) {
		boolean finished = false;
		if (place.equals("Oxford")) {
			currentList = oxfClassList;
			finished = true;
		} else if (place.equals("Hamilton")) {
			currentList = hamClassList;
			finished = true;
		} else if (place.equals("MiddleTown")) {
			currentList = midClassList;
			finished = true;
		} else if (place.equals("All")) {
			currentList = allClassList;
			finished = true;
		}
		return finished;
	}
	
	public List<Requirement> getMajorRequirements(String major) {
		List<Requirement> majorRequirements = new ArrayList<>();
		for (Requirement req : degreeRequirements) {
			if (req.getUniqueId().contains(major))
					majorRequirements.add(req);
		}
		return majorRequirements;
	}
	
	
	
	/**
	 * Hard coded way of setting the final exam slots
	 * @param allClassList
	 * @param cats
	 * @throws Exception
	 */
	private static void setCategories(List<Class> classList, List<Category> cats) {
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