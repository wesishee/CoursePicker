<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- @author Wes Ishee -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UGA Course Picker</title>

<script type="text/javascript">
//@author: Kim Bradley
//submits to controller with correct prefixes of the selected requirement
function reqRefresh(sel_req) {
	var refresh = document.getElementById("refresh_form");
    refresh.req.value = sel_req;
    refresh.submit();
}

//submites to controller with correct course numbers of the selected prefix
function pfxRefresh(sel_pfx) {
	var refresh = document.getElementById("refresh_form");
	refresh.pfx.value = sel_pfx;
    refresh.submit();
}

//submits to controller with selected course number
function crsNoRefresh(sel_crsNo) {
	var refresh = document.getElementById("refresh_form");
	refresh.crsNo.value = sel_crsNo;
	refresh.submit();
	}

//@author: Wes Ishee
//deletes row with given row index and call number and submits to Schedule
function deleteRow(row_index,row_call_no) {
	var table = document.getElementById('table_schedule');
	var del_form = document.getElementById('delete_form');
    del_form.delCallNo.value = row_call_no;
    table.deleteRow(row_index);
    del_form.submit();
}


//deletes all rows and submits to Schedule
function clearSchedule() {
	var r = confirm("Are you sure you want to clear all courses from schedule?");
	if (r==true) {
		var table = document.getElementById('table_schedule');
		var del_form = document.getElementById('delete_form');
		del_form.clearSchedule.value = 'true';
		for(var i=table.rows.length-1; i>0; i--)
			table.deleteRow(i);
		del_form.submit();
	}
}
</script>
</head>
<body>
	<img src="ugahead.jpg" alt="University of Georgia" >
	<h1>UGA Course Picker</h1>
	
	<!-- Hidden form to submit dropdown selections
	@author Wes Ishee -->
	<form method="get" action="Controller" id="refresh_form" >
		<input type="hidden" name="req"/>
		<input type="hidden" name="pfx"/>
		<input type="hidden" name="crsNo"/>
		</form>
		
	<!-- Table displaying user selected requirement, department(prefix),
	and course numbers.
	@author Wes Ishee -->
	<table id="table">
	<tr id="requirement"><td>Requirement: </td>
		<td><b>${reqName }</b></td>
	</tr>
	
	<tr id="department"><td>Select Department</td>
		<td><b>${pfx }</b></td>
	</tr>
	
	<tr id="course"><td>Select Course</td>
		<td><b>${crsNo }</b></td>
	</tr></table>
	
	<!-- Table displaying all sections for user selected prefix and course number.
	@author Wes Ishee -->
	<table id="table_sections" class=.visible border="1">
		<tr>
			<th></th>
			<th>Line</th>
			<th>Department</th>
			<th>Course #</th>
			<th>Call #</th>
			<th>Hrs</th>
			<th>Time</th>
			<th>Days</th>
			<th>Bldg-Room</th>
			<th>Sess</th>
			<th>Instructor</th>
		</tr>
	
	<c:forEach items="${sectionList }" var="course" varStatus="line">
		<tr>
			<th><form method="post" action="Controller">
				<input type="hidden" name="callNo" value=${course.callNo }>
				<input type="submit" value="Add Course"></form></th>
			<th>${line.count }</th>
			<th>${course.prefix }</th>
			<th>${course.courseNo }</th>
			<th>${course.callNo }</th>
			<th>${course.hours }Hrs</th>
			<th>${course.time }</th>
			<th>${course.days }</th>
			<th>${course.building }-${course.room }</th>
			<th>${course.session }</th>
			<th>${course.instructor }</th>
		</tr>
	</c:forEach></table>

	<!-- Hidden form to submit a course delete or a schedule clear
	@author Wes Ishee -->
	<form method="post" action="Schedule" id="delete_form" >
		<input type="hidden" name="delCallNo"/>
		<input type="hidden" name="clearSchedule"/></form>

	<p style="color:red;font-weight:bold;">${errorMessage }
	
	</p>
	<h3 style="color:green">Current Schedule
	
	</h3>
	
	<!-- Table containing the schedule.  Will be updated whenever a new course is added
	or deleted.
	@author Wes Ishee -->
	<table id="table_schedule" class=.visible border="1"bordercolor="grey">
		<tr>
			<th style="color:grey;"></th>
			<th style="color:grey;">Line</th>
			<th style="color:grey;">Department</th>
			<th style="color:grey;">Course #</th>
			<th style="color:grey;">Call #</th>
			<th style="color:grey;">Hrs</th>
			<th style="color:grey;">Time</th>
			<th style="color:grey;">Days</th>
			<th style="color:grey;">Bldg-Room</th>
			<th style="color:grey;">Sess</th>
			<th style="color:grey;">Instructor</th>
		</tr>
	<c:forEach items="${scheduleList }" var="course" varStatus="line" >
		<tr>
			<th><button onclick="deleteRow(${line.count },${course.callNo })"><img src="removeButton.jpeg" alt="Delete" height="30" width="30"></button></th>
			<th style="color:grey;">${line.count }</th>
			<th style="color:grey;">${course.prefix }</th>
			<th style="color:grey;">${course.courseNo }</th>
			<th style="color:grey;">${course.callNo }</th>
			<th style="color:grey;">${course.hours }Hrs</th>
			<th style="color:grey;">${course.time }</th>
			<th style="color:grey;">${course.days }</th>
			<th style="color:grey;">${course.building }-${course.room }</th>
			<th style="color:grey;">${course.session }</th>
			<th style="color:grey;">${course.instructor }</th>
		</tr>
	</c:forEach>
</table>
	
	<table>
	<tr><form method="get" action="Home"><input type="submit" value="Add A Different Course"></form></tr>
	<tr><button onclick="clearSchedule()">Clear All Courses</button> </tr>
	<tr><form method="get" action="Schedule"><button type="submit" >View Weekly Schedule</button></form></tr>
	</table>
	
</body>
</html>
