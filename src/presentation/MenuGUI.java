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
            fondo = ImageIO.read(new File("resources/MenuImaginePvZ.jpg"));
        }catch (IOException e){
            e.printStackTrace();
        }
        setLayout(null);
        prepareBotonsElement();
    }

    private void prepareBotonsElement() {

    }

    private void prepareActions() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
