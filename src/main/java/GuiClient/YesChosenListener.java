package GuiClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 *   @author Kumbi
 *   Listener class for YES button
 */

public class YesChosenListener implements ActionListener {

    private GuiView guiView;

    public YesChosenListener(GuiView guiView){
        this.guiView = guiView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String message = "y";
        guiView.sendNotification(message);
        guiView.setMessage("y", true);

    }

}
