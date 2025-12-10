/*     */ package com.funcom.rpgengine2.quests.objectives;
/*     */ 
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.gameengine.PairedWCandBoolean;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DefaultQuestObjective
/*     */   implements QuestObjective
/*     */ {
/*     */   protected String objectiveId;
/*     */   protected String mapId;
/*     */   protected String startMissionText;
/*     */   protected String unCompletedMissionText;
/*     */   protected String completedMissionText;
/*     */   private String iconPath;
/*     */   private String shortObjetiveText;
/*     */   private String goToId;
/*     */   private int endZoneHeight;
/*     */   private int endZoneWidth;
/*     */   private List<PairedWCandBoolean> gotoPosition;
/*     */   private String regionID;
/*     */   
/*     */   public DefaultQuestObjective(String objectiveId) {
/*  30 */     this.objectiveId = objectiveId;
/*     */   }
/*     */   
/*     */   public String getObjectiveId() {
/*  34 */     return this.objectiveId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addStartObjectiveText(String startMissionText) {
/*  39 */     this.startMissionText = startMissionText;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStartObjectiveText() {
/*  44 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.startMissionText);
/*     */   }
/*     */   
/*     */   public String getStartObjectiveTextUnLocalized() {
/*  48 */     return this.startMissionText;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addUnCompletedObjectiveText(String unCompletedMissionText) {
/*  53 */     this.unCompletedMissionText = unCompletedMissionText;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUnCompletedObjectiveText() {
/*  58 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.unCompletedMissionText);
/*     */   }
/*     */   
/*     */   public String getUnCompletedObjectiveTextUnLocalized() {
/*  62 */     return this.unCompletedMissionText;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCompletedObjectiveText(String completedMissionText) {
/*  67 */     this.completedMissionText = completedMissionText;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCompletedObjectiveText() {
/*  72 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.completedMissionText);
/*     */   }
/*     */   
/*     */   public String getCompletedObjectiveTextUnLocalized() {
/*  76 */     return this.completedMissionText;
/*     */   }
/*     */   
/*     */   public String getMapId() {
/*  80 */     return this.mapId;
/*     */   }
/*     */   
/*     */   public void setMapId(String mapId) {
/*  84 */     this.mapId = mapId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addIconPath(String iconPath) {
/*  89 */     if (iconPath.length() != 0) {
/*  90 */       this.iconPath = iconPath;
/*     */     } else {
/*  92 */       this.iconPath = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getIconPath() {
/*  97 */     return this.iconPath;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getShortObjectiveText() {
/* 102 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.shortObjetiveText);
/*     */   }
/*     */   
/*     */   public String getShortObjectiveTextUnLocalized() {
/* 106 */     return this.shortObjetiveText;
/*     */   }
/*     */   
/*     */   public void setShortObjetiveText(String shortObjetiveText) {
/* 110 */     this.shortObjetiveText = shortObjetiveText;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setObjectiveLocationDefinition(String goToId, int endZoneHeight, int endZoneWidth) {
/* 115 */     this.goToId = goToId;
/* 116 */     this.endZoneHeight = endZoneHeight;
/* 117 */     this.endZoneWidth = endZoneWidth;
/*     */   }
/*     */   
/*     */   public String getGoToId() {
/* 121 */     return this.goToId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGotoZoneHeight() {
/* 126 */     return this.endZoneHeight;
/*     */   }
/*     */   
/*     */   public int getGotoZoneWidth() {
/* 130 */     return this.endZoneWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGotoPositions(List<PairedWCandBoolean> gotoPosition) {
/* 135 */     if (this.gotoPosition != null || gotoPosition == null)
/*     */       return; 
/* 137 */     this.gotoPosition = gotoPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PairedWCandBoolean> getGotoPositions() {
/* 142 */     return this.gotoPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRegionID(String regionID) {
/* 147 */     this.regionID = regionID;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRegionID() {
/* 152 */     return this.regionID;
/*     */   }
/*     */   
/*     */   public abstract ObjectiveType getObjectiveType();
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\DefaultQuestObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */