package GUI.Functions;

import GUI.AppMainFrame;
import op.Operations;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/* This class represents the info-section and the flights that are shown */

public class Registration_GUI extends JPanel implements ActionListener  {
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 3);

    private int WIDTH;
    private int HEIGHT;

    private Operations look_up; // the server that the client belongs ...
    private String UNIQUE_ID;
    private Color BACKR_COLOR;

    private JFrame frameBelongs;
    /// "Add info" panel
    private JPanel info_JPanel;
    private JLabel from_Jlabel;
    private JLabel to_Jlabel;
    private JLabel passNum_Jlabel;
    private JLabel departureDate_Jlabel;
    private JLabel returnDate_Jlabel;
    private JLabel sumbit_JLabel;

    //

    public JTextField passNum_JText; /// WE MAKE IT PUBLIC, SO THE CLASS ClientInfo_GUI can see this and send it to the server_one so the corrected INSERTIONS AND UPDATE BEEN DONE ...
    // JComboBoxes
    String[] airports ={"Samos Aristarchos Samios Airport", "Athens Airport Eleftherios Venizelos", "Mykonos International Airport",
                        "Milos Airport", "Airport of Thessaloniki Macedonia"};
    private JComboBox from_comboBox;
    private JComboBox to_comboBox;

    // DatePicker
    private Date selectedDateDeparture;
    private Date selectedDateReturn;

    //

    public JButton submit_butt;

    /// flights panel
    private JPanel flights_panel;
    private int temp = 0; // if 0 no flights displayed, if 1 flights displayed
    private ArrayList<JPanel> FLIGHTS_PANELS_LIST = new ArrayList<>(); // this list is to handle all the panels with the flights ...
    private JScrollPane scrollPane;

    public Registration_GUI(AppMainFrame appMainFrame, int WIDTH, int HEIGHT, Color BACKR_COLOR, Operations look_up, String unique_id)
    {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.BACKR_COLOR = BACKR_COLOR;
        this.UNIQUE_ID = unique_id;
        this.look_up = look_up;
        this.frameBelongs = appMainFrame;
        this.setBackground(BACKR_COLOR);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT - 30)); // -... because of the menu ...
        this.setLayout(null);

        setInfoPanel(WIDTH, HEIGHT, BACKR_COLOR);
        setFlightsPanel(WIDTH, HEIGHT, BACKR_COLOR);

        this.setVisible(true);
    }

    private void setInfoPanel(int WIDTH, int HEIGHT, Color BACKR_COLOR) {
        System.out.println("\nCLIENT STATUS: CREATING INFO PANEL...");
        info_JPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(6,2);
        gridLayout.setVgap(2);
        info_JPanel.setBounds(0,0,WIDTH,300);
        info_JPanel.setBackground(BACKR_COLOR);
        info_JPanel.setLayout(gridLayout);


        from_Jlabel = new JLabel("From:");
        to_Jlabel = new JLabel("To:");
        passNum_Jlabel = new JLabel("Passengers num:");
        departureDate_Jlabel = new JLabel("Departure date:");
        returnDate_Jlabel = new JLabel("Return date:");
        sumbit_JLabel = new JLabel("When you finish press here:");
        //
        setJLabels(from_Jlabel);
        setJLabels(to_Jlabel);
        setJLabels(passNum_Jlabel);
        setJLabels(departureDate_Jlabel);
        setJLabels(returnDate_Jlabel);
        setJLabels(sumbit_JLabel);

        // ComboBoxes
        from_comboBox = new JComboBox(airports);
        from_comboBox.setFont(new Font("Serif", Font.BOLD, 20));
        from_comboBox.setBorder(border);
        from_comboBox.setSelectedIndex(-1);
        to_comboBox = new JComboBox(airports);
        to_comboBox.setFont(new Font("Serif", Font.BOLD, 20));
        to_comboBox.setBorder(border);
        to_comboBox.setSelectedIndex(-1);

        // DatePicker
        // DatePicker
        UtilDateModel model1 = new UtilDateModel();
        Properties p1 = new Properties();
        p1.put("text.today", "Today");
        p1.put("text.month", "Month");
        p1.put("text.year", "Year");
        JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p1);
        datePanel1.setBackground(Color.GRAY);
        JDatePickerImpl departureDate_DatePicker = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
        // Listeners
        departureDate_DatePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDateDeparture = (Date) departureDate_DatePicker.getModel().getValue();
                if (selectedDateDeparture != null) {
                    System.out.println("CLIENT STATUS: " + selectedDateDeparture.toString());
                } else {
                    System.out.println("CLIENT STATUS: No date selected");
                }
            }
        });
        //
        UtilDateModel model2 = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Today");
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
        JDatePickerImpl returnDate_DatePicker = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        // Listeners
        returnDate_DatePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDateReturn = (Date) returnDate_DatePicker.getModel().getValue();
                if (selectedDateReturn != null) {
                    System.out.println("CLIENT STATUS: " + selectedDateReturn.toString());
                } else {
                    System.out.println("CLIENT STATUS: No date selected");
                }
            }
        });

        //

        passNum_JText = new JTextField();
        setJTextFields(passNum_JText);


        submit_butt = new JButton("Search");
        submit_butt.setFocusable(false);
        submit_butt.setFont(new Font("Serif", Font.BOLD, 30));
        submit_butt.setHorizontalAlignment(SwingConstants.CENTER);
        submit_butt.setBorder(border);
        submit_butt.addActionListener(this);


        info_JPanel.add(from_Jlabel);
        info_JPanel.add(from_comboBox);

        info_JPanel.add(to_Jlabel);
        info_JPanel.add(to_comboBox);

        info_JPanel.add(passNum_Jlabel);
        info_JPanel.add(passNum_JText);

        info_JPanel.add(departureDate_Jlabel);
        info_JPanel.add((Component) departureDate_DatePicker);

        info_JPanel.add(returnDate_Jlabel);
        info_JPanel.add((Component) returnDate_DatePicker);

        info_JPanel.add(sumbit_JLabel);
        info_JPanel.add(submit_butt);

        this.add(info_JPanel);
        System.out.println("CLIENT STATUS: INFO PANEL CREATED");
    }
    private void setFlightsPanel(int WIDTH, int HEIGHT, Color BACKR_COLOR)
    {
        System.out.println("\nCLIENT STATUS: CREATING FLIGHT PANEL...");
        flights_panel = new JPanel();
        flights_panel.setBounds(0,300,WIDTH,HEIGHT - 330);
        flights_panel.setLayout(new GridLayout(1,1));
        flights_panel.setBackground(BACKR_COLOR);

        scrollPane = new JScrollPane(flights_panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 300, WIDTH, HEIGHT - 330);

        this.add(scrollPane);
        System.out.println("CLIENT STATUS: FLIGHT PANEL CREATED");
    }
    private void generateFlights(String from, String to, int passNum, String departureD, String returnD) {
        flights_panel.removeAll();
        ArrayList<String[]> flights = null;
        try {
            String msg_from_server = look_up.sendMSG(UNIQUE_ID, "I want the flights");
            System.out.println("CLIENT STATUS: MSG FROM SERVER == " + msg_from_server); // we do this just so the server admin can know what it will happen ...
            flights = look_up.getAllFlights(UNIQUE_ID, from, to, passNum, departureD, returnD); // all the flights
            System.out.println("CLIENT STATUS: FLIGHTS RECEIVED");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(flights != null) {
            if(flights.size() == 0) {
                JOptionPane.showMessageDialog(null, "No flights found", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            flights_panel.setLayout(new GridLayout(flights.size() + 2, 1));
            createFlightsPanels(flights);
            scrollPane.revalidate();
            scrollPane.repaint();
            System.out.println("CLIENT STATUS: FLIGHTS GENERATED");
        } else {
            JOptionPane.showMessageDialog(null, "Error occurred, cannot generate flights", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    /* This class is just to add the flights to the GUI */
    private void createFlightsPanels(ArrayList<String[]> flights) {
        for (int i = 0;i < flights.size();i++) {
            JPanel flight_panel = new JPanel();
            flight_panel.setBackground(Color.DARK_GRAY);
            flight_panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    flight_panel.setBackground(Color.LIGHT_GRAY);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    flight_panel.setBackground(Color.DARK_GRAY);
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("CLIENT STATUS: A FLIGHT PANEL PRESSED -- " + flight_panel + " --");
                    int index = FLIGHTS_PANELS_LIST.indexOf(flight_panel);
                    ClientInfo_GUI clientInfoGui = new ClientInfo_GUI(UNIQUE_ID, WIDTH, HEIGHT, BACKR_COLOR, (AppMainFrame) frameBelongs, getThis(), look_up);
                    clientInfoGui.findPanelPressed(flights, index);
                    setClientInfoPanel(clientInfoGui);
                }
            });
            flight_panel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (flight_panel.contains(e.getPoint())) {
                        flight_panel.setBackground(Color.LIGHT_GRAY);
                    } else {
                        flight_panel.setBackground(Color.DARK_GRAY);
                    }
                }
            });
            flight_panel.setLayout(new GridLayout(1,1));

            JLabel descr_label = new JLabel();
            descr_label.setForeground(Color.DARK_GRAY);
            descr_label.setFont(new Font("Serif", Font.BOLD, 50));
            descr_label.setHorizontalAlignment(SwingConstants.CENTER);
            descr_label.setForeground(Color.WHITE);
            descr_label.setBorder(border);
            String descr_str = "<html><font color='blue'>" + takeAbbreviation((flights.get(i)[2])) + "</font>: " + (flights.get(i)[5])
                    + " ========> <font color='blue'>" + takeAbbreviation(flights.get(i)[3]) + "</font>: " + (flights.get(i)[6])
                    + " DATE:" + (flights.get(i)[4]) + "</html>";
            descr_label.setText(descr_str);

            flight_panel.add(descr_label);
            FLIGHTS_PANELS_LIST.add(flight_panel);

            flights_panel.add(flight_panel);
        }
        System.out.println("CLIENT STATUS: FLIGHT LABELS GENERATED ...");
    }
    ////
    /* In this func we add the clientPanel in the frame and remove this JPanel */
    private void setClientInfoPanel(JPanel panel) {
        frameBelongs.remove(this);
        frameBelongs.add(panel);

        frameBelongs.revalidate();
        frameBelongs.repaint();
    }
    private Registration_GUI getThis() {
        return this;
    }
    ////

    private String takeAbbreviation(String str) {
        return switch (str) {
            case "Samos Aristarchos Samios Airport" -> "SMI";
            case "Athens Airport Eleftherios Venizelos" -> "ATH";
            case "Mykonos International Airport" -> "JMK";
            case "Milos Airport" -> "MLO";
            default -> "SKG";
        };
    }

    private void setJLabels(JLabel jLabel) {
        jLabel.setFont(new Font("Serif", Font.BOLD, 25));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setForeground(Color.WHITE);
        jLabel.setBorder(border);
    }
    private void setJTextFields(JTextField jTextField) {
        jTextField.setFont(new Font("Serif", Font.BOLD, 20));
        jTextField.setHorizontalAlignment(SwingConstants.CENTER);
        jTextField.setBorder(border);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit_butt && from_comboBox.getSelectedItem() != null && to_comboBox.getSelectedItem() != null && !Objects.equals(passNum_JText.getText(), "") && selectedDateDeparture != null && selectedDateReturn != null)
        {
            System.out.println("\nCLIENT STATUS: SUBMIT BUTTON PRESSED");
            if(temp == 1) {
                for(JPanel panel: FLIGHTS_PANELS_LIST)
                    flights_panel.remove(panel);
                scrollPane.revalidate();
                scrollPane.repaint();
                temp = 0;
                FLIGHTS_PANELS_LIST.removeAll(FLIGHTS_PANELS_LIST); // this is necessary, so when the user presses a JPanel, we can know which is it ...
            }
            String correctDepartureDate = convertDateFormat(selectedDateDeparture);
            String correctReturnDate = convertDateFormat(selectedDateReturn);
            generateFlights((String) from_comboBox.getSelectedItem(), (String) to_comboBox.getSelectedItem(), Integer.parseInt(passNum_JText.getText()), correctDepartureDate, correctReturnDate);
            temp = 1;
        } else {
            JOptionPane.showMessageDialog(null, "Fill all the fields", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /* Simple function to convert the format we get from the class DateLabelFormatter
    *  etc. Wed May 10 20:08:17 EEST 2023 to yyyy-MM-dd, so we can search it in a DATABASE */
    private String convertDateFormat(Date dateToConvert) {
        String dateString = String.valueOf(dateToConvert);

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = null;
        try {
            Date date = inputDateFormat.parse(dateString);
            formattedDate = outputDateFormat.format(date);
            System.out.println("CLIENT STATUS: DATE GENERATED -- "  + formattedDate + " --"); // Output: 2023-05-10
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    // For the DateFormater ...
    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }
        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                Date date = cal.getTime();
                return dateFormatter.format(date);
            }
            return "";
        }

    }
}
