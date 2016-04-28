package CourseData;

/**
 * Class to handle courses.
 */
public class Course implements Comparable<Course> {

	private String subject; //Subject of the course.
	private String title; //Title of the course.
	private String courseNumber; //Number of the course.

	/**
	 * Constructor for courses.
	 * @param subject Subject of the course.
	 * @param title Title of the course.
	 * @param courseNumber Number of the course.
	 */
	public Course(String subject, String title, String courseNumber) {
		if (subject == "" || title == "" || courseNumber == "") {
			System.err.println("Error: Invalid input sent to Course constructor");
			throw new IllegalArgumentException();
		}
		this.subject = subject;
		this.title = title;
		this.courseNumber = courseNumber;
	}
	/**
	 * Creates course with out title based on its short name
	 * @param shortTitle
	 */
	public Course(String shortTitle) {
		String[] split = shortTitle.split("\\s+");
		subject = split[0];
		courseNumber = split[1];
	}
	/**
	 * Gets the short name of the Course
	 * @return
	 */
	public String getShortName() {
		return String.format("%s %s",subject, courseNumber);
	}
	//getters
	public String getTitle() {
		return title;
	}
	public String getCourseNumber(){
		return courseNumber;
	}
	public String getSubject(){
		return subject;
	}// end getters

	/**
	 * Compares two courses with each other.
	 */
	public int compareTo(Course o) {
		return this.getShortName().toLowerCase().compareTo(o.getShortName().toLowerCase());
	}

	@Override
	/**
	 * Generates hash code for each course
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseNumber == null) ? 0 : courseNumber.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}
	@Override
	/**
	 * checks to see if two courses are the same, compares shortnames only
	 */
	public boolean equals(Object obj) {
		boolean ret = false;
		if( obj instanceof Course) {
			Course other = (Course) obj;
			ret = this.getShortName().equals(other.getShortName());
		}
		return ret;
	}

	/**
	 * ToString method to return the shortName and the title of the course.
	 */
	public String toString() {
		return String.format("%s - %s", getShortName(), title);
	}
	/**
	 * To string for file saving
	 * @return
	 */
	public String saveString() {
		return String.format("%s|%s|%s", subject, title, courseNumber);
	}

	/**
	 * ToString method that includes the specific section of the course.
	 * @param section Section of the course.
	 * @return String with the shortName, section, and title of the course.
	 */
	public String toString(String section) {
		return String.format("%s%s - %s", getShortName(), section, title);
	}
	
	
}
