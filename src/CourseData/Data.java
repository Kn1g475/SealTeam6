package CourseData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import Main.Constants;

/**
 * The container class for the data structure that holds all class data
 * @author matt
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
	 * @throws Exception
	 */
	public void findConflicts() throws Exception {
		Errors.displayError(this.classList);
	}

	/**
	 * Processes a file and adds new entries to the data
	 * @param dataFile
	 * @return
	 */
	public String readNewCourseData(File dataFile) {
		boolean warningsTriggered = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));

			// Checks the first line of the file to check if is a valid csv file or
			// not
			String line = br.readLine();
			if (line == null || !line.equals(Constants.FIRST_LINE_OF_CSV)) {
				br.close();
				return "Error: Invalid CSV File";
			}

			// Read each line of the file
			readFile: while ((line = br.readLine()) != null) {
				// regex code acquired from stackoverflow:
				// http://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
				// parses each line of the inputed file to extract individual data
				// while maintaining strings held within quotations
				String lineArgs[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)",-1);

				// check that array has correct # of arguments
				if (lineArgs.length != 14) {
					System.out.printf("Warning: a line in the file had an invalid number of elements, skipped: %s\n", line);
					warningsTriggered = true;
					continue readFile;
				}
				// for each argument, read in data
				for (int i = 0; i < 14; i++) {
					// skip the line if any element is blank
					if (lineArgs[i].equals("")) {
						System.out.printf("Warning: a line in the file had blank elements, skipped: %s\n", line);
						warningsTriggered = true;
						continue readFile;
					}
					// skip the line is unable to parse numerical values in line
					if (i == 8 || i == 9) {
						try {
							Integer.parseInt(lineArgs[i]);
						} catch (Exception e) {
							System.out.printf("Warning: unable to parse a start/end time for a line, skipped: %s\n", line);
							warningsTriggered = true;
							continue readFile;
						}
					}
				}

				// skip the line if it represents a hamilton or middletown campus
				if (lineArgs[3].contains("H ") || lineArgs[3].contains("M ")) {
					System.out.printf("Warning: Middletown/Hamilton campus class detected, skipped: %s\n", line);
					warningsTriggered = true;
					continue readFile;
				}

				Instructor tempInstructor = new Instructor(lineArgs[13]);

				if (!instructorList.contains(tempInstructor))
					instructorList.add(new Instructor(lineArgs[13]));

				Subject tempSubject = new Subject(lineArgs[1]);

				if (!subjectList.contains(tempSubject))
					subjectList.add(new Subject(lineArgs[1]));

				Course tempCourse = new Course(new Subject(lineArgs[1]), lineArgs[4], lineArgs[2]);

				if (!courseList.contains(tempCourse)) {
					courseList.add(new Course(subjectList.get(subjectList.indexOf(tempSubject)), lineArgs[4], lineArgs[2]));
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
					classList.get(classList.indexOf(tempClass)).addMeetingDays(lineArgs[10]);
					System.out.printf("Note: Duplicate class detected, added meeting days and skipped: %s\n", tempClass);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			return "Error: Could not find the file";
		} catch (Exception e) {
			return "Error: Could not read file";
		}
		if (warningsTriggered)
			return "Successfully processed file, with warnings";
		else
			return "Successfully processed file";
	}
	/**
	 * Dumps the data structure to a string variable
	 */
	public String toString() {
		StringBuilder ret = new StringBuilder();

		ret.append("\n+----------------------------+\n");
		ret.append("|         Data Output        |\n");
		ret.append("+----------------------------+\n");

		Collections.sort(classList);
		ret.append(String.format("Classes:\t%s\n", classList));
		ret.append(String.format("Class Count: %d\n", classList.size()));

		Collections.sort(instructorList);
		ret.append(String.format("Instructors: %s\n", instructorList));
		ret.append(String.format("Instr Count: %d\n", instructorList.size()));

		Collections.sort(courseList);
		ret.append(String.format("Courses:\t%s\n", courseList));
		ret.append(String.format("Course Count: %d\n", courseList.size()));

		Collections.sort(subjectList);
		ret.append(String.format("Subjects:\t%s\n", subjectList));

		return ret.toString();
	}
}