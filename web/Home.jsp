<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- @author Wes Ishee
@author Kim Bradley-->
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
	//@author: Kim Bradley
	//submites to controller with correct course numbers of the selected prefix
	function pfxRefresh(sel_pfx) {
		var refresh = document.getElementById("refresh_form");
		refresh.pfx.value = sel_pfx;
        refresh.submit();
	}
	//@author: Kim Bradley
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
	
	//@author Kim Bradley
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
		
	<!-- Table displaying dropdown options of requirement, department(prefix),
	and course numbers. Will be dynamically generated when options are selected.
	@author Kim Bradley
	@author Wes Ishee -->	
	<table id="table">
	<tr id="requirement"><td>Requirement: </td>
		<td><select id="requiremet_list" onchange="reqRefresh(this.options[this.selectedIndex].value)">
			<option disabled="disabled" selected>Choose a Requirement</option>
			<option value="1" >Cultural Diversity Requirement</option>
			<option value="2" >Environmental Literacy Requirement</option>
			<option value="3" >Core Curriculum I: Foundation Courses</option>
			<option value="4" >Core Curriculum II: Physical Sciences</option>
			<option value="5" >Core Curriculum III: Life Sciences</option>
			<option value="6" >Core Curriculum IV: Quantitative Reasoning</option>
			<option value="7" >Core Curriculum V: World Languages and Culture</option>
			<option value="8" >Core Curriculum VI: Humanities and Arts</option>
			<option value="9" >Core Curriculum VII: Social Sciences</option>
			<option value="10" >Franklin College Foreign Language</option>
			<option value="11" >Literature</option>
			<option value="12" >Franklin College Fine Arts</option>
			<option value="13" >History</option>
			<option value="14" >Franklin College Social Science</option>
			<option value="15" >Franklin College Biological Science</option>
			<option value="16" >Franklin College Physical Science</option>
			<option value="17" >Franklin College Multicultural</option>
			<option value="18" >Core Curriculum VIII: Major Requirements</option>
			<option value="19" >Computer Science Major Courses</option>
		</select></td>
	</tr>
	
	<tr id="department"><td>Select Department</td>
		<td><select onchange = "pfxRefresh(this.options[this.selectedIndex].value)">
			<option disabled="disabled" selected>Choose a Department</option>
			<c:forEach items="${prefixList }" var="prefix">
			<option value=${prefix }>${prefix }</option></c:forEach>
		</select></td>
	</tr>
	
	<tr id="course"><td>Select Course</td>
		<td><select onchange = "crsNoRefresh(this.options[this.selectedIndex].value)">
			<option disabled="disabled" selected>Choose a Course</option>
			<c:forEach items="${crsNoList }" var="crsNo">
			<option value=${crsNo }>${crsNo }</option></c:forEach>
		</select></td>
	</tr></table>
	
	<p style="color:red;font-weight:bold;">${errorMessage }</p>
	
	
	<!-- Hidden form to submit a course delete or a schedule clear
	@author Wes Ishee -->
	<form method="post" action="Home" id="delete_form" >
		<input type="hidden" name="delCallNo"/>
		<input type="hidden" name="clearSchedule"/></form>
	
	<h3 style="color:green">Current Schedule
	
	</h3>
		
	<!-- Table containing the schedule.  Will be updated whenever a new course is added
	or deleted.
	@author Wes Ishee
	@author Kim Bradley -->
	<table id="table_schedule" class=.visible border="1">
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
	<c:forEach items="${scheduleList }" var="course" varStatus="line" >
		<tr>
			<th><button onclick="deleteRow(${line.count },${course.callNo })"><img src="removeButton.jpeg" alt="Delete" height="30" width="30"></button></th>
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
	</c:forEach>
</table>
	
	<table>
	<tr><button onclick="clearSchedule()">Clear All Courses</button> </tr>
	<tr><form method="get" action="Schedule"><button type="submit" >View Weekly Schedule</button></form></tr>
	</table>
		
</body>
</html>