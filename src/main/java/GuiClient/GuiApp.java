package GuiClient;

import Network.ClientSide;

import javax.swing.*;

/**
 *
 *   @author Kumbi
 *   main class for gui app
 */


public class GuiApp
{
    public static void main( String[] args ) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Welcome to Santorini!");
                //ClientSideGui client = new ClientSideGui();
                GuiView guiView = new GuiView();
                GuiImageModifier guiImageModifier = new GuiImageModifier();
                guiView.addObserver(guiImageModifier);
                guiImageModifier.addObserver(guiView);
                guiView.createAndStartGUI();
               // client.run();
                System.out.println("Now the game is closing.");
            }
        });
    }
}
