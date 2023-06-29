package GUI.Functions;
import GUI.AppMainFrame;
import op.Operations;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientInfo_GUI extends JPanel implements ActionListener {
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private final int WIDTH;
    private final int HEIGHT;

    // Button JPanel
    private JPanel button_Jpanel;
    private JButton submit_butt;
    private JButton goBack_butt;
    // Main_Jpanel contains right_JPanel and left_JPanel ...
    private JPanel main_JPanel;
    private JPanel right_JPanel, left_JPanel;
    /// Right JPanel has TOP_RIGHT_JPANEL and BOTTOM_RIGHT_JPANEL
    private JPanel TOP_RIGHT_JPANEL;
    private JPanel BOTTOM_RIGHT_JPANEL;
    private JTextField passport_JText;
    private JTextField name_JText;
    private JTextField surname_JText;
    private JTextField city_JText;
    private JTextField address_JText;
    private JTextField phone_JText;
    private JTextField email_JText;
    private JComboBox class_JComdoBox;
    /// left_JPanel has TOP_LEFT_JPANEL and BOTTOM_LEFT_JPANEL
    private JPanel TOP_LEFT_JPANEL;
    private JPanel BOTTOM_LEFT_JPANEL;


    //
    private AppMainFrame frame_belongs;
    private Registration_GUI father_GUI;
    //
    // This is the info we gonna add in the flight Details ...
    private String airline;
    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private String departureDate;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String availableSeats;
    private String price;
    //
    private final Color BACKR_COLOR;

    /* We add this so we can call the operation submit from here */
    private Operations look_up;
    private String UNIQUE_ID; // this is so we can know in which client the ClientInfo_Panel belongs

    public ClientInfo_GUI(String UNIQUE_ID, int WIDTH, int HEIGHT, Color BACKR_COLOR, AppMainFrame frame_belongs, Registration_GUI father_GUI, Operations look_up) {

        this.look_up = look_up;
        this.UNIQUE_ID = UNIQUE_ID;

        // The panel that is the father of this Panel ...
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.frame_belongs = frame_belongs;
        this.father_GUI = father_GUI;
        this.BACKR_COLOR = BACKR_COLOR;

        this.setBackground(BACKR_COLOR);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(null);

        setButtons();

        this.setVisible(true);
    }

    /* This function is to load all the info for the flight the user pressed ... */
    protected void findPanelPressed(ArrayList<String[]> flightInfo, int INDEX_PANEL_PRESSED) {
        for(int i = 0;i < flightInfo.size();i++) {
            if(i == INDEX_PANEL_PRESSED) {
                this.airline = flightInfo.get(i)[0];
                this.flightNumber = flightInfo.get(i)[1];
                this.departureAirport = flightInfo.get(i)[2];
                this.arrivalAirport = flightInfo.get(i)[3];
                this.departureDate = flightInfo.get(i)[4];
                this.arrivalTime = flightInfo.get(i)[5];
                this.departureTime = flightInfo.get(i)[6];
                this.duration = flightInfo.get(i)[7];
                this.availableSeats = flightInfo.get(i)[8];
                this.price = flightInfo.get(i)[9];
            }
        }
        setMainForm();
        /*System.out.println("TEST --> " + "\n");
        System.out.println(airline);
        System.out.println(flightNumber);
        System.out.println(departureAirport);
        System.out.println(arrivalAirport);
        System.out.println(departureDate);
        System.out.println(arrivalTime);
        System.out.println(departureTime);
        System.out.println(duration);
        System.out.println(availableSeats);
        System.out.println(price);*/
    }

    private void setButtons() {
        button_Jpanel = new JPanel();
        button_Jpanel.setBackground(new Color(51,153,255));
        button_Jpanel.setBounds(0,0,WIDTH, 50);
        button_Jpanel.setLayout(new GridLayout(1,2));

        submit_butt = new JButton("Submit");
        submit_butt.setFocusable(false);
        submit_butt.setFont(new Font("Serif", Font.BOLD, 30));
        submit_butt.setHorizontalAlignment(SwingConstants.CENTER);
        submit_butt.setBorder(border);
        submit_butt.addActionListener(this);
        //
        goBack_butt = new JButton("Go back");
        goBack_butt.setFocusable(false);
        goBack_butt.setFont(new Font("Serif", Font.BOLD, 30));
        goBack_butt.setHorizontalAlignment(SwingConstants.CENTER);
        goBack_butt.setBorder(border);
        goBack_butt.addActionListener(this);
        //
        button_Jpanel.add(goBack_butt);
        button_Jpanel.add(submit_butt);

        this.add(button_Jpanel);
    }

    private void setMainForm() {
        main_JPanel = new JPanel();
        main_JPanel.setBackground(BACKR_COLOR);
        main_JPanel.setBounds(0,50,WIDTH, HEIGHT - 85);
        main_JPanel.setLayout(new GridLayout(1,2));

        // left_JPanel
        left_JPanel = new JPanel();
        left_JPanel.setBackground(BACKR_COLOR);
        left_JPanel.setLayout(null);

        TOP_LEFT_JPANEL = new JPanel();
        TOP_LEFT_JPANEL.setBounds(0,0,WIDTH/2, 60);
        TOP_LEFT_JPANEL.setBackground(BACKR_COLOR);
        TOP_LEFT_JPANEL.setLayout(new GridLayout(1,1));
        JLabel flight_details_JLabel = new JLabel("Flight Details");
        setJLabels(flight_details_JLabel);
        TOP_LEFT_JPANEL.add(flight_details_JLabel);

        BOTTOM_LEFT_JPANEL = new JPanel();
        BOTTOM_LEFT_JPANEL.setBounds(0,60,WIDTH/2, HEIGHT - 130);
        BOTTOM_LEFT_JPANEL.setBackground(BACKR_COLOR);
        BOTTOM_LEFT_JPANEL.setLayout(new GridLayout(10,2));

        JLabel flightNumber_JLabel = new JLabel("Flight Number:");
        setJLabels(flightNumber_JLabel);
        JLabel flightNumber_value_JLabel = new JLabel(flightNumber);
        setJLabels(flightNumber_value_JLabel);

        JLabel airline_JLabel = new JLabel("Airline:");
        setJLabels(airline_JLabel);
        JLabel airline_value_JLabel = new JLabel(airline);
        setJLabels(airline_value_JLabel);

        JLabel departureAirport_JLabel = new JLabel("Departure Airport:");
        setJLabels(departureAirport_JLabel);
        JLabel departureAirport_value_JLabel = new JLabel(departureAirport);
        setJLabels(departureAirport_value_JLabel);

        JLabel arrivalAirport_JLabel = new JLabel("Arrival Airport:");
        setJLabels(arrivalAirport_JLabel);
        JLabel arrivalAirport_value_JLabel = new JLabel(arrivalAirport);
        setJLabels(arrivalAirport_value_JLabel);

        JLabel departureDate_JLabel = new JLabel("Departure date:");
        setJLabels(departureDate_JLabel);
        JLabel departureDate_value_JLabel = new JLabel(departureDate);
        setJLabels(departureDate_value_JLabel);

        JLabel departureTime_JLabel = new JLabel("Departure Time:");
        setJLabels(departureTime_JLabel);
        JLabel departureTime_value_JLabel = new JLabel(departureTime);
        setJLabels(departureTime_value_JLabel);

        JLabel arrivalTime_JLabel = new JLabel("Arrival Time:");
        setJLabels(arrivalTime_JLabel);
        JLabel arrivalTime_value_JLabel = new JLabel(arrivalTime);
        setJLabels(arrivalTime_value_JLabel);

        JLabel duration_JLabel = new JLabel("Duration:");
        setJLabels(duration_JLabel);
        JLabel duration_value_JLabel = new JLabel(duration);
        setJLabels(duration_value_JLabel);

        JLabel availableSeats_JLabel = new JLabel("Available Seats:");
        setJLabels(availableSeats_JLabel);
        JLabel availableSeats_value_JLabel = new JLabel(availableSeats);
        setJLabels(availableSeats_value_JLabel);

        JLabel price_JLabel = new JLabel("Price:");
        setJLabels(price_JLabel);
        JLabel price_value_JLabel = new JLabel(price);
        setJLabels(price_value_JLabel);
        //
        BOTTOM_LEFT_JPANEL.add(flightNumber_JLabel);
        BOTTOM_LEFT_JPANEL.add(flightNumber_value_JLabel);

        BOTTOM_LEFT_JPANEL.add(airline_JLabel);
        BOTTOM_LEFT_JPANEL.add(airline_value_JLabel);

        BOTTOM_LEFT_JPANEL.add(departureAirport_JLabel);
        BOTTOM_LEFT_JPANEL.add(departureAirport_value_JLabel);

        BOTTOM_LEFT_JPANEL.add(arrivalAirport_JLabel);
        BOTTOM_LEFT_JPANEL.add(arrivalAirport_value_JLabel);

        BOTTOM_LEFT_JPANEL.add(departureDate_JLabel);
        BOTTOM_LEFT_JPANEL.add(departureDate_value_JLabel);

        BOTTOM_LEFT_JPANEL.add(departureTime_JLabel);
        BOTTOM_LEFT_JPANEL.add(departureTime_value_JLabel);

        BOTTOM_LEFT_JPANEL.add(arrivalTime_JLabel);
        BOTTOM_LEFT_JPANEL.add(arrivalTime_value_JLabel);

        BOTTOM_LEFT_JPANEL.add(duration_JLabel);
        BOTTOM_LEFT_JPANEL.add(duration_value_JLabel);

        BOTTOM_LEFT_JPANEL.add(availableSeats_JLabel);
        BOTTOM_LEFT_JPANEL.add(availableSeats_value_JLabel);

        BOTTOM_LEFT_JPANEL.add(price_JLabel);
        BOTTOM_LEFT_JPANEL.add(price_value_JLabel);

        left_JPanel.add(TOP_LEFT_JPANEL);
        left_JPanel.add(BOTTOM_LEFT_JPANEL);

        // right_JPanel
        right_JPanel = new JPanel();
        right_JPanel.setBackground(Color.WHITE);
        right_JPanel.setLayout(null);

        TOP_RIGHT_JPANEL = new JPanel();
        TOP_RIGHT_JPANEL.setBounds(0,0,WIDTH/2, 60);
        TOP_RIGHT_JPANEL.setBackground(new Color(17, 0, 166));
        TOP_RIGHT_JPANEL.setLayout(new GridLayout(1,1));
        JLabel your_info = new JLabel("Your Info");
        setJLabels(your_info);
        TOP_RIGHT_JPANEL.add(your_info);

        BOTTOM_RIGHT_JPANEL = new JPanel();
        BOTTOM_RIGHT_JPANEL.setBounds(0,60,WIDTH/2, HEIGHT - 130);
        BOTTOM_RIGHT_JPANEL.setBackground(new Color(17, 0, 166));
        BOTTOM_RIGHT_JPANEL.setLayout(new GridLayout(8,2));

        JLabel passport_JLabel = new JLabel("Passport ID:");
        setJLabels(passport_JLabel);
        passport_JText = new JTextField();
        setJTextFields(passport_JText);

        JLabel name_JLabel = new JLabel("Name:");
        setJLabels(name_JLabel);
        name_JText = new JTextField();
        setJTextFields(name_JText);

        JLabel surname_JLabel = new JLabel("Surname:");
        setJLabels(surname_JLabel);
        surname_JText = new JTextField();
        setJTextFields(surname_JText);

        JLabel city_JLabel = new JLabel("City:");
        setJLabels(city_JLabel);
        city_JText = new JTextField();
        setJTextFields(city_JText);

        JLabel address_JLabel = new JLabel("Address:");
        setJLabels(address_JLabel);
        address_JText = new JTextField();
        setJTextFields(address_JText);

        JLabel phone_JLabel = new JLabel("Phone:");
        setJLabels(phone_JLabel);
        phone_JText = new JTextField();
        setJTextFields(phone_JText);

        JLabel email_JLabel = new JLabel("Email:");
        setJLabels(email_JLabel);
        email_JText = new JTextField();
        setJTextFields(email_JText);

        JLabel class_JLabel = new JLabel("Class:");
        setJLabels(class_JLabel);
        String[] classSeat = {"Business", "Economy"};
        class_JComdoBox = new JComboBox(classSeat);
        class_JComdoBox.setFont(new Font("Serif", Font.BOLD, 20));
        class_JComdoBox.setBorder(border);
        class_JComdoBox.setSelectedIndex(-1);

        BOTTOM_RIGHT_JPANEL.add(passport_JLabel);
        BOTTOM_RIGHT_JPANEL.add(passport_JText);

        BOTTOM_RIGHT_JPANEL.add(name_JLabel);
        BOTTOM_RIGHT_JPANEL.add(name_JText);

        BOTTOM_RIGHT_JPANEL.add(surname_JLabel);
        BOTTOM_RIGHT_JPANEL.add(surname_JText);

        BOTTOM_RIGHT_JPANEL.add(city_JLabel);
        BOTTOM_RIGHT_JPANEL.add(city_JText);

        BOTTOM_RIGHT_JPANEL.add(address_JLabel);
        BOTTOM_RIGHT_JPANEL.add(address_JText);

        BOTTOM_RIGHT_JPANEL.add(phone_JLabel);
        BOTTOM_RIGHT_JPANEL.add(phone_JText);

        BOTTOM_RIGHT_JPANEL.add(email_JLabel);
        BOTTOM_RIGHT_JPANEL.add(email_JText);

        BOTTOM_RIGHT_JPANEL.add(class_JLabel);
        BOTTOM_RIGHT_JPANEL.add(class_JComdoBox);


        right_JPanel.add(TOP_RIGHT_JPANEL);
        right_JPanel.add(BOTTOM_RIGHT_JPANEL);



        main_JPanel.add(left_JPanel);
        main_JPanel.add(right_JPanel);

        this.add(main_JPanel);
    }
    private void setJLabels(JLabel jLabel) {
        jLabel.setFont(new Font("Serif", Font.BOLD, 30));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setForeground(Color.WHITE);
        jLabel.setBorder(border);
    }
    private void setJTextFields(JTextField jTextField) {
        jTextField.setFont(new Font("Serif", Font.BOLD, 20));
        jTextField.setHorizontalAlignment(SwingConstants.CENTER);
        jTextField.setBorder(border);
    }

    /* We call to go back to the father panel of this panel, which is Registration_GUI.
    *  We also remove this panel from the JFrame that this Panel belongs */
    public void goBackGUI() {
        frame_belongs.remove(this);
        frame_belongs.add(father_GUI);

        frame_belongs.revalidate();
        frame_belongs.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit_butt)
        {
            System.out.println("CLIENT STATUS: SUBMIT BUTTON PRESSED");
            if(passport_JText.getText().equals("") || name_JText.getText().equals("")  || surname_JText.getText().equals("")
                    || city_JText.getText().equals("")  || address_JText.getText().equals("") || phone_JText.getText().equals("")
                    || email_JText.getText().equals("") || class_JComdoBox.getSelectedItem() == null)
            {
                System.out.println("CLIENT STATUS: CANNOT PROCEED THE USER MUST FILL ALL THE FIELDS");
                JOptionPane.showMessageDialog(this, "You must fill all the fields", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                /* NOTE: HERE THE FUNCTION SUBMITTING IS GONNA BE CALLED, SO WE WILL SEND TO THE SERVER_ONE THE
                *  INFO OF THE CLIENT (USER/CUSTOMER) ... */
                System.out.println("CLIENT STATUS: ALL FIELDS HAVE BEEN FILLED");
                try {
                    String MSG_RESULT = look_up.submitFlight(UNIQUE_ID, flightNumber, passport_JText.getText(), name_JText.getText(), surname_JText.getText(),
                            city_JText.getText(), address_JText.getText(), phone_JText.getText(),
                            email_JText.getText(), class_JComdoBox.getSelectedItem().toString(), father_GUI.passNum_JText.getText());
                    if(MSG_RESULT.equals("NOT_AVAILABLE_SEATS")) {
                        JOptionPane.showMessageDialog(this, "Not available seats in the class class_JComdoBox.getSelectedItem().toString()" +
                                        " for " + father_GUI.passNum_JText.getText() + " passengers.", "Warning",
                                JOptionPane.WARNING_MESSAGE);
                        goBackGUI();
                        father_GUI.submit_butt.doClick(); // we force the click because we want to refresh the flights ...
                    } else if(MSG_RESULT.equals("ERROR")) {
                        JOptionPane.showMessageDialog(this, "An error occurred try again please.", "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "The submission of the flight has been done correctly.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        goBackGUI();
                        father_GUI.submit_butt.doClick(); // we force the click because we want to refresh the flights ...
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if(e.getSource() == goBack_butt)
        {
            System.out.println("CLIENT STATUS: GO BACK BUTTON PRESSED");
            goBackGUI();
        }
    }
}
