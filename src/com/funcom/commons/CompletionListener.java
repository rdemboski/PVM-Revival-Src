package com.funcom.commons;

public interface CompletionListener {
  void processCompleted();
  
  public static class Dummy implements CompletionListener {
    public void processCompleted() {}
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\CompletionListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */