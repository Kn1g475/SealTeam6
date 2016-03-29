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
	
	public Requirement(String uniqueId, Course course, String prequisties, boolean senoirLevel, int hours, boolean instructorPermission, String type) {
		this.uniqueId = uniqueId;
		this.course = course;
		
		prequisites = new ArrayList<>();
		
		this.seniorLevel = seniorLevel;
		this.hours = hours;
		this.instructorPermission = instructorPermission;
		this.type = type;
	}
}
