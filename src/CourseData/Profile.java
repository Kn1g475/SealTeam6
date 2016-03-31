package CourseData;

import java.util.ArrayList;
import java.util.List;

public class Profile {
	private List<Course> takenCourses;
	private List<Class> schedule;
	private String uniqueID;
	private List<Requirement> major;
	
	public Profile() {
		takenCourses = new ArrayList<>();
		schedule = new ArrayList<>();	
	}
	
	public Profile(Data data) {
		super();
	}
	/**
	 * Gets a copy of the taken courses
	 * @return - a copy of the current taken courses
	 */
	public ArrayList<Course> getCoursesTaken() {
		ArrayList<Course> copy = new ArrayList<>(takenCourses);
		return copy;
	}
	/**
	 * Gets a copy of the schedule
	 * @return - a copy of the current schedule
	 */
	public ArrayList<Class> getSchedule() {
		ArrayList<Class> copy = new ArrayList<>(schedule);
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
	public void addClass(Class clas) {
		schedule.add(clas);
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
}
