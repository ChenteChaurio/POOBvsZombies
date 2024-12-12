package presentation;

import domain.PoobVsZombies;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PeaA {
    private JPanel parentPanel;
    private JLabel peaLabel;
    private Timer movementTimer;
    private PoobVsZombies game;
    private int startX, startY, currentX, currentY;
    private int cellWidth;
    private int panelWidth;

    private static final int TIME_TO_CROSS_CELL = 400; // 400ms por celda
    private static final int FRAME_DELAY = 50;

    public PeaA(int x, int y, JPanel parentPanel, PoobVsZombies game) {
        this.startX = x;
        this.startY = y;
        this.currentX = y;
        this.parentPanel = parentPanel;
        this.game = game;

        initializePea();
    }

    private void initializePea() {
        peaLabel = new JLabel();

        JButton[][] buttons = ((TableroGUI)parentPanel).getButtons();
        cellWidth = buttons[0][0].getWidth();

        panelWidth = ((TableroGUI)parentPanel).getButtonsPanel().getWidth();

        int buttonWidth = buttons[0][0].getWidth();
        int buttonHeight = buttons[0][0].getHeight();

        int posX = buttons[startX][currentX].getX() +
                ((TableroGUI)parentPanel).getButtonsPanel().getX();
        int posY = buttons[startX][currentX].getY() +
                ((TableroGUI)parentPanel).getButtonsPanel().getY();

        peaLabel.setBounds(posX, posY, buttonWidth, buttonHeight);
        parentPanel.add(peaLabel);
        try {
            Image peaImage = ImageIO.read(new File(
                    "POOBvsZombies/resources/Plantas/Peashooter/Proyectil/1.png"
            ));
            peaLabel.setIcon(new ImageIcon(peaImage));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        startMovement();
    }

    private void startMovement() {
        double distancePerFrame = (cellWidth / (TIME_TO_CROSS_CELL / FRAME_DELAY)) * 1.0;

        movementTimer = new Timer(FRAME_DELAY, new ActionListener() {
            private int totalMovement = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton[][] buttons = ((TableroGUI)parentPanel).getButtons();
                JPanel buttonsPanel = ((TableroGUI)parentPanel).getButtonsPanel();

                int movement = (int)distancePerFrame;
                totalMovement += movement;

                Point currentLocation = peaLabel.getLocation();
                peaLabel.setLocation(currentLocation.x + movement, currentLocation.y);

                if (currentLocation.x > buttonsPanel.getWidth() + buttonsPanel.getX()) {
                    removePea();
                }
            }
        });

        movementTimer.start();
    }

    //eliminar proyectil
    private void removePea() {
        if (movementTimer != null) {
            movementTimer.stop();
        }

        // Remover el label
        if (peaLabel != null && parentPanel != null) {
            parentPanel.remove(peaLabel);
            parentPanel.revalidate();
            parentPanel.repaint();
        }
    }

    // detener el movimiento si toca
    public void stopMovement() {
        removePea();
    }
}