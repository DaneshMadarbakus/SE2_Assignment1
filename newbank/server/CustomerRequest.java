package newbank.server;

import newbank.models.CustomerID;

/**
 * This is the interface which should be implemented by classes representing parsed customer requests.
 * The rationale is that parsed requests and their parameters carry with them knowledge of how they should
 * be carried out. i.e. They can execute themselves.
 */
public interface CustomerRequest {

    String execute(NewBank bank, CustomerID customer);

}
