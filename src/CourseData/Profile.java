package CourseData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exceptions.InvalidClassException;
/**
 * A class to store the information for a students information and class schedule
 * @author Sam Levy
 */
public class Profile {
	private List<Course> takenCourses;
	private List<Class> schedule;
	private String uniqueID;
	private String major;
	public Map<Course, Requirement> majorReq;
	private int hours;
	private String curYear;

	public List<Category> finalsCategories;
	public List<String> Errors;
	public List<String> Warnings;
	/**
	 * Constructor
	 */
	public Profile() {
		takenCourses = new ArrayList<>();
		finalsCategories = new ArrayList<>();
		schedule = new ArrayList<>();
		
		Errors = new ArrayList<>();
		Warnings = new ArrayList<>();
		
		majorReq = new HashMap<>();
		hours = 0;
		curYear = "";
		uniqueID = "";
		major = "";
	}
	/**
	 * Gets a copy of the taken courses
	 * @return - a copy of the current taken courses
	 */
	public List<Course> getCoursesTaken() {
		List<Course> copy = new ArrayList<>(takenCourses);
		return copy;
	}
	/**
	 * Gets a copy of the schedule
	 * @return - a copy of the current schedule
	 */
	public List<Class> getSchedule() {
		List<Class> copy = new ArrayList<>(schedule);
		return copy;
	}
	/**
	 * Adds a Course that has been taken
	 * @param course
	 */
	public void addCourse(Course course) {
		if(!takenCourses.contains(course))
			takenCourses.add(course);
	}
	public void addCourse(List<Course> courses) {
		for (Course c: courses)
			addCourse(c);
	}
	/**
	 * Adds a Class to a schedule
	 * @param clas
	 */
	public boolean addClass(Class clas) {
		boolean check = false;
		if (!checkTimeOverlap(clas) && ! this.checkAlreadyHave(clas)) {
			schedule.add(clas);
			check = true;
		} 
		return check;
	}
	/**
	 * Adds all given classes to the schedule
	 * @param classes
	 * @return
	 */
	public List<String> addClasses(List<Class> classes) {
		List<String> errors = new ArrayList<>();
		for (Class c : classes) {
			if (checkTimeOverlap(c))
				errors.add(String.format("Invalid: %s overlaps with a course you are taking", c.getCourse().toString(c.section)));
			else if(checkAlreadyHave(c))
				errors.add(String.format("Invalid: %s is already apart of your schedule taking", c.getCourse().getShortName()));
			else 
				schedule.add(c);
		}
		
		return errors;
	}
	
