package pr;

public interface protocol {
    public int getAllFlights_func = 1;
    public int submitBooking = 2; /// user submits info to book a flight ...

    /* Confirmation messages */
    public String DONE = "DONE";
    public String RECEIVED = "RECEIVED";

    /* ERRORS */
    public String BAD_CODE = "BAD_CODE";
    public String ERROR = "ERROR";
    public String PASSENGER_ALREADY_EXISTS = "PASSENGER_ALREADY_EXISTS";
    public String NOT_AVAILABLE_SEATS = "NOT_AVAILABLE_SEATS";
}
