package servlets;


import helper.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller servlet that handles the dropdowns on Home.jsp and adding courses from Sections.jsp
 * Servlet implementation class addCourseServlet
 * 
 * @author Matt Kravchak
 * @author Kevin Cleveland
 * @author Kim Bradley
 */
@WebServlet("/Controller")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Used for dynamically creating and showing select options on Home.jsp
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @author Kim Bradley
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();	
		HttpSession session = request.getSession();
		DBHelper helper = new DBHelper();
		session.setAttribute("errorMessage", null);
		
		session.setAttribute("scheduleList", helper.getSchedule());
		/* If request gives the user selected requirement */
		if (request.getParameter("req") != null && (session.getAttribute("req") == ""
													|| session.getAttribute("req") == null)) {
			int req = Integer.parseInt(request.getParameter("req"));
			session.setAttribute("req",req);
			session.setAttribute("reqName", helper.getRequirement(req));
			//System.out.println("REQ");
			session.setAttribute("prefixList", helper.getPrefix(req));
		}
		/* If request gives the user selected prefix */
		if (request.getParameter("pfx") != "" && (session.getAttribute("req") != ""
													|| session.getAttribute("req") != null)) {
			//System.out.println("PFX");
			String pfx = request.getParameter("pfx");
			session.setAttribute("pfx", pfx);
			session.setAttribute("crsNoList", helper.getCourseNo((Integer)session.getAttribute("req"), pfx));
		}
		/* If the request gives the user selected course number */
		if (request.getParameter("crsNo") != "" && (session.getAttribute("pfx") != ""
													|| session.getAttribute("pfx") != null)) {
			//System.out.println("CRSNO");
			String crsNo = request.getParameter("crsNo");
			session.setAttribute("crsNo", crsNo);
			session.setAttribute("sectionList", helper.getSections((String)session.getAttribute("pfx"), crsNo));
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/Sections.jsp");
			dispatcher.forward(request, response);
		}
		else {
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/Home.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	/**
	 * Submits 'Add Course' from Sections.jsp
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @author Matt Kravchak
	 * @author Kevin Cleveland
	 * @author Kim Bradley
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();	
		HttpSession session = request.getSession();
		DBHelper helper = new DBHelper();
		session.setAttribute("errorMessage", "");
		
		if (request.getParameter("callNo") != null) {
			/* If the course was added to the schedule_list of DBHelper */
			if(helper.addCourse(Integer.parseInt(request.getParameter("callNo"))) ){
				session.setAttribute("scheduleList", helper.getSchedule());
				/* Clear session attributes and go back to Home.jsp */
				session.setAttribute("req", "");
				session.setAttribute("prx", "");
				session.setAttribute("prefixList", "");
				session.setAttribute("crsNo", "");
				session.setAttribute("crsNoList", "");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/Home.jsp");
				dispatcher.forward(request, response);
			}
			
			/* There is a schedule conflict, show error message */
			else{ 
				session.setAttribute("scheduleList", helper.getSchedule());
				session.setAttribute("errorMessage", "**CANNOT ADD COURSE** Delete conflicting course or add a different section.");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/Sections.jsp");
				dispatcher.forward(request, response);
			}
		}
	}

}