	/**
	 * Removes a Course that has been taken
	 * @param course
	 * @return
	 */
	public boolean removeCourse(Course course) {
		return takenCourses.remove(course);
	}
	/**
	 * removes all the given courses
	 * @param courses
	 * @return
	 */
	public boolean removeCourse(List<Course> courses) {
		return takenCourses.removeAll(courses);
	}
	/**
	 * Removes a Class from the schedule
	 * @param clas
	 * @return
	 */
	public boolean removeClass(Class clas) {
		return schedule.remove(clas);
	}
	/**
	 * Removes all the given classes
	 * @param classes
	 * @return
	 */
	public boolean removeClass(List<Class> classes) {
		return schedule.removeAll(classes);
	}
	//getters and setters
	public String getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public String getCurYear() {
		return curYear;
	}
	public void setCurYear(String curYear) {
		this.curYear = curYear;
	}//end getters and setters
	/**
	 * Saves all the information in the profile to a text file in the
	 * folder of the .jar named uniqueIDMajor.prof
	 */
	public void saveProfile() {
		File save = new File(uniqueID + major + ".prof");
		System.out.println(save.getAbsolutePath());
		if (!save.exists())
			try {
				save.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(save,false))) {
			StringBuilder sb = new StringBuilder();
			sb.append("Unique Id: " + uniqueID + "\n");
			sb.append("Major: " + major + "\n");
			sb.append("Year: " + curYear + "\n");
			sb.append("Hours: " + hours + "\n");
			sb.append("Courses: \n");
			for (Course c : this.takenCourses) {
				sb.append(c.saveString() + "\n");
			}
			sb.append("Classes: \n");
			for (Class c : schedule) {
				sb.append(c.fileSave() + "\n");
			}
			bw.write(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Reads the information from the selected file and adds it to the profile
	 * @param selectedFile
	 * @return
	 */
	public String readProfile(File selectedFile) {
		if (!selectedFile.getName().endsWith(".prof")) {
			return "Error: Incorrect File Format";
		}
		try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
			String line = br.readLine();
			String [] temp = line.split(":");
			temp[1] = temp[1].trim();
			this.uniqueID = temp[1];

			line = br.readLine();
			temp = line.split(":");
			temp[1] = temp[1].trim();
			major = temp[1];

			line = br.readLine();
			temp = line.split(":");
			temp[1] = temp[1].trim();
			curYear = temp[1];


			line = br.readLine();
			temp = line.split(":");
			temp[1] = temp[1].trim();
			hours = Integer.parseInt(temp[1]);
			br.readLine();
			while(!(line = br.readLine().trim()).equals("Classes:")) {
				System.out.println(line);
				temp = line.split("\\|");
				Course tempCourse = new Course(temp[0],temp[1],temp[2]);
				addCourse(tempCourse);
			}
			while((line = br.readLine()) != null) {
				System.out.println(line);
				Class tempClass = new Class(line);
				if (!schedule.contains(tempClass))
					addClass(tempClass);
			}

		} catch (IOException e) {
			e.printStackTrace();
			return "Error: File could not be Read";
		}
		return "";
	}
	/**
	 * Checks to see if the made schedule is feasible or not
	 * @return
	 */
	public boolean checkFeasibility() {
		Errors.clear();
		Warnings.clear();
		for (Class c : schedule) {
			String err = checkPreReq(c);
			if (err.contains("Invalid"))
				Errors.add(err);
			if (err.contains("Warning"))
				Warnings.add(err);
		}
		checkMajorReqs();
		System.out.println(Errors);
		System.out.println(Warnings);
		return Errors.isEmpty();
	}
	
	/**
	 * checks to see if a selected class happens during any of the selected courses times
	 * @param add
	 * @return
	 */
	private boolean checkTimeOverlap(Class add) {
		boolean test = false;
		for (Class clas : schedule) {
			if(clas.doesOverlap(add)) {
				test = true;
				break;
			}
		}
		return test;
	}
	/**
	 * checks to see if you already taking a class
	 * @param add
	 * @return
	 */
	private boolean checkAlreadyHave(Class add) {
		for (Class c : schedule)
			if (add.getCourse().equals(c.getCourse()))
				return true;
		return false;
	}
	/**
	 * Checks Prerequisites for a given class
	 */
	private String checkPreReq(Class test) {
		Course curCourse = test.getCourse();
		if (majorReq.containsKey(curCourse)) {
			//get requirements for course
			Requirement req = majorReq.get(curCourse);
			//check hours
			if (req.getHours() > getHours())
				return String.format("Invalid: Need %d for %s you have %d ", req.getHours(), curCourse.toString(test.section), getHours());
			//check prerequisites
			if (!req.hasPrereqs(this.takenCourses))
				return String.format("Invalid: %s requires that you have taken: %s", curCourse.toString(test.section) ,req.getPrequisites());
			//check senior level
			if (req.isSeniorLevel())
				if (this.curYear.equals("Fourth Year +"))
					return "Invalid: Senior standing required for " + curCourse.toString(test.section);
			//warn for instructor permission
			if (req.isInstructorPermission())
				return String.format("Warning: %s requires instructor permission", curCourse.toString(test.section));
		}
		else {
			System.out.println("Does not contain " + curCourse);
		}
		return "";
	}
	/**
	 * checks Major Requirements
	 */
	private void checkMajorReqs() {
		int affiliate = 0;
		int research = 0;
		int compSci = 0;
		int engineer = 0;
		//get current elective course count
		for (Course c : takenCourses) {
			if (majorReq.containsKey(c.getShortName())) {
				Requirement req = majorReq.get(c.getShortName());
				switch (req.getType()) {
				case "COMPUTER SCIENCE":
					compSci++;
					break;
				case "AFFILIATE":
					affiliate++;
					break;
				case "RESEARCH":
					research++;
					break;
				case "ENGINEERING":
					engineer++;
					break;
				}
			}
		}
		//get new course elective count
		for (Class c : schedule) {
			Course curCourse = c.getCourse();
			if (majorReq.containsKey(curCourse)) {
				Requirement req = majorReq.get(curCourse);
				switch (req.getType()) {
				case "COMPUTER SCIENCE":
					compSci++;
					if (compSci > 3) {
						Warnings.add(String.format("Warning: %s, a computer science elective, is not useful to your graduation", curCourse.toString(c.section)));
					}
					break;
				case "AFFILIATE":
					affiliate++;
					if (affiliate > 2) {
						Warnings.add(String.format("Warning: %s, an affiliate elective, is not useful to your graduation", curCourse.toString(c.section)));
					}
					break;
				case "RESEARCH":
					research++;
					if (research > 1) {
						Warnings.add(String.format("Warning: %s, a research elective, is not useful to your graduation", curCourse.toString(c.section)));
					}
					break;
				case "ENGINEERING":
					engineer++;
					break;
				} 
			} else {
				Warnings.add(String.format("Warning: %s is not required for your major", curCourse.toString(c.section)));
			}
			
		}
		
		
		
	}
	/**
	 * searches for final conflicts
	 * @throws InvalidClassException
	 */
	public void findConflicts() throws InvalidClassException {
		finalsCategories = new ArrayList<>();
		for (Class c: schedule)
			c.setCategory(null);
		Data.setCategories(schedule, finalsCategories);
		displayError(schedule);
	}


	/**
	 * method marks classes have overlapping times
	 * 
	 * @param allClassList
	 * @throws Exception
	 */
	private void displayError(List<Class> classList) throws InvalidClassException {
		for (int i = 0; i < classList.size() - 1 ; i++) {
			for (int j =  i + 1; j < classList.size(); j++) {
				Class a = classList.get(i);
				Class b = classList.get(j);

				if (a.getCategory() == null || b.getCategory() == null)
					throw new InvalidClassException("There is a class that does not exist");
				if (a.times.get(a.getMeetingDays().charAt(0)).getEndTime() <= b.times.get(b.getMeetingDays().charAt(0)).getEndTime() 
						&& a.getCategory().equals(b.getCategory()) && !a.getCourse().equals(b.getCourse())) {
					a.hasConflict = true;
					b.hasConflict = true;
					a.getCategory().hasConflicts = true;
					System.out.println(String.format("There is a conflict!: \n\t%s ----- %s", a.toString(), b.toString()));
				}
			}
		}
	}
}
