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
* Servlet helps to add transaction into cart.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/pays/prepay")
public class PreparePay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(PreparePay.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("/pays/prepay#dopost started");
		
		boolean status = (boolean) request.getSession().getAttribute("customerStatus");
		if(status != true){

		String userEmail = request.getUserPrincipal().getName();
		String account = request.getParameter("selectAcc1");
		String toPay = request.getParameter("toPay");
		String toPayInfo = request.getParameter("toPayInfo");
		Double money = Double.parseDouble(request.getParameter("money"));
		int res = 0;
		Connection con = DBManager.getDBCon();
		try {
			res = DBManager.addPayment(con, account, userEmail, toPay, toPayInfo, money);
			LOGGER.warn("prepared payments was addinf into cart.");
		} catch (SQLException e) {
			LOGGER.error("Cannot execute querry", e);
		}
		request.getSession().setAttribute("resultOp", res);
		response.sendRedirect("./payments.jsp");
		}else response.sendRedirect("./mypays.jsp");
	}

}
