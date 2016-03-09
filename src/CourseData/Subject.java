package CourseData;

/**
 * Class to handle the subject of the courses.
 */
public class Subject implements Comparable<Subject> {

	//String that holds the three letter subject of the course.
	private String subject;

	/**
	 * Subject constructor
	 * @param subject Three letter string that defines the subject of the course.
	 */
	public Subject(String subject) {
		if(subject == ""){	
			System.err.println("Error: no subject sent to constructor");
			throw new IllegalArgumentException();
		}
		this.subject = subject;
	}

	/**
	 * Compares two subjects.
	 */
	public int compareTo(Subject o) {
		return this.toString().toLowerCase().compareTo(o.toString().toLowerCase());
	}
	/**
	 * Checks if two subjects are the same.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Subject) {
			Subject test = (Subject) obj;
			return test.subject.equalsIgnoreCase(this.subject);
		}
		return false;
	}
	/**
	 * Returns the three letter subject.
	 */
	public String toString() {
		return subject;
	}

}
