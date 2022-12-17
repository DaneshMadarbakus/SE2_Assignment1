package newbank.server;

import java.util.regex.*;

/**
 * Class which has responsibility for parsing customer requests, and packaging those requests as
 * another class (implementing CustomerRequest) so that it can be dealt with elsewhere.
 * The regex contains a series of sub-expressions matching each possible request from the protocol.txt document.
 * There are "OR"ed together, such that if an input matches one of them it populates named groups containing the
 * request type and its parameters.
 */
public class RequestParser {
    //This regex matches on the request patterns documented in protocol.txt.
    private static Pattern requestPattern = Pattern
            .compile(
                    "(?<showAccounts>SHOWMYACCOUNTS)" +
                            "|(?<newAccount>NEWACCOUNT)\\s(?<Name>(\\w+))" +
                            "|(?<move>MOVE)\\s(?<moveAmount>(\\d+\\.?\\d*|\\.\\d+))\\s(?<moveFromAccount>(\\w+))\\s(?<moveToAccount>(\\w+))" +
                            "|(?<pay>PAY)\\s(?<personCompany>(\\w+))\\s(?<payAmount>(\\d+\\.?\\d*|\\.\\d+))"
            );

    public static CustomerRequest ParseRequest(String request) {

        Matcher requestMatcher = requestPattern.matcher(request);

        CustomerRequest customerRequest = null;

        if (requestMatcher.find()) {

            if (requestMatcher.group("showAccounts") != null) {
                System.out.println("Received a SHOWMYACCOUNTS request");
                customerRequest = new ShowAccountsRequest();
            }
            if (requestMatcher.group("newAccount") != null) {
                System.out.println("Received a NEWACCOUNT request");
            }
            if (requestMatcher.group("move") != null) {
                System.out.println("Received a MOVE request");
                customerRequest = new MoveFundsRequest(
                        Double.parseDouble(requestMatcher.group("moveAmount")),
                        requestMatcher.group("moveFromAccount"),
                        requestMatcher.group("moveToAccount")
                );
            }
            if (requestMatcher.group("pay") != null) {
                System.out.println("Received a PAY request");
                customerRequest = new MakePaymentRequest(
                        requestMatcher.group("personCompany"),
                        Double.parseDouble(requestMatcher.group("payAmount"))
                );
            }
        }
        return customerRequest;
    }
}

