package helper;

import java.util.ArrayList;

/**
 * Represents a course from Course Picker database that includes a variable for holding the
 * JavaScript to create this course's cells on schedule canvas.
 * 
 * @author Kim Bradley
 */
public class Course {
	
	/* Important column attributes from static_report_entry where the data types are equivalent to
	 * given static_report_entry columns.
	 */
	private int callNo;
	private String prefix;
	private String courseNo;
	private String title;
	private String instructor;
	private String hours;
	private String session;
	private ArrayList<String> days;
	private ArrayList<Integer> startTime;
	private ArrayList<Integer> endTime;
	private int building;
	private String room;
	
	private String time; // string representation of start and end times
	private ArrayList<String> cellScript; /* ArrayList of strings for each line of JavaScript to
	write this courses cell in Canvas */
	
	/**
	 * Constructor
	 */
	public Course() { super(); }
	
	/**
	 * @return the callNo
	 */
	public int getCallNo() {
		return callNo;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @return the courseNo
	 */
	public String getCourseNo() {
		return courseNo;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the instructor
	 */
	public String getInstructor() {
		return instructor;
	}

	/**
	 * @return the hours
	 */
	public String getHours() {
		return hours;
	}

	/**
	 * @return the session
	 */
	public String getSession() {
		return session;
	}

	/**
	 * @return the days
	 */
	public ArrayList<String> getDays() {
		return days;
	}

	/**
	 * @return the startTime
	 */
	public ArrayList<Integer> getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public ArrayList<Integer> getEndTime() {
		return endTime;
	}

	/**
	 * @return the building
	 */
	public int getBuilding() {
		return building;
	}

	/**
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}
	
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * @return the cellScript
	 */
	public ArrayList<String> getCellScript() {
		return cellScript;
	}

	
	/**
	 * @param callNo the callNo to set
	 */
	public void setCallNo(int callNo) {
		this.callNo = callNo;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix.trim();
	}

	/**
	 * @param courseNo the courseNo to set
	 */
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo.trim();
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title.trim();
	}

	/**
	 * @param instructor the instructor to set
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor.trim();
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(String hours) {
		this.hours = hours.trim();
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(String session) {
		this.session = session.trim();
	}

	/**
	 * Stores the given string as an array of Strings representing the days that this course meets.
	 * Stored as ArrayList (rather than String array) to keep all arrays constant.
	 * 
	 * @param days the days to set
	 * @author Kim Bradley
	 */
	public void setDays(String days) {
		ArrayList<String> str = new ArrayList<String>();
		days.trim();
		/* Divide days string by " " and store in string array */
		String[] dys = days.split(" ");
		/* Loop through string array and put items into array list of arrays*/
		for (int i=0; i<dys.length; i++) {
			/* If the string only contains letters (avoid whitespace strings) */
			if(dys[i].matches("[a-zA-Z]+")) { str.add(dys[i].trim()); }
		}
		this.days = str;
	}

	/**
	 * Stores the given string as an array of Integers where the first int is the hour, and the
	 * second int is the minute. Times saved in military time.
	 * 
	 * @param startTime the startTime to set
	 * @author Kim Bradley
	 */
	public void setStartTime(String startTime) {
		startTime.trim();
		ArrayList<Integer> time = new ArrayList<Integer>();
		/* If class has no time, convert to zeros */
		if (startTime.matches("\\s+") || startTime.isEmpty()) {
			time.add(0); time.add(0);
			this.startTime = time;
			return;
		}
		int hour = Integer.parseInt(startTime.substring(0,2));
		int minute = Integer.parseInt(startTime.substring(2,4));
		/* If time is P, convert hour to military time */
		if (startTime.charAt(4) == 'P') {
			if (hour != 12) {hour += 12; }
		}
		time.add(hour);
		time.add(minute);
		this.startTime = time;
	}

	/**
	 * Stores the given string as an array of Integers where the first int is the hour, and the
	 * second int is the minute. Times saved in military time.
	 * 
	 * @param endTime the endTime to set
	 * @author Kim Bradley
	 */
	public void setEndTime(String endTime) {
		endTime.trim();
		ArrayList<Integer> time = new ArrayList<Integer>();
		/* If class has no time, convert to zeros */
		if (endTime.matches("\\s+") || endTime.isEmpty()) {
			time.add(0); time.add(0);
			this.endTime = time;
			return;
		}
		int hour = Integer.parseInt(endTime.substring(0,2));
		int minute = Integer.parseInt(endTime.substring(2,4));
		/* If time is P, convert hour to military time */
		if (endTime.charAt(4) == 'P') {
			if (hour != 12) {hour += 12; }
		}
		time.add(hour);
		time.add(minute);
		this.endTime = time;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(int building) {
		this.building = building;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(String room) {
		this.room = room.trim();
	}
	
	/**
	 * Creates a string of start and end times to be used in printing out the course in a table.
	 * 
	 * @param startTime the start time to set
	 * @param endTime the end time to set
	 * @author Kim Bradley
	 */
	public void setTime(String startTime, String endTime) {
		this.time = (startTime.substring(0,2) + ":" +
					startTime.substring(2) + "-" +
					endTime.substring(0,2) + ":" +
					endTime.substring(2));
	}
	
	/**
	 * Creates the cell JavaScript for this course with the proper location on the canvas, and is
	 * only called after the rest of the Course attributes have been set.
	 * 
	 * @author Kim Bradley
	 */
	public void setCellScript() {
		ArrayList<String> str = new ArrayList<String>();
		
		int start = this.startTime.get(0)*60 + this.startTime.get(1);
		int end = this.endTime.get(0)*60 + this.endTime.get(1);
		String text = this.prefix + " " + this.courseNo;
		/* Loop through each day in the ArrayList days for this course, and creates a cell for each day */
		for (String i : this.days) {
			if (i.equals("M")) {
				str.add("ctx.globalAlpha = 0.5;"); // make cell background slightly transparent
				str.add("ctx.fillRect(145," + (start-465) + ",100," + (end-start) + ");"); // draw rectangle
				str.add("ctx.globalAlpha = 1;"); // make text opaque
				str.add("ctx.strokeText('" + text + "',150," + (start-455) + "+5,90);"); // draw course name
				str.add("ctx.strokeText('" + this.time + "',150," + (start-455) + "+30,90);"); // draw time, offset by 25
			}else if (i.equals("T")) {
				str.add("ctx.globalAlpha = 0.5;");
				str.add("ctx.fillRect(245," + (start-465) + ",100," + (end-start) + ");");
				str.add("ctx.globalAlpha = 1;");
				str.add("ctx.strokeText('" + text + "',250," + (start-455) + "+5,90);");
				str.add("ctx.strokeText('" + this.time + "',250," + (start-455) + "+30,90);");
			}else if (i.equals("W")) {
				str.add("ctx.globalAlpha = 0.5;");
				str.add("ctx.fillRect(345," + (start-465) + ",100," + (end-start) + ");");
				str.add("ctx.globalAlpha = 1;");
				str.add("ctx.strokeText('" + text + "',350," + (start-455) + "+5,90);");
				str.add("ctx.strokeText('" + this.time + "',350," + (start-455) + "+30,90);");
			}else if (i.equals("R")) {
				str.add("ctx.globalAlpha = 0.5;");
				str.add("ctx.fillRect(445," + (start-465) + ",100," + (end-start) + ");");
				str.add("ctx.globalAlpha = 1;");
				str.add("ctx.strokeText('" + text + "',450," + (start-455) + "+5,90);");
				str.add("ctx.strokeText('" + this.time + "',450," + (start-455) + "+30,90);");
			}else if (i.equals("F")) {
				str.add("ctx.globalAlpha = 0.5;");
				str.add("ctx.fillRect(545," + (start-465) + ",100," + (end-start) + ");");
				str.add("ctx.globalAlpha = 1;");
				str.add("ctx.strokeText('" + text + "',550," + (start-455) + "+5,90);");
				str.add("ctx.strokeText('" + this.time + "',550," + (start-455) + "+30,90);");
			}
		}
		this.cellScript = str;
	}
	
}