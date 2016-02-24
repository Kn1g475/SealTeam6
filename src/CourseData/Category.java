package CourseData;

import java.util.ArrayList;

import Main.Constants;

/**
 * A category represents a final exam slot. A class can match a category,
 * indicating that the class has it's final during that final exam slot
 * 
 * @author matt
 * 
 */
public class Category {

	public ArrayList<Class> classesInThisCategory;

	ArrayList<Integer> meetingDayPatterns;

	// start time inclusive, end time exclusive
	int startTime, endTime;

	// represents the time the final starts for this category.
	public int finalTime;
	public String finalDay;

	public boolean hasConflicts;

	/**
	 * A new category represents the slot start time/end time, slot meeting
	 * days, and the final exam meeting time/day
	 * 
	 * @param startTime
	 * @param endTime
	 * @param finalTime
	 * @param finalDay
	 * @param meetingDays
	 */
	public Category(int startTime, int endTime, int finalTime, String finalDay,
			String meetingDays) {

		hasConflicts = false;

		this.startTime = startTime;
		this.endTime = endTime;

		this.finalTime = finalTime;
		this.finalDay = finalDay;

		this.meetingDayPatterns = new ArrayList<Integer>();
		this.classesInThisCategory = new ArrayList<Class>();

		this.addMeetingPattern(meetingDays);

	}

	/**
	 * Create a final exam without a final day
	 * 
	 * @param startTime
	 * @param endTime
	 * @param finalTime
	 * @param finalDay
	 */
	public Category(int startTime, int endTime, int finalTime, String finalDay) {

		this(startTime, endTime, finalTime, finalDay, "");

	}

	/**
	 * This method returns whether or not a class object "matches" this final
	 * exam slot, meaning that that class will have a final during the time that
	 * this slot represents
	 * 
	 * @param c
	 * @return ifMatches
	 */
	public boolean matches(Class c) {

		if (c.startTime >= this.startTime && c.startTime < this.endTime) {

			for (int m : this.meetingDayPatterns) {

				if (c.matchesMeetingTime(m)) {

					if (!this.classesInThisCategory.contains(c)) {

						this.classesInThisCategory.add(c);
					}

					return true;
				}
			}

			return false;

		} else {

			return false;
		}

	}

	/**
	 * Since final exam slots can span multiple meeting times, you must add
	 * meeting day "patterns" in the form of a binary string. For example, if a
	 * final is for classes that only meet Tuesday and Thursday, then you would
	 * add 01010 as a meeting pattern to that slot
	 * 
	 * @param pattern
	 */
	public void addMeetingPattern(String pattern) {

		int n = this.convertStringToBinary(pattern);

		this.meetingDayPatterns.add(n);

	}

	/**
	 * Takes the integer reprentation of a meeting pattern and adds it to this
	 * final slot
	 * 
	 * @param days
	 * @return
	 */
	private int convertStringToBinary(String days) {

		int ret = 0;

		for (int i = 0; i < days.length(); i++) {

			char x = days.charAt(i);

			switch (x) {

			case 'M':
				ret = ret | 16;
				// finalDay = "Monday";
				break;
			case 'T':
				ret = ret | 8;
				// finalDay = "Tuesday";
				break;
			case 'W':
				ret = ret | 4;
				// finalDay = "Wednesday";
				break;
			case 'R':
				ret = ret | 2;
				// finalDay = "Thursay";
				break;
			case 'F':
				ret = ret | 1;
				// finalDay = "Friday";
				break;
			default:
				continue;
			}

		}

		return ret;

	}

	/**
	 * Returns the long name of a single character day representation
	 * @param day
	 * @return
	 */
	private String getLongDay(String day) {

		char x = day.charAt(0);

		switch (x) {

		case 'M':
			return "Monday";
		case 'T':
			return "Tuesday";
		case 'W':
			return "Wednesday";
		case 'R':
			return "Thursay";
		case 'F':
			return "Friday";
		default:
			return "INVALID INPUT";
		}

	}

	/**
	 * The string representation of this final
	 */
	public String toString() {

		return "Final on " + getLongDay(this.finalDay) + " at "
				+ Constants.timeToString(this.finalTime);

	}

}
