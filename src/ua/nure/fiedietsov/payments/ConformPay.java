/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.payments;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.fiedietsov.DAO.DBManager;

/**
* WebServlet ConformPay. 
* It add a new payment to DB.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/pays/confpay")
public class ConformPay extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(ConformPay.class);
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("/pays/confpay#dopost started");
		String confirm = request.getParameter("confirm");
		String dtime = request.getParameter("delete");
		String userEmail = request.getUserPrincipal().getName();
		
		if (dtime != null){
			Connection con = DBManager.getDBCon();
			Boolean delPayResult = DBManager.delPayment(con, dtime);
			
			request.getSession().setAttribute("delPayResult", delPayResult);
			LOGGER.debug("payment was deleted");
		}
		if (confirm != null){
			Connection con = DBManager.getDBCon();
			Boolean confPayResult = DBManager.confPayment(con, confirm, userEmail);
			DBManager.delPayment(con, confirm);
			
			request.getSession().setAttribute("confPayResult", confPayResult);
			LOGGER.debug("payment was confirmed");
		}
		
		
		response.sendRedirect("./getpays");
	}

}
