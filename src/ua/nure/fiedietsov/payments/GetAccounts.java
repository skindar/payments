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

import ua.nure.fiedietsov.DAO.Account;
import ua.nure.fiedietsov.DAO.DBManager;
/**
* The main servlet. It all starts from this servlet
* Get's accounts info, put's some attribute and other.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/pays/getaccounts")
public class GetAccounts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(GetAccounts.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOGGER.debug("/pays/getaccounts#doget started");
		String userEmail = request.getUserPrincipal().getName();
		Connection con = null;
		List<Account> accounts = null;
		HttpSession session = request.getSession();
		boolean status = false;

		if (request.isUserInRole("admin"))
			session.setAttribute("isAdmin", true);
		try {
			con = DBManager.getDBCon();
			accounts = DBManager.getAccounts(con, userEmail);
			status = DBManager.isCustomerLocked(con, userEmail);
			LOGGER.debug("Have gotten accounts and status");
		} catch (SQLException e) {
			LOGGER.error("Cannot execute querry", e);
		} finally {
			if (null != con) {

				try {
					con.close();
				} catch (SQLException e1) {
					LOGGER.error("Cannot close connection", e1);

				}

			}
		}

		if (accounts.size() == 0)
			accounts = null;

		if (session.getAttribute("balance") == null && accounts!= null)
			session.setAttribute("balance", accounts.get(0).getAcc_balance());
		session.setAttribute("accounts", accounts);
		session.setAttribute("customerStatus", status);
		response.sendRedirect("pays/mypays.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("/pays/getaccounts#dopost started");
		boolean status = false;


		HttpSession session = request.getSession();
		String userEmail = request.getParameter("userEmail");

		Connection con = null;
		List<Account> accounts = null;
		if (request.getParameter("verified") != null) {
			userEmail = (String) session.getAttribute("userEmail");
		}
		if (request.getParameter("locked") != null) {
			userEmail = (String) session.getAttribute("userEmail");
		}
		session.setAttribute("userEmail", userEmail);

		if (request.isUserInRole("admin"))
			session.setAttribute("isAdmin", true);
		try {con = DBManager.getDBCon();
		
			if (request.getParameter("verified") != null) {
				Account acc = DBManager.getAccount(con, request.getParameter("verified"));
				System.out.println("acc.ver = " + acc.isVerified());
				Boolean bool = !(acc.isVerified());


				DBManager.setVerified(con, request.getParameter("verified"), bool);
			}

			if (request.getParameter("locked") != null) {
				Boolean b = DBManager.getCustomer(con, request.getParameter("locked")).getLock();
				DBManager.setLocked(con, request.getParameter("locked"), !b);
			}
			accounts = DBManager.getAccounts(con, userEmail);
			status = DBManager.isCustomerLocked(con, userEmail);

		} catch (SQLException e) {
			LOGGER.error("Cannot execute querry", e);
		} finally {
			if (null != con) {

				try {
					con.close();
				} catch (SQLException e1) {
					LOGGER.error("Cannot close connection", e1);
				}

			}
		}

		if (accounts.size() == 0)
			accounts = null;
		
		session.setAttribute("customerStatus", status);
		session.setAttribute("accounts", accounts);
		response.sendRedirect("/payments/admin/admin.jsp");

	}

}
