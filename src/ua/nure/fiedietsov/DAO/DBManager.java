/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.DAO;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
/**
* Class access the database. 
* It helps to get connections 
* to perform sql queries.
* @author Fiedietsov V.
* @version 1.0
*/
public class DBManager {
	
	private static final Logger LOGGER = Logger.getLogger(DBManager.class);
	private static final double MAX_BALANCE_VALUE = 9999999.99d;

	
	private static final String SQL_GET_ACC_MAILS = "select * from customers;";
	private static final String SQL_GET_ACCS_2 = "SELECT accounts.*, transactions.* FROM accounts, transactions where accounts.account =  transactions.account && accounts.customer_email=? ;";
	private static final String SQL_GET_TRNC_COUNT = "select count(*) from transactions where customer_email=?;";
	private static final String SQL_ADD_USER = "INSERT INTO customers(customer_email,customer_name,password) values(?, ?, ?); ";
	private static final String SQL_ADD_USER_ROLE = "INSERT INTO roles() values(?, ?); ";
	private static final String SQL_ADD_CARD = "INSERT INTO accounts(customer_email,account,info) values(?, ?, ?); ";
	private static final String SQL_GET_CUSTOMER = "SELECT * FROM customers WHERE customer_email=?;";
	private static final String SQL_GET_ACCOUNT = "SELECT * FROM accounts WHERE account=?;";
	private static final String SQL_GET_TRANSACTION = "select * from transactions where account=?;";
	private static final String SQL_GET_PAYMENT = "select * from payments where customer_email = ?;";
	private static final String SQL_GET_ACCOUNT_INFO = "SELECT accounts.*, customers.locked FROM accounts, customers where customers.customer_email = ? AND accounts.customer_email = ?; ";
	private static final String SQL_SET_ACCOUNT_VERIF = "UPDATE accounts SET verified=? WHERE account=?; ";
	private static final String SQL_GET_CUSTOMER_LOCKED = "SELECT locked from customers WHERE customer_email=?; ";
	private static final String SQL_SET_CUSTOMER_LOCKED = "UPDATE customers SET locked=? WHERE customer_email=?; ";
	private static final String SQL_GET_BALANCE = "select acc_balance from accounts WHERE account=?; ";
	private static final String SQL_ADD_MONEY = "UPDATE accounts SET acc_balance=? WHERE account=?; ";
	private static final String SQL_ADD_MONEY_HISTORY = "INSERT INTO transactions(category,account,summary,total,balance ) values (?, ?, ?, ?, ?); ";
	//private static final String SQL_SET_ACCOUNT_CURRENCY = "UPDATE accounts SET currency=? WHERE account=?;";
	private static final String SQL_GET_PREP_PAYM = "select * from payments where dtime = ? && customer_email=?;";
	private static final String SQL_ADD_PREP_PAYM = "INSERT INTO payments(customer_email, account,summary,comment,total ) values (?, ?, ?, ?, ?);";
	private static final String SQL_DEL_PREP_PAYM = "DELETE from payments where dtime = ?;";
	/**
	* Support method helps to get status 
	* of customer account
	* Have got connection and tried to 
	* Execute query to get status of 
	* customer account . 
	* @param con take simple java.sql.Connection connection
	* @param userEmail to get access to database
	* @author Fiedietsov V.
	* @version 1.0
	*/
	@SuppressWarnings("resource")
	public static List<User> getUserTrancsCount(Connection con) throws SQLException{
		List<User> trncCount = new ArrayList<User>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_ACC_MAILS);
			rs = pstmt.executeQuery();
			int tmpInCount = 0;
			System.out.println(pstmt);
			while (rs.next()) {
				trncCount.add(new User(rs.getString("customer_email")));
			}
			
				for(User tmUser : trncCount){
				pstmt.clearBatch();
				pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_ACCS_2);
				pstmt.setString(1, tmUser.getEmail());
				System.out.println(pstmt);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					tmpInCount++;
				}
				tmUser.setCountTranc(tmpInCount);
