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
    private JButton optionsButton; // noton panel de opciones
    private JDialog optionsDialog; // opciones
    private boolean isPeaShooterSelected = false;
    private boolean isSunflowerSelected = false;
    private boolean isPotatoMineSelected = false;
    private boolean isWallnutSelected = false;
    private boolean isECIPlantSelected = false;
    private boolean isRepeaterSelected = false;
    private Image[] plantImages;

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

            plantImages = new Image[5]; // Cambia el tamaño según la cantidad de plantas
            plantImages[0] = ImageIO.read(new File("POOBvsZombies/resources/plantas/Sunflower/icono.png"));
            plantImages[1] = ImageIO.read(new File("POOBvsZombies/resources/plantas/Peashooter/icono.png"));
            plantImages[2] = ImageIO.read(new File("POOBvsZombies/resources/plantas/Potatobomb/icono.png"));
            plantImages[3] = ImageIO.read(new File("POOBvsZombies/resources/plantas/Wallnut/icono.png"));
            plantImages[4] = ImageIO.read(new File("POOBvsZombies/resources/plantas/ECIplant/icono.png"));
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

        //opciones
        optionsButton = new JButton("Opciones");

        add(optionsButton);
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
                buttons[i][j].setBorderPainted(false);
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

            if (i < plantImages.length) {
                extraButtons[i].setIcon(new ImageIcon(plantImages[i]));
            }

            gbc.gridx = 0;
            gbc.gridy = i;
            extraButtons[i].addActionListener(new ExtraButtonClickListener(i));//indice del boton
            extraButtonsPanel.add(extraButtons[i], gbc);
        }
    }

    private void prepareActions() {
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOptionsPanel();
            }
        });

    }

    private void openOptionsPanel() {
        optionsDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Opciones", true);
        optionsDialog.setLayout(new BorderLayout());
        optionsDialog.setSize(400, 200);
        optionsDialog.setLocationRelativeTo(this);
        optionsDialog.setUndecorated(true);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);

        JButton saveButton = new JButton("Guardar");
        JButton backButton = new JButton("Volver");
        JButton exitButton = new JButton("Salir");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        buttonPanel.add(saveButton, gbc);
        gbc.gridy = 1;
        buttonPanel.add(backButton, gbc);
        gbc.gridy = 2;
        buttonPanel.add(exitButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(Box.createVerticalGlue(), gbc);
        gbc.gridy = 1;
        backgroundPanel.add(buttonPanel, gbc);
        gbc.gridy = 2;
        backgroundPanel.add(Box.createVerticalGlue(), gbc); //espaciador

        optionsDialog.add(backgroundPanel, BorderLayout.CENTER);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Juego guardado (sin funcionalidad por ahora).");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsDialog.dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((PoobVsZombiesGUI) SwingUtilities.getWindowAncestor(TableroGUI.this)).showCard("Menu");
                optionsDialog.dispose();
            }
        });

        optionsDialog.setVisible(true);
    }

    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = ImageIO.read(new File("POOBvsZombies/resources/Pause.PNG")); // Cambia la ruta a tu imagen
            } catch (IOException e) {
                e.printStackTrace();
            }
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private void adjustButtonPanel() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        //ajsute tablero
        buttonsPanel.setBounds((int)(panelWidth*.9)/8, (int)(panelHeight*.95)/7, (int)(panelWidth*1.2)/2, (int)(panelHeight*1.64)/2);
        //ajuste plantas
        extraButtonsPanel.setBounds((int)(panelWidth*.01)/8, (int)(panelHeight*1.1)/7, (int)(panelWidth*.4)/4, (int)(panelHeight*1.1)/2);

        optionsButton.setBounds((int)(panelWidth*7)/8, (int)(panelHeight*.01)/7, (int)(panelWidth*.4)/4, (int)(panelHeight*.8)/17);

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
            }else if (isECIPlantSelected){
                try{
                    EciPlant eciPlant = new EciPlant(x, y, game);
                    game.setThing(x, y, eciPlant);
                    animateECIplant(x, y);
                    buttons[x][y].setText("E");
                    isECIPlantSelected = false;
                }catch (PoobVsZombiesException ex){
                    ex.printStackTrace();
                }
//            }else if (isRepeaterSelected) {
//                try {
//                    Repeater repeater = new Repeater(x, y, game);
//                    RepeaterA repeaterAnimation = new RepeaterA(x, y, TableroGUI.this, game);
//                    repeater.setRepeaterAnimation(repeaterAnimation); // Asignar la animación al Repeater
//                    game.setThing(x, y, repeater);
//                    buttons[x][y].setText("R");
//                    isRepeaterSelected = false;
//                } catch (PoobVsZombiesException ex) {
//                    ex.printStackTrace();
//                }
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
            if (index == 0) {
                System.out.println("Sunflower seleccionado.");
                isSunflowerSelected = true;
            } else if (index == 1) {
                System.out.println("Peashooter seleccionado.");
                isPeaShooterSelected = true;
            } else if (index == 2) {
                System.out.println("PotatoMine seleccionado.");
                isPotatoMineSelected = true;
            } else if (index == 3) {
                System.out.println("Wallnut seleccioando.");
                isWallnutSelected = true;
            }else if (index == 4){
                System.out.println("ECIplant seleccioando.");
                isECIPlantSelected = true;
            }else {
                System.out.println("Botón extra clickeado: " + e.getActionCommand());
            }
        }
    }


    private void animatePeashooter(int x, int y) {
        PeaShooterA peaShooterAnimation = new PeaShooterA(x, y, this, game);

        for (Thing thing : game.getBoard()[x][y]) {
            if (thing instanceof PeaShooter) {
                ((PeaShooter) thing).setPeaShooterAnimation(peaShooterAnimation);
                break;
            }
        }
//        public void animatePeaProjectile() {
//            PeaA peaProjectile = new PeaA(x, y, this, game);
//            Pea pea = new Pea(x, y + 1);
//            pea.setPeaAnimation(peaProjectile);
//            game.addPea(pea);
//        }
    }

    private void animateSunFlower(int x, int y) {
        SunFlowerA sunFlowerAnimation = new SunFlowerA(x, y, this, game);

        for (Thing thing : game.getBoard()[x][y]) {
            if (thing instanceof Sunflower) {
                ((Sunflower) thing).setSunFlowerAnimation(sunFlowerAnimation);
                break;
            }
        }
    }

    private void animateWallnut(int x, int y) {
        WallnutA wallnutAnimation = new WallnutA(x, y, this, game);

        for (Thing thing : game.getBoard()[x][y]) {
            if (thing instanceof Wallnut) {
                ((Wallnut) thing).setWallnutAnimation(wallnutAnimation);
                break;
            }
        }
    }

    private void animatePotatomine(int x, int y) {
        PotatoMineA potatoMineAnimation = new PotatoMineA(x, y, this, game);

        for (Thing thing : game.getBoard()[x][y]) {
            if (thing instanceof PotatoMine) {
                ((PotatoMine) thing).setPotatoMineAnimation(potatoMineAnimation);
                break;
            }
        }
    }

    private void animateECIplant(int x, int y) {
        ECIplantA eciPlantAnimation = new ECIplantA(x, y, this, game);

        for (Thing thing : game.getBoard()[x][y]) {
            if (thing instanceof EciPlant) {
                ((EciPlant) thing).setEciPlantAnimation(eciPlantAnimation);
                break;
            }
        }
    }

//    private void animateRepeater(int x, int y) {
//        RepeaterA animateRepeaterAnimation = new RepeaterA(x, y, this, game);
//
//    }



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

