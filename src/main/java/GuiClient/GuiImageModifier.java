package GuiClient;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 *
 *   @author Kumbi
 *   Class for Modifying the jbutton icons and for sending message to the jtext field
 */


public class GuiImageModifier extends Observable implements Observer {

    private String  message;

    public GuiImageModifier(){

    }

    private void setbuttonImage(JLabel jLabel){

    }
    private void setMessage(String message) {
            notifyObservers(new MessageFromServer(MessageFromServerType.MessageToDisplay, message));
    }
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof GUIMessage) {
            GUIMessage c = (GUIMessage) arg;

                    setChanged();
            }
        }
  //  }

}
