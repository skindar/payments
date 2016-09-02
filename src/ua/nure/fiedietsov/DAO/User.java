/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.DAO;

import java.util.List;
/**
* The main entity. 
* Helps to get and keep DB rows and cells 
* contain customer's info.
* Have different constructions, which may be used
* depending on context.
* @author Fiedietsov V.
* @version 1.0
*/
public class User {
	private String name;
	private String email;
	private String password;
	private String role = "user";
	private List<String> accounts;
	private Boolean lock;
	private int countTranc = 0;
	
	public User(String email) {
		this.email = email;
	}
	
	
	public void setCountTranc(int countTranc) {
		this.countTranc = countTranc;
	}


	public int getCountTranc() {
		return countTranc;
	}
	public User(String email, int countTranc) {
		this.email = email;
		this.countTranc = countTranc;
	}
	public User(String email, Boolean lock) {

		this.email = email;
		this.lock = lock;
	}
	public User(String name, String email, String password, String role, List<String> accounts, Boolean lock) {

		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.accounts = accounts;
		this.lock = lock;
	}
	public Boolean getLock() {
		return lock;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getAccounts() {
		return accounts;
	}



	

}
