package com.funcom.tcg.client.ui.hud;

import com.funcom.tcg.client.ui.quest2.QuestWindowModel;
import com.jmex.bui.BWindow;

public interface QuestPickupModel extends QuestWindowModel {
  boolean hasOtherQuest();
  
  void questRejected(BWindow paramBWindow);
  
  void questAccepted();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\QuestPickupModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */