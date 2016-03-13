package CourseData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import Main.Constants;
import Exceptions.*;
/**
 * The container class for the data structure that holds all class data
 * @author matt
 */
public class Data {
	public ArrayList<Class> allClassList;
	public ArrayList<Class> oxfClassList;
	public ArrayList<Class> hamClassList;
	public ArrayList<Class> midClassList;
	public ArrayList<Class> currentList;
	public ArrayList<Category> finalsCategories;
	private Map<String,ArrayList<String>> prerequisites;
	/**
	 * Creates a new empty data structure
	 */
	public Data() {
		allClassList = new ArrayList<>();
		oxfClassList = new ArrayList<>();
		hamClassList = new ArrayList<>();
		midClassList = new ArrayList<>();
		prerequisites = new HashMap<>();
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
	 * Parses the requirements of courses in from a file;
	 * @param dataFile
	 * @return
	 */
	public String readNewPrereqData(File dataFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			String line = br.readLine();
			if (line == null || !line.contains(Constants.FIRST_LINE_OF_REQ)) {
				br.close();
				return "Error: Invalid REQ File";
			}	
			while((line = br.readLine()) != null) {
				String[] tokens = line.split(" \\| ");
				prerequisites.put(tokens[0], new ArrayList<>());
				for(int i = 1; i < tokens.length; i++) {
					prerequisites.get(tokens[0]).add(tokens[i]);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			return "Error: Could not find the file";
		} catch (IOException e) {
			return "Error: Could not read file";
		} 
		return "Finished Successfully";
	}
	/**
	 * Processes a file and adds new entries to the data
	 * @param dataFile
	 * @return
	 */
	public String readNewCourseData(File dataFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			// Checks the first line of the file to check if is a valid csv file or not
			String line = br.readLine();
			if (line == null || !line.contains(Constants.FIRST_LINE_OF_CSV)) {
				br.close();
				return "Error: Invalid CSV File";
			}
			// Read each line of the file
			readFile: while ((line = br.readLine()) != null) {
				String lineArgs[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)",-1);
				if (lineArgs.length < 14) {
					System.out.printf("Warning: a line in the file had an invalid number of elements, skipped: %s\n", line);
					continue readFile;
				}
				// for each argument, read in data
				for (int i = 0; i < 14; i++) {
					// skip the line if any element is blank
					if (lineArgs[i].equals("")) {
						System.out.printf("Warning: a line in the file had blank elements, skipped: %s\n", line);
						continue readFile;
					}
					// skip the line is unable to parse numerical values in line
					if ((i == 8 || i == 9) && !lineArgs[i].matches("\\d+")) {
						System.out.printf("Warning: unable to parse a start/end time for a line, skipped: %s\n", line);
						continue readFile;
					}
				}
				// Note: meeting days is not part of checking equality
				Class tempClass = new Class(lineArgs);
				if(!allClassList.contains(tempClass))
					allClassList.add(tempClass);
				else {
					// if duplicate class has different meeting times, add them
					allClassList.get(allClassList.indexOf(tempClass)).addMeetingDays(lineArgs[10]);
					System.out.printf("Note: Duplicate class detected, added meeting days and skipped: %s\n", tempClass);
				}
			}
			sortClasses();
			br.close();
		} catch (FileNotFoundException e) {
			return "Error: Could not find the file";
		} catch (IOException e) {
			return "Error: Could not read file";
		} catch (InvalidClassException e) {
			return e.getMessage();
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
}