package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel{
  //GENERAL
  JPanel buttonPanel = new JPanel();
  JButton hitButton = new JButton("Hit");
  JButton stayButton = new JButton("Stay");
  JButton restartButton = new JButton("Restart");

  Random random = new Random();

  int actualCardWidth = 63;
  int actualCardHeight = 98;
  int defaultPlayerCardPosition = 160;
  int windowSize = 350;

  //GAME RESOLUTION
  //Todo: Add screen change functionality
  int cardSize = 3;
  int boardWith = windowSize * cardSize;
  int boardHeight = windowSize * cardSize;
  int cardWidth = actualCardWidth * cardSize;
  int cardHeight = actualCardHeight * cardSize;
  int playerCardPosition = defaultPlayerCardPosition * cardSize;
  int gap = boardWith / 52;

  //ENTITY
  User dealer = new User();
  User player = new User();
  ArrayList<Card> deck;
  Card hiddenCard;

  public GamePanel() {
    startGame();
    buttonPanelListener();
  }

  private void buttonPanelListener() {
    hitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addCardFromDeck(player);

        if (reduceUserAce(player) > 21){
          hitButton.setEnabled(false);
        }
        repaint();
      }
    });

    restartButton.addActionListener(e -> {
      startGame();
      repaint();
    });

    stayButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);

        while (dealer.sum < 17){
          addCardFromDeck(dealer);
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

      g.drawImage(hiddenCardImage, gap, 20, cardWidth, cardHeight, null);

      drawUserHand(dealer, g, gap + cardWidth + 5, 20);
      drawUserHand(player, g, gap, playerCardPosition);

      checkWhoWon(g);

    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public void startGame(){
    hitButton.setEnabled(true);
    stayButton.setEnabled(true);
    restartButton.setVisible(false);

    buildDeck();
    shuffleDeck();

    createUserStartHand(dealer, 1, true);
    createUserStartHand(player, 2, false);
  }

  public void createUserStartHand(User user, int cardCount, boolean isDealer){
    user.hand = new ArrayList<Card>();
    user.sum = 0;
    user.aceCount = 0;

    if (isDealer){
      hiddenCard = deck.remove(deck.size() - 1);
      user.sum += hiddenCard.getValue();
      user.aceCount += hiddenCard.isAce() ? 1 : 0;
    }

    for (int i = 0; i < cardCount; i++) {
      Card card = deck.remove(deck.size() - 1);
      user.sum += card.getValue();
      user.aceCount += card.isAce() ? 1 : 0;
      user.hand.add(card);
    }
  }

  private void checkWhoWon(Graphics g) {
    if (!stayButton.isEnabled()) {
      dealer.sum = reduceUserAce(dealer);
      player.sum = reduceUserAce(player);

      String message = "";
      if (player.isOutOfRange() || player.sum < dealer.sum) {
        message = "You Lose!";
      } else if (dealer.isOutOfRange() || player.sum > dealer.sum) {
        message = "You Win!";
      } else if (player.sum == dealer.sum) {
        message = "Tie!";
      }

      g.setFont(new Font("Arial", Font.PLAIN, 30));
      g.setColor(Color.white);
      g.drawString(message, (boardWith / 2) - 60, cardHeight + (cardHeight / 2));
      restartButton.setVisible(true);
    }
  }

  public void addCardFromDeck(User user){
    Card card = deck.remove(deck.size() - 1);
    user.sum += card.getValue();
    user.aceCount += card.isAce() ? 1 : 0;
    user.hand.add(card);
  }

  public void buildDeck(){
    deck = new ArrayList<Card>();

    for (Type type : Type.values()) {
      for (Values value : Values.values()) {
        deck.add(new Card(value, type));
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

  public void drawUserHand(User user, Graphics g, int x, int y){
  for (int i = 0; i < user.hand.size(); i++) {
    Card card = user.hand.get(i);
    Image cardImage = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
    g.drawImage(cardImage, x + (cardWidth + gap) * i , y, cardWidth, cardHeight, null);
  }
}

  public int reduceUserAce(User user){
    while (user.sum > 21 && user.aceCount > 0){
      user.sum -= 10;
      user.aceCount -= 1;
    }
    return user.sum;
  }
}
