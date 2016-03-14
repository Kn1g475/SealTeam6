package CourseData;

/**
 * Class to handle the instructor of the class.
 */
public class Instructor implements Comparable<Instructor> {
	String firstName; //First name of the instructor.
	String lastName; //Last name of the instructor

	/**
	 * Constructor for the instructor (that rhymed).
	 * @param name Full name of the instructor. Ex. Last, First
	 */
	public Instructor(String name) {
		if(name == ""){
			System.err.println("No name sent to instructor constructor");
			throw new IllegalArgumentException();
		}
		if (!name.contains(",")) {
			System.err.println("ERROR: Attempted to create an instructor, but no comma in name to parse first/last name with\n");
			throw new IllegalArgumentException();
		}
		name = name.replace("\"", "");
		this.lastName = name.substring(0, name.indexOf(',')).trim();
		this.firstName = name.substring(name.indexOf(',') + 1).trim();
	}

	/**
	 * Method to compare two instructors.
	 */
	public int compareTo(Instructor o) {
		return (lastName+firstName).toLowerCase().compareTo((o.lastName+o.firstName).toLowerCase());
	}

	/**
	 * Checks to see if two instructors are the same instructor.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Instructor) {
			Instructor test = (Instructor) obj;
			return test.toString().equalsIgnoreCase(toString());
		}
		return false;
	}

	/**
	 * ToString method that returns the instructors name.
	 */
	public String toString() {
		return String.format("%s %s", firstName,lastName);
	}
}
