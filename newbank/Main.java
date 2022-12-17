package newbank;

import newbank.client.ExampleClient;
import newbank.server.NewBankServer;


public class Main {


    public static void main(String[] args) {
        final int port = 14002;
        final String ip = "localhost";
        try {
            NewBankServer server = new NewBankServer(port);
            server.start();
            ExampleClient client = new ExampleClient(ip, port);
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
