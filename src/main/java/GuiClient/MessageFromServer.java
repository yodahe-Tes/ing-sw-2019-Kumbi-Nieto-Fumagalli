package GuiClient;

/**
 *
 *   @author Kumbi
 *   Class for representation message from server
 */

public class MessageFromServer {

    private String value;
    private MessageFromServerType messageType;

    public MessageFromServer(MessageFromServerType messageType, String value){
        this.value = value;
        this.messageType = messageType;
    }


    public String getValue() {
        return value;
    }

    public MessageFromServerType getMessageType() {
        return messageType;
    }
}
