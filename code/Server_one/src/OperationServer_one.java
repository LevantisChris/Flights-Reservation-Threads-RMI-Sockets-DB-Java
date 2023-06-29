import pr.protocol;

import MAINTAIN_CLIENT.ServerClient;
import MAINTAIN_CLIENT.Session;
import Socket.SocketConnection;
import op.Operations;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

public class OperationServer_one extends UnicastRemoteObject implements Operations, protocol {

    private ArrayList<String[]> DATA_GENERATED = new ArrayList<>();
    private static int clientNumber = 1;
    private List<Session> clients = new ArrayList<>();

    protected OperationServer_one() throws RemoteException {
        super();
    }

    @Override
    public String connect() throws RemoteException {
        Session session;
        try {
            String ip = RemoteServer.getClientHost();
            session = new Session(new ServerClient(clientNumber, ip));
            session.start(); // start the session
            clients.add(session);
            System.out.println("SERVER_ONE: STATUS OF THREAD/SESSION CREATED -- " + session.isAlive() + " --");
            System.out.println("------------------------------------------------------------------------------------------------------------\n");
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
        }
        clientNumber++;
        /* We return to the client the UNIQUE ID that is created in the server
         *  We do this so the client can send it in the other functions as a parameter.
         *  In that way, we can know what client is calling the function each time.
         *  */
        return session.belongingClient.CLIENT_ID;
    }

    @Override
    public String disconnect(String id) throws RemoteException {
        for(int i = 0;i < clients.size();i++) {
            if(id.equals(clients.get(i).belongingClient.CLIENT_ID)) {
                System.out.println("\nSERVER_ONE STATUS: THE CLIENT WITH ID -- " + id + " -- WILL BE DELETED (SESSION AND CLIENT ITSELF)");
                clients.get(i).requestStop();
                clients.remove(i); // remove the client that is disconnecting from the server ...
                return "DONE";
            }
        }
        return "NOT-DONE";
    }

    @Override
    public String sendMSG(String UniqueID, String msg) throws RemoteException {
        System.out.println("SERVER_ONE STATUS: MSG FROM -- " + UniqueID + " -- is the following -- " + msg + " --");
        return "RECEIVED";
    }

    /* This method is called from the client when the submitting button is pressed.
    *  It will get the flights that server 1 is going to get from server 2, and
    *  then send them back to the client */
    @Override
    public synchronized ArrayList<String[]> getAllFlights(String UniqueID, String from, String to, int passNum, String departureD, String returnD) throws RemoteException {
        if(DATA_GENERATED.size() != 0) // We have to empty the list ...
            DATA_GENERATED.removeAll(DATA_GENERATED);
        System.out.println("SERVER_ONE STATUS: THE CLIENT -- " + UniqueID + " -- WITH SESSION STATUS " + checkSession(UniqueID) + " RUNS getAllFlights ");
        String PARAMETERS_FOR_QUERY = from + "/" + to + "/" + passNum + "/" + departureD + "/" + returnD;

        //ArrayList<String> flights = new ArrayList<>();
        SocketConnection socketConnection = new SocketConnection(protocol.getAllFlights_func, PARAMETERS_FOR_QUERY, UniqueID);

        flightDisplay(socketConnection.JSON_STR_ALL_FLIGHTS_GOING);

        flightDisplay(socketConnection.JSON_STR_ALL_FLIGHTS_RETURNING);

        return DATA_GENERATED;
    }

