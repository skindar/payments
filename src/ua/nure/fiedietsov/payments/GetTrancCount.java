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
import ua.nure.fiedietsov.DAO.User;

@WebServlet("/admin/getcounts")
public class GetTrancCount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		List<User> trncCount = null;
		Connection con = null;
		try {
			con = DBManager.getDBCon();
			trncCount = DBManager.getUserTrancsCount(con);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (null != con) {

				try {
					con.close();
				} catch (SQLException e1) {
				}

			}
			if (trncCount != null) {
				for (User user : trncCount) {
					System.out.println(user.getEmail() + " " + user.getCountTranc());
				}
			}
			session.setAttribute("trncCount", trncCount);
			System.out.println("ALL OK");

			response.sendRedirect("/payments/admin/tranccounts.jsp");
		}

	}
}
