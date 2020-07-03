package GuiClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 *   @author Kumbi
 *   Listener class for number 1 button
 */

public class OneChosenListener implements ActionListener {


    private GuiView guiView;

    public OneChosenListener(GuiView guiView){
        this.guiView = guiView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String message = "1";
        guiView.sendNotification(message);
        guiView.setMessage("1", true);


    }
}

