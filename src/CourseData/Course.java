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
	public Course(String shortTitle) {
		String[] split = shortTitle.split("\\s+");
		subject = split[0];
		courseNumber = split[1];
	}
	public String getShortName() {
		return String.format("%s %s",subject, courseNumber);
	}
	public String getTitle() {
		return String.format("%s",title);
	}

	/**
	 * Compares two courses with each other.
	 */
	public int compareTo(Course o) {
		return this.getShortName().toLowerCase().compareTo(o.getShortName().toLowerCase());
	}

	/**
	 * Checks to see if two courses are equal with each other.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Course) {
			Course test = (Course) obj;
			return test.getShortName().equalsIgnoreCase(this.getShortName());
		}
		return false;
	}

	/**
	 * ToString method to return the shortName and the title of the course.
	 */
	public String toString() {
		return String.format("%s - %s", getShortName(), title);
	}
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
