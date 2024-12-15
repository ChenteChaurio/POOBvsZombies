package presentation;

import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PoobVsZombiesGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public PoobVsZombiesGUI(){
        prepareElements();
        prepareActions();
    }

    private void prepareElements(){
        setTitle("POOBvsZombies");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Insets insets = getToolkit().getScreenInsets(getGraphicsConfiguration());
        int width = screenSize.width - insets.left - insets.right;
        int height = screenSize.height - insets.top - insets.bottom;
        setSize(width, height);
        setLocation(0, 0);
        setUndecorated(true);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);
        prepareElementsStart();
    }

    private void prepareElementsStart(){
        StartGUI startGUI = new StartGUI(this);
        mainPanel.add(startGUI, "Start");

        MenuGUI menuGUI = new MenuGUI(this);
        mainPanel.add(menuGUI, "Menu");

        ModosGUI modosGUI = new ModosGUI(this);
        mainPanel.add(modosGUI, "Modos");

        SeleccionPlantasGUI seleccionPlantasGUI = new SeleccionPlantasGUI(this);
        mainPanel.add(seleccionPlantasGUI, "SeleccionPlantas");

        TableroGUI tableroGUI = null;
        try {
            tableroGUI = new TableroGUI(new PoobVsZombies(null));
        } catch (PoobVsZombiesException e) {
            throw new RuntimeException(e);
        }
        mainPanel.add(tableroGUI, "Tablero");

    }

    private void prepareActions(){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }

    public static void main(String[] args) {
        PoobVsZombiesGUI gui = new PoobVsZombiesGUI();
        gui.setVisible(true);
    }
}
