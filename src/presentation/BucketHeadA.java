package presentation;

import domain.PoobVsZombies;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class BucketHeadA {
    private JPanel parentPanel;
    private JLabel bucketHeadLabel;
    private Timer animationTimer;
    private int[] idleFrameNumbers;
    private int[] attackFrameNumbers;
    private PoobVsZombies game;
    private int x, y;

    public BucketHeadA(int x, int y, JPanel parentPanel, PoobVsZombies game) {
        this.x = x;
        this.y = y;
        this.parentPanel = parentPanel;
        this.game = game;

        idleFrameNumbers = new int[]{1, 2, 3, 4, 5, 6, 7};
        attackFrameNumbers = new int[]{1, 2, 3, 4, 5, 6, 7};

        initializeAnimation();
    }

    private void initializeAnimation() {
        bucketHeadLabel= new JLabel();
        // Calcular posición JLabel
        JButton[][] buttons = ((TableroGUI)parentPanel).getButtons();
        int buttonWidth = buttons[0][0].getWidth();
        int buttonHeight = buttons[0][0].getHeight();

        int posX = buttons[x][y].getX() + ((TableroGUI)parentPanel).getButtonsPanel().getX();
        int posY = buttons[x][y].getY() + ((TableroGUI)parentPanel).getButtonsPanel().getY();

        bucketHeadLabel.setBounds(posX, posY, buttonWidth, buttonHeight);
        parentPanel.add(bucketHeadLabel);

        animateIdle();
    }

    public void updatePosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;

        // Calcular nueva posición en el panel
        JButton[][] buttons = ((TableroGUI)parentPanel).getButtons();
        int posX = buttons[x][y].getX() + ((TableroGUI)parentPanel).getButtonsPanel().getX();
        int posY = buttons[x][y].getY() + ((TableroGUI)parentPanel).getButtonsPanel().getY();

        // Actualizar la posición del JLabel
        bucketHeadLabel.setBounds(posX, posY, bucketHeadLabel.getWidth(), bucketHeadLabel.getHeight());
        parentPanel.revalidate();
        parentPanel.repaint();
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
                            "POOBvsZombies/resources/Zombies/Buckethead/" +
                                    idleFrameNumbers[currentFrame[0]] + ".png"
                    ));
                    bucketHeadLabel.setIcon(new ImageIcon(frame));

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

    public void animateAttack() {
        stopAnimation();

        Timer attackTimer = new Timer(500, null);
        final int[] currentAttackFrame = {0};

        ActionListener attackFrameAnimation = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Image frame = ImageIO.read(new File(
                            "POOBvsZombies/resources/Zombies/Buckethead/Ataque/" +
                                    attackFrameNumbers[currentAttackFrame[0]] + ".png"
                    ));

                    bucketHeadLabel.setIcon(new ImageIcon(frame));

                    currentAttackFrame[0] = (currentAttackFrame[0] + 1) % attackFrameNumbers.length;

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        attackTimer.addActionListener(attackFrameAnimation);
        attackTimer.start();

        parentPanel.revalidate();
        parentPanel.repaint();
    }

    public void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    public JLabel getPeaShooterLabel() {
        return bucketHeadLabel;
    }
}
