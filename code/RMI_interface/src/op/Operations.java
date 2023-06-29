package op;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

public interface Operations extends Remote {
    public String connect() throws RemoteException; // This is used for the client when it connects for the first time
    public String disconnect(String UniqueID) throws RemoteException; // This is used for when the client is disconnected from the server
    //
    public String sendMSG(String UniqueID, String msg) throws RemoteException;
    public ArrayList<String[]> getAllFlights(String UniqueID, String from, String to, int passNum, String departureD, String returnD) throws RemoteException;
    public String submitFlight(String UniqueID, String flightCode, String passPASSPORT, String passNAME, String passSURNAME, String passCITY, String passADDRESS, String passPHONE, String passEMAIL, String passCLASS, String passNUMS) throws RemoteException;
}
