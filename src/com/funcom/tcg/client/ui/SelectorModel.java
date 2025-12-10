package com.funcom.tcg.client.ui;

import java.util.NoSuchElementException;

public interface SelectorModel {
  void addChangeListener(ChangeListener paramChangeListener);
  
  void removeChangeListener(ChangeListener paramChangeListener);
  
  boolean hasPrevious();
  
  boolean hasNext();
  
  Object getCurrent();
  
  void next() throws NoSuchElementException;
  
  void previous() throws NoSuchElementException;
  
  void first();
  
  void last();
  
  int getSize();
  
  void setSelectionIndex(int paramInt);
  
  public static interface ChangeListener {
    void changeEvent(SelectorModel param1SelectorModel);
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\SelectorModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */