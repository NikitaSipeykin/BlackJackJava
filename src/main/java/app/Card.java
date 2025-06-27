package app;

import java.util.Objects;

public class Card {
  private final Values value;
  private final Type type;
  private String imageValue;
  private String imageType;

  public Card(Values value, Type type) {
    this.value = value;
    this.type = type;

    setValueImage();
    setTypeImage();
  }

  public int getValue() {
    int numericalValue;

    switch (value){
      case ACE -> numericalValue = 11;
      case TWO -> numericalValue = 2;
      case THREE -> numericalValue = 3;
      case FOUR -> numericalValue = 4;
      case FIVE -> numericalValue = 5;
      case SIX -> numericalValue = 6;
      case SEVEN -> numericalValue = 7;
      case EIGHT -> numericalValue = 8;
      case NINE -> numericalValue = 9;
      case TEN -> numericalValue = 10;
      case JACK -> numericalValue = 10;
      case QUEEN -> numericalValue = 10;
      case KING -> numericalValue = 10;
      default -> throw new IllegalStateException("Unexpected value: " + value);
    }
    return numericalValue;
  }

  private void setValueImage(){
    switch (value){
      case ACE -> imageValue = "A";
      case TWO -> imageValue = "2";
      case THREE -> imageValue = "3";
      case FOUR -> imageValue = "4";
      case FIVE -> imageValue = "5";
      case SIX -> imageValue = "6";
      case SEVEN -> imageValue = "7";
      case EIGHT -> imageValue = "8";
      case NINE -> imageValue = "9";
      case TEN -> imageValue = "10";
      case JACK -> imageValue = "J";
      case QUEEN -> imageValue = "Q";
      case KING -> imageValue = "K";
      default -> throw new IllegalStateException("Unexpected value: " + value);
    }
  }

  private void setTypeImage(){
    switch (type){
      case CLUBS -> imageType = "C";
      case HEARTS -> imageType = "H";
      case SPADES -> imageType = "S";
      case DIAMONDS -> imageType = "D";
    }
  }

  public Type getType() {
    return type;
  }

  @Override
  public String toString() {
    return imageValue + "-" + imageType;
  }

  public boolean isAce() {
    return Objects.equals(value, Values.ACE);
  }

  public String getImagePath(){
    return "/myCards/"+ toString() + ".png";
  }
}
