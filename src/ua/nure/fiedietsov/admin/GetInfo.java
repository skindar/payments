/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.fiedietsov.DAO.Account;
import ua.nure.fiedietsov.DAO.DBManager;
import ua.nure.fiedietsov.payments.GetTransactions;

/**
* Admin base Servlet 
* It helps to send specialized query to DB.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/admin/getinfo")
public class GetInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(GetInfo.class);


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("/admin/getinfo#dopost started");
		
		Connection con = null;
		HttpSession session = request.getSession();
		Account acc = null;
		
		
		try {
			con = DBManager.getDBCon();
			acc = DBManager.getAccInfo(con, request.getParameter("selectAcc") );
			LOGGER.warn("Acc info  was getting");

		} catch (SQLException e) {
			LOGGER.error("Cannot execute querry", e);
		} finally {
			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					LOGGER.error("cannot close con", e);
				}

			}
		}
		session.setAttribute("accInfo", acc);
		response.sendRedirect("admin.jsp");
	}

}
