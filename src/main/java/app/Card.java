package app;

import java.util.Objects;

public class Card {
  private String value;
  private String type;

  public Card(String value, String type) {
    this.value = value;
    this.type = type;
  }

  public int getValue() {
    int numericalValue;

    if ("AJKQ".contains(value)){
      if (value.equals("A")){
        numericalValue = 11;
      }else {
        numericalValue = 10;
      }
    }else {
      numericalValue = Integer.parseInt(value);
    }

    return numericalValue;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return  value + "-" + type;
  }

  public boolean isAce() {
    return Objects.equals(value, "A");
  }

  public String getImagePath(){
    return "/cards/"+ toString() + ".png";
  }
}
