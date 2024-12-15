package presentation;

import domain.PoobVsZombies;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ECIplantA {
    private JPanel parentPanel;
    private JLabel ECIplantLabel;
    private Timer animationTimer;
    private int[] idleFrameNumbers;
    private PoobVsZombies game;
    private int x, y;

    public ECIplantA(int x, int y, JPanel parentPanel, PoobVsZombies game){
        this.x = x;
        this.y = y;
        this.parentPanel = parentPanel;
        this.game = game;

        idleFrameNumbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        initializeAnimation();
    }

    private void initializeAnimation(){
        ECIplantLabel = new JLabel();

        JButton[][] buttons = ((TableroGUI)parentPanel).getButtons();
        int buttonWidth = buttons[0][0].getWidth();
        int buttonHeight = buttons[0][0].getHeight();

        int posX = buttons[x][y].getX() + ((TableroGUI)parentPanel).getButtonsPanel().getX();
        int posY = buttons[x][y].getY() + ((TableroGUI)parentPanel).getButtonsPanel().getY();

        ECIplantLabel.setBounds(posX, posY, buttonWidth, buttonHeight);
        parentPanel.add(ECIplantLabel);

        animateIdle();
    }

    public void animateIdle() {
        stopAnimation();

        animationTimer = new Timer(200, null);
        final int[] currentFrame = {0};

        ActionListener frameAnimation = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Image frame = ImageIO.read(new File(
                            "POOBvsZombies/resources/Plantas/ECIplant/" +
                                    idleFrameNumbers[currentFrame[0]] + ".png"
                    ));

                    ECIplantLabel.setIcon(new ImageIcon(frame));

                    currentFrame[0] = (currentFrame[0] + 1) % idleFrameNumbers.length;

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        animationTimer.addActionListener(frameAnimation);
        animationTimer.start();

        parentPanel.revalidate();
        parentPanel.repaint();
    }

    public void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    public void removeLabel() {
        if (ECIplantLabel != null && parentPanel != null) {
            parentPanel.remove(ECIplantLabel);
            parentPanel.revalidate();
            parentPanel.repaint();
        }
    }

    public JLabel getECIplantLabel() {
        return ECIplantLabel;
    }
}
