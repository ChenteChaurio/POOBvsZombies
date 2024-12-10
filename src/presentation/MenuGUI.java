package presentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuGUI extends JPanel {
    private Image fondo;
    private final PoobVsZombiesGUI poobVsZombiesGUI;

    public MenuGUI(PoobVsZombiesGUI poobVsZombiesGUI) {
        this.poobVsZombiesGUI = poobVsZombiesGUI;
        prepareElements();
        prepareActions();
    }
    private void prepareElements() {
        try {
            fondo = ImageIO.read(new File("POOBvsZombies/resources/fondo.jpg"));
        }catch (IOException e){
            e.printStackTrace();
        }
        setLayout(null);
        prepareBotonsElement();
    }

    private void prepareBotonsElement() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        JButton jugarButton = new JButton("Jugar");
        JButton cargarButton = new JButton("Cargar partida");
        JButton salirButton = new JButton("Salir");

        int buttonWidth = width / 4;
        int buttonHeight = height / 10;

        jugarButton.setBounds((width - buttonWidth) / 2, height / 3, buttonWidth, buttonHeight);
        cargarButton.setBounds((width - buttonWidth) / 2, height / 2, buttonWidth, buttonHeight);
        salirButton.setBounds((width - buttonWidth) / 2, (height * 2) / 3, buttonWidth, buttonHeight);

        add(jugarButton);
        add(cargarButton);
        add(salirButton);
    }

    private void prepareActions() {
        JButton jugarButton = (JButton) getComponent(0);
        jugarButton.addActionListener(e -> {
            poobVsZombiesGUI.showCard("Modos");
        });

        JButton cargarButton = (JButton) getComponent(1);
        cargarButton.addActionListener(e -> {
            System.out.println("Cargando partida");
        });

        JButton salirButton = (JButton) getComponent(2);
        salirButton.addActionListener(e -> {
            System.exit(0);
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
