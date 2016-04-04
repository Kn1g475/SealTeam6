package CourseData;

import java.util.ArrayList;
import java.util.List;

public class Profile {
	private List<Course> takenCourses;
	private List<Class> schedule;
	private String uniqueID;
	private List<Requirement> major;
	private int hours;
	private String curYear;
	
	public Profile() {
		takenCourses = new ArrayList<>();
		schedule = new ArrayList<>();
	}
	public Profile(String curYear, int hours, List<Requirement> major) {
		this();
		this.hours = hours;
		this.major = major;
		this.curYear = curYear;
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
			for(Requirement req : major) {
				
			}
		}
		return false;
	}
	
	
	
}
