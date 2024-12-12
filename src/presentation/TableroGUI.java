package presentation;

import domain.*;
import domain.PoobVsZombies;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TableroGUI extends JPanel {
    private PoobVsZombies game;
    private JButton[][] buttons;
    private Image fondo;
    private JPanel buttonsPanel;
    private JPanel extraButtonsPanel; // panel plantas
    private JButton[] extraButtons; // botones plantas
    private boolean isPeaShooterSelected = false;
    private boolean isSunflowerSelected = false;
    private boolean isPotatoMineSelected = false;
    private boolean isWallnutSelected = false;
    private boolean isECIPlantSelected = false;

    public TableroGUI(PoobVsZombies game) {
        this.game = game;
        prepareElements();
        prepareActions();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustButtonPanel();
            }
        });
    }

    private void prepareElements() {
        try {
            fondo = ImageIO.read(new File("POOBvsZombies/resources/tablero.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
    }

    private void initialize() {
        setLayout(null);
        buttons = new JButton[PoobVsZombies.height][PoobVsZombies.width];

        buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setOpaque(false);
        prepareBotonsTablero(buttonsPanel);

        //panel plantas
        extraButtonsPanel = new JPanel(new GridBagLayout());
        extraButtonsPanel.setOpaque(false);
        prepareExtraButtons(extraButtonsPanel);

        //buttonsPanel.setBounds(200, 150, 1200, 800);
        //extraButtonsPanel.setBounds(50, 150, 100, 800);
        add(buttonsPanel);
        add(extraButtonsPanel);
    }

    private void prepareBotonsTablero(JPanel buttonsPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);

        for (int i = 0; i < PoobVsZombies.height; i++) {
            for (int j = 0; j < PoobVsZombies.width; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setOpaque(false);
                buttons[i][j].setContentAreaFilled(false);
                //button.setBorderPainted(false);
                buttons[i][j].setVisible(true);
                gbc.gridx = j;
                gbc.gridy = i;
                buttons[i][j].setActionCommand(i + "," + j);
                buttons[i][j].addActionListener(new ButtonClickListener());
                buttonsPanel.add(buttons[i][j], gbc);
            }
        }
    }

    private void prepareExtraButtons(JPanel extraButtonsPanel) {
        extraButtons = new JButton[6];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);

        for (int i = 0; i < 6; i++) {
            extraButtons[i] = new JButton();
            extraButtons[i].setOpaque(false);
            extraButtons[i].setContentAreaFilled(false);
            //button.setBorderPainted(false);
            extraButtons[i].setVisible(true);
            gbc.gridx = 0;
            gbc.gridy = i;
            extraButtons[i].addActionListener(new ExtraButtonClickListener(i));//indice del boton
            extraButtonsPanel.add(extraButtons[i], gbc);
        }
    }

    private void prepareActions() {

    }

    private void adjustButtonPanel() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        //ajsute tablero
        buttonsPanel.setBounds((int)(panelWidth*.9)/8, (int)(panelHeight*.95)/7, (int)(panelWidth*1.2)/2, (int)(panelHeight*1.64)/2);
        //ajuste plantas
        extraButtonsPanel.setBounds((int)(panelWidth*.2)/8, (int)(panelHeight*1.1)/7, (int)(panelWidth*.3)/4, (int)(panelHeight*1.5)/2);

        buttonsPanel.revalidate();
        buttonsPanel.repaint();
        extraButtonsPanel.revalidate();
        extraButtonsPanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String[] coordinates = command.split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            System.out.println("Botón clickeado en: " + x + ", " + y);
            if (isPeaShooterSelected) {
                try {
                    PeaShooter peaShooter = new PeaShooter(x, y, game);
                    game.setThing(x, y, peaShooter);
                    animatePeashooter(x, y);
                    buttons[x][y].setText("P");
                    isPeaShooterSelected = false;
                } catch (PoobVsZombiesException ex) {
                    ex.printStackTrace();
                }
            } else if (isSunflowerSelected) {
                try {
                    Sunflower sunflower = new Sunflower(x, y, game);
                    game.setThing(x, y, sunflower);
                    animateSunFlower(x, y);
                    buttons[x][y].setText("S");
                    isSunflowerSelected = false;
                } catch (PoobVsZombiesException ex) {
                    ex.printStackTrace();
                }
            } else if (isPotatoMineSelected) {
                try {
                    PotatoMine potatoMine = new PotatoMine(x, y, game);
                    game.setThing(x, y, potatoMine);
                    animatePotatomine(x, y);
                    buttons[x][y].setText("B");
                    isPotatoMineSelected = false;

                } catch (PoobVsZombiesException ex) {
                    ex.printStackTrace();
                }
            }else if (isWallnutSelected){
                try{
                    Wallnut wallnut = new Wallnut(x, y, game);
                    game.setThing(x, y, wallnut);
                    animateWallnut(x, y);
                    buttons[x][y].setText("W");
                    isWallnutSelected = false;
                }catch (PoobVsZombiesException ex){
                    ex.printStackTrace();
                }
            } else   {
                updateButton(x, y);
            }
        }
    }

    private class ExtraButtonClickListener implements ActionListener {
        private int index;

        public ExtraButtonClickListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (index == 0) { // Si es el primer botón
                System.out.println("Sunflower seleccionado.");
                isSunflowerSelected = true; // Marcar que se ha seleccionado un Sunflower
            } else if (index == 1) { // Si es el segundo botón
                System.out.println("Peashooter seleccionado.");
                isPeaShooterSelected = true; // Marcar que se ha seleccionado un Peashooter
            } else if (index == 2) {
                System.out.println("PotatoMine seleccionado.");
                isPotatoMineSelected = true;
            } else if (index == 3) {
                System.out.println("Wallnut seleccioando.");
                isWallnutSelected = true;
            }else{
                System.out.println("Botón extra clickeado: " + e.getActionCommand());
            }
        }
    }


    private void animatePeashooter(int x, int y) {
        PeaShooterA peaShooterAnimation = new PeaShooterA(x, y, this, game);
        PeaA peaProjectile = new PeaA(x, y, this, game);
    }

    private void animateSunFlower(int x, int y) {
        SunFlowerA sunFlowerAnimation = new SunFlowerA(x, y, this, game);
    }

    private void animateWallnut(int x, int y) {
        WallnutA animateWallNutAnimation = new WallnutA(x, y, this, game);
    }

    private void animatePotatomine(int x, int y) {
        PotatoMineA aniamtePotatoMineAnimation = new PotatoMineA(x, y, this, game);
    }



    public JButton[][] getButtons() {
        return buttons;
    }

    public JPanel getButtonsPanel() {
        return buttonsPanel;
    }

    private void updateButton(int x, int y) {
        buttons[x][y].setText("P");
    }
}

