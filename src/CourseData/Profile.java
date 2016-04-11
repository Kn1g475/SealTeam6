package CourseData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidClassException;

public class Profile {
	private List<Course> takenCourses;
	private List<Class> schedule;
	private String uniqueID;
	private String major;
	private List<Requirement> majorReq;
	private int hours;
	private String curYear;
	
	public List<Category> finalsCategories;
	
	public Profile() {
		takenCourses = new ArrayList<>();
		finalsCategories = new ArrayList<>();
		schedule = new ArrayList<>();
		majorReq = new ArrayList<>();
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
	/**
	 * Adds a Class to a schedule
	 * @param clas
	 */
	public boolean addClass(Class clas) {
		boolean check = false;
		if (!checkTimeOverlap(clas)) {
			schedule.add(clas);
			check = true;
		} 
		return check;
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
	 * Removes a Class from the schedule
	 * @param clas
	 * @return
	 */
	public boolean removeClass(Class clas) {
		return schedule.remove(clas);
	}
	
	
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
	}
	
	public void saveProfile() {
		File save = new File(uniqueID + major + ".prof");
		System.out.println(save.getAbsolutePath());
		if (!save.exists())
			try {
				save.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(save))) {
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
	public String readProfile(File selectedFile) {
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
				takenCourses.add(new Course(temp[0],temp[1],temp[2]));
			}
			while((line = br.readLine()) != null) {
				System.out.println(line);
				schedule.add(new Class(line));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return "Error: File could not be Read";
		}
		return "";
	}
	
	public boolean checkFeasibility() {
		return !checkPreReq();
	}
	
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
	
	private boolean checkPreReq() {
		for (Class clas : schedule) {
			for(Requirement req : majorReq) {
				
			}
		}
		return false;
	}
	
	public boolean isFilledIn() {
		return !uniqueID.isEmpty() && !curYear.isEmpty() && !major.isEmpty();
	}
	
	public void findConflicts() throws InvalidClassException{
		Data.setCategories(schedule, finalsCategories);
		displayError(schedule);
	}
	

	/**
	 * method marks classes have overlapping times
	 * 
	 * @param allClassList
	 * @throws Exception
	 */
	private static void displayError(List<Class> classList) throws InvalidClassException {
		for (int i = 0; i < classList.size() - 1 ; i++) {
			for (int j =  i + 1; j < classList.size(); j++) {
				Class a = classList.get(i);
				Class b = classList.get(j);

				if (a.getCategory() == null || b.getCategory() == null)
					throw new InvalidClassException("There is a class that does not exist");
				if (a.endTime <= b.startTime && a.getCategory().equals(b.getCategory()) && !a.getCourse().equals(b.getCourse())) {
					a.hasConflict = true;
					b.hasConflict = true;
					a.getCategory().hasConflicts = true;
					System.out.println(String.format("There is a conflict!: \n\t%s ----- %s", a.toString(), b.toString()));
				}
			}
		}
	}
}
