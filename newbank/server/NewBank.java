package newbank.server;

import newbank.models.Account;
import newbank.models.Customer;
import newbank.models.CustomerID;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String, Customer> customers;
	
//	private static final RequestParser requestParser = new RequestParser();

	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.setPassword("bhagy1");
		bhagy.addAccount(new Account("Main", 1000.0));
		bhagy.addAccount(new Account("Savings", 1000.0));
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.setPassword("christina1");
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		

	}
	
	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			if (customers.get(userName).checkPassword(password)) {
				return new CustomerID(userName);
			}
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {

		CustomerRequest customerRequest = RequestParser.ParseRequest(request);

		if (customerRequest !=null){
			return customerRequest.execute(this, customer);
		} else {
			return "FAIL";
		}
	}

	protected String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	protected String moveFunds(CustomerID customer, double amount, String fromAccountName, String toAccountName ){
		Customer c = customers.get(customer.getKey());
		Account fromAccount = c.getAccountByName(fromAccountName);
		Account toAccount = c.getAccountByName(toAccountName);
		if (fromAccount.transferToAccount(toAccount, amount)){
			return "SUCCESS";
		} else {
			return "FAIL";
		}
	}

	protected String makePayment(CustomerID customer, String toPayee, double amount){
		Customer c = customers.get(customer.getKey());
		Account mainAccount = c.getAccountByName("Main");

		if(mainAccount.transferToPayee(toPayee, amount)){
			return "SUCCESS";
		} else {
			return "FAIL";
		}
	}

	public CustomerID createAccount(String userName, String password) {
		Customer newCustomer = new Customer();
		newCustomer.setPassword(password);
		customers.put(userName, newCustomer);
		// retrieve CustomerID, and at the same time check that account creation has succeeded.
		CustomerID customer = checkLogInDetails(userName, password);
		System.out.println("Created account for " + customer.getKey());
		return customer;
	}

}
