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
* Servlet add new payment to DB.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/pays/pays")
public class Pays extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Pays.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("/pays/pays#dopost started");
		
		String userEmail = request.getUserPrincipal().getName();
		String account1 = request.getParameter("selectAcc1");
		String account2 = request.getParameter("selectAcc2");
		String sMoney = request.getParameter("pays");
		Double money = Double.parseDouble(sMoney);
		HttpSession session = request.getSession();
		boolean status = (boolean) session.getAttribute("customerStatus");
		if(status != true){

		List<Account> accounts = null;
		Connection con = DBManager.getDBCon();
		try {
			String success = DBManager.sendMoney(con, account1, account2, money);
			request.getSession().setAttribute("paySuccess", success);
			accounts = DBManager.getAccounts(con, userEmail);
			LOGGER.warn(success);
		} catch (SQLException e) {
			LOGGER.error("Cannot execute querry to add payments", e);
		}
		
		session.setAttribute("accounts", accounts);
		response.sendRedirect("./pays.jsp");
		}else response.sendRedirect("./mypays.jsp");
		
	}

}
