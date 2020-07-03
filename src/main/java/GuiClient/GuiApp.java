package GuiClient;

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
                GuiView guiView = new GuiView();
                GuiImageModifier guiImageModifier = new GuiImageModifier();
                guiView.addObserver(guiImageModifier);
                guiImageModifier.addObserver(guiView);
                guiView.createAndStartGUI();
            }
        });
    }
}
