package newbank.server;

import newbank.models.CustomerID;

public class MakePaymentRequest implements CustomerRequest{
    private double amount;
    private String toPayee;

     MakePaymentRequest(String toPayee, double amount){
        this.amount = amount;
        this.toPayee = toPayee;
     }

     @Override
    public String execute(NewBank bank, CustomerID customer){
        return bank.makePayment(customer, toPayee, amount);
    }
}
