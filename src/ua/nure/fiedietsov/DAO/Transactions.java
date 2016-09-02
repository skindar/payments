/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.DAO;
/**
* One of the main entities. 
* Helps to get and keep DB rows and cells.
* Have different constructions, which may be used
* depending on context.
* @author Fiedietsov V.
* @version 1.0
*/
public class Transactions {
	private String dtime;
	private String category;
	private String account;
	private String summary;
	private double total;
	private double balance;
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Transactions(){
		
	}
	
	public Transactions(String dtime, String category, String account, String summary, Double total, Double balance) {
		this.dtime = dtime;
		this.category = category;
		this.account = account;
		this.summary = summary;
		this.total = total;
		this.balance = balance;

	}
	public String getDtime() {
		return dtime;
	}
	@Override
	public String toString() {
		return "Transactions [dtime=" + dtime + ", summary=" + summary + ", total=" + total + ", balance=" + balance
				+ "]";
	}
	public String getCategory() {
		return category;
	}
	public String getAccount() {
		return account;
	}
	public String getSummary() {
		return summary;
	}
	public Double getTotal() {
		return total;
	}
	public Double getBalance() {
		return balance;
	}
	

}
