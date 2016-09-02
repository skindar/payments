/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.fiedietsov.DAO.DBManager;
import ua.nure.fiedietsov.DAO.User;
/**
* Servlet helps to get transactions from DB.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/unauth/regis")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Registration.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("registration.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("/unauth/regis#dopost started");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		boolean flag = false;
		if(!pass.equals((String)request.getParameter("repass"))){
			response.sendRedirect("registration.jsp");
			flag = true;
		}
		User user = new User(name, email, GenerateMD5.generate(pass));
		Connection con = DBManager.getDBCon();

		try {
			DBManager.createUser(con, user);
			LOGGER.debug("User " + email + " created correctly");		
		} catch (SQLException e) {
			response.getWriter().println(e);
			LOGGER.error("User create failed", e);
		} finally{
			if ( con != null){
				try {
					con.close();
				} catch (SQLException e) {
					LOGGER.error("cannot close con", e);
					e.printStackTrace();
				}
			}
		}
		if (!flag) response.sendRedirect("/");
	}

}
