/*     */ package com.funcom.tcg.client.ui.quest2;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.TcgBContainer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuestMissionsPane
/*     */   extends BContainer
/*     */ {
/*  22 */   private static final Rectangle R_MISSION_REWARD_LABEL = new Rectangle(28, 125, 226, 18);
/*  23 */   private static final Rectangle R_MISSION_REWARD_CONTAINER = new Rectangle(0, 0, 406, 146);
/*     */   
/*     */   private BLabel lblMissionRewardLabel;
/*     */   
/*     */   private MissionsContainer pnlMissionsContainer;
/*     */   private TcgBContainer pnlMissionRewardContainer;
/*     */   private ResourceManager resourceManager;
/*     */   private boolean showRewards;
/*     */   private QuestWindowModel model;
/*     */   
/*     */   public QuestMissionsPane(ResourceManager resourceManager, boolean showRewards, QuestWindowModel model, Rectangle rMissionsContainer) {
/*  34 */     this.resourceManager = resourceManager;
/*  35 */     this.showRewards = showRewards;
/*  36 */     this.model = model;
/*  37 */     setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*     */     
/*  39 */     this.pnlMissionsContainer = new MissionsContainer(resourceManager, rMissionsContainer.height, rMissionsContainer.width);
/*  40 */     Rectangle missionConstraints = new Rectangle(rMissionsContainer);
/*  41 */     if (!showRewards)
/*  42 */       missionConstraints.y = 0; 
/*  43 */     add((BComponent)this.pnlMissionsContainer, missionConstraints);
/*  44 */     if (showRewards) {
/*  45 */       this.pnlMissionRewardContainer = new TcgBContainer();
/*  46 */       add((BComponent)this.pnlMissionRewardContainer, R_MISSION_REWARD_CONTAINER);
/*     */       
/*  48 */       this.lblMissionRewardLabel = new BLabel(TcgGame.getLocalizedText("questwindow.missionswindow.missionrewardlabel", new String[0]));
/*  49 */       add((BComponent)this.lblMissionRewardLabel, R_MISSION_REWARD_LABEL);
/*     */     } 
/*  51 */     refresh();
/*     */   }
/*     */   
/*     */   public void refresh() {
/*  55 */     if (this.model.hasQuest()) {
/*  56 */       setObjectives(this.model.getCurrentMissionObjectives());
/*  57 */       setCurrentMission(0, 0, "", this.model.getCurrentMissionRewards());
/*     */     } else {
/*     */       
/*  60 */       clear();
/*     */     } 
/*     */   }
/*     */   public void clear() {
/*  64 */     setObjectives(new ArrayList<MissionObjective>());
/*  65 */     setCurrentMission(0, 0, TcgGame.getLocalizedText("questwindow.missionswindow.nocurrentmission", new String[0]), new ArrayList<MissionReward>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setObjectives(List<MissionObjective> missions) {
/*  74 */     this.pnlMissionsContainer.setObjectives(missions);
/*     */   }
/*     */   
/*     */   private void setCurrentMission(int currentMission, int numMissions, String missionName, List<MissionReward> rewards) {
/*  78 */     this.pnlMissionsContainer.setCurrentMission(currentMission, numMissions, missionName);
/*  79 */     if (this.showRewards) {
/*  80 */       setMissionRewards(rewards);
/*     */     }
/*     */   }
/*     */   
/*  84 */   private static final Rectangle R_MISSION_REWARD_ICON_LABEL = new Rectangle(0, 29, 48, 48);
/*  85 */   private static final Rectangle R_MISSION_REWARD_ICON_DETAIL = new Rectangle(38, 11, 81, 27);
/*  86 */   private static final int MISSION_REWARD_PAIR_WIDTH = Math.max(R_MISSION_REWARD_ICON_LABEL.width, R_MISSION_REWARD_ICON_DETAIL.width + R_MISSION_REWARD_ICON_DETAIL.x);
/*     */   private static final int MISSION_REWARD_SEPPARATOR = -13;
/*     */   
/*     */   private void setMissionRewards(List<MissionReward> rewards) {
/*  90 */     if (rewards.size() > 3) throw new IllegalStateException("Design supports max 3 rewards, please revise"); 
/*  91 */     BContainer contentPane = this.pnlMissionRewardContainer.getContentPane();
/*  92 */     contentPane.removeAll();
/*  93 */     if (rewards.size() == 0)
/*     */       return; 
/*  95 */     float xPos = R_MISSION_REWARD_CONTAINER.width / 2.0F - MISSION_REWARD_PAIR_WIDTH / 2.0F * rewards.size();
/*  96 */     for (MissionReward reward : rewards) {
/*  97 */       Rectangle iconLabelPos = new Rectangle((int)xPos, R_MISSION_REWARD_ICON_LABEL.y, R_MISSION_REWARD_ICON_LABEL.width, R_MISSION_REWARD_ICON_LABEL.height);
/*  98 */       contentPane.add((BComponent)new BackgroundIconLabel(this.resourceManager, reward.getIconPath()), iconLabelPos);
/*  99 */       Rectangle iconDetailPos = new Rectangle((int)xPos + R_MISSION_REWARD_ICON_DETAIL.x, R_MISSION_REWARD_ICON_LABEL.y + R_MISSION_REWARD_ICON_DETAIL.y, R_MISSION_REWARD_ICON_DETAIL.width, R_MISSION_REWARD_ICON_DETAIL.height);
/* 100 */       BLabel detail = new BLabel("x " + reward.getNumber());
/* 101 */       detail.setStyleClass("label-mission-reward");
/* 102 */       contentPane.add((BComponent)detail, iconDetailPos);
/*     */       
/* 104 */       xPos += (MISSION_REWARD_PAIR_WIDTH + -13);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\QuestMissionsPane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */