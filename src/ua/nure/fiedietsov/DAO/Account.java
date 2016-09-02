/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.DAO;
/**
* The main entity. 
* Helps to get and keep DB rows and cells 
* contain customer's and account's info.
* Have different constructions, which may be used
* depending on context.
* @author Fiedietsov V.
* @version 1.0
*/
public class Account {
	private String customer_email;
	private String account;
	private String info;
	private String acc_balance;
	private boolean verified;
	private boolean locked;
	private String  currency;
	
	
	
	
	

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public boolean isLocked() {
		return locked;
	}
	public Account(String customer_email, String account, String info, String acc_balance, boolean verified, boolean locked) {
		this.customer_email = customer_email;
		this.account = account;
		this.info = info;
		this.acc_balance = acc_balance;
		this.verified = verified;
	}
	public Account(String account, boolean locked) {

		this.account = account;
		this.locked = locked;
	}
	public Account( boolean verified, String account) {

		this.account = account;
		this.verified = verified;
	}
	@Override
	public String toString() {
		return "Account [account=" + account + ", info=" + info + "]";
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public String getAccount() {
		return account;
	}
	public String getInfo() {
		return info;
	}
	public String getAcc_balance() {
		return acc_balance;
	}
	public boolean isVerified() {
		return verified;
	}
	

}
