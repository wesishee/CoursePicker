package test;

import java.util.ArrayList;

import helper.*;
import junit.framework.TestCase;

/**
 * Test Class that tests the helpers of the Course Picker app, including Course.java class and DBHelper class.
 * 
 * @author Kim Bradley
 */

public class HelperTest  extends TestCase {

	public void testCourse() {
		/* create a Course instance */
		Course instance = new Course();
		assertNotNull("Create Course object", instance);
		
		/* test get and set methods of various attributes */
		instance.setBuilding(1023);
		assertEquals("Set building", 1023, instance.getBuilding());
		instance.setInstructor("Dr. Bob");
		assertEquals("Set instructor", "Dr. Bob", instance.getInstructor());
		instance.setCallNo(4300);
		assertEquals("Set callNo", 4300, instance.getCallNo());
		/* test conversion methods (days, startTime, endTime) */
		instance.setDays("M W F ");
		ArrayList<String> days = new ArrayList<String>();
		days.add("M"); days.add("W"); days.add("F");
		assertEquals("Set days", days, instance.getDays());
		instance.setStartTime("0330P ");
		ArrayList<Integer> time = new ArrayList<Integer>();
		time.add(15); time.add(30);
		assertEquals("Set startTime", time, instance.getStartTime());
		instance.setEndTime("0800A ");
		time.clear();
		time.add(8); time.add(0);
		assertEquals("Set endTime", time, instance.getEndTime());
	}
	
