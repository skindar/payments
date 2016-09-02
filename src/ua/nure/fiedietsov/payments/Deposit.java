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
* WebServlet Deposit. 
* It add a new deposit to DB.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/pays/putdeposit")
public class Deposit extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(Deposit.class);
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("/pays/putdeposit#dopost started");
		
		String account = request.getParameter("selectAcc");
		String userEmail = request.getUserPrincipal().getName();
		HttpSession session = request.getSession();
		boolean status = (boolean) session.getAttribute("customerStatus");
		if(status != true){
		List<Account> accounts = null;
		boolean success = false;
		double income = Double.parseDouble(request.getParameter("addMoney"));
		Connection con  = DBManager.getDBCon();
		try {
			success = DBManager.addMoney(con, account, income);
			request.getSession().setAttribute("incomeSuccess", success);
			accounts = DBManager.getAccounts(con, userEmail);
		} catch (SQLException e) {
			LOGGER.error("Cannot execute querry", e);
		}finally{
			try {
				con.close();
			} catch (SQLException e1) {
				LOGGER.error("Cannot close connection", e1);
				e1.printStackTrace();
			}
		}
		session.setAttribute("accounts", accounts);
		
		response.sendRedirect("./deposit.jsp");
		}else response.sendRedirect("./mypays.jsp");
	}

}
