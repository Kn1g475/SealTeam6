package CourseData;

/**
 * Class to handle courses.
 */
public class Course implements Comparable<Course> {

	Subject subject; //Subject of the course.
	String title; //Title of the course.
	String courseNumber; //Number of the course.

	public String shortName; //Combined subject and course number string.

	/**
	 * Constructor for courses.
	 * @param subject Subject of the course.
	 * @param title Title of the course.
	 * @param courseNumber Number of the course.
	 */
	public Course(Subject subject, String title, String courseNumber) {

		if (subject == null || title == "" || courseNumber == "") {

			System.out
					.println("Error: Invalid input sent to Course constructor");
			throw new IllegalArgumentException();

		}

		this.subject = subject;
		this.title = title;
		this.courseNumber = courseNumber;

		this.shortName = this.subject.toString() + this.courseNumber;

	}

	/**
	 * Compares two courses with each other.
	 */
	public int compareTo(Course o) {

		return this.toString().toLowerCase()
				.compareTo(o.toString().toLowerCase());
	}

	/**
	 * Checks to see if two courses are equal with each other.
	 */
	public boolean equals(Object obj) {

		if (obj instanceof Course) {

			Course test = (Course) obj;
			if (test.toString().equalsIgnoreCase(this.toString())) {

				return true;

			} else {

				return false;
			}

		} else {

			return false;
		}
	}

	/**
	 * ToString method to return the shortName and the title of the course.
	 */
	public String toString() {

		return this.shortName + " - " + this.title;

	}

	/**
	 * ToString method that includes the specific section of the course.
	 * @param section Section of the course.
	 * @return String with the shortName, section, and title of the course.
	 */
	public String toString(String section) {

		return this.shortName + section + " - " + this.title;

	}

	/**
	 * Main method to test the Coure class.
	 * @param args
	 */
	public static void main(String args[]) {

		/*
		 * Testing...
		 */

		Subject testSub = new Subject("CSE");
		Subject testSub2 = new Subject("cSe");

		Course testCourse = new Course(testSub, "Learning about Programming",
				"101");

		System.out.println(testCourse);

		Course testCourse2 = new Course(testSub2, "Learning about PrOgramming",
				"101");

		Course testCourse3 = new Course(testSub, "Learning about Programming",
				"102");

		System.out.println(testCourse2.equals(testCourse));
		System.out.println(testCourse3.equals(testCourse));

		System.out.println(testSub.equals(testSub2));

		System.out.println(testCourse2.compareTo(testCourse));

		System.out.println(testCourse2);

	}

}
