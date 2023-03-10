package newbank.server;

import newbank.models.CustomerID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewBankClientHandler extends Thread{
	
	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;
	
	
	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}
	
	public void run() {
		// keep getting requests from the client and processing them
		try {
			// ask for user name,
			boolean isNewUser = false;

			out.println("Enter Username, or press enter to create a new account");
			String userName = in.readLine();

			if (userName.isBlank()){
				isNewUser = true;
				out.println("Enter a new user name");
				userName = in.readLine();
			}

			// ask for password
			out.println("Enter Password");
			String password = in.readLine();
			out.println("Checking Details...");


			// authenticate or create user and get customer ID token from bank for use in subsequent requests
			CustomerID customer;
			if (isNewUser){
				customer = bank.createAccount(userName, password);
			} else {
				customer = bank.checkLogInDetails(userName, password);
			}

			// if the user is authenticated then get requests from the user and process them 
			if(customer != null) {
				out.println("Log In Successful. What do you want to do?");
				while(true) {
					String request = in.readLine();
					System.out.println("Request from " + customer.getKey());
					String response = bank.processRequest(customer, request);
					out.println(response);
				}
			}
			else {
				out.println("Log In Failed");
				run();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

}