    /* We create the date that the booking was made */
    @Override
    public String submitFlight(String UniqueID, String flightCode, String passPASSPORT, String passNAME, String passSURNAME, String passCITY, String passADDRESS, String passPHONE, String passEMAIL, String passCLASS, String passNUMS) {
        System.out.println("SERVER_ONE STATUS: THE CLIENT -- " + UniqueID + " -- WITH SESSION STATUS " + checkSession(UniqueID) + " RUNS submitFlight ");
        String passBOOKING_DATE = String.valueOf(java.time.LocalDate.now());
        System.out.println("SERVER_ONE STATUS: DATE THAT THE BOOKING WAS MADE BY THE CLIENT -- " + UniqueID + " -- IS -- " + passBOOKING_DATE + " --");
        String PARAMETERS_FOR_QUERY = passBOOKING_DATE + "/" + flightCode + "/" + passPASSPORT + "/" + passNAME + "/" + passSURNAME
                + "/" + passCITY + "/" + passADDRESS + "/" + passPHONE + "/" + passEMAIL + "/" + passCLASS.toLowerCase() + "/" + passNUMS;

        SocketConnection socketConnection = new SocketConnection(protocol.submitBooking, PARAMETERS_FOR_QUERY, UniqueID);
        //String msg_to_send_for_status = socketConnection.FINAL_STATUS;
        String msg_to_send_for_status = null;
        try {
            msg_to_send_for_status = socketConnection.waitForResponse();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("SERVER_ONE STATUS: MSG FROM SERVER_TWO FOR THE RUN OF THE FUNC submitBooking IS -- " + msg_to_send_for_status + " --");
        if(msg_to_send_for_status.equals(protocol.DONE)) {
            return protocol.DONE;
        } else if(msg_to_send_for_status.equals(protocol.NOT_AVAILABLE_SEATS))
            return protocol.NOT_AVAILABLE_SEATS;
        else {
            return protocol.ERROR;
        }
    }

    /* check if the thread with the ID idToFind is alive or not */
    private synchronized boolean checkSession(String idToFind) {
        for(int i = 0;i < clients.size();i++) {
            if(idToFind.equals(clients.get(i).belongingClient.CLIENT_ID)) {
                if(clients.get(i).isAlive()) {
                    System.out.println("SERVER_ONE STATUS: IN checkSession REQUESTED BY NAME CLIENT " + this.clients.get(i).belongingClient.CLIENT_ID + " SESSION/THREAD NAME -- " + clients.get(i).sessionName + "/" + clients.get(i).getName());
                    return true;
                }
            }
        }
        return false;
    }

    /* With the help of the Library Jackson with jars jackson.core,
    *   jackson.databind and jackson-annotations-2.15.0 we
    *   take the query with the data and make it in a form that we can use
    *
    *   NOTE: WE TAKE IT AS A JSON_STRING ...
    * */
    private void flightDisplay(String jsonString) {
        System.out.println("TEST JSON STRING --> " + jsonString);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        for (JsonNode flightNode : rootNode) {
            String [] row = new String[10];

            String airline = flightNode.get("AIRLINE_NAME").asText();
            row[0] = airline;

            String flightNumber = flightNode.get("FLIGHT_NUMBER").asText();
            row[1] = flightNumber;

            String departureAirport = flightNode.get("DEPARTURE_AIRPORT").asText();
            row[2] = departureAirport;

            String arrivalAirport = flightNode.get("ARRIVAL_AIRPORT").asText();
            row[3] = arrivalAirport;

            String departureDate = flightNode.get("DEPARTURE_DATE").asText();
            row[4] = departureDate;

            String departureTime = flightNode.get("DEPARTURE_TIME").asText();
            row[5] = departureTime;

            String arrivalTime = flightNode.get("ARRIVAL_TIME").asText();
            row[6] = arrivalTime;


            int duration = flightNode.get("FLIGHT_DURATION").asInt();
            row[7] = String.valueOf(duration);

            int availableSeats = flightNode.get("AVAILABLE_SEATS").asInt();
            row[8] = String.valueOf(availableSeats);

            double price = flightNode.get("PRICE").asDouble();
            row[9] = String.valueOf(price);


            System.out.printf("SERVER_ONE STATUS: DATA GENERATED -- %s %s from %s to %s on %s at %s - %s (%d minutes) - %d available seats - %.2f EUR%n --",
                    airline, flightNumber, departureAirport, arrivalAirport, departureDate, departureTime, arrivalTime,
                    duration, availableSeats, price);
            System.out.println();

            DATA_GENERATED.add(row);
        }
    }
}
