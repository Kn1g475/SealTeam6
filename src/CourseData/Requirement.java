package CourseData;

import java.util.ArrayList;
import java.util.List;

public class Requirement {
	private List<String> prequisites;
	private boolean seniorLevel;
	private int hours;
	private boolean instructorPermission;
	private String type;
	
	public Requirement(String prequisities, boolean seniorLevel, int hours, boolean instructorPermission, String type) {
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
		this(Args[3], Args[4].equals("1") ? true : false, Integer.parseInt(Args[5]), Args[6].equals("1") ? true : false, Args[7]); 
	}
	
	public boolean hasPrereqs(List<Course> taken) {
		//System.out.println(prequisites);
		for (String shortTitle : prequisites) {
			//System.out.println(shortTitle);
			if (!taken.contains(new Course(shortTitle))) {
				return false;
			}
		}
		return true;
	}
	
	//Accessors
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
