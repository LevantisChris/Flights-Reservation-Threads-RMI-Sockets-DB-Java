package MAINTAIN_CLIENT;
// in Server_one
import java.time.LocalDateTime;

public class Session extends Thread {
    public String sessionName;
    public ServerClient belongingClient; // the client that the session belongs ...
    public LocalDateTime SESS_TIME_CREATION; // the time that the session created
    private  boolean stopRequested = false;

    public Session(ServerClient client) {
        this.sessionName = this.getName();
        this.belongingClient = client;
        SESS_TIME_CREATION = LocalDateTime.now();
        System.out.println("\n------------------------------------------------------------------------------------------------------------");
        System.out.println("SERVER_ONE STATUS: CLIENT -- " + belongingClient.CLIENT_ID + " -- SESSION CREATED AT TIME -- " + SESS_TIME_CREATION + " --");
    }

    public void requestStop() {
        this.stopRequested = true;
    }

    public void run() {
        System.out.println("SERVER_ONE STATUS: CLIENT -- " + belongingClient.CLIENT_ID + " -- SESSION/TREAD RUNS AT TIME -- " + SESS_TIME_CREATION + " --\n");
        while (!stopRequested) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("SERVER_ONE STATUS: THE THREAD/SESSION OF THE CLIENT WITH ID -- " + belongingClient.CLIENT_ID + " -- HAS STOP/DELETED\n");
    }
}
