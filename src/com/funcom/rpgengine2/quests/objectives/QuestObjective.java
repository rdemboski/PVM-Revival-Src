package com.funcom.rpgengine2.quests.objectives;

import com.funcom.gameengine.PairedWCandBoolean;
import java.util.List;

public interface QuestObjective {
  String getObjectiveId();
  
  void addStartObjectiveText(String paramString);
  
  String getStartObjectiveText();
  
  void addUnCompletedObjectiveText(String paramString);
  
  String getUnCompletedObjectiveText();
  
  void addCompletedObjectiveText(String paramString);
  
  String getCompletedObjectiveText();
  
  ObjectiveType getObjectiveType();
  
  String getMapId();
  
  void setMapId(String paramString);
  
  int getAmount();
  
  void addIconPath(String paramString);
  
  String getIconPath();
  
  void setShortObjetiveText(String paramString);
  
  String getShortObjectiveText();
  
  void setObjectiveLocationDefinition(String paramString, int paramInt1, int paramInt2);
  
  String getGoToId();
  
  int getGotoZoneHeight();
  
  int getGotoZoneWidth();
  
  void setGotoPositions(List<PairedWCandBoolean> paramList);
  
  List<PairedWCandBoolean> getGotoPositions();
  
  void setRegionID(String paramString);
  
  String getRegionID();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\QuestObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */