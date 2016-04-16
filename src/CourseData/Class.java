package CourseData;

import java.util.HashMap;
import java.util.Map;

import Main.Constants;

/**
 * Class to handle the creation of classes.
 */
public class Class implements Comparable<Class> {

	private Course course; //Course associated with the class.
	public String CRN_Number; //Course registration number
	public String section; //Section of the class.

	public Map<Character,TimeInterval> times;
	private String instructor; //Instructor of the class.

	public boolean hasConflict = false; //Boolean that determines if there is a conflicting final.
	private int meetingDays; //Days that the classes meet.
	private Category category; //Category of the class.

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
	public Class(Course course, String instructor, String CRN_Number, String section, int startTime, int endTime, String days) {
		this.course = course;
		this.instructor = instructor;
		this.CRN_Number = CRN_Number;
		this.section = section;
		this.times = new HashMap<>();
		addTimes(days,startTime,endTime);
		this.meetingDays = 0;

		addMeetingDays(days);

		if (this.course == null || this.instructor == "" || this.CRN_Number == "" || this.section == ""
				|| startTime < 0 || startTime > 2400 || endTime < 0 || endTime > 2400
				|| endTime <= startTime || days == "") {
			System.err.println("Error: Invalid or blank input sent to a Class constructor");
			throw new IllegalArgumentException();
		}
	}

	public Class(String[] lineArgs) {
		this(new Course(lineArgs[1], lineArgs[4], lineArgs[2]),lineArgs[9],lineArgs[0],
				lineArgs[3],Integer.parseInt(lineArgs[6]),Integer.parseInt(lineArgs[7]),lineArgs[5]);
	}
	
	public Class(String line) {
		String[] args = line.split("\\|");
		
		this.CRN_Number = args[0];
		this.course = new Course(args[1],args[2],args[3]);
		this.section = args[4];
		
		this.times = new HashMap<>();
		String[] temp = args[5].split(":");
		String days = "";
		String keys = "";
		Map<Character,TimeInterval> inter = new HashMap<>();
		for(String t : temp) {
			if(t.contains("-")) {
				String[] temp2 = t.split("-");
				temp2[0] = temp2[0].trim();
				temp2[1] = temp2[1].trim();
				for (char key : keys.toCharArray()) {
					inter.put(key, new TimeInterval(Integer.parseInt(temp2[0]),Integer.parseInt(temp2[1])));
					days.replace(Character.toString(key), "");
				}
			} else {
				t = t.trim();
				days += t;
				keys += t;
			}
		}
		//System.out.println(inter);
		addTimes(inter);
		this.instructor = args[6];
		this.meetingDays = 0;
		addMeetingDays(days);
	}

	public boolean doesOverlap(Class test) {
		for(char day : test.times.keySet()) {
			if (times.containsKey(day)) {
				if(times.get(day).doesOverlap(test.times.get(day)) || test.times.get(day).doesOverlap(times.get(day)))
					return true;
			}
		}
		return false;
	}

	public void addLab(Class lab) {
		addTimes(lab.times);
		addMeetingDays(lab.getMeetingDays());
	}

	private void addTimes(String days, int startTime, int endTime) {
		for (char day : days.toCharArray()) {
			if (!times.containsKey(day)) {
				times.put(day, new TimeInterval(startTime,endTime));
			}
		}
	}
	private void addTimes(Map<Character,TimeInterval> map) {
		for (char day : map.keySet()) {
			addTimes(Character.toString(day),map.get(day).getStartTime(),map.get(day).getEndTime());
		}
	}
	private String getTimes(String meetingDays) {
		StringBuilder br = new StringBuilder();
		String checked = "";
		boolean isNew = true;
		for (char day : meetingDays.toCharArray()) {
			for (char day2 : checked.toCharArray()) {
				if(times.get(day2).equals(times.get(day))) {
					br.insert(br.indexOf(checked)+1, day);
					isNew = false;
				}
			}
			checked += day;
			if(isNew) {
				br.append(day+": ");
				br.append(String.format("%s - %s\t", 
						Constants.timeToString(times.get(day).getStartTime()),Constants.timeToString(times.get(day).getEndTime())));
			}
			isNew = true;
		}
		return br.toString();
	}
	
	
	private String getSaveTimes (String meetingDays) {
		StringBuilder br = new StringBuilder();
		String checked = "";
		boolean isNew = true;
		for (char day : meetingDays.toCharArray()) {
			for (char day2 : checked.toCharArray()) {
				if(times.get(day2).equals(times.get(day))) {
					br.insert(br.indexOf(checked)+1, day);
					isNew = false;
				}
			}
			checked += day;
			if(isNew) {
				br.append(day+": ");
				br.append(String.format("%d - %d\t:",times.get(day).getStartTime(),times.get(day).getEndTime()));
			}
			isNew = true;
		}
		return br.toString();
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
		return this.CRN_Number.compareTo(o.CRN_Number);	
	}

	/**
	 * ToString method that returns all the information of the class.
	 */
	public String toString() {
		return String.format("%s:\t\t%s\t\t%s\t\t%s", CRN_Number,
				course.toString(section),instructor,getTimes(getMeetingDays()));
	}
	public String fileSave() {
		return String.format("%s|%s|%s|%s|%s", CRN_Number,course.saveString(), section,getSaveTimes(getMeetingDays()),instructor);
	}
	/**
	 * ToString method that returns all information except for the info of the final.
	 * @return
	 */
	public String toStringReportHTMLRow() {
		return String.format("%s <td>%s%s</td><td>%s</td><td>(%s)</td></tr>",
				(hasConflict)?("<tr style=\"font-weight:bold;\"><td>***</td>"):("<tr><td></td>"),
						course.getShortName(),section, getTimes(getMeetingDays()), instructor, CRN_Number);
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
