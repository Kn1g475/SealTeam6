package CourseData;

import CourseData.Class;
import Main.Constants;

/**
 * Class to handle the creation of classes.
 */
public class Class implements Comparable<Class> {

	Course course; //Course associated with the class.
	public String CRN_Number; //Course registration number
	String section; //Section of the class.
	public int startTime; //Start time of the class.
	public int endTime; //End time of the class.
	Instructor instructor; //Instructor of the class.
	
	public boolean hasConflict = false; //Boolean that determines if there is a conflicting final.
	private int meetingDays; //Days that the classes meet.
	String location;
	Category category; //Category of the class.

	/**
	 * Constructor for the Class.
	 * @param course Course of the class.
	 * @param instructor Instructor of the class.
	 * @param CRN_Number Course registration number for the specific class.
	 * @param section Section of the class.
	 * @param startTime Time that the class starts.
	 * @param endTime Time that the class ends.
	 * @param days Days that the class meets.
	 */
	public Class(Course course, Instructor instructor, String CRN_Number, String section, int startTime, int endTime, String days) {
		this.course = course;
		this.instructor = instructor;
		this.CRN_Number = CRN_Number;
		this.section = section;
		this.startTime = startTime;
		this.endTime = endTime;
		this.meetingDays = 0;

		addMeetingDays(days);

		if (this.course == null || this.instructor == null || this.CRN_Number == "" || this.section == ""
				|| this.startTime < 0 || this.startTime > 2400 || this.endTime < 0 || this.endTime > 2400
				|| this.endTime <= this.startTime || days == "") {
			System.err.println("Error: Invalid or blank input sent to a Class constructor");
			throw new IllegalArgumentException();
		}
	}
	public Class(String[] lineArgs) {
		this(new Course(new Subject(lineArgs[1]), lineArgs[4], lineArgs[2]),new Instructor(lineArgs[13]),lineArgs[0],
				lineArgs[3],Integer.parseInt(lineArgs[8]),Integer.parseInt(lineArgs[9]),lineArgs[10]);
		
		if (lineArgs[3].contains("H "))
			this.location = "Hamilton";
		else if(lineArgs[3].contains("M "))
			this.location = "MiddleTown";
		else
			this.location = "Oxford";
	}
	
	public String getLocation() {
		return location;
	}
	
	/**
	 * Accesser method for the course.
	 * @return Course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Mutator method for the categories.
	 * @param cat Category to be set.
	 */
	public void setCategory(Category cat) {
		this.category = cat;
	}

	/**
	 * Accessor method for the category.
	 * @return Category
	 */
	public Category getCategory() {
		return this.category;
	}
	
	/**
	 * Checks if the times and days of the category matches with the class.
	 * @param cat
	 * @return
	 */
	public boolean matches(Category cat){
		return cat.matches(this);
	}
	
	// Note: Equals does not include meeting days. A class is still equal if
	// they have all the same properties but have different meeting days
	/**
	 * Checks to see if two classes are the same class.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Class) {
			Class test = (Class) obj;
			return test.CRN_Number.equalsIgnoreCase(this.CRN_Number);
		}
		return false;
	}

	/**
	 * CompareTo method to compare classes.
	 */
	public int compareTo(Class o) {
//		return this.toString().toLowerCase()
//				.compareTo(o.toString().toLowerCase());
		return this.startTime - o.startTime;	
	}

//	/**
//	 * ToString methods that returns a string without meeting days.
//	 * @return String without meeting days.
//	 */
//	public String toStringWithOutMeetingDays() {
//
//		return course.shortName + this.section + " from " + this.startTime + " to " + this.endTime + " ("
//				+ this.CRN_Number + ")";
//
//	}

	/**
	 * ToString method that returns all the information of the class.
	 */
	public String toString() {
		return String.format("%s with %s from %d to %d on %s with final at %s(%s)", 
				course.toString(section),instructor,startTime,endTime,getMeetingDays(),getFinalInfo(),CRN_Number);
	}
	
	/**
	 * ToString method that returns all information except for the infor of the final.
	 * @return
	 */
	public String toStringReportHTMLRow() {
		return String.format("%s <td>%s%s</td><td>%s - %s </td><td>%s</td><td>%s, %s</td><td>(%s)</td></tr>",
				(hasConflict)?("<tr style=\"font-weight:bold;\"><td>***</td>"):("<tr><td></td>"),
						course.shortName,section, Constants.timeToString(startTime),
						Constants.timeToString(endTime), getMeetingDays(), instructor.lastName,
						instructor.firstName, CRN_Number);

	}
	
	/**
	 * Gets the final meeting times of the class if the class has a category.
	 * @return String of the time and day the final for the class is unless it does not have a category.
	 */
	private String getFinalInfo(){
		if(this.category != null)
			return String.format("%s on %s", getCategory().finalTime,getCategory().finalDay);
		else
			return "NO FINAL SET";
	}
	
	/**
	 * Checks to see of two meeting times are at the same time
	 */
	public boolean matchesMeetingTime(int m){
		return (this.meetingDays & m) == this.meetingDays;
	}
	
	/**
	 * Adds the days that the classes meets in the constructor.
	 * @param days String of days that the classes meets.
	 */
	public void addMeetingDays(String days) {
		for (int i = 0; i < days.length(); i++) {
			char x = days.charAt(i);
			switch (x) {
			case 'M':
				this.meetingDays = this.meetingDays | 16;
				break;
			case 'T':
				this.meetingDays = this.meetingDays | 8;
				break;
			case 'W':
				this.meetingDays = this.meetingDays | 4;
				break;
			case 'R':
				this.meetingDays = this.meetingDays | 2;
				break;
			case 'F':
				this.meetingDays = this.meetingDays | 1;
				break;
			default:
				continue;
			}
		}
	}

	/**
	 * Gets the meeting days of the class.
	 * @return String of of the meeting days of the class.
	 */
	public String getMeetingDays() {
		StringBuilder ret = new StringBuilder();
		if( (this.meetingDays & 16) == 16)
			ret.append("M");
		if( (this.meetingDays & 8) == 8)
			ret.append("T");
		if( (this.meetingDays & 4) == 4)
			ret.append("W");
		if( (this.meetingDays & 2) == 2)
			ret.append("R");
		if( (this.meetingDays & 1) == 1)
			ret.append("F");
		return ret.toString();
	}
}