package CourseData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import CourseData.*;
import Main.Constants;

/**
 * The container class for the data structure that holds all class data
 * 
 * @author matt
 * 
 */
public class Data {

	public ArrayList<Class> classList;
	public ArrayList<Course> courseList;
	public ArrayList<Subject> subjectList;
	public ArrayList<Instructor> instructorList;
	public ArrayList<Category> finalsCategories;

	/**
	 * Creates a new empty data structure
	 */
	public Data() {

		classList = new ArrayList<Class>();
		courseList = new ArrayList<Course>();
		subjectList = new ArrayList<Subject>();
		instructorList = new ArrayList<Instructor>();
		finalsCategories = new ArrayList<Category>();

	}

	/**
	 * Calls the static function that sets the categories for this data
	 * structure
	 * 
	 * @throws Exception
	 */
	public void setCategories() throws Exception {

		Errors.setCategories(this.classList, this.finalsCategories);

	}

	/**
	 * calls the static function that flags conflicts in the data
	 * 
	 * @throws Exception
	 */
	public void findConflicts() throws Exception {

		Errors.displayError(this.classList);

	}

	/**
	 * Processes a file and adds new entries to the data
	 * 
	 * @param dataFile
	 * @return
	 */
	public String readNewCourseData(File dataFile) {

		Scanner in;

		try {
			in = new Scanner(dataFile);

		} catch (FileNotFoundException e) {

			return "Error: Could not find the file";

		} catch (Exception e) {

			return "Error: Could not read file";
		}

		// Checks the first line of the file to check if is a valid csv file or
		// not
		if (!in.hasNext() || !in.nextLine().equals(Constants.FIRST_LINE_OF_CSV)) {

			in.close();
			return "Error: Invalid CSV File";
		}

		boolean warningsTriggered = false;

		// Read each line of the file
		readFile: while (in.hasNext()) {

			String line = in.nextLine();

			// regex code acquired from stackoverflow:
			// http://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
			// parses each line of the inputed file to extract individual data
			// while maintaining strings held within quotations
			String lineArgs[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)",
					-1);

			// check that array has correct # of arguments
			if (lineArgs.length != 14) {

				System.out
						.println("Warning: a line in the file had an invalid number of elements, skipped: "
								+ line);
				warningsTriggered = true;
				continue readFile;

			}

			// for each argument, read in data
			for (int i = 0; i < 14; i++) {
				
				// skip the line if any element is blank
				if (lineArgs[i].equals("")) {
					System.out
							.println("Warning: a line in the file had blank elements, skipped: "
									+ line);
					warningsTriggered = true;
					continue readFile;
				}
				// skip the line is unable to parse numerical values in line
				if (i == 8 || i == 9) {
					try {
						Integer.parseInt(lineArgs[i]);

					} catch (Exception e) {

						System.out
								.println("Warning: unable to parse a start/end time for a line, skipped: "
										+ line);
						warningsTriggered = true;
						continue readFile;
					}
				}

			}
			
			// skip the line if it represents a hamilton or middletown campus
			if (lineArgs[3].contains("H ") || lineArgs[3].contains("M ")) {

				System.out
						.println("Warning: Middletown/Hamilton campus class detected, skipped: "
								+ line);
				warningsTriggered = true;
				continue readFile;

			}

			Instructor tempInstructor = new Instructor(lineArgs[13]);

			if (!instructorList.contains(tempInstructor)) {

				instructorList.add(new Instructor(lineArgs[13]));

			}

			Subject tempSubject = new Subject(lineArgs[1]);

			if (!subjectList.contains(tempSubject)) {

				subjectList.add(new Subject(lineArgs[1]));

			}

			Course tempCourse = new Course(new Subject(lineArgs[1]),
					lineArgs[4], lineArgs[2]);

			if (!courseList.contains(tempCourse)) {

				courseList.add(new Course(subjectList.get(subjectList
						.indexOf(tempSubject)), lineArgs[4], lineArgs[2]));

			}

			// Note: meeting days is not part of checking equality
			Class tempClass = new Class(tempCourse, tempInstructor,
					lineArgs[0], lineArgs[3], Integer.parseInt(lineArgs[8]),
					Integer.parseInt(lineArgs[9]), lineArgs[10]);

			if (!classList.contains(tempClass)) {

				classList.add(new Class(courseList.get(courseList
						.indexOf(tempCourse)), instructorList
						.get(instructorList.indexOf(tempInstructor)),
						lineArgs[0], lineArgs[3],
						Integer.parseInt(lineArgs[8]), Integer
								.parseInt(lineArgs[9]), lineArgs[10]));

			} else {

				// if duplicate class has different meeting times, add them
				classList.get(classList.indexOf(tempClass)).addMeetingDays(
						lineArgs[10]);
				System.out
						.println("Note: Duplicate class detected, added meeting days and skipped: "
								+ tempClass);

			}

		}

		in.close();

		if (warningsTriggered)
			return "Successfully processed file, with warnings";

		else
			return "Successfully processed file";

	}

	/**
	 * Dumps the data structure to a string variable
	 */
	public String toString() {

		String ret = "";

		ret += ("\n+----------------------------+\n");
		ret += ("|         Data Output        |\n");
		ret += ("+----------------------------+\n");

		Collections.sort(classList);

		ret += ("Classes:     " + classList + "\n");
		ret += ("Class Count: " + classList.size() + "\n");

		Collections.sort(instructorList);

		ret += ("Instructors: " + instructorList + "\n");
		ret += ("Instr Count: " + instructorList.size() + "\n");

		Collections.sort(courseList);

		ret += ("Courses:     " + courseList + "\n");
		ret += ("Course Count:" + courseList.size() + "\n");

		Collections.sort(subjectList);

		ret += ("Subjects:    " + subjectList + "\n");

		return ret;

	}

	/**
	 * main method for testing
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String arg[]) throws Exception {

		Data tester = new Data();

		tester.readNewCourseData(new File("test.csv"));

		// Errors.setCategories(tester.classList);

		Errors.displayError(tester.classList);

	}

}