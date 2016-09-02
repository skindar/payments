/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.payments;

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

/**
* WebServlet AddAccount. 
* It helps to add a new account to DB 
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/pays/addnew")
public class AddAccount extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(AddAccount.class);
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		String type = request.getParameter("select") + " ";
		String exp = request.getParameter("exp") + " ";
		String last = request.getParameter("last");

		Connection con = DBManager.getDBCon();
		String currEmail = request.getUserPrincipal().getName();
		try {
			DBManager.addAccount(con, currEmail, account, type + exp + last);
			LOGGER.warn("Card " + account + " was adding successfully to " + currEmail);		
		} catch (SQLException e) {
			response.getWriter().println(e);
			LOGGER.error("Card adding failed ", e);
		}
		response.sendRedirect("/");
	}

}
