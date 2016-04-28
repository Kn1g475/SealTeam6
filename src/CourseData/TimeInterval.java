package CourseData;
/**
 * Handles time periods
 * @author Sam Levy
 *
 */
public class TimeInterval {
	private int startTime;
	private int endTime;
	/**
	 * Constructor
	 * @param start
	 * @param end
	 */
	public TimeInterval(int start, int end) {
		startTime = start;
		endTime = end;
	}
	//getters
	public int getStartTime() {
		return startTime;
	}
	public int getEndTime() {
		return endTime;
	}//end Getters
	/**
	 * To String
	 */
	public String toString() {
		return String.format("%d - %d", getStartTime(), getEndTime());
	}
	/**
	 * Checks to see if an interval equals this one
	 * @param test
	 * @return
	 */
	public boolean equals(TimeInterval test) {
		return getStartTime() == test.getStartTime() && getEndTime() == test.getEndTime();
	}
	/**
	 * checks to see if and interval overlaps with this interval
	 * @param temp
	 * @return
	 */
	public boolean doesOverlap(TimeInterval temp) {
		return (temp.getStartTime() >= this.getStartTime() && temp.getStartTime() <= this.getEndTime()) ||
				(temp.getEndTime() >= this.getStartTime() && temp.getEndTime() <= this.getEndTime());
	}
}
