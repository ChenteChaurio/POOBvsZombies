package presentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ModosGUI extends JPanel {
    private Image fondo;
    private final PoobVsZombiesGUI poobVsZombiesGUI;

    public ModosGUI(PoobVsZombiesGUI poobVsZombiesGUI) {
        this.poobVsZombiesGUI = poobVsZombiesGUI;
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        try {
            fondo = ImageIO.read(new File("POOBvsZombies/resources/fondo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(null);
        prepareBotonsElement();
    }

    private void prepareBotonsElement() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        JButton PvsMButton = new JButton("PvsM");
        JButton MvsMButton = new JButton("MvsM");
        JButton PvsPButton = new JButton("PvsP");

        int buttonWidth = width / 4;
        int buttonHeight = height / 10;

        PvsMButton.setBounds((width - buttonWidth) / 2, height / 3, buttonWidth, buttonHeight);
        MvsMButton.setBounds((width - buttonWidth) / 2, height / 2, buttonWidth, buttonHeight);
        PvsPButton.setBounds((width - buttonWidth) / 2, (height * 2) / 3, buttonWidth, buttonHeight);

        add(PvsMButton);
        add(MvsMButton);
        add(PvsPButton);
    }

    private void prepareActions() {
        JButton PvsMButton = (JButton) getComponent(0);
        PvsMButton.addActionListener(e -> {
            poobVsZombiesGUI.showCard("SeleccionPlantas");
        });

        JButton MvsMButton = (JButton) getComponent(1);
        MvsMButton.addActionListener(e -> {
            System.out.println("Modo MvsM seleccionado.");
        });

        JButton PvsPButton = (JButton) getComponent(2);
        PvsPButton.addActionListener(e -> {
            System.out.println("Modo PvsP seleccionado.");
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
