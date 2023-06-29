import GUI.AppMainFrame;
import op.Operations;

import javax.swing.*;
import java.rmi.Naming;

public class OperationClient {
    public OperationClient () {
        AppMainFrame appMainFrame;
        try {
            String name = "//127.0.0.1/OperationServer_one";
            Operations look_up = (Operations) Naming.lookup(name);
            String id = look_up.connect();
            System.out.println("CLIENT STATUS: UNIQUE ID FROM SERVER IS == " + id);
            appMainFrame = new AppMainFrame(id); // set up the unique id that generated from the server
            appMainFrame.setServer(look_up);
            System.out.println("CLIENT STATUS: CONNECTED TO SERVER");
        } catch (Exception e) {
            System.out.println("CLIENT STATUS: OPERATION CLIENT ERROR: " + e);
            JOptionPane.showMessageDialog(null, "Error occurred, check your internet connection", "Warning", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
            System.exit(201);
        }
    }
}

