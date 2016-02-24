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
			
			System.out.println("No name sent to instructor constructor");
			throw new IllegalArgumentException();
		}

		if (!name.contains(",")) {

			System.out.println("ERROR: Attempted to create an instructor, "
					+ "but no comma in name to parse first/last name with\n");
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

		return (this.lastName+this.firstName).toLowerCase()
				.compareTo((o.lastName+o.firstName).toLowerCase());
	}

	/**
	 * Checks to see if two instructors are the same instructor.
	 */
	public boolean equals(Object obj) {

		if (obj instanceof Instructor) {

			Instructor test = (Instructor) obj;
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
	 * ToString method that returns the instructors name.
	 */
	public String toString() {

		return this.firstName + " " + this.lastName;
	}

	//Main method to test the instructor class.
	public static void main(String args[]) {

		/*
		 * Testing...
		 */

		Instructor test = new Instructor(" \"Krump\"         , Norman ");

		Instructor test2 = new Instructor(" \"Krump   \"         , Norman ");

		Instructor test3 = new Instructor(" \"Krumper\"         , Norman ");

		System.out.println(test);

		System.out.println(test.equals(test2));
		System.out.println(test3.equals(test2));
	}

}
