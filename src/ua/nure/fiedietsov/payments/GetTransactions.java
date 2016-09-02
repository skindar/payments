/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.payments;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.fiedietsov.DAO.DBManager;
import ua.nure.fiedietsov.DAO.Transactions;
/**
* Servlet helps to get transactions from DB.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/pays/gettransactions")
public class GetTransactions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(GetTransactions.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("/pays/gettransactions#dopost started");
		
		HttpSession session = request.getSession();
		String account = request.getParameter("selectAcc");
		Connection con = null;
		List<Transactions> trancs = null;
		try {
			con = DBManager.getDBCon();
			trancs = DBManager.getTransactions(con, account);
			LOGGER.warn("Transactions was getting");

		} catch (SQLException e) {
			LOGGER.error("Cannot execute querry", e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LOGGER.error("cannot close con", e);

				}
			}
		}
		session.setAttribute("trancs", trancs);
		if (request.isUserInRole("admin"))response.sendRedirect("../admin/admin.jsp");
		else response.sendRedirect("./history.jsp");
	}

}
