import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
	
@SuppressWarnings("serial")
public class OpenStackMeetingsServlet extends HttpServlet {
		
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException
		{
			HttpSession session = request.getSession(true);
			
			String url = request.getRequestURL().toString();
			
			if (session.isNew()) {
				String encodedURL = response.encodeURL(url);				
				writeResponse(request, response, "no session", encodedURL);
			}			
			else {
				String encodedURL = response.encodeURL(url);				
				writeResponse(request, response, "in session", encodedURL);		
			}
			
		}
		private int getYear (HttpServletRequest request, HttpServletResponse response) throws IOException
		{
			try{
				return Integer.parseInt(request.getParameter("year"));
			}
			catch (NumberFormatException e){
				return -1;
			}
		}
		private String getProject (HttpServletRequest request, HttpServletResponse response) throws IOException
		{
			return request.getParameter("project");
		}
		private String getSession (HttpServletRequest request, HttpServletResponse response) throws IOException
		{
			return request.getParameter("session");
		}
		private boolean endSession (HttpServletRequest request, HttpServletResponse response) throws IOException
		{
			if (request.getParameter("session") != null && request.getParameter("session").equals("end"))
				return true;
			return false;
		}
		private void writeResponse(HttpServletRequest request, HttpServletResponse response, String you, String url) throws IOException {
			endSession(request, response);
			PrintWriter pw = response.getWriter();
			pw.println("<html>");
			
			if (you.equalsIgnoreCase("no session") && ! (request.getParameter("session") != null && getSession(request, response).equals("start")) ) {
				pw.println("No session has been started");
				pw.println("<br> To start a session, click <a href=" + url + "> here </a>");
			}
			else{
				pw.println("Session started <br>");
				if(getYear(request, response) > 0)
					pw.println("year = " + getYear(request, response) + "<br>");
				if(getProject(request, response) != null)
					pw.println("project = " + getProject(request, response) + "<br>");
			}
			pw.println("</html>");			
		}
}