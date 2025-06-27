package app;

import java.util.ArrayList;

public class User {
  public ArrayList<Card> hand;
  public int sum;
  public int aceCount;

  public boolean isOutOfRange(){
    return sum > 21;
  }
}