	public void testDBHelper() {
		/* create a DBHelper instance */
		DBHelper instance = new DBHelper();
		assertNotNull("Create DBHelper object", instance);
		
		/* test getRequirement method for all requirement numbers */
		
		assertEquals("Requirement for req 1", "Cultural Diversity Requirement", instance.getRequirement(1));
		assertEquals("Requirement for req 2", "Environmental Literacy Requirement", instance.getRequirement(2));
		assertEquals("Requirement for req 3", "Core Curriculum I: Foundation Courses", instance.getRequirement(3));
		assertEquals("Requirement for req 4", "Core Curriculum II: Physical Sciences", instance.getRequirement(4));
		assertEquals("Requirement for req 5", "Core Curriculum II: Life Sciences", instance.getRequirement(5));
		assertEquals("Requirement for req 6", "Core Curriculum III: Quantitative Reasoning", instance.getRequirement(6));
		assertEquals("Requirement for req 7", "Core Curriculum IV: World Languages and Culture", instance.getRequirement(7));
		assertEquals("Requirement for req 8", "Core Curriculum IV: Humanities and Arts", instance.getRequirement(8));
		assertEquals("Requirement for req 9", "Core Curriculum V: Social Sciences", instance.getRequirement(9));
		assertEquals("Requirement for req 10", "Franklin College Foreign Language", instance.getRequirement(10));
		assertEquals("Requirement for req 11", "Franklin College Literature", instance.getRequirement(11));
		assertEquals("Requirement for req 12", "Franklin College Fine Arts/Philosophy/Religion", instance.getRequirement(12));
		assertEquals("Requirement for req 13", "Franlin College History", instance.getRequirement(13));
		assertEquals("Requirement for req 14", "Franklin CollegeSocial Sciences other than History", instance.getRequirement(14));
		assertEquals("Requirement for req 15", "Franklin College Biological Sciences", instance.getRequirement(15));
		assertEquals("Requirement for req 16", "Franklin College:Physical Sciences", instance.getRequirement(16));
		assertEquals("Requirement for req 17", "Franklin College Multicultural Requirement", instance.getRequirement(17));
		assertEquals("Requirement for req 18", "Core CurriculumVI: Major related courses", instance.getRequirement(18));
		assertEquals("Requirement for req 19", "Computer Science Major Courses", instance.getRequirement(19));
		
		/* test getPrefix method */
		ArrayList<String> inst = instance.getPrefix(5);
		ArrayList<String> curr = new ArrayList<String>();
		curr.add("ANTH");curr.add("BIOL");curr.add("CRSS");curr.add("ECOL");
		curr.add("FANR");curr.add("MARS");curr.add("GEOG");curr.add("HONS");
		curr.add("CHEM");curr.add("GEOL");curr.add("PHYS");curr.add("PBIO");
		assertEquals("Prefix for req 5", true, inst.containsAll(curr));
		
		inst.clear();
		curr.clear();
		
		/* test getCourseNo method */
		inst = instance.getCourseNo(5, "MARS");
		curr.add("1100");curr.add("1025H");curr.add("1020L");curr.add("1025L");
		assertEquals("Course Number for req 5 and prefix MARS", true, inst.containsAll(curr));
		
		inst.clear();
		curr.clear();
		
		/* test getSections method */
		ArrayList<Course> sect = instance.getSections("MARS", "1100");
		assertEquals("Sections for MARS 1100 length", 3, sect.size());
		/* check prefixes */
		assertEquals("Section 1 prefix for MARS 1100", "MARS", sect.get(0).getPrefix());
		assertEquals("Section 2 prefix for MARS 1100", "MARS", sect.get(1).getPrefix());
		assertEquals("Section 3 prefix for MARS 1100", "MARS", sect.get(2).getPrefix());
		/* check call numbers */
		assertEquals("Section 1 course number for MARS 1100", "1100", sect.get(0).getCourseNo());
		assertEquals("Section 2 course number for MARS 1100", "1100", sect.get(1).getCourseNo());
		assertEquals("Section 3 course number for MARS 1100", "1100", sect.get(2).getCourseNo());
		/* check various attributes from sections to verify Course objects */
		ArrayList<String> days = new ArrayList<String>();
		ArrayList<Integer> time = new ArrayList<Integer>();
		assertEquals("Section 1 call number for MARS 1100", 35862, sect.get(0).getCallNo());
		assertEquals("Section 1 instructor for MARS 1100", "KING", sect.get(0).getInstructor());
		days.add("T"); days.add("R");
		assertEquals("Section 1 days for MARS 1100", days, sect.get(0).getDays());
		days.clear();
		time.add(14); time.add(0);
		assertEquals("Section 1 start time for MARS 1100", time, sect.get(0).getStartTime());
		time.clear();
		time.add(15); time.add(15);
		assertEquals("Section 1 end time for MARS 1100", time, sect.get(0).getEndTime());
		time.clear();
		/* section 3 */
		assertEquals("Section 3 call number for MARS 1100", 75864, sect.get(2).getCallNo());
		assertEquals("Section 3 instructor for MARS 1100", "GREENE", sect.get(2).getInstructor());
		days.add("M"); days.add("W"); days.add("F");
		assertEquals("Section 3 days for MARS 1100", days, sect.get(2).getDays());
		days.clear();
		time.add(10); time.add(10);
		assertEquals("Section 3 start time for MARS 1100", time, sect.get(2).getStartTime());
		time.clear();
		time.add(11); time.add(0);
		assertEquals("Section 3 end time for MARS 1100", time, sect.get(2).getEndTime());
		time.clear();
		
		/* test getCourse method */
		Course crs = instance.getCourse(73063);
		assertEquals("Course 12782 prefix", "MUSI", crs.getPrefix());
		assertEquals("Course 12782 course number", "3470", crs.getCourseNo());
		assertEquals("Course 12782 title", "KEYBD MUSICIANSHIP", crs.getTitle());
		assertEquals("Course 12782 instructor", "THOMAS, M.", crs.getInstructor());
		assertEquals("Course 12782 hours", "1.00", crs.getHours());
		assertEquals("Course 12782 session", "Thru", crs.getSession());
		days.add("W");
		assertEquals("Course 12782 days", days, crs.getDays());
		days.clear();
		time.add(15); time.add(35);
		assertEquals("Course 12782 start time", time, crs.getStartTime());
		time.clear();
		time.add(16); time.add(25);
		assertEquals("Course 12782 end time", time, crs.getEndTime());
		time.clear();
		assertEquals("Course 12782 building", 1691, crs.getBuilding());
		assertEquals("Course 12782 room", "0504", crs.getRoom());
		
		/* test addCourse method */
		assertEquals("Empty schedule", 0, DBHelper.schedule_list.size());
		assertEquals("Add course 24055", true, instance.addCourse(24055));
		assertEquals("Schedule for add", 1, DBHelper.schedule_list.size());
		assertEquals("Added course 24055 callNo", 24055, DBHelper.schedule_list.get(0).getCallNo());
		/* conflict course add */
		assertEquals("Add conflict course 13060", false, instance.addCourse(13060));
		assertEquals("Schedule for conflict add", 1, DBHelper.schedule_list.size());

		/* test deleteCourse method */
		instance.addCourse(72690);
		assertEquals("Delete course 12345", false, instance.deleteCourse(12345));
		assertEquals("Schedule for non-delete", 2, DBHelper.schedule_list.size());
		assertEquals("Delete course 24055", true, instance.deleteCourse(24055));
		assertEquals("Schedule for delete", 1, DBHelper.schedule_list.size());

		/* test doesConflict method */
		assertEquals("Conflict for section 1 and section 2", true, instance.doesConflict(sect.get(0), sect.get(1)));
		assertEquals("Conflict for section 1 and section 2", false, instance.doesConflict(sect.get(0), sect.get(2)));
		
		/* test schedule list */
		DBHelper instance2 = new DBHelper();
		assertEquals("Add course 24055 callNo with instance2", true, instance2.addCourse(24055));
		assertEquals("Schedule list update over instance", 2, DBHelper.schedule_list.size());
		assertEquals("Schedule list item 1", 72690, DBHelper.schedule_list.get(0).getCallNo());
		assertEquals("Schedule list item 2", 24055, DBHelper.schedule_list.get(1).getCallNo());

		/* test clearSchedule method */
		assertEquals("Schedule clear", true, instance.clearSchedule());
	}

}
