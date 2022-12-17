package newbank.models;

import java.util.ArrayList;
import java.util.Objects;

public class Customer {

	private ArrayList<Account> accounts;
	private int passwordHashed;

	public Customer() {
		accounts = new ArrayList<>();
	}

	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}


	public Boolean checkPassword(String passwordEntered) {
		return Objects.equals(passwordEntered.hashCode(), passwordHashed);
	}

	public void setPassword(String password) {
		this.passwordHashed = password.hashCode();
	}

	public Account getAccountByName(String accountName){
		for (Account account : accounts) {
			if (account.getAccountName().equals(accountName)){
				return account;
			}
		}
		return null;
	}

}
