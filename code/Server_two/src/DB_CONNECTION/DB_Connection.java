package DB_CONNECTION;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

/* Here we establish a connection with the server, and we execute the query */

public class DB_Connection {
    private final String url = "jdbc:mysql://localhost:3306/flights_db";
    private final String username = "root";
    private final String password = "kolos2020"; // Συγνώμη για αυτό, απλά αυτό τον κωδικό είχα στη Βάση στον υπολογιστή ...

    public String getData(String statement_str) {
        System.out.println("SERVER_TWO STATUS: getData CALLED ...");
        ResultSet resultSet = null;
        String JSON_STR = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(statement_str);

            try {
                JSON_STR = resultSetToJson(resultSet); // This is the JSON which contains the data from the database ...
                //System.out.println("TEST -->" + JSON_STR);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("SERVER_TWO STATUS: //ERROR// IN THE DB_connection IN THE FUNC getData ...");
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println("SERVER_TWO STATUS: //ERROR// IN THE DB_connection IN THE FUNC getData ...");
            throw new RuntimeException(e);
        }
        return JSON_STR;
    }

    /* This function is to get specific info for a specific flight; it is used in the submit function in the class Session */
    public String getSpecificInfoForFlight(String statement_str, Connection connection) {
        System.out.println("SERVER_TWO STATUS: getSpecificInfoForFlight CALLED ...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement(statement_str);
            ResultSet resultSet = statement.executeQuery(statement_str);

            /* The following string will only contain the DEPARTURE_DATE, DEPARTURE_TIME, ARRIVAL_TIME
            *
            * NOTE!!!!: WE DON'T CREATE A JSON_STRING LIKE IN THE FUNCTION getData BECAUSE WE HAVE A VERY SMALL AMOUNT OF INFO THAT WE WANT FROM THE DATABASE
            * AND WE JUST WANT IT TO COMPLETE THE FUNCTION SUBMIT IN THE CLASS SESSION ... */
            String result = null;
            while (resultSet.next())
                result = resultSet.getString("DEPARTURE_DATE") + "/" + resultSet.getString("DEPARTURE_TIME") + "/" + resultSet.getString("ARRIVAL_TIME");

            return result;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("SERVER_TWO STATUS: //ERROR// IN THE DB_connection IN THE FUNC getSpecificInfoForFlight ...");
            System.out.println(e);
            return null;
        }
    }

    /* 1 --> DONE
    *  2 --> PASSENGER_ALREADY_EXISTS
    *  3--> ERROR
    *                                    */
    public int insertPassenger(String statement_str) {
        System.out.println("SERVER_TWO STATUS: insertPassenger CALLED ...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();

            int rows_affected = statement.executeUpdate(statement_str);
            System.out.println("SERVER_TWO STATUS: ROWS AFFECTED IN insertPassenger -- " + rows_affected + " --");

            connection.close();
            return 0; // the insertPassenger has been done WITH NO problems ...
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("SERVER_TWO STATUS: //PASSENGER_ALREADY_EXISTS// IN THE DB_connection IN THE FUNC insertPassenger ...");
            System.out.println(e);
            return 1;
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println("SERVER_TWO STATUS: //ERROR// IN THE DB_connection IN THE FUNC insertPassenger ...");
            System.out.println(e);
            return 2; // the insertPassenger has been done WITH problems ...
        }
    }

    public boolean insertBooking(String statement_str, Connection connection) {
        System.out.println("SERVER_TWO STATUS: insertBooking CALLED ...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement(statement_str);

            int rows_affected = statement.executeUpdate(statement_str);
            System.out.println("SERVER_TWO STATUS: ROWS AFFECTED IN insertBooking -- " + rows_affected + " --");

            return true; // the insertBooking process has been done WITH NO problems ...
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("SERVER_TWO STATUS: //ERROR// IN THE DB_connection IN THE FUNC insertBooking ...");
            System.out.println(e);
            return false; // the insertBooking process has been done WITH problems ...
        }
    }

    public boolean updateSeat(String statement_str, Connection connection) {
        System.out.println("SERVER_TWO STATUS: updateSeat CALLED ...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement(statement_str);

            int rows_affected = statement.executeUpdate(statement_str);
            System.out.println("SERVER_TWO STATUS: ROWS AFFECTED IN updateSeat -- " + rows_affected + " --");

            return true; // the updateSeat process has been done WITH NO problems ...
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("SERVER_TWO STATUS: //ERROR// IN THE DB_connection IN THE FUNC updateSeat ...");
            System.out.println(e);
            return false; // the updateSeat process has been done WITH problems ...
        }
    }

    public String checkSeats(String statement_str, Connection connection) {
        System.out.println("SERVER_TWO STATUS: getSpecificInfoForFlight CALLED ...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement(statement_str);
            ResultSet resultSet = statement.executeQuery(statement_str);

            String result = null;
            while (resultSet.next())
                result = resultSet.getString("COUNTER");

            return result;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("SERVER_TWO STATUS: //ERROR// IN THE DB_connection IN THE FUNC getSpecificInfoForFlight ...");
            System.out.println(e);
            return null;
        }
    }

    ///
    private String resultSetToJson(ResultSet resultSet) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                Object value = resultSet.getObject(i);

                jsonObject.put(columnName, value);
            }

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }
}
