/*     */ package com.funcom.tcg.client.breadcrumbs;
/*     */ 
/*     */ import com.funcom.gameengine.PairedWCandBoolean;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.breadcrumbs.BreadcrumbManager;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*     */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*     */ import com.funcom.rpgengine2.quests.objectives.TCGObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class BreadcrumbsUpdateListener
/*     */   extends QuestModel.QuestChangeAdapter
/*     */   implements ClientQuestData.QuestListener
/*     */ {
/*  23 */   private static final Logger LOGGER = Logger.getLogger(BreadcrumbsUpdateListener.class);
/*     */   private ChunkWorldNode chunkWorldNode;
/*     */   private BreadcrumbManager breadcrumbManager;
/*     */   private ClientPlayer player;
/*     */   private ClientQuestData mission;
/*     */   
/*     */   public BreadcrumbsUpdateListener(ChunkWorldNode chunkWorldNode, BreadcrumbManager breadcrumbManager, ClientPlayer player) {
/*  30 */     if (chunkWorldNode == null)
/*  31 */       throw new IllegalArgumentException("chunkWorldNode = null"); 
/*  32 */     if (breadcrumbManager == null)
/*  33 */       throw new IllegalArgumentException("breadcrumbManager = null"); 
/*  34 */     if (player == null) {
/*  35 */       throw new IllegalArgumentException("player = null");
/*     */     }
/*  37 */     this.chunkWorldNode = chunkWorldNode;
/*  38 */     this.breadcrumbManager = breadcrumbManager;
/*  39 */     this.player = player;
/*     */   }
/*     */   
/*     */   public void missionAdded(QuestModel questModel, ClientQuestData mission) {
/*  43 */     if (!this.chunkWorldNode.isPathgraphAvailable()) {
/*  44 */       LOGGER.error("Pathgraph not generated for map: " + this.chunkWorldNode.getBasePath());
/*     */       
/*     */       return;
/*     */     } 
/*  48 */     if (!this.breadcrumbManager.isEnabled()) {
/*  49 */       LOGGER.warn("Breadcrumbs disabled by setting property to 'false'. Check breadcrumbs.properties to enable.");
/*     */       
/*     */       return;
/*     */     } 
/*  53 */     if (this.mission != null) {
/*  54 */       this.mission.removeQuestListener(this);
/*     */     }
/*  56 */     this.mission = mission;
/*     */     
/*  58 */     if (this.mission == null) {
/*  59 */       this.breadcrumbManager.hideBreadcrumbs();
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     this.mission.addQuestListener(this);
/*     */     
/*  65 */     repathIfPositionAvailable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void questCompleted(QuestModel questModel, ClientQuestData quest) {
/*  70 */     this.breadcrumbManager.reset();
/*     */   }
/*     */   
/*     */   public void questObjectiveCompleted(ClientQuestData clientQuestData, TCGObjectiveTracker objectiveTracker) {
/*  74 */     repathIfPositionAvailable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void questObjectiveUpdated(ClientQuestData clientQuestData, TCGObjectiveTracker objectiveTracker) {}
/*     */   
/*     */   private void repathIfPositionAvailable() {
/*  81 */     WorldCoordinate targetPosition = findAppropriateTargetPosition();
/*  82 */     if (targetPosition == null) {
/*  83 */       this.breadcrumbManager.hideBreadcrumbs();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  88 */     if (!this.breadcrumbManager.isAlreadyPathing(this.player.getPosition(), targetPosition)) {
/*  89 */       this.breadcrumbManager.findNewPath(this.player.getPosition(), targetPosition);
/*     */     }
/*     */   }
/*     */   
/*     */   private WorldCoordinate findAppropriateTargetPosition() {
/*  94 */     Set<ClientObjectiveTracker> unfinishedObjectives = this.mission.getIncompleteQuestObjectives();
/*  95 */     if (unfinishedObjectives.isEmpty()) {
/*  96 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 100 */     ClientObjectiveTracker nearestObjective = QuestModel.findFirstOutOfTheMapObjective(this.player.getPosition(), this.mission, true);
/* 101 */     if (nearestObjective != null) {
/* 102 */       QuestObjective questObjective = nearestObjective.getObjective();
/* 103 */       List<PairedWCandBoolean> gotoPositions = questObjective.getGotoPositions();
/* 104 */       if (gotoPositions != null && !gotoPositions.isEmpty()) {
/* 105 */         return ((PairedWCandBoolean)gotoPositions.get(0)).getWc();
/*     */       }
/*     */ 
/*     */       
/* 109 */       return new WorldCoordinate(0, 0, 0.0D, 0.0D, questObjective.getMapId(), 0);
/*     */     } 
/* 111 */     if (LOGGER.isEnabledFor((Priority)Level.ERROR)) {
/* 112 */       LOGGER.error("We totally couldn't find nearest objective. This is weird, and it shouldn't happen!");
/*     */     }
/* 114 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\breadcrumbs\BreadcrumbsUpdateListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */