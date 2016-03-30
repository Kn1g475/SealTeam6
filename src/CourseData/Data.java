package CourseData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
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
	public List<Category> finalsCategories;
	/**
	 * Creates a new empty data structure
	 */
	public Data() {
		allClassList = new ArrayList<>();
		oxfClassList = new ArrayList<>();
		hamClassList = new ArrayList<>();
		midClassList = new ArrayList<>();
		updateCurrentList("Oxford");
		finalsCategories = new ArrayList<>();
	}
	/**
	 * Calls the static function that sets the categories for this data
	 * structure
	 */
	public void setCategories() {
		Errors.setCategories(currentList, this.finalsCategories);
	}
	/**
	 * calls the static function that flags conflicts in the data
	 * @throws InvlaidClassException
	 */
	public void findConflicts() throws InvalidClassException {
		Errors.displayError(currentList);
	}
	
	/**
	 * Processes a file and adds new entries to the data
	 * @param dataFile
	 * @return
	 */
	public String readNewCourseData() {
		try (Connection data = DriverManager.getConnection("jdbc:mysql://134.53.148.193/levysj","levysj-r","q8mYPAyUAHeeByQ4");
			 Statement stm = data.createStatement();
			) {
			// Checks the first line of the file to check if is a valid csv file or not
			ResultSet rset = stm.executeQuery("SELECT " + Constants.COLUMNS_IN_DATABASE + "FROM CSE_Course_Schedule_Spring_2016 WHERE 1");
			String[] columnArgs = Constants.COLUMNS_OF_DATABASE.split(",");
			String[] lineArgs = new String[columnArgs.length];
			ReadRow: while (rset.next()) {
				for(int i = 0; i < columnArgs.length; i++) {
					lineArgs[i] = rset.getString(columnArgs[i]);
				}
				// for each argument, read in data
				for (int i = 0; i < 10; i++) {
					// skip the line if any element is blank
					if (lineArgs[i].equals("")) {
						System.out.printf("Warning: a line in the file had blank elements, skipped: %s\n",dump(lineArgs));
						continue ReadRow;
					}
					// skip the line is unable to parse numerical values in line
					if ((i == 6 || i == 7) && !lineArgs[i].matches("\\d+")) {
						System.out.printf("Warning: unable to parse a start/end time for a line, skipped: %s\n", dump(lineArgs));
						continue ReadRow;
					}
				}
				// Note: meeting days is not part of checking equality
				Class tempClass = new Class(lineArgs);
				if(!allClassList.contains(tempClass))
					allClassList.add(tempClass);
				else {
					// if duplicate class has different meeting times, add them
					allClassList.get(allClassList.indexOf(tempClass)).addMeetingDays(lineArgs[5]);
					System.out.printf("Note: Duplicate class detected, added meeting days and skipped: %s\n", tempClass);
				}
			}
			sortClasses();
			rset.close();
			stm.close();
			data.close();
		} catch (InvalidClassException e) {
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
	 * Dumps the data structure to a string variable
	 */
	public String toString() {
		StringBuilder ret = new StringBuilder();

		ret.append("\n+----------------------------+\n");
		ret.append("|         Data Output        |\n");
		ret.append("+----------------------------+\n");

		Collections.sort(allClassList);
		ret.append(String.format("Classes:\t%s\n", allClassList));
		ret.append(String.format("Class Count: %d\n", allClassList.size()));

		return ret.toString();
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
	
	private String dump(String[] array) {
		StringBuilder br = new StringBuilder();
		for(String ele : array) {
			br.append(ele + '\t');
		}
		return br.toString();
		
	}
}