package presentation;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StartGUI extends JPanel {
    private Image fondo;
    private Image logoImage;
    private final PoobVsZombiesGUI poobVsZombiesGUI;
    private JButton botonStart;
    private Timer fadeTimer;
    private Clip clip;
    private float alpha = 0f;
    private int step = 0;
    private long lastUpdateTime = System.currentTimeMillis();

    public StartGUI(PoobVsZombiesGUI main) {
        poobVsZombiesGUI = main;
        prepareElements();
        prepareActions();
        playBackgroundMusic();
        startIntroSequence();
    }

    private void prepareElements() {
        try {
            fondo = ImageIO.read(new File("resources/ImagineMenu/StartImaginePvZ.jpg"));
            logoImage = ImageIO.read(new File("resources/ImagineMenu/Logo.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lo sentimos algo ha ocurrido");
        }
        setLayout(null);
        prepareBotonElement();
    }

    private void prepareBotonElement() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        botonStart = new JButton("JUGAR");
        int x = width - width / 8 - 10;
        int y = height - height / 10 - 10;
        System.out.println(width + " " + height);
        botonStart.setBounds(x, y, width/(192/17), height/(108/5));
        botonStart.setVisible(false);
        add(botonStart);
    }

    private void playBackgroundMusic() {
        try {
            File audioFile = new File("resources/MusicMenu/IntroTheme.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    private void startIntroSequence() {
        fadeTimer = new Timer(30, e -> {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastUpdateTime;
            if (elapsedTime >= 50) {
                lastUpdateTime = currentTime;
                if (alpha < 1f) {
                    alpha += 0.05f;
                    alpha = Math.min(1f, alpha);
                }
                repaint();
                if (alpha >= 1f && step == 0) {
                    step = 1;
                    alpha = 0f;
                } else if (alpha >= 1f && step == 1) {
                    step = 2;
                    alpha = 0f;
                } else if (alpha >= 1f && step == 2) {
                    step = 3;
                    fadeTimer.stop();
                    botonStart.setVisible(true);
                }
            }
        });
        fadeTimer.start();
    }

    private void prepareActions() {
        botonStart.addActionListener(e -> {
            poobVsZombiesGUI.showCard("Menu");
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        int logoX = getWidth() / 2 - logoImage.getWidth(this) / 2;
        int logoY = getHeight() / 2 - logoImage.getHeight(this) / 2;
        if (step == 1) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.drawImage(logoImage, logoX, logoY, this);
        } else if (step == 2) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - alpha));
            g.drawImage(logoImage, logoX, logoY, this);
        } else if (step == 3) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
