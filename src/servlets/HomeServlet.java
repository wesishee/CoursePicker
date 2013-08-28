package servlets;

import helper.DBHelper;

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
 * Controller servlet that handles forwards back to Home.jsp and delete course/clear schedule form Home.jsp
 * Servlet implementation class HomeServlet
 * 
 * @author Kim Bradley
 */
@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Resets all session attributes and forwards back to Home.jsp
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @author Kim Bradley
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();	
		HttpSession session = request.getSession();
		
		session.setAttribute("req", "");
		session.setAttribute("prx", "");
		session.setAttribute("prefixList", "");
		session.setAttribute("crsNo", "");
		session.setAttribute("crsNoList", "");
		session.setAttribute("errorMessage", "");
		
		RequestDispatcher dispatcher = ctx.getRequestDispatcher("/Home.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Used when submitting a delete course or clear schedule command from Home.jsp
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @author Kim Bradley
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();	
		HttpSession session = request.getSession();
		DBHelper helper = new DBHelper();
		session.setAttribute("req", "");
		session.setAttribute("prx", "");
		session.setAttribute("prefixList", "");
		session.setAttribute("crsNo", "");
		session.setAttribute("crsNoList", "");
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
		RequestDispatcher dispatcher = ctx.getRequestDispatcher("/Home.jsp");
		dispatcher.forward(request, response);
	}

}
