package app;

import javax.swing.*;
import java.awt.*;

public class App {
  public static JFrame window;

  public static void main(String[] args) {
    window = new JFrame("Black Jack");
    GamePanel gamePanel = new GamePanel();

    window.setVisible(true);
    window.setSize(gamePanel.boardWith, gamePanel.boardHeight);
    window.setLocationRelativeTo(null);
    window.setResizable(false);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    gamePanel.setLayout(new BorderLayout());
    gamePanel.setBackground(new Color(177, 162, 141));
    window.add(gamePanel);

    gamePanel.buttonPanel.setLayout(new BorderLayout());
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    gamePanel.settingsButton.setFocusable(false);
    leftPanel.add(gamePanel.settingsButton);

    gamePanel.hitButton.setFocusable(false);
    gamePanel.stayButton.setFocusable(false);
    gamePanel.restartButton.setFocusable(false);
    gamePanel.restartButton.setVisible(false);

    rightPanel.add(gamePanel.hitButton);
    rightPanel.add(gamePanel.stayButton);
    rightPanel.add(gamePanel.restartButton);

    gamePanel.buttonPanel.add(leftPanel, BorderLayout.WEST);
    gamePanel.buttonPanel.add(rightPanel, BorderLayout.EAST);

    window.add(gamePanel.buttonPanel, BorderLayout.SOUTH);
  }
}
