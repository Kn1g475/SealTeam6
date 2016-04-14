package CourseData;

public class TimeInterval {
	private int startTime;
	private int endTime;

	public TimeInterval(int start, int end) {
		startTime = start;
		endTime = end;
	}
	public int getStartTime() {
		return startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public String toString() {
		return String.format("%d - %d", getStartTime(), getEndTime());
	}
	public boolean equals(TimeInterval test) {
		return getStartTime() == test.getStartTime() && getEndTime() == test.getEndTime();
	}
	public boolean doesOverlap(TimeInterval temp) {
		return (temp.getStartTime() >= this.getStartTime() && temp.getStartTime() <= this.getEndTime()) ||
				(temp.getEndTime() >= this.getStartTime() && temp.getEndTime() <= this.getEndTime());
	}
	public boolean inInterval(int time) {
		return time >= getStartTime() && time < getEndTime();
	}
}
