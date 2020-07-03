package GuiClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 *   @author Kumbi
 *   Listener class for NO button
 */

public class NoChosenListener implements ActionListener {

    private GuiView guiView;

    public NoChosenListener(GuiView guiView){
        this.guiView = guiView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = "n";
        guiView.sendNotification(message);


    }
}
