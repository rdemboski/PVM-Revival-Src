package com.funcom.tcg.client.ui;

import com.jmex.bui.util.Rectangle;

public interface SelectableButtonInter {
  void update(long paramLong);
  
  boolean isSelected();
  
  Rectangle getTargetBounds();
  
  void modelChanged();
  
  void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void setBoundsToTarget();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\SelectableButtonInter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */