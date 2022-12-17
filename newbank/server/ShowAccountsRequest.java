package newbank.server;

import newbank.models.CustomerID;

public class ShowAccountsRequest implements CustomerRequest {

    @Override
    public String execute(NewBank bank, CustomerID customer) {
        return bank.showMyAccounts(customer);
    }
}
