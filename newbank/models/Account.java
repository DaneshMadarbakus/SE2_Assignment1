package newbank.models;

public class Account {

	private final String accountName;
	private double openingBalance;
	private double currentBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.currentBalance = openingBalance;
	}

	public boolean transferToAccount(Account destinationAccount, double amount){
		boolean success = false;
		// can sufficient funds be withdrawn from this account?
		double transferFunds = withdraw(amount);
		if (transferFunds > 0.0){
			// try to deposit in destination account
			if (!destinationAccount.deposit(transferFunds)){
				// if deposit fails, return funds to this account.
				this.deposit(transferFunds);
			} else {
				success = true;
			}
		}
		return success;
	}

	public boolean transferToPayee(String toPayee, double amount){
		boolean success = false;
		double transferFunds = withdraw(amount);

		if (transferFunds > 0.0){
			// try to deposit in destination account
			success = true;
		}
		// write code to record payment to toPayee

		return success;
	}

	public String toString() {
		return (accountName + ": " + currentBalance);
	}

	public String getAccountName(){
		return accountName;
	}

	public boolean deposit(double amount){
		setCurrentBalance(getCurrentBalance() + amount);
		// return a boolean here. Set to true for now, but allows for implementation of failures in future. (account freeze?)
		return true;
	}
	private void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	private double getCurrentBalance(){
		return this.currentBalance;
	}

	private double withdraw(double amount){
		if (getCurrentBalance() >= amount){
			setCurrentBalance(this.getCurrentBalance() - amount);
			return amount;}
		else {
			return 0.0;
		}
	}
}
