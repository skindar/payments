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

import ua.nure.fiedietsov.DAO.Account;
import ua.nure.fiedietsov.DAO.DBManager;
import ua.nure.fiedietsov.DAO.Transactions;

/**
* WebServlet. It help's to get
* payments from DB.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/pays/getpays")
public class GetPays extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userEmail = request.getUserPrincipal().getName();
		Connection con = DBManager.getDBCon();
		List<Transactions> pays = null;
		List<Account> accounts = null;
		try {
			pays = DBManager.getPayments(con, userEmail);
			accounts = DBManager.getAccounts(con, userEmail);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("accounts", accounts);
		session.setAttribute("payments", pays);
		response.sendRedirect("./cart.jsp");

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("getpaye#doPOST");
	}

}
