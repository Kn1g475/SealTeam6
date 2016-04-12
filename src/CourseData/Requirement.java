package CourseData;

import java.util.ArrayList;
import java.util.List;

public class Requirement {
	private String uniqueId;
	private Course course;
	private List<String> prequisites;
	private boolean seniorLevel;
	private int hours;
	private boolean instructorPermission;
	private String type;
	
	public Requirement(String uniqueId, Course course, String prequisities, boolean seniorLevel, int hours, boolean instructorPermission, String type) {
		this.uniqueId = uniqueId;
		this.course = course;
		
		this.prequisites = new ArrayList<>();
		String[] args = prequisities.split("\\|");
		for (String arg : args)
			this.prequisites.add(arg);
		
		this.seniorLevel = seniorLevel;
		this.hours = hours;
		this.instructorPermission = instructorPermission;
		this.type = type;
	}
	
	public Requirement(String[] Args) {
		this(Args[0],new Course( Args[1],Args[3] ,Args[2]), Args[4], 
				Args[5].equals("1") ? true : false, Integer.parseInt(Args[6]), Args[7].equals("1") ? true : false, Args[8]); 
	}
	
	//Accessors
	public String getUniqueId() {
		return uniqueId;
	}
	public Course getCourse() {
		return course;
	}
	public List<String> getPrequisites() {
		return prequisites;
	}
	public boolean isSeniorLevel() {
		return seniorLevel;
	}
	public int getHours() {
		return hours;
	}
	public boolean isInstructorPermission() {
		return instructorPermission;
	}
	public String getType() {
		return type;
	}
	//end Accessors
	
}
