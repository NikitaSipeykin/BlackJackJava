package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class BlackJack {
  ArrayList<Card> deck;
  Random random = new Random();

  //dealer
  Card hiddenCard;
  ArrayList<Card> dealerHand;
  int dealerSum;
  int dealerAceCount;

  //player
  ArrayList<Card> playerHand;
  int playerSum;
  int playerAceCount;

  //window //Todo: can be separate class
  int boardWith = 600;
  int boardHeight = boardWith;

  int cardWith = 110;
  int cardHeight = 154;

  JFrame frame = new JFrame("Black Jack");

  //Todo: WTF?!
  JPanel gp = new JPanel(){
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      try{
        //draw hiddenCard
        Image hiddenCardImage = new ImageIcon(getClass().getResource("/cards/BACK.png")).getImage();
        if (!stayButton.isEnabled()){
          hiddenCardImage = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
        }
        g.drawImage(hiddenCardImage, 20, 20, cardWith, cardHeight, null);

        //draw dealer's hand
        for (int i = 0; i < dealerHand.size(); i++) {
          Card card = dealerHand.get(i);
          Image cardImage = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
          g.drawImage(cardImage, cardWith + 25 + (cardWith + 5) * i, 20, cardWith, cardHeight, null);
        }

        //draw player's hand
        for (int i = 0; i < playerHand.size(); i++) {
          Card card = playerHand.get(i);
          Image cardImage = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
          g.drawImage(cardImage, 20 + (cardWith + 5) * i, 320, cardWith, cardHeight, null);
        }

        if (!stayButton.isEnabled()){
          dealerSum = reduceDealerAce();
          playerSum = reducePlayerAce();

          String message = "";
          if (playerSum > 21 || playerSum < dealerSum){
            message = "You Lose!";
          } else if (dealerSum > 21 || playerSum > dealerSum) {
            message = "You Win!";
          } else if (playerSum == dealerSum) {
            message = "Tie!";
          }

          g.setFont(new Font("Arial", Font.PLAIN, 30));
          g.setColor(Color.white);
          g.drawString(message, 220, 250);
        }
      }catch (Exception e){
        e.printStackTrace();
      }

    }
  };
  JPanel buttonPanel = new JPanel();
  JButton hitButton = new JButton("Hit");
  JButton stayButton = new JButton("Stay");

  public BlackJack() {
    startGame();

    frame.setVisible(true);
    frame.setSize(boardWith, boardHeight);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    gp.setLayout(new BorderLayout());
    gp.setBackground(new Color(177, 162, 141));
    frame.add(gp);

    hitButton.setFocusable(false);
    buttonPanel.add(hitButton);
    stayButton.setFocusable(false);
    buttonPanel.add(stayButton);
    frame.add(buttonPanel, BorderLayout.SOUTH);

    //todo: make separate method
    hitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Card card = deck.remove(deck.size() - 1);
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);
        if (reducePlayerAce() > 21){
          hitButton.setEnabled(false);
        }
        gp.repaint();
      }
    });

    stayButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);

        while (dealerSum < 17){
          Card card = deck.remove(deck.size() - 1);
          dealerSum += card.getValue();
          dealerAceCount += card.isAce() ? 1 : 0;
          dealerHand.add(card);
        }
        gp.repaint();
      }
    });

    gp.repaint();
  }

  public void startGame(){
    buildDeck();
    shuffleDeck();

    //DEALER //Todo: can be separate class
    dealerHand = new ArrayList<Card>();
    dealerSum = 0;
    dealerAceCount = 0;

    hiddenCard = deck.remove(deck.size() - 1); //remove card from last index
    dealerSum += hiddenCard.getValue();
    dealerAceCount += hiddenCard.isAce() ? 1 : 0;

    Card card = deck.remove(deck.size() - 1);
    dealerSum += card.getValue();
    dealerAceCount += card.isAce() ? 1 : 0;
    dealerHand.add(card);

    //PLAYER //Todo: can be separate class
    playerHand = new ArrayList<Card>();
    playerSum = 0;
    playerAceCount = 0;

    for (int i = 0; i < 2; i++) {
      card = deck.remove(deck.size() - 1);
      playerSum += card.getValue();
      playerAceCount += card.isAce() ? 1 : 0;
      playerHand.add(card);
    }
  }

  public void buildDeck(){
    deck = new ArrayList<Card>();
    //todo: change to ENUM
    String[] values = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    //todo: change to ENUM
    String[] types = {"C","D","H","S"};

    for (int i = 0; i < types.length; i++) {
      for (int j = 0; j < values.length; j++) {
        Card card = new Card(values[j], types[i]);
        deck.add(card);
      }
    }
  }

  public void shuffleDeck(){
    for (int i = 0; i < deck.size(); i++) {
      int j = random.nextInt(deck.size());
      Card currCard = deck.get(i);
      Card randomCard = deck.get(j);
      deck.set(i, randomCard);
      deck.set(j, currCard);
    }
  }

  public int reducePlayerAce(){
    while (playerSum > 21 && playerAceCount > 0){
      playerSum -= 10;
      playerAceCount -= 1;
    }
    return playerSum;
  }

  public int reduceDealerAce(){
    while (dealerSum > 21 && dealerAceCount > 0){
      dealerSum -= 10;
      dealerAceCount -= 1;
    }
    return dealerSum;
  }

}
