/*    */ package com.funcom.tcg.client.ui.maps;
/*    */ 
/*    */ import com.funcom.commons.FileUtils;
/*    */ import com.funcom.gameengine.PairedWCandBoolean;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.view.MapObject;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.Localizer;
/*    */ import com.funcom.tcg.client.ui.hud2.QuestHudModel;
/*    */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*    */ import com.funcom.tcg.client.ui.quest2.MissionObjective;
/*    */ import com.funcom.tcg.token.TCGWorld;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapWindowModelImpl
/*    */   implements MapWindowModel
/*    */ {
/*    */   public String getName() {
/* 25 */     return MainGameState.getPlayerModel().getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldCoordinate getPlayerPosition() {
/* 30 */     return MainGameState.getPlayerModel().getPosition();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMapId() {
/* 35 */     return ((TCGWorld)MainGameState.getWorld()).getChunkWorldNode().getChunkWorldInfo().getMapId();
/*    */   }
/*    */ 
/*    */   
/*    */   public void addQuestChangeListener(QuestModel.QuestChangeAdapter regionQuestChangeListener) {
/* 40 */     MainGameState.getQuestModel().addChangeListener((QuestModel.QuestChangeListener)regionQuestChangeListener);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getQuestRegionID() {
/* 45 */     return MainGameState.getQuestHudModel().getQuestRegionID();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNotVisitedMap(String map) {
/* 50 */     return (MainGameState.getVisitedMaps() == null || !MainGameState.getVisitedMaps().contains(map));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean showTutorialMap() {
/* 55 */     return (MainGameState.getVisitedMaps() == null || !MainGameState.getVisitedMaps().contains("003_g_humidjungle"));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<MapObject> getQuestObjectivesList() {
/* 60 */     List<MapObject> questObjects = new ArrayList<MapObject>();
/* 61 */     QuestHudModel questHudModel = MainGameState.getQuestHudModel();
/* 62 */     List<MissionObjective> missionObjectives = questHudModel.getCurrentMissionObjectives();
/* 63 */     String mapId = FileUtils.trimTailingSlashes(getMapId());
/* 64 */     for (MissionObjective missionObjective : missionObjectives) {
/* 65 */       if (!missionObjective.isCompleted()) {
/* 66 */         List<PairedWCandBoolean> gotoPositions = missionObjective.getObjective().getGotoPositions();
/* 67 */         if (gotoPositions != null)
/* 68 */           for (PairedWCandBoolean gotoPosition : gotoPositions) {
/* 69 */             if (gotoPosition.getWc().getMapId().equals(mapId)) {
/*    */               String icon;
/* 71 */               if (questHudModel.getCurrentMissionNum() == questHudModel.getMissionTotalNum() - 1) {
/* 72 */                 icon = MapObject.MapObjectType.QUESTGIVER_TYPE_COMPLETE.getIcon();
/*    */               } else {
/* 74 */                 icon = MapObject.MapObjectType.QUEST_OBJECTIVE_TYPE.getIcon();
/*    */               } 
/* 76 */               questObjects.add(new MapObject("", gotoPosition.getWc(), icon));
/*    */             } 
/*    */           }  
/*    */       } 
/*    */     } 
/* 81 */     return questObjects;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getPlayerAngle() {
/* 86 */     return MainGameState.getPlayerNode().getAngle();
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceGetter getResourceGetter() {
/* 91 */     return TcgGame.getResourceGetter();
/*    */   }
/*    */ 
/*    */   
/*    */   public Localizer getLocalizer() {
/* 96 */     return (Localizer)MainGameState.getInstance();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\maps\MapWindowModelImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */