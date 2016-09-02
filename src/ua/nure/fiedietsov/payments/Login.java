/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.payments;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
/**
* Support Servlet helps to check connection from DataSorce.
* @author Fiedietsov V.
* @version 1.0
*/
@WebServlet("/data_source_check")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Login.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("(login.class)/data_source_check#doget started");
		response.setContentType("text/html;charset=UTF-8");

		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			DataSource ds = (DataSource) envCtx.lookup("jdbc/paymentsdb");

			Connection con = ds.getConnection();
			
			System.out.println(con);
			LOGGER.warn(con);
			
			response.getWriter().println("con is " + con);
			con.close();
		} catch (NamingException | SQLException ex) {
			LOGGER.error(ex);

			StringWriter sw = new StringWriter();
			LOGGER.error("cannot open|close con", ex);
			response.getWriter().println(sw);
		}


	}

}
