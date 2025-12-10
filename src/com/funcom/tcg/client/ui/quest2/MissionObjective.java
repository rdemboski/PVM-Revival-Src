package com.funcom.tcg.client.ui.quest2;

import com.funcom.rpgengine2.quests.objectives.QuestObjective;
import com.funcom.tcg.client.ui.giftbox.HudInfoModel;

public interface MissionObjective extends HudInfoModel {
  String getObjectiveText();
  
  String getObjectiveIconPath();
  
  QuestObjective getObjective();
  
  boolean isCompleted();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\MissionObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */