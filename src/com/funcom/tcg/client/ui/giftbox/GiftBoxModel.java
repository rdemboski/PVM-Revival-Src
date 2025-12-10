package com.funcom.tcg.client.ui.giftbox;

import com.funcom.rpgengine2.quests.reward.QuestRewardData;
import java.util.List;

public interface GiftBoxModel {
  String getTitle();
  
  List<QuestRewardData> getRewardDataList();
  
  String getIcon();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\giftbox\GiftBoxModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */