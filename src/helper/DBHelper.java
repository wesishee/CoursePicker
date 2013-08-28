package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Database Helper class to retrieve requirement prefixes, requirement course numbers, and class
 * sections. Also has methods to get a course by its call number, delete a course, etc. User's
 * added courses are stored in the static variable 'schedule_list'.
 * 
 * @author Kim Bradley
 */
public class DBHelper {
	
	/* Database on MySQL on my VM number 26 is named 'csci4300' */
	final static String JDBC_URL = "jdbc:mysql://172.17.152.26/csci4300";
	// static String JDBC_URL = "jdbc:mysql://localhost/csci4300";
	
	/* Static array list of Course objects that have been added to the schedule */
	public static ArrayList<Course> schedule_list = new ArrayList<Course>();
	
	/* Get the string requirement for a specified requirement number from the table requirements */
	PreparedStatement getRequirementStatement;
	/* List all prefixes for a specified requirement from the table course_requirements */
	PreparedStatement listPrefixStatement;
	/* List all course numbers for a specified requirement and prefix from the table course_requirements */
	PreparedStatement listCourseNoStatement;
	/* List all sections for a specified prefix and course number from the table static_report_entry */
	PreparedStatement listSectionsStatement;
	/* Get the course for a specified call number from the table static_report_entry */
	PreparedStatement getCourseStatement;

	/**
	 * Constructor that instantiates the MySQL driver, connects to MySQL, and sets the prepared statements
	 * 
	 * @author Kim Bradley
	 */
	public DBHelper() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Instantiated MYSQL driver.");
			/* Log in as user='root' and password='csci4300' */
			Connection conn = DriverManager.getConnection(JDBC_URL, "root", "csci4300");
			System.out.println("Connected to MYSQL.");
			
			getRequirementStatement = conn.prepareStatement("select req from requirements where num=?");
			listPrefixStatement = conn.prepareStatement("select prefix from course_requirements where req=?");
			listCourseNoStatement = conn.prepareStatement("select courseNo from course_requirements where req=? and prefix=?");
			listSectionsStatement = conn.prepareStatement("select * from static_report_entry where prefix=? and courseNo=?");
			getCourseStatement = conn.prepareStatement("select * from static_report_entry where callNo=?");
			
			//addCourse(72690);
			//addCourse(24055);
		}
		catch (Exception e) {
			System.out.println("Error constructing DBHelper.\n" +
				e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Given a requirement number, returns the String representation of that requirement.
	 * 
	 * @param req requirement number
	 * @return String requirement name
	 * @author Kim Bradley
	 */
	public String getRequirement(int req) {
		String str = "";
		try {
			getRequirementStatement.setInt(1, req);
			ResultSet rs = getRequirementStatement.executeQuery();
			rs.next();
			str = rs.getString("req");
		}
		catch (Exception e) {
			System.out.println("Error getting requirement name.\n" +
					e.getClass().getName() + ": " + e.getMessage());
		}
		return str.trim();
	}
	
	/**
	 * Given a requirement's associated number, returns an array of the optional prefixes without duplicates.
	 * 
	 * @param req requirement number
	 * @return ArrayList of Strings for all prefixes from the given requirement
	 * @author Kim Bradley
	 */
 	public ArrayList<String> getPrefix(int req) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			listPrefixStatement.setInt(1, req);
			ResultSet rs = listPrefixStatement.executeQuery();
			String str;
			/* Loop through the result set and add prefixes to list until no more entries are left */
			while (rs.next()) {
				str = rs.getString("prefix");
				/* If list doesn't contain current prefix, add it to the list (prevent duplicates) */
				if (!list.contains(str)) { list.add(str); }
			}
		}
		catch (Exception e) {
			System.out.println("Error populating prefix list.\n" +
					e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * Given a requirement's associated number and a prefix, returns an array of the optional course numbers.
	 * 
	 * @param req requirement number
	 * @param pfx prefix to search
	 * @return ArrayList of Strings for all course numbers from the given requirement and prefix
	 * @author Kim Bradley
	 */
	public ArrayList<String> getCourseNo(int req, String pfx) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			listCourseNoStatement.setInt(1, req);
			listCourseNoStatement.setString(2, pfx);
			ResultSet rs = listCourseNoStatement.executeQuery();
			String str;
			/* Loop through the result set and add course numbers to list until no more entries are left */
			while (rs.next()) {
				str = rs.getString("courseNo").trim();
				/* If list doesn't contain current courseNo, add it to the list (prevent duplicates) */
				if (!list.contains(str)) { list.add(str); }
			}
		}
		catch (Exception e) {
			System.out.println("Error populating course number list.\n" +
					e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * Given a prefix and a course number, returns an array of the offered sections.
	 * 
	 * @param pfx prefix to search
	 * @param crsNo course number to search
	 * @return ArrayList of Course objects for all course numbers from the given requirement and prefix
	 * @author Kim Bradley
	 */
	public ArrayList<Course> getSections(String pfx, String crsNo) {
		ArrayList<Course> list = new ArrayList<Course>();
		try {
			listSectionsStatement.setString(1, pfx);
			listSectionsStatement.setString(2, crsNo);
			ResultSet rs = listSectionsStatement.executeQuery();
			int clNo;
			/* Loop through the result set, create Course object for each, and add to list until no
			 * more entries are left */
			while (rs.next()) {
				clNo = rs.getInt("callNo");
				list.add(getCourse(clNo));
			}
		}
		catch (Exception e) {
			System.out.println("Error populating sections list.\n" +
					e.getClass().getName() + ": " + e.getMessage());
		}
		return list;
	}
	
	/**
	 * Given a call number, return a course object for that call number.
	 * 
	 * @param clNo the call number to add
	 * @return course object for given call number
	 * @author Kim Bradley
	 */
	public Course getCourse(int clNo) {
		Course crs = new Course();
		try {
			getCourseStatement.setInt(1, clNo);
			ResultSet rs = getCourseStatement.executeQuery();
			if(rs.next()) {
			/* Populate course object with row attributes */
			crs.setTime(rs.getString("startTime"), rs.getString("endTime"));
			crs.setCallNo(rs.getInt("callNo"));
			crs.setBuilding(rs.getInt("building"));
			crs.setPrefix(rs.getString("prefix"));
			crs.setCourseNo(rs.getString("courseNo"));
			crs.setTitle(rs.getString("title"));
			crs.setInstructor(rs.getString("instructor"));
			crs.setHours(rs.getString("hours"));
			crs.setSession(rs.getString("session"));
			crs.setDays(rs.getString("days"));
			crs.setStartTime(rs.getString("startTime"));
			crs.setEndTime(rs.getString("endTime"));
			crs.setRoom(rs.getString("room"));
			crs.setCellScript();
			}
		}
		catch (Exception e) {
			System.out.println("Error getting course.\n" +
					e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
		return crs;
	}
	
	/**
	 * Given a call number, returns true if course was added to the schedule and false if there was a conflict.
	 * 
	 * @param clNo call number of course to add
	 * @return boolean for if the course was added or not
	 * @author Kim Bradley
	 */
	public boolean addCourse(int clNo) {
		Course newCrs = getCourse(clNo);
		/* Loop through schedule arrayList to compare each schedule course to new course */
		for (Course crs : schedule_list) {
			/* If conflict, do not add, return false */
			if (doesConflict(newCrs, crs)) { return false; }
		}
		schedule_list.add(newCrs);
		return true;
	}
	
	/**
	 * Given a call number, deletes it from schedule array
	 * 
	 * @param clNo call number of course to delete
	 * @return boolean for if the course was deleted or not
	 * @author Kim Bradley
	 */
	public boolean deleteCourse(int clNo) {
		Course delCrs = getCourse(clNo);
		for (Course crs : schedule_list) {
			if (crs.getCallNo() == delCrs.getCallNo()) {
				return schedule_list.remove(crs);
			}
		}
		return false;
	}
	
	/**
	 * Given two courses, return whether or not the two courses conflict by comparing both their start
	 * and end times and their days. Used by addCourse method to check conflicts.
	 * 
	 * @param crs1 course 1
	 * @param crs2 course 2
	 * @return boolean for if the two courses conflict
	 * @author Kim Bradley
	 */
	public boolean doesConflict(Course crs1, Course crs2) {
		/* Loop through the days of course 1 */
		for (String s : crs1.getDays()) {
			/* Loop through the days of course 2 to compare each to current course 1 day */
			for (String j : crs2.getDays()) {
				/* If current course 1 day equals current course 2 day */
				if (s.equals(j)) {
					/* If end time hour of course 1 is greater than start time hour of course 2 */
					if (crs1.getEndTime().get(0) > crs2.getStartTime().get(0)) {
						if (crs1.getStartTime().get(0) > crs2.getEndTime().get(0)) { return false; }
						return true;
					}
					/* Else if both hours equal */
					else if (crs1.getEndTime().get(0) == crs2.getStartTime().get(0)) {
						/* If course 1 end time minute is greater than course 2 end time minute */
						if (crs1.getEndTime().get(1) > crs2.getStartTime().get(1)) { return true; }
					}
				}
			}
		}
		/* Return false if method makes it through all if conditions without a return */
		return false;
	}
	
	/**
	 * Clear schedule array
	 * 
	 * @return boolean for schedule clear
	 * @author Kim Bradley
	 */
	public boolean clearSchedule() {
		schedule_list.clear();
		return true;
	}

	/**
	 * Get schedule array
	 * 
	 * @return boolean for schedule clear
	 * @author Kim Bradley
	 */
	public ArrayList<Course> getSchedule() {
		ArrayList<Course> list = schedule_list;
		return list;
	}
}

