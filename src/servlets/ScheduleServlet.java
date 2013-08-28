package servlets;

import helper.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller servlet that handles displaying the schedule canvas and delete course/clear
 * schedule from Sections.jsp
 * Servlet implementation class ControllerServlet
 * 
 * @author Kim Bradley
 */
@WebServlet("/Schedule")
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Used when submitting 'View Weekly Schedule'
	 * (displays the schedule of courses by weekly layout through HTML5 Canvas and JavaScript)
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @author Kim Bradley
	 * @author Kevin Cleveland
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		DBHelper helper = new DBHelper();
		session.setAttribute("req", "");
		session.setAttribute("prx", "");
		session.setAttribute("prefixList", "");
		session.setAttribute("crsNo", "");
		session.setAttribute("crsNoList", "");
		session.setAttribute("errorMessage", "");
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html"); 
		out.print("<html><head>");
		out.print("<h1 align='center'>Weekly Schedule");
		out.print("");
		out.print("</h1>");
		out.print("<h4 alight = 'right'><a href='Home.jsp'>Back to Home</a></h4>");
		
		/* Create canvas object to add course cells onto */
		out.print("<canvas id='canvasSchedule' width='745' height='900'>Your browser does not support HTML5 Canvas.</canvas>");
		out.print("<script>");
		
		out.print("var c = document.getElementById('canvasSchedule');");
		out.print("var ctx = c.getContext('2d');");
		out.print("ctx.globalAlpha = 1;"); // make sure everything is drawn opaque
		out.print("ctx.lineJoin='round';");
		
		/* Draw lines for every 30 minutes from 8:00am to 10:00pm */
		out.print("var hours = ['8:00am', '8:30am', '9:00am', '9:30am','10:00am', '10:30am','11:00am', '11:30am'," +
				"'12:00am', '12:30pm','1:00pm', '1:30pm','2:00pm', '2:30pm','3:00pm', '3:30pm','4:00pm', '4:30pm'," +
				"'5:00pm', '5:30pm','6:00pm', '6:30pm','7:00pm', '7:30pm','8:00pm', '8:30pm','9:00pm', '9:30pm'," +
				"'10:00pm', '10:30pm'];");
		out.print("ctx.strokeStyle='#E6E6E6';");
		out.print("ctx.textAlign = 'right';");
		out.print("ctx.font = '10px sans-serif';");
		out.print("ctx.beginPath();");
		out.print("var k=0;");
		out.print("for (var i=15; i<895; i+=30) {");
			out.print("ctx.fillText(hours[k],45,i,45);");
			out.print("ctx.moveTo(45,i);");
			out.print("ctx.lineTo(885,i);");
			out.print("ctx.stroke();");
			out.print("k++; }");
		
		/* Greyout Sunday and Saturday */
		out.print("ctx.fillStyle = '#F2F2F2';");
		out.print("ctx.fillRect(45,15,100,885);");
		out.print("ctx.fillRect(645,15,100,885);");
		
		/* Draw lines for each day of the week */
		out.print("ctx.fillStyle = '#000000';");
		out.print("var days = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];");
		out.print("ctx.strokeStyle='#000000';");
		out.print("ctx.textAlign = 'center';");
		out.print("ctx.font = '13px sans-serif';");
		out.print("k=0;");
		out.print("for (var i=45; i<700; i+=100) {");
			out.print("ctx.fillText(days[k],i+50,10,100);");
			out.print("ctx.strokeRect(i,15,100,885);");
			out.print("k++;}");
			
		/* Draw cells for each course in schedule */
		for (Course crs : helper.getSchedule()) {
			out.println("ctx.lineJoin='round';");
			out.println("ctx.fillStyle = '#'+Math.floor(Math.random()*16777215).toString(16);"); // generate random color
			out.println("ctx.textAlign = 'left';");
			out.println("ctx.font = '15px sans-serif';");
			
			/* Print cellScript ArrayList for current course in schedule */
			for (String str : crs.getCellScript()) {
				out.println(str);
				//System.out.println(str);
			}
		}	
		out.print("</script></head><body></body></html>");

	}
	
	/**
	 * Used when submitting a delete course or clear schedule command from Sections.jsp
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @author Matt Kravchak
	 * @author Kim Bradley
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();	
		HttpSession session = request.getSession();
		DBHelper helper = new DBHelper();
		
		session.setAttribute("errorMessage", "");
		
		/* If user selected clear schedule */
		if (request.getParameter("clearSchedule").equals("true")) {
			helper.clearSchedule();
		}
		/* If user selected delete certain course */
		else if (request.getParameter("delCallNo") != null) {
			int callNo = Integer.parseInt(request.getParameter("delCallNo"));
			helper.deleteCourse(callNo);
		}
		
		session.setAttribute("scheduleList", helper.getSchedule());
		RequestDispatcher dispatcher = ctx.getRequestDispatcher("/Sections.jsp");
		dispatcher.forward(request, response);
	}

}