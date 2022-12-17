package newbank.server;

import newbank.models.CustomerID;

/**
 * Class which carries a parsed MOVE request, and the method required to execute the request
 */
public class MoveFundsRequest implements CustomerRequest{
    private double amount;
    private String fromAccountName;
    private String toAccountName;

    MoveFundsRequest(double amount, String fromAccountName, String toAccountName){
        this.amount = amount;
        this.fromAccountName = fromAccountName;
        this.toAccountName = toAccountName;
    }
    @Override
    public String execute(NewBank bank, CustomerID customer) {
        return bank.moveFunds(customer, amount, fromAccountName, toAccountName);
    }
}
