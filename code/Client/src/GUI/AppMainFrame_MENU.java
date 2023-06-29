package GUI;
import javax.swing.*;
public class AppMainFrame_MENU extends JMenuBar {
    public JMenu menu; // Menu
    public JMenu uniqueID;
    public JMenuItem registrationFlight;
    public JMenuItem exit;
    public AppMainFrame_MENU(String id)
    {
        menu = new JMenu("Functions");
        registrationFlight = new JMenuItem("Registration flight");
        exit = new JMenuItem("Exit the app");

        if(id == null)
            id = "ID_ERROR";

        uniqueID = new JMenu("ID: " + id);
        uniqueID.setEnabled(false);

        menu.add(registrationFlight);
        menu.add(exit);

        this.add(uniqueID);
        this.add(menu);
    }
}
