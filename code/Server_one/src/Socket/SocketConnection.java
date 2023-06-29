package Socket;

import pr.protocol;

import java.io.*;
import java.net.Socket;

public class SocketConnection implements protocol {

    // THIS TWO FOR THE getAllFlights_func
    public String JSON_STR_ALL_FLIGHTS_GOING = null;
    public String JSON_STR_ALL_FLIGHTS_RETURNING = null;
    //
    //
    // THIS IS FOR THE submitBooking
    /* This indicates the status of the function submitBooking, if all are completed
     *  correctly then this is equal with protocol.DONE otherwise is equal with ERROR
     *  NOTE: WE ADD THIS VARIABLE SO IN THE CLASS Operation_Server, WE CAN KNOW IF THE FUNCTION
     *  COMPLETED SUCCESSFULLY OR NOT. WITH THE HELP OF THE VARIABLE, WE CAN KNOW WHAT TO SEND TO THE CLIENT ...
     *  */
    public String FINAL_STATUS = null;


    public SocketConnection(int FUNCTION_CODE, String MSG, String ID_CLIENT) {

        Socket connection = null;

        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        //
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        //
        ObjectInputStream objectInputStream = null;

        try {
            connection = new Socket("localhost", 5505);
            // Strings write/send
            outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            // String read/receive
            inputStreamReader = new InputStreamReader(connection.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            // Object, for the data as ResultSet
            objectInputStream = new ObjectInputStream(connection.getInputStream());

            System.out.println("\nSERVER_ONE: CONNECTION TO SERVER_TWO(" + connection.getInetAddress() + ", " + connection.getPort() + ")" + " FOR THE CLIENT -- " + ID_CLIENT + " --");
            String message = MSG + "\n"; // For getAllFlights contains the parameters for the query ...

            // The first one is for getAllFlights_func
            if(String.valueOf(FUNCTION_CODE).equals(String.valueOf(getAllFlights_func))) {

                System.out.println("SERVER_ONE STATUS: getAllFlights_func REQUESTED FROM CLIENT WITH ID -- " + ID_CLIENT + " --");
                bufferedWriter.write(FUNCTION_CODE); // send the function code in the server_two
                bufferedWriter.flush();

                String CONF_CODE1 = bufferedReader.readLine();
                System.out.println("SERVER_ONE STATUS: 1 CONFIRMATION CODE FROM CLIENT (AFTER SENDING THE FUNCTION CODE) -- " + CONF_CODE1 + " --");

                bufferedWriter.write(message); // We send the parameters ...
                bufferedWriter.flush();

                String CONF_CODE2 = bufferedReader.readLine();
                System.out.println("SERVER_ONE STATUS: 1 CONFIRMATION CODE FROM CLIENT (AFTER SENDING THE PARAMETERS) -- " + CONF_CODE2 + " --");

                this.JSON_STR_ALL_FLIGHTS_GOING = bufferedReader.readLine(); // We read the results (DATA FROM THE SERVER)
                bufferedWriter.write(protocol.RECEIVED + "\n");  // we send a confirmation code ...
                bufferedWriter.flush();
                this.JSON_STR_ALL_FLIGHTS_RETURNING = bufferedReader.readLine();
                bufferedWriter.write(protocol.RECEIVED + "\n");  // we send a confirmation code ...
                bufferedWriter.flush();

            } else if(String.valueOf(FUNCTION_CODE).equals(String.valueOf(submitBooking))) {

                //System.out.println("TEST FUNC --> " + FUNCTION_CODE);
                System.out.println("SERVER_ONE STATUS: submitBooking REQUESTED FROM CLIENT WITH ID -- " + ID_CLIENT + " --");
                bufferedWriter.write(FUNCTION_CODE); // send the function code in the server_two
                bufferedWriter.flush();

                String CONF_CODE1 = bufferedReader.readLine();
                System.out.println("SERVER_ONE STATUS: 2 CONFIRMATION CODE FROM CLIENT (AFTER SENDING THE FUNCTION CODE) -- " + CONF_CODE1 + " --");

                bufferedWriter.write(message); // We send the parameters ...
                bufferedWriter.flush();

                String CONF_CODE2 = bufferedReader.readLine();
                System.out.println("SERVER_ONE STATUS: 2 CONFIRMATION CODE FROM CLIENT (AFTER SENDING THE PARAMETERS) -- " + CONF_CODE2 + " --");

                /* If the operation is completed successfully, then we will get here a MSG that says DONE if
                * it is not completed successfully we will get NOT_AVAILABLE_SEATS or ERROR */
                String MSG_FROM_SERVER = bufferedReader.readLine();
                System.out.println("SERVER_ONE STATUS: MSG FROM SERVER FOR THE OPERATION submitBooking IS -- " + MSG_FROM_SERVER + " --");

                bufferedWriter.write(protocol.RECEIVED + "\n"); // we send a confirmation code ...
                bufferedWriter.flush();

                FINAL_STATUS = MSG_FROM_SERVER;

            }
            else {
                System.out.println("SERVER_ONE STATUS: //ERROR// WITH DECIDING WHICH FUNCTION TO DO -- " + protocol.BAD_CODE + " --");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized String waitForResponse() throws InterruptedException {
        while (FINAL_STATUS == null) {
            wait();
        }
        return FINAL_STATUS;
    }

}
