package com.funcom.tcg.client.ui.chat;

import com.jmex.bui.BComponent;
import com.jmex.bui.BLabel;

public interface ChatWindowPane {
  void addChatComponent(BComponent paramBComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void moveChatBubble(BLabel paramBLabel, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void removeChatBubble(BComponent paramBComponent, boolean paramBoolean);
  
  int getWidth();
  
  int getHeight();
  
  String getStyleClassLabel();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\chat\ChatWindowPane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */