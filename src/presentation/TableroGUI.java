package presentation;

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

        buttonsPanel.setBounds(200, 150, 1200, 800);
        add(buttonsPanel);
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

    private void prepareActions() {

    }

    private void adjustButtonPanel() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        buttonsPanel.setBounds((int)(panelWidth*.9)/8, (int)(panelHeight*.95)/7, (int)(panelWidth*1.2)/2, (int)(panelHeight*1.64)/2);
        buttonsPanel.revalidate();
        buttonsPanel.repaint();
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
            updateButton(x, y);
        }
    }

    private void updateButton(int x, int y) {
        buttons[x][y].setText("P");
    }
}

