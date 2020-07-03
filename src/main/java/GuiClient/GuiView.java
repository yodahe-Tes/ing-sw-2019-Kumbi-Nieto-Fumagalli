package GuiClient;


import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 *
 *   @author Kumbi
 *   Class for creating all the visual components of gui
 */

public class GuiView extends Observable implements Observer {

    private JFrame frame = new JFrame("Santorini");
    private JTextField textField = new JTextField();
    private JPanel panel = new JPanel();
    private String message = "wait";
    private ClientSideGui client;

    JButton createSquareButton(String SQUARE, ImageIcon icon){
        final JButton button = new JButton(SQUARE ,icon );

        button.addActionListener(new SquareChosenListener(this));
        return button;
    }



    JButton createOperationButton(String op){
        final JButton button = new JButton(op);
        switch(op){
            case "YES":
                button.addActionListener(new YesChosenListener(this));
                break;
            case "NO":
                button.addActionListener(new NoChosenListener(this));
                break;

            case "ONE":
                button.addActionListener(new OneChosenListener(this));
                break;
            case "TWO":
                button.addActionListener(new TwoChosenListener(this));
                break;

            default:
                button.addActionListener(new NoChosenListener(this));
                break;
        }
        return button;
    }



    void createBoard(JPanel p){

        p.add(createOperationButton("ONE"));
        p.add(createOperationButton("TWO"));
        p.add(createOperationButton("YES"));
        p.add(createOperationButton("NO"));
        p.add(createOperationButton(""));




        p.add(createSquareButton( "1,1" ,new ImageIcon("resources/row1-col-1.jpg")));
        p.add(createSquareButton("1,2", new ImageIcon("resources/row1-col-2.jpg")));
        p.add(createSquareButton("1,3", new ImageIcon("resources/row1-col-3.jpg")));
        p.add(createSquareButton("1,4", new ImageIcon("resources/row1-col-4.jpg")));
        p.add(createSquareButton("1,5", new ImageIcon("resources/row1-col-5.jpg")));

        p.add(createSquareButton("2,1", new ImageIcon("resources/row2-col-1.jpg")));
        p.add(createSquareButton("2,2", new ImageIcon("resources/row2-col-2.jpg")));
        p.add(createSquareButton("2,3", new ImageIcon("resources/row2-col-3.jpg")));
        p.add(createSquareButton("2,4", new ImageIcon("resources/row2-col-4.jpg")));
        p.add(createSquareButton("2,5", new ImageIcon("resources/row2-col-5.jpg")));

        p.add(createSquareButton("3,1", new ImageIcon("resources/row3-col-1.jpg")));
        p.add(createSquareButton("3,2", new ImageIcon("resources/row3-col-2.jpg")));
        p.add(createSquareButton("3,3", new ImageIcon("resources/row3-col-3.jpg")));
        p.add(createSquareButton("3,4", new ImageIcon("resources/row3-col-4.jpg")));
        p.add(createSquareButton("3,5", new ImageIcon("resources/row3-col-5.jpg")));

        p.add(createSquareButton("4,1", new ImageIcon("resources/row4-col-1.jpg")));
        p.add(createSquareButton("4,2", new ImageIcon("resources/row4-col-2.jpg")));
        p.add(createSquareButton("4,3", new ImageIcon("resources/row4-col-3.jpg")));
        p.add(createSquareButton("4,4", new ImageIcon("resources/row4-col-4.jpg")));
        p.add(createSquareButton("4,5", new ImageIcon("resources/row4-col-5.jpg")));

        p.add(createSquareButton("5,1", new ImageIcon("resources/row5-col-1.jpg")));
        p.add(createSquareButton("5,2", new ImageIcon("resources/row5-col-2.jpg")));
        p.add(createSquareButton("5,3", new ImageIcon("resources/row5-col-3.jpg")));
        p.add(createSquareButton("5,4", new ImageIcon("resources/row5-col-4.jpg")));
        p.add(createSquareButton("5,5", new ImageIcon("resources/row5-col-5.jpg")));


    }

    public void createAndStartGUI(){

        textField.setEditable(false);
        textField.setText(message);
        textField.setHorizontalAlignment(JTextField.CENTER);
        frame.setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(6, 5));
        createBoard(panel);
        frame.add(textField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message, boolean show) {
        this.message = message;
        if(show){
            textField.setText(this.message);
        }
    }

    public void sendNotification(String message) {
        setChanged();
        notifyObservers(message);
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof GuiImageModifier && arg instanceof MessageFromServer){
            MessageFromServer m = (MessageFromServer)arg;
            switch (m.getMessageType()){
                case actionToPerform:
                   //                                            TODO
                    break;
                case MessageToDisplay:
                    message = m.toString();
                    textField.setText(m.toString());
                    break;
                default:
                    break;
            }

        }
    }
}