//				tmpInCount = tmUser.getCountTranc();
//				tmUser.setCountTranc(tmpInCount + rs.getInt("count(*)"));

			}
		}finally{
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}
		}
		return trncCount ;
		
	}
	
	
	
	
	public static boolean isCustomerLocked(Connection con, String userEmail) throws SQLException{

		boolean status = false;
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_CUSTOMER_LOCKED);
			pstmt.setString(1, userEmail);
			rs = pstmt.executeQuery();
			System.out.println(pstmt);
			while (rs.next()) {
				status = rs.getBoolean("locked");
				LOGGER.warn("isLocked " + userEmail + " is " + status) ;
			}

		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}
		}
		return status;
	}

	/**
	* Class take connection and try to 
	* Execute query to confirm payment 
	*It also take  Connection con gets anywhere
	* @param con 
	* @return boolean resulOp
	* 
	*/
	@SuppressWarnings("resource")
	public static boolean confPayment(Connection con, String dtime, String userEmail) {
		
		boolean res = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double balance = 0d;
		try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			LOGGER.error("setAutoComit error", e1);
			e1.printStackTrace();
		}

		Transactions tranc = new Transactions();
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_PREP_PAYM);
			pstmt.setString(1, dtime);
			pstmt.setString(2, userEmail);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tranc = new Transactions(rs.getString(1), Category.PAYS.toString(), rs.getString(3),
						rs.getString(4) + " " + rs.getString(5), rs.getDouble(6), 0d);
			}
			pstmt.clearBatch();
			// Get balance
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_BALANCE);
			pstmt.setString(1, tranc.getAccount());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				balance = rs.getDouble("acc_balance");
			}

			System.out.println("BALANCE = " + balance);
			if ((balance - tranc.getTotal()) < 0) {

				throw new SQLException("not enough money from acc");
			}
			tranc.setBalance(balance - tranc.getTotal());

			pstmt.clearBatch();
			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_MONEY);
			pstmt.setDouble(1, tranc.getBalance());
			pstmt.setString(2, tranc.getAccount());
			pstmt.executeUpdate();

			pstmt.clearBatch();
			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_MONEY_HISTORY);
			pstmt.setString(1, Category.PAYS.toString());
			pstmt.setString(2, tranc.getAccount());
			pstmt.setString(3, tranc.getSummary());
			pstmt.setDouble(4, tranc.getTotal());
			pstmt.setDouble(5, tranc.getBalance());
			System.out.println(pstmt);
			pstmt.executeUpdate();
			pstmt.clearBatch();

			con.commit();
			res = true;

		} catch (SQLException e) {
			LOGGER.error("sql error", e);
			e.printStackTrace();

		} finally {
			try {
				if (rs != null) {

					rs.close();

				}
				if (pstmt != null) {
					pstmt.close();
				}
				con.setAutoCommit(true);
			} catch (SQLException e) {
				LOGGER.error("block finally error", e);
				e.printStackTrace();
			}
		}

		return res;
	}
	/** 
	 * Class take connection and try to 
	 * Execute query to delete payment from cart
	 * @param dtime String date format 2016-08-25 15:45:59.0
	 * @return res is boolean value of result operation
	 */
	public static boolean delPayment(Connection con, String dtime) {

		boolean res = false;
		PreparedStatement pstmt = null;

		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_DEL_PREP_PAYM);
			pstmt.setString(1, dtime);
			pstmt.execute();

		} catch (SQLException e) {
			LOGGER.error(" error", e);
			e.printStackTrace();
		}
		return res;
	}
	/** 
	 * Class take connection and try to 
	 * Execute query to add payment to cart
	 * @param 
	 * @return res is boolean value of result operation
	 */
	public static int addPayment(Connection con, String account, String userEmail, String toPay, String toPayInfo,
			Double money) throws SQLException {

		int res = 1;
		// double balance = 0d;
		String summary = "from " + account + " to " + toPay;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//List<Transactions> payments = new ArrayList<Transactions>();
		//Transactions tranc;
		try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			LOGGER.error("setAutoComit error", e1);
		}

		try {

			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_PREP_PAYM);
			pstmt.setString(1, userEmail);
			pstmt.setString(2, account);
			pstmt.setString(3, summary);
			pstmt.setString(4, toPayInfo);
			pstmt.setDouble(5, money);
			pstmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			LOGGER.error("error", e);
			con.rollback();
			res = 11;
		}

		finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.setAutoCommit(true);
		}
		return res;
	}
	/** 
	 * Method take connection and try to 
	 * Execute query to get payments
	 * @param userEmail String customer email
	 * @return List of Transactions 
	 */
	public static List<Transactions> getPayments(Connection con, String userEmail) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Transactions> payments = new ArrayList<Transactions>();
		Transactions tranc;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_PAYMENT);
			pstmt.setString(1, userEmail);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tranc = new Transactions(rs.getString(1), Category.PAYS.toString(), rs.getString(3),
						rs.getString(4) + " " + rs.getString(5), rs.getDouble(6), 0d);
				payments.add(tranc);

			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return payments;

	}
	/** 
	 * Method take connection and try to 
	 * Execute query to change balance between accounts
	 * @param 
	 * @return message can take "successfully" or "negative"
	 */
	@SuppressWarnings("resource")
	public static String sendMoney(Connection con, String account1, String account2, double money) throws SQLException {
		String message = "successfully";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double balance1 = 0d;
		double balance2 = 0d;
		double resBalance1 = 0d;
		double resBalance2 = 0d;

		try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			LOGGER.error("setAutoComit error", e1);
		}
		try {
			// check balances possibility
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_BALANCE);
			pstmt.setString(1, account1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				balance1 = rs.getDouble("acc_balance");
			}
			pstmt.clearBatch();
			if (balance1 - money < 0) {
				LOGGER.error("Negative balance");
				return message = "negative";
			}
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_BALANCE);
			pstmt.setString(1, account2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				balance2 = rs.getDouble("acc_balance");
			}
			if (balance2 + money > MAX_BALANCE_VALUE) {
				LOGGER.error("balance overflow");
				return message = "balance overflow";
			}
			pstmt.clearBatch();
			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_MONEY);
			resBalance1 = balance1 - money;
			pstmt.setDouble(1, resBalance1);
			pstmt.setString(2, account1);
			pstmt.executeUpdate();
			resBalance2 = balance2 + money;
			pstmt.setDouble(1, resBalance2);
			pstmt.setString(2, account2);
			pstmt.executeUpdate();

			// block to add history
			pstmt.clearBatch();

			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_MONEY_HISTORY);
			pstmt.setString(1, Category.ACC_EXCHANGE.toString());
			pstmt.setString(2, account1);
			pstmt.setString(3, "exchange between own accounts " + account1 + " to " + account2);
			pstmt.setDouble(4, money);
			pstmt.setDouble(5, resBalance1);
			pstmt.executeUpdate();
			pstmt.clearBatch();

			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_MONEY_HISTORY);
			pstmt.setString(1, Category.INCOME_FROM_TRANSFER.toString());
			pstmt.setString(2, account2);
			pstmt.setString(3, "exchange between own accounts " + account1 + " to " + account2);
			pstmt.setDouble(4, money);
			pstmt.setDouble(5, resBalance2);
			pstmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			LOGGER.error(" error", e);
			con.rollback();
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}
			con.setAutoCommit(true);
		}
		return message;
	}
	/** 
	 * Method take connection and try to 
	 * Execute query to set income for balance
	 * @param 
	 * @return result boolean 
	 */
	public static boolean addMoney(Connection con, String account, double income) throws SQLException {
		boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double balance = 0d;
		double money = 0d;
		try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			LOGGER.error("setAutoComit error", e1);
		}
		try {

			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_BALANCE);
			pstmt.setString(1, account);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				balance = rs.getDouble("acc_balance");
			}
			pstmt.clearBatch();
			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_MONEY);
			if (income + balance < MAX_BALANCE_VALUE) {
				money = income + balance;
			} else
				new SQLException("Max value is eceeded");
			pstmt.setDouble(1, money);
			pstmt.setString(2, account);
			System.out.println(pstmt);
			pstmt.executeUpdate();
			// block to add history
			pstmt.clearBatch();

			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_MONEY_HISTORY);
			pstmt.setString(1, Category.INCOME_FROM_CASH.toString());
			pstmt.setString(2, account);
			pstmt.setString(3, "income from cash");
			pstmt.setDouble(4, income);
			pstmt.setDouble(5, money);
			pstmt.executeUpdate();
			con.commit();
			result = true;

		} catch (SQLException e) {
			LOGGER.error(" error", e);
			con.rollback();
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}
			con.setAutoCommit(true);
		}
		return result;
	}
	/** 
	 * Method take connection and try to 
	 * Execute query to get account information
	 * @param 
	 * @return acc type  Account which contain addition info 
	 */
	public static Account getAccount(Connection con, String account) throws SQLException {
		PreparedStatement pstmt = null;
		Account acc = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_ACCOUNT);
			pstmt.setString(1, account);
			rs = pstmt.executeQuery();
			System.out.println(pstmt);
			while (rs.next()) {
				acc = new Account(rs.getBoolean("verified"), rs.getString("account"));
			}

		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}
		}
		return acc;
	}
	/** 
	 * Method take connection and try to 
	 * Execute query to get customer information
	 * @param 
	 * @return user type  User which contain addition info 
	 */
	public static User getCustomer(Connection con, String email) throws SQLException {
		PreparedStatement pstmt = null;
		User user = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_CUSTOMER);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User(rs.getString("customer_email"), rs.getBoolean("locked"));
			}

		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}
		}
		return user;
	}
	/** 
	 * Method take connection and try to 
	 * Execute query to set/unset locked flag
	 * @param 
	 */
	public static void setLocked(Connection con, String customer_email, Boolean isLocked) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_SET_CUSTOMER_LOCKED);
			pstmt.setBoolean(1, isLocked);
			pstmt.setString(2, customer_email);
			System.out.println(pstmt);
			pstmt.executeUpdate();

		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	/** 
	 * Method take connection and try to 
	 * Execute query to set/unset verified flag
	 * @param 
	 */
	public static void setVerified(Connection con, String account, Boolean boolVerified) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_SET_ACCOUNT_VERIF);
			pstmt.setBoolean(1, boolVerified);
			pstmt.setString(2, account);
			System.out.println(pstmt);
			pstmt.executeUpdate();

		} finally {

			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	/** 
	 * Method take connection and try to 
	 * Execute query to get account infomation
	 * @param 
	 * @return acc info
	 */
	public static Account getAccInfo(Connection con, String account) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Account acc = null;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_ACCOUNT_INFO);
			pstmt.setString(1, account);
			pstmt.setString(2, account);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				acc = new Account(rs.getString("customer_email"), rs.getString("account"), rs.getString("info"),
						rs.getString("acc_balance"), rs.getBoolean("verified"), rs.getBoolean("locked"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return acc;
	}
	/** 
	 * Method take connection and try to 
	 * Execute query to get accounts infomation
	 * @param 
	 * @return acc list accounts info
	 */
	public static List<Account> getAccounts(Connection con, String userEmail) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Account> accounts = new ArrayList<Account>();
		Account acc;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_ACCOUNT_INFO);
			pstmt.setString(1, userEmail);
			pstmt.setString(2, userEmail);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				acc = new Account(rs.getString("customer_email"), rs.getString("account"), rs.getString("info"),
						rs.getString("acc_balance"), rs.getBoolean("verified"), rs.getBoolean("locked"));
				acc.setCurrency(rs.getString("currency"));
				accounts.add(acc);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return accounts;

	}
	/** 
	 * Method take connection and try to 
	 * Execute query to get transactions infomation from DB
	 * @param 
	 * @return transactions list
	 */
	public static List<Transactions> getTransactions(Connection con, String account) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Transactions> transactions = new ArrayList<Transactions>();
		Transactions tranc;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_GET_TRANSACTION);
			pstmt.setString(1, account);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tranc = new Transactions(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getDouble(5), rs.getDouble(6));
				transactions.add(tranc);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return transactions;

	}
	/** 
	 * Method take connection and try to 
	 * Execute query to add account into DB
	 * @param 
	 * @return result boolean
	 */
	public static boolean addAccount(Connection con, String currUserEmail, String account, String info)
			throws SQLException {
		boolean res = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_CARD);
			pstmt.setString(1, currUserEmail);
			pstmt.setString(2, account);
			pstmt.setString(3, info);
			pstmt.executeUpdate();

			res = true;

		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return res;

	}
	/** 
	 * Method take connection and try to 
	 * Execute query to add new user into DB
	 * @param 
	 * @return result boolean
	 */
	public static boolean createUser(Connection con, User user) throws SQLException {
		boolean res = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_USER);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPassword());
			System.out.println(pstmt);
			pstmt.executeUpdate();
			pstmt.clearBatch();
			pstmt = (PreparedStatement) con.prepareStatement(SQL_ADD_USER_ROLE);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getRole());
			pstmt.executeUpdate();
			res = true;

		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return res;
	}
	/** 
	 * Method return new connection to DB
	 * @param 
	 * @return Connection con
	 */
	public static Connection getDBCon() {
		Connection con = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			DataSource ds = (DataSource) envCtx.lookup("jdbc/paymentsdb");

			con = (Connection) ds.getConnection();
		} catch (NamingException | SQLException ex) {
			ex.printStackTrace();

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			System.out.println(sw);
		}
		return con;
	}

}
/** 
 * Class contains enum category 
 */
enum Category {
	INCOME_FROM_CASH, INCOME_FROM_TRANSFER, PAYS, ACC_EXCHANGE
}
