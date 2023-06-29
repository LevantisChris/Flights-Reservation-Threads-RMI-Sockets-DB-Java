import java.rmi.Naming;
import java.rmi.registry.Registry;

public class Main_serverOne {
    public static void main(String[] args) {
        try {
            Registry registry = java.rmi.registry.LocateRegistry.createRegistry(1099);
            OperationServer_one serverOne = new OperationServer_one();
            Naming.rebind("//127.0.0.1/OperationServer_one", serverOne);
            System.out.println("SERVER_ONE STATUS: SERVER IS UP AND RUNNING...");
        } catch (Exception e) {
            System.out.println("SERVER_ONE STATUS: SERVER NOT CONNECTED: " + e);
            System.exit(101);
        }
    }
}