package presentation;

import domain.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class RepeaterA {
    private JPanel parentPanel;
    private JLabel repeaterLabel;
    private Timer animationTimer;
    private int[] idleFrameNumbers;
    private int[] attackFrameNumbers;
    private PoobVsZombies game;
    private int x, y;

    // Variables para las animaciones de Threepeater y Gatling
    private int evolutionStage; // 0: Repeater, 1: Threepeater, 2: Gatling
    private int[] threepeaterIdleFrameNumbers;
    private int[] gatlingIdleFrameNumbers;
    private int[] threepeaterAttackFrameNumbers;
    private int[] gatlingAttackFrameNumbers;

    public RepeaterA(int x, int y, JPanel parentPanel, PoobVsZombies game) {
        this.x = x;
        this.y = y;
        this.parentPanel = parentPanel;
        this.game = game;

        idleFrameNumbers = new int[]{1, 2, 3, 4, 5};
        attackFrameNumbers = new int[]{1, 2};

        // Inicializar las animaciones de Threepeater y Gatling
        threepeaterIdleFrameNumbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        gatlingIdleFrameNumbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        threepeaterAttackFrameNumbers = new int[]{1, 2};
        gatlingAttackFrameNumbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8};

        initializeAnimation();
    }

    private void initializeAnimation() {
        repeaterLabel = new JLabel();
        // Calcular posición JLabel
        JButton[][] buttons = ((TableroGUI) parentPanel).getButtons();
        int buttonWidth = buttons[0][0].getWidth();
        int buttonHeight = buttons[0][0].getHeight();

        int posX = buttons[x][y].getX() + ((TableroGUI) parentPanel).getButtonsPanel().getX();
        int posY = buttons[x][y].getY() + ((TableroGUI) parentPanel).getButtonsPanel().getY();

        repeaterLabel.setBounds(posX, posY, buttonWidth, buttonHeight);
        parentPanel.add(repeaterLabel);

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
                    Image frame;
                    if (evolutionStage == 0) { // Repeater
                        frame = ImageIO.read(new File(
                                "POOBvsZombies/resources/Plantas/Repeater/" +
                                        idleFrameNumbers[currentFrame[0]] + ".png"
                        ));
                    } else if (evolutionStage == 1) { // Threepeater
                        frame = ImageIO.read(new File(
                                "POOBvsZombies/resources/Plantas/Threepeater/" +
                                        threepeaterIdleFrameNumbers[currentFrame[0]] + ".png"
                        ));
                    } else { // Gatling
                        frame = ImageIO.read(new File(
                                "POOBvsZombies/resources/Plantas/Gatling/" +
                                        gatlingIdleFrameNumbers[currentFrame[0]] + ".png"
                        ));
                    }
                    repeaterLabel.setIcon(new ImageIcon(frame));

                    currentFrame[0] = (currentFrame[0] + 1) % (evolutionStage == 0 ? idleFrameNumbers.length : (evolutionStage == 1 ? threepeaterIdleFrameNumbers.length : gatlingIdleFrameNumbers.length));

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        animationTimer.addActionListener(frameAnimation);
        animationTimer.start();

        parentPanel.revalidate();
        parentPanel .repaint();
    }

    public void animateAttack() {
        stopAnimation();

        Timer attackTimer = new Timer(500, null);
        final int[] currentAttackFrame = {0};

        ActionListener attackFrameAnimation = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Image frame;
                    if (evolutionStage == 0) { // Repeater
                        frame = ImageIO.read(new File(
                                "POOBvsZombies/resources/Plantas/Repeater/Ataque/" +
                                        attackFrameNumbers[currentAttackFrame[0]] + ".png"
                        ));
                    } else if (evolutionStage == 1) { // Threepeater
                        frame = ImageIO.read(new File(
                                "POOBvsZombies/resources/Plantas/Threepeater/Ataque/" +
                                        threepeaterAttackFrameNumbers[currentAttackFrame[0]] + ".png"
                        ));
                    } else { // Gatling
                        frame = ImageIO.read(new File(
                                "POOBvsZombies/resources/Plantas/Gatling/ataque/" +
                                        gatlingAttackFrameNumbers[currentAttackFrame[0]] + ".png"
                        ));
                    }
                    repeaterLabel.setIcon(new ImageIcon(frame));

                    currentAttackFrame[0] = (currentAttackFrame[0] + 1) % (evolutionStage == 0 ? attackFrameNumbers.length : (evolutionStage == 1 ? threepeaterAttackFrameNumbers.length : gatlingAttackFrameNumbers.length));

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

    public void evolveToThreepeater() {
        evolutionStage = 1; // Cambiar a Threepeater
        animateIdle(); // Iniciar animación idle de Threepeater
    }

    public void evolveToGatling() {
        evolutionStage = 2; // Cambiar a Gatling
        animateIdle(); // Iniciar animación idle de Gatling
    }

    public void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    public JLabel getRepeaterLabel() {
        return repeaterLabel;
    }
}