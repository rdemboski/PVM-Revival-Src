package com.funcom.tcg.client.ui.quest2;

import com.funcom.rpgengine2.combat.ElementManager;
import com.funcom.rpgengine2.pickupitems.PickUpManager;
import com.funcom.rpgengine2.quests.reward.QuestRewardData;
import com.funcom.tcg.client.model.rpg.ItemRegistry;
import com.funcom.tcg.client.model.rpg.PetRegistry;
import com.funcom.tcg.client.model.rpg.VisualRegistry;
import java.util.List;

public interface QuestWindowModel {
  List<MissionObjective> getCurrentMissionObjectives();
  
  int getCurrentMissionNum();
  
  int getMissionTotalNum();
  
  String getCurrentMissionName();
  
  String getCurrentMissionIcon();
  
  List<MissionReward> getCurrentMissionRewards();
  
  List<QuestRewardData> getQuestRewards();
  
  void refresh();
  
  String getQuestIcon();
  
  String getQuestName();
  
  String getQuestGiver();
  
  String getQuestLocation();
  
  String getQuestText();
  
  boolean hasQuest();
  
  PickUpManager getPickUpManager();
  
  VisualRegistry getVisualRegistry();
  
  PetRegistry getPetRegistry();
  
  ItemRegistry getItemRegistry();
  
  ElementManager getElementManager();
  
  void addQuestWindowModelListener(QuestWindowModelListener paramQuestWindowModelListener);
  
  boolean removeQuestWindowModelListener(QuestWindowModelListener paramQuestWindowModelListener);
  
  int getDailyQuestLimit();
  
  int getDailyQuestMax();
  
  public static interface QuestWindowModelListener {
    void questWindowModelChanged(QuestWindowModel param1QuestWindowModel);
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\QuestWindowModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */