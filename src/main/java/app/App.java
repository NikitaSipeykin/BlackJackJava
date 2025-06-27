package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    gamePanel.hitButton.setFocusable(false);
    gamePanel.buttonPanel.add(gamePanel.hitButton);
    gamePanel.stayButton.setFocusable(false);
    gamePanel.buttonPanel.add(gamePanel.stayButton);
    gamePanel.restartButton.setFocusable(false);
    gamePanel.restartButton.setVisible(false);
    gamePanel.buttonPanel.add(gamePanel.restartButton);
    window.add(gamePanel.buttonPanel, BorderLayout.SOUTH);
  }
}
