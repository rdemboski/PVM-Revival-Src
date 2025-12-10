/*    */ package com.funcom.tcg.client.ui.quest2;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.ui.TcgBContainer;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MissionsContainer
/*    */   extends TcgBContainer
/*    */ {
/* 18 */   private static String singleMissionString = TcgGame.getLocalizedText("questwindow.missionswindow.singlemissionprefix", new String[0]);
/*    */   
/*    */   private BLabel lblMissionName;
/* 21 */   private List<IconDetailPair> iconDetalPairs = new LinkedList<IconDetailPair>();
/*    */   private ResourceManager resourceManager;
/*    */   private static final int spacing = 53;
/*    */   
/*    */   public MissionsContainer(ResourceManager resourceManager, int height, int width) {
/* 26 */     this.resourceManager = resourceManager;
/* 27 */     this.lblMissionName = new BLabel("No data");
/* 28 */     getContentPane().add((BComponent)this.lblMissionName, new Rectangle(12, height - 84, width - 65, 29));
/*    */     
/* 30 */     int currentHeight = height - 84 - 53;
/* 31 */     while (currentHeight > 0) {
/* 32 */       this.iconDetalPairs.add(createNewIconDetailPair(currentHeight, width - 72));
/* 33 */       currentHeight -= 53;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private IconDetailPair createNewIconDetailPair(int y, int width) {
/* 39 */     TCGTextAndManyIconsMixedContainer mixedContainer = new TCGTextAndManyIconsMixedContainer(this.resourceManager);
/* 40 */     getContentPane().add((BComponent)mixedContainer, new Rectangle(30, y, width, 48));
/* 41 */     return mixedContainer;
/*    */   }
/*    */   
/*    */   public void setObjectives(List<MissionObjective> objectives) {
/* 45 */     if (objectives.size() > 4) throw new RuntimeException("This gui is designed for max 4 objectives.");
/*    */     
/* 47 */     Iterator<MissionObjective> missionIterator = objectives.iterator();
/* 48 */     for (IconDetailPair iconDetailPair : this.iconDetalPairs) {
/* 49 */       if (missionIterator.hasNext()) {
/* 50 */         MissionObjective mission = missionIterator.next();
/* 51 */         iconDetailPair.update(mission);
/* 52 */         iconDetailPair.setAlpha(mission.isCompleted() ? 0.5F : 1.0F); continue;
/*    */       } 
/* 54 */       iconDetailPair.clear();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setCurrentMission(int currentMissionNum, int numMissions, String missionName) {
/* 59 */     if (numMissions == 0) {
/* 60 */       this.lblMissionName.setText(String.format(singleMissionString, new Object[] { missionName }));
/*    */     } else {
/*    */       
/* 63 */       this.lblMissionName.setText("");
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface IconDetailPair {
/*    */     void update(MissionObjective param1MissionObjective);
/*    */     
/*    */     void clear();
/*    */     
/*    */     void setAlpha(float param1Float);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\MissionsContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */