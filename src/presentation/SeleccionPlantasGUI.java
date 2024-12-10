package presentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SeleccionPlantasGUI extends JPanel {
    private Image fondo;
    private final PoobVsZombiesGUI poobVsZombiesGUI;

    public SeleccionPlantasGUI(PoobVsZombiesGUI poobVsZombiesGUI) {
        this.poobVsZombiesGUI = poobVsZombiesGUI;
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        try {
            fondo = ImageIO.read(new File("POOBvsZombies/resources/plants-vs-zombies-elegir-plantas.jpg"));
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

        JButton planta1Button = new JButton("Planta 1");
        JButton planta2Button = new JButton("Planta 2");
        JButton planta3Button = new JButton("Planta 3");
        JButton iniciarButton = new JButton("Iniciar");

        int buttonWidth = width / 4;
        int buttonHeight = height / 10;

        int buttonWidthstart = width / 5;
        int buttonHeightstart = height / 12;

        planta1Button.setBounds((width - buttonWidth) / 2, height / 3, buttonWidth, buttonHeight);
        planta2Button.setBounds((width - buttonWidth) / 2, height / 2, buttonWidth, buttonHeight);
        planta3Button.setBounds((width - buttonWidth) / 2, (height * 2) / 3, buttonWidth, buttonHeight);
        iniciarButton.setBounds((width/2)-540, (height/2)+440, buttonWidthstart,buttonHeightstart);

        add(planta1Button);
        add(planta2Button);
        add(planta3Button);
        add(iniciarButton);
    }

    private void prepareActions() {
        JButton planta1Button = (JButton) getComponent(0);
        planta1Button.addActionListener(e -> {
            System.out.println("Planta 1 seleccionada.");
            // Aquí puedes agregar la lógica para seleccionar la planta
        });

        JButton planta2Button = (JButton) getComponent(1);
        planta2Button.addActionListener(e -> {
            System.out.println("Planta 2 seleccionada.");
            // Aquí puedes agregar la lógica para seleccionar la planta
        });

        JButton planta3Button = (JButton) getComponent(2);
        planta3Button.addActionListener(e -> {
            System.out.println("Planta 3 seleccionada.");
            // Aquí puedes agregar la lógica para seleccionar la planta
        });

        JButton iniciarButton = (JButton) getComponent(3);
        iniciarButton.addActionListener(e -> {
            poobVsZombiesGUI.showCard("Tablero");
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}