package GuiClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 *   @author Kumbi
 *   Listener class for number 2 button
 */

public class TwoChosenListener implements ActionListener {


    private GuiView guiView;

    public TwoChosenListener(GuiView guiView){
        this.guiView = guiView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String message = "2";
        guiView.sendNotification(message);
        guiView.setMessage("2", true);
    }
}

