package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel{
  JPanel buttonPanel = new JPanel();
  JButton hitButton = new JButton("Hit");
  JButton stayButton = new JButton("Stay");

  Random random = new Random();
  ArrayList<Card> deck;

  //game resolution
  int boardWith = 1200;
  int boardHeight = boardWith;
  int cardWith = 126;
  int cardHeight = 196;

  //dealer
  Card hiddenCard;
  ArrayList<Card> dealerHand;
  int dealerSum;
  int dealerAceCount;

  //player
  ArrayList<Card> playerHand;
  int playerSum;
  int playerAceCount;

  boolean isGameOver = false;

  public GamePanel() {
    startGame();

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
        if (isGameOver){
          startGame();
        }
        repaint();
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
        if (isGameOver){
          startGame();
        }
        repaint();
      }
    });

    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    try{
      //draw hiddenCard
      Image hiddenCardImage = new ImageIcon(getClass().getResource("/myCards/BACK.png")).getImage();
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
          isGameOver = true;
        } else if (dealerSum > 21 || playerSum > dealerSum) {
          message = "You Win!";
          isGameOver = true;
        } else if (playerSum == dealerSum) {
          message = "Tie!";
          isGameOver = true;
        }

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setColor(Color.white);
        g.drawString(message, 550, 300);
      }
    }catch (Exception e){
      e.printStackTrace();
    }
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

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 12; j++) {
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
