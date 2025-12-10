package com.funcom.tcg.client.ui.maps;

import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.model.ResourceGetter;
import com.funcom.gameengine.view.MapObject;
import com.funcom.tcg.client.ui.Localizer;
import com.funcom.tcg.client.ui.quest.QuestModel;
import java.util.List;

public interface MapWindowModel {
  String getName();
  
  WorldCoordinate getPlayerPosition();
  
  String getMapId();
  
  void addQuestChangeListener(QuestModel.QuestChangeAdapter paramQuestChangeAdapter);
  
  String getQuestRegionID();
  
  boolean hasNotVisitedMap(String paramString);
  
  boolean showTutorialMap();
  
  List<MapObject> getQuestObjectivesList();
  
  float getPlayerAngle();
  
  ResourceGetter getResourceGetter();
  
  Localizer getLocalizer();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\maps\MapWindowModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */