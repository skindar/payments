/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.payments;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
* Logout Servlet removes attr from session.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/pays/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Logout.class); 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("/pays/logout#doget started");
		
		HttpSession session = request.getSession();
		session.removeAttribute("userEmail");
		session.invalidate();
		request.logout();
		response.sendRedirect("/");
		LOGGER.debug("user unlogged");

	}
}
