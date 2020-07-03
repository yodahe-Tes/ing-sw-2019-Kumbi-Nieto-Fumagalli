package GuiClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 *   @author Kumbi
 *   Listener class for chosen square button
 */

public class SquareChosenListener implements ActionListener {

    private GuiView guiView;
    public SquareChosenListener(GuiView guiView){
        this.guiView = guiView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton)e.getSource();

        String message = button.getText();

        guiView.setMessage(message, true);

        guiView.sendNotification(message);
    }
}
