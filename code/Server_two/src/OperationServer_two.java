import MAINTAIN_CLIENT.ServerClient;
import MAINTAIN_CLIENT.Session;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class OperationServer_two {

    private static int clientNumber = 1;

    public OperationServer_two() {
        start();
    }

    private void start() {

        Socket connection = null;
        ServerSocket server = null;

        try  {
            server = new ServerSocket(5505);
            System.out.println("SERVER_TWO STATUS: ACTIVE PORT -- " + server.getLocalPort() + " --");
            while(true) {
                connection = server.accept();
                System.out.println("\nSERVER_TWO STATUS: A CONNECTION ESTABLISHED");
                /* Each time a client (in our project the client is the server_one) a session will be created
                *  With that way we can handle multiple clients (server_one) */
                String clientIP = server.getInetAddress().getHostAddress();
                Session session = new Session(new ServerClient(clientNumber, clientIP), connection);
                session.start(); // start the session ...
                clientNumber++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
