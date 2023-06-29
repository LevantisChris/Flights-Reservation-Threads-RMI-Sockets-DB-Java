package MAINTAIN_CLIENT;

import pr.protocol;
import DB_CONNECTION.DB_Connection;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

// in Server_two
public class Session extends Thread implements protocol {
    public String sessionName;
    public ServerClient belongingClient; // the client that the session belongs ...
    public LocalDateTime SESS_TIME_CREATION; // the time that the session created
    private  boolean stopRequested = false;

    private Socket connection = null;

    public Session(ServerClient client, Socket connection) {
        this.connection = connection;

        this.sessionName = this.getName();
        this.belongingClient = client;
        SESS_TIME_CREATION = LocalDateTime.now();
        System.out.println("\n------------------------------------------------------------------------------------------------------------");
        System.out.println("SERVER_TWO STATUS: CLIENT -- " + belongingClient.CLIENT_ID + " -- SESSION CREATED AT TIME -- " + SESS_TIME_CREATION + " --");
    }

    public void requestStop() {
        this.stopRequested = true;
    }

    public void run() {
        System.out.println("SERVER_TWO STATUS: CLIENT -- " + belongingClient.CLIENT_ID + " -- SESSION/TREAD RUNS AT TIME -- " + SESS_TIME_CREATION + " --\n");
        while (!stopRequested) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            OutputStreamWriter outputStreamWriter = null;
            BufferedWriter bufferedWriter = null;
            //
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            // For the resultSet we sent back to the server_one
            ObjectOutputStream objectOutputStream = null;

            try {
                // Strings write/send
                outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                // String read/receive
                inputStreamReader = new InputStreamReader(connection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                // Object send/write
                objectOutputStream = new ObjectOutputStream(connection.getOutputStream());


                String WANTING_FUNC = String.valueOf(bufferedReader.read()); // We take the operation code ...
                bufferedWriter.write(protocol.RECEIVED); // we send a confirmation code ...
                bufferedWriter.newLine();
                bufferedWriter.flush();
                if (WANTING_FUNC.equals(String.valueOf(protocol.getAllFlights_func))) {

                    System.out.println("SERVER_TWO STATUS: THE USER WITH THE ID -- " + belongingClient.CLIENT_ID + " -- WANTS TO USE THE FUNCTION -- " + getAllFlights_func + " --");

                    String PARAMETERS_FOR_QUERY = bufferedReader.readLine(); // here we take the parameters for the query …
                    bufferedWriter.write(protocol.RECEIVED); // we send a confirmation code ...
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    bufferedWriter.write(getAllFlightsGoing(PARAMETERS_FOR_QUERY)); // send the data of the database ...
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    String CONF_CODE1 = bufferedReader.readLine();
                    System.out.println("SERVER_TWO STATUS: THE SERVER_ONE SEND A CONFIRMATION CODE (AFTER FIRST SEND OF THE FLIGHTS GOING) -- " + CONF_CODE1 + " --");

                    bufferedWriter.write(getAllFlightsReturning(PARAMETERS_FOR_QUERY)); // send the data of the database ...
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    String CONF_CODE2 = bufferedReader.readLine();
                    System.out.println("SERVER_TWO STATUS: THE SERVER_ONE SEND A CONFIRMATION CODE (AFTER SECOND SEND OF THE FLIGHTS RETURNING) -- " + CONF_CODE2 + " --");

                } else if(WANTING_FUNC.equals(String.valueOf(protocol.submitBooking))) {

                    System.out.println("SERVER_TWO STATUS: THE USER WANTS -- " + belongingClient.CLIENT_ID + " -- TO USE THE FUNCTION -- " + getAllFlights_func + " --");

                    String PARAMETERS_FOR_QUERY = bufferedReader.readLine(); // here we take the parameters for the query …
                    bufferedWriter.write(protocol.RECEIVED); // we send a confirmation code ...
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    String msg_write = submitFlight(PARAMETERS_FOR_QUERY);
                    if(Objects.equals(msg_write, protocol.DONE)) {
                        msg_write = protocol.DONE;
                    } else if(Objects.equals(msg_write, protocol.NOT_AVAILABLE_SEATS)){
                        msg_write = protocol.NOT_AVAILABLE_SEATS;
                    } else {
                        msg_write = protocol.ERROR;
                    }

                    bufferedWriter.write(msg_write); // send DONE OR NOT_AVAILABLE_SEATS OR ERROR in the database ONE ...
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    String CONF_CODE1 = bufferedReader.readLine();
                    System.out.println("SERVER_TWO STATUS: 2 THE SERVER_ONE SEND A CONFIRMATION CODE (AFTER THE SEND OF DONE, ERROR OR NOT_AVAILABLE_SEATS) -- " + CONF_CODE1 + " --");

                } else {

                    System.out.println("SERVER_TWO STATUS: //ERROR// IN TRYING TO ANALYZE THE FUNCTION THE CLIENT WANTS -- " + protocol.BAD_CODE + " --");
                    outputStreamWriter.write(protocol.BAD_CODE + "\n");
                    outputStreamWriter.flush();

                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                try {
                    connection.close();
                    requestStop();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        }
        System.out.println("SERVER_TWO STATUS: THE THREAD/SESSION OF THE CLIENT WITH ID -- " + belongingClient.CLIENT_ID + " -- HAS STOP/DELETED\n");
    }

    /* Submit Flight - Book FLight */
    private synchronized String submitFlight(String PARAMETERS_FOR_QUERY) {

        String [] parameters = PARAMETERS_FOR_QUERY.split("/");

        String date = parameters[0]; // booking date ...
        int flightCode = Integer.parseInt(parameters[1]);
        int passport = Integer.parseInt(parameters[2]); // In the DB we have it as an INTEGER ...
        String name = parameters[3];
        String surname = parameters[4];
        String city = parameters[5];
        String address = parameters[6];
        String phone = parameters[7];
        String email = parameters[8];
        String class_ = parameters[9];
        int passengerNum = Integer.parseInt(parameters[10]);

        DB_Connection dbConnection = new DB_Connection(); // We open a connection with server two ...

        String url = "jdbc:mysql://localhost:3306/flights_db";
        String username = "root";
        String password = "kolos2020";

        Connection connection = null;
        try {

            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);

            /* FIRSTLY, WE WILL HAVE TO SEE HOW MANY SEATS ARE AVAILABLE IN THE FLIGHT BASED ON THE NUMBER THE CLIENT HAS GIVE AND OF COURSE THE CLASS */
            System.out.println("SERVER_TWO STATUS: //EXECUTING --CHECK-- QUERY//");
            String check_query = "SELECT COUNT(FLIGHT_NUMBER) AS COUNTER FROM SEAT\n" +
                    "WHERE FLIGHT_NUMBER = " + flightCode + " AND \n" +
                    "CLASS = '" + class_ + "' AND\n" +
                    "IS_AVAILABLE = 1;";
            int AVAILABLE_SEATS = Integer.parseInt(dbConnection.checkSeats(check_query, connection));
            if(passengerNum > AVAILABLE_SEATS) {
                System.out.println("SERVER_TWO STATUS: TOO MANY PASSENGERS CANNOT FIT IN THE FLIGHT --> FLIGHT_NUM -- " + flightCode +
                        ", CLASS -- " + class_ + ", NUM_OF_AVAILABLE_SEATS -- " + AVAILABLE_SEATS + " --");
                return protocol.NOT_AVAILABLE_SEATS;
            }

            /* NOTE!!!!!!!: THE INSERTION OF THE PASSENGER IS NOT NECESSARILY TO COMMIT WITH THE OTHER QUERIES
            *  THAT'S BECAUSE, ONLY ONE TIME WE ADD A NEW PASSENGER, BUT THIS PASSENGER CAN MAKE A LOT OF
            *  BOOKING AND RESERVE AS SEATS HE/SHE WANTS ... */
            // First query ...
            System.out.println("SERVER_TWO STATUS: //EXECUTING 1 QUERY//");
            String query_str1 = "INSERT INTO PASSENGER " +
                    "(PASSENGER_PASSPORT_NUMBER, NAMEp, SURNAMEp, CITY, ADDRESS, PHONE, EMAIL)\n" +
                    "VALUES (" + passport + ", '" + name + "', '" + surname + "', '" + city + "', '" + address + "', '" + phone + "', '" + email + "');\n";
            if(dbConnection.insertPassenger(query_str1) == 0) {
                System.out.println("SERVER_TWO STATUS: insertPassenger DONE");
            } else if(dbConnection.insertPassenger(query_str1) == 1) {
                System.out.println("SERVER_TWO STATUS: insertPassenger " + protocol.PASSENGER_ALREADY_EXISTS); /// it's OK we don't mind ...
                /// we just continue if the passenger already exists ...
            } else {
                System.out.println("SERVER_TWO STATUS: insertPassenger ERROR");
                return protocol.ERROR; // if error occurred, we must stop ...
            }
            //

            // Second query ...
            System.out.println("SERVER_TWO STATUS: //EXECUTING 2 QUERY//");
            String []small_info_data = null;
            String query_str2 = "SELECT DEPARTURE_DATE, DEPARTURE_TIME, ARRIVAL_TIME " +
                    "FROM FLIGHT " +
                    "WHERE FLIGHT_NUMBER = " + flightCode;
            String SMALL_INFO_DATA =  dbConnection.getSpecificInfoForFlight(query_str2, connection); // returning DEPARTURE_DATE, DEPARTURE_TIME, ARRIVAL_TIME of a specific flight ...
            small_info_data = SMALL_INFO_DATA.split("/");
            //

            // Third query ...
            System.out.println("SERVER_TWO STATUS: //EXECUTING 3 QUERY//");
            String query_str3 = "INSERT INTO BOOKING (BOOKING_DATE, PASSENGER_PASSPORT_NUMBER, FLIGHT_NUMBER, DEPARTURE_DATE, DEPARTURE_TIME, ARRIVAL_TIME)\n" +
                    "VALUES ('" + date + "', " + passport + ", " + flightCode + ", '" + small_info_data[0] + "', '" + small_info_data[1] + "', '" + small_info_data[2] + "');\n";

            if(dbConnection.insertBooking(query_str3, connection)) {
                System.out.println("SERVER_TWO STATUS: insertBooking DONE");
            } else {
                System.out.println("SERVER_TWO STATUS: insertBooking ERROR");
                return protocol.ERROR;
            }
            //


            /* We will run this for all the pass that the user has picked,
             *
             *  NOTE!!!!: WE DON'T NEED TO BOOK passengerNum TIMES BUT ONLY TAKE
             *  passengerNum SEATS IN THE PLANE, BECAUSE ONE PERSON BOOKS THE FLIGHT,
             *  FOR EXAMPLE, A FAMILY WITH TWO CHILDREN ...*/
            for (int i = 0; i < passengerNum; i++) {
                // Fourth query ...
                System.out.println("SERVER_TWO STATUS: //EXECUTING 4 QUERY//");
                String query_str4 = "UPDATE SEAT \n" +
                        "SET IS_AVAILABLE = 0, " + "\n" +
                        "PASSENGER_PASSPORT_NUMBER = " + passport + "\n" +
                        "WHERE FLIGHT_NUMBER = " + flightCode + " \n" +
                        "AND CLASS = '" + class_ + "' \n" +
                        "AND IS_AVAILABLE = 1 \n" +
                        "LIMIT 1;";
                /* This will update the first available seat that founds, based on the flight number and the class */
                if (dbConnection.updateSeat(query_str4, connection)) {
                    System.out.println("SERVER_TWO STATUS: insertBooking DONE");
                } else {
                    System.out.println("SERVER_TWO STATUS: insertBooking ERROR");
                    return protocol.ERROR;
                }
            }
            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // If query fails ...
                    return protocol.ERROR; // also return ERROR ...
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return protocol.DONE;
    }

    /* Take the flights going ... */
    private String getAllFlightsGoing(String PARAMETERS_FOR_QUERY) {

        String [] parameters = PARAMETERS_FOR_QUERY.split("/");

        String from = parameters[0];
        String to  = parameters[1];
        String passNum  = parameters[2];
        String departureD  = parameters[3];
        String returnD  = parameters[4];

        System.out.println("SERVER_TWO STATUS: THE QUERY IN THE FUNCTION getAllFlightsGoing WILL BE EXECUTED WITH THE FOLLOWING PARAMETERS -- F: " + from + "T: " + to + "PNum: " + passNum + "DDate: " + departureD + "RDate: " + returnD);

        String query_str =
                "SELECT F.FLIGHT_NUMBER, F.AIRLINE_NAME, F.DEPARTURE_AIRPORT, F.ARRIVAL_AIRPORT, F.DEPARTURE_DATE, F.DEPARTURE_TIME, \n" +
                        "F.ARRIVAL_TIME, F.FLIGHT_DURATION, F.PRICE, COUNT(S.SEAT_ID) AS AVAILABLE_SEATS\n" +
                        "FROM FLIGHT F\n" +
                        "INNER JOIN SEAT S ON F.FLIGHT_NUMBER = S.FLIGHT_NUMBER\n" +
                        "WHERE S.IS_AVAILABLE = 1 AND \n" +
                        "F.DEPARTURE_AIRPORT = '" + from + "' AND\n" +
                        "F.ARRIVAL_AIRPORT = '" + to + "' AND\n" +
                        "F.DEPARTURE_DATE = '" + departureD + "'\n" +
                        "GROUP BY F.FLIGHT_NUMBER \n" +
                        "HAVING COUNT(S.SEAT_ID) >= " + passNum + ";";
        DB_Connection dbConnection = new DB_Connection(); // We open a connection with server two ...

        return dbConnection.getData(query_str); // We pass as parameter a statement ...
    }

    /* Take the flights returning ...
     *  NOTE: WE ONLY CHANGE THE FROM AND THE GOING*/
    private String getAllFlightsReturning(String PARAMETERS_FOR_QUERY) {

        String [] parameters = PARAMETERS_FOR_QUERY.split("/");
        System.out.println("TEST LENGTH --> " + parameters.length);

        String from = parameters[0];
        String to  = parameters[1];
        String passNum  = parameters[2];
        String departureD  = parameters[3];
        String returnD  = parameters[4];

        System.out.println("SERVER_TWO STATUS: THE QUERY IN THE FUNCTION getAllFlightsReturning WILL BE EXECUTED WITH THE FOLLOWING PARAMETERS -- F: " + from + "T: " + to + "PNum: " + passNum + "DDate: " + departureD + "RDate: " + returnD);

        String query_str =
                "SELECT F.FLIGHT_NUMBER, F.AIRLINE_NAME, F.DEPARTURE_AIRPORT, F.ARRIVAL_AIRPORT, F.DEPARTURE_DATE, F.DEPARTURE_TIME, \n" +
                        "F.ARRIVAL_TIME, F.FLIGHT_DURATION, F.PRICE, COUNT(S.SEAT_ID) AS AVAILABLE_SEATS\n" +
                        "FROM FLIGHT F\n" +
                        "INNER JOIN SEAT S ON F.FLIGHT_NUMBER = S.FLIGHT_NUMBER\n" +
                        "WHERE S.IS_AVAILABLE = 1 AND \n" +
                        "F.DEPARTURE_AIRPORT = '" + to + "' AND\n" +
                        "F.ARRIVAL_AIRPORT = '" + from + "' AND\n" +
                        "F.DEPARTURE_DATE = '" + returnD + "'\n" +
                        "GROUP BY F.FLIGHT_NUMBER \n" +
                        "HAVING COUNT(S.SEAT_ID) >= " + passNum + ";";
        DB_Connection dbConnection = new DB_Connection(); // We open a connection with server two ...

        return dbConnection.getData(query_str); // We pass as parameter a statement ...
    }
}
