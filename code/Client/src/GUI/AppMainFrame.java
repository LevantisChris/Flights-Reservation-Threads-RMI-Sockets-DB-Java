package GUI;

import GUI.Functions.Registration_GUI;
import op.Operations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class AppMainFrame extends JFrame implements ActionListener {
    private Operations look_up; // the server that the client belongs ...
    private String UNIQUE_ID = null;
    private final int WIDTH = 1800;
    private final int HEIGHT = 950;
    private final Color BACKR_COLOR = Color.DARK_GRAY; // all the panels have the same background color

    // Menu
    private AppMainFrame_MENU menu;
    private MenuHandler handler;
    public AppMainFrame(String UNIQUE_ID) {
        this.UNIQUE_ID = UNIQUE_ID;

        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Flight registration app");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setUndecorated(true);

        /// Menu
        menu = new AppMainFrame_MENU(UNIQUE_ID);
        handler = new MenuHandler();
        addListenersInMenu(handler);
        this.setJMenuBar(menu);

        this.setVisible(true);
    }
    /*-----------------------------------IMPORTANT-CODE----------------------------------------------------------------------*/
    /* Set the server */
    public void setServer(Operations look_up) {
        this.look_up = look_up;
    }
    /*-----------------------------------IMPORTANT-CODE----------------------------------------------------------------------*/
    private void addListenersInMenu(MenuHandler handler) {
        menu.registrationFlight.addActionListener(handler);
        menu.exit.addActionListener(handler);
        menu.uniqueID.addActionListener(handler);
    }
    /* Add a panel to main frame */
    private void addAPanel(JPanel panel) {
        this.setLayout(new GridLayout(1,1)); // We add this because we want the panels in the frames to take all the space ...
        this.add(panel);
        this.pack();
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Listeners
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private AppMainFrame getAppMainFrame() {
        return this;
    }
    /// Menu Handler
    class MenuHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == menu.exit)
            {
                System.out.println("\n\nCLIENT STATUS: EXIT APP PRESSED");
                try {
                    look_up.sendMSG(UNIQUE_ID, "I am disconnecting");
                    System.out.println("\nSTATUS CLIENT: IN THE DISCONNECT METHOD --> " + look_up.disconnect(UNIQUE_ID) + "\n");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(99);
            }
            if(e.getSource() == menu.registrationFlight)
            {
                menu.registrationFlight.setEnabled(false);
                Registration_GUI registrationGui = new Registration_GUI(getAppMainFrame(), WIDTH, HEIGHT, BACKR_COLOR, look_up, UNIQUE_ID);
                addAPanel(registrationGui);
            }
        }
    }
}
