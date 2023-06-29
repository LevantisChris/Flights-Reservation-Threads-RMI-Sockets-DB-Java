package MAINTAIN_CLIENT;
// in Server_two
public class ServerClient {
    /* This two lines of code help us know what is the id of each client */
    public String CLIENT_ID = "CLIENT-00";
    public String IP;

    /* Set up a new client and create a UNIQUE-ID */
    public ServerClient(int clientNumber, String IP) {
        this.CLIENT_ID  = CLIENT_ID + clientNumber;
        this.IP = IP;
    }
}
