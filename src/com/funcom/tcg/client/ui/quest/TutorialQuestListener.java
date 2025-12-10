/*     */ package com.funcom.tcg.client.ui.quest;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.rpgengine2.quests.objectives.SpecialTutorialObjective;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.hud.QuestTextType;
/*     */ import com.funcom.tcg.client.ui.hud.TutorialArrowDirection;
/*     */ import com.funcom.tcg.net.message.CompleteTutorialQuestObjectiveMessage;
/*     */ import com.jme.system.DisplaySystem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TutorialQuestListener
/*     */   extends QuestModel.QuestChangeAdapter
/*     */ {
/*     */   public static final String PET_TUTORIAL_KEY = "pet-tutorial";
/*     */   public static final String END_TUTORIAL_KEY = "end-tutorial";
/*     */   
/*     */   public void missionAdded(QuestModel questModel, ClientQuestData mission) {
/*  28 */     for (ClientObjectiveTracker objective : mission.getQuestObjectives()) {
/*  29 */       if (objective.getObjective() instanceof SpecialTutorialObjective) {
/*  30 */         String specialId = ((SpecialTutorialObjective)objective.getObjective()).getSpecialId();
/*  31 */         if (specialId.equals("pet-tutorial")) {
/*  32 */           if (MainGameState.getPlayerModel().getActivePet().getLevel() > 1) {
/*     */             try {
/*  34 */               NetworkHandler.instance().getIOHandler().send((Message)new CompleteTutorialQuestObjectiveMessage("pet-tutorial"));
/*     */             }
/*  36 */             catch (InterruptedException e) {}
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*  42 */           highlightPetButton(0);
/*  43 */           if (MainGameState.getPauseModel() != null)
/*  44 */             MainGameState.getPauseModel().instantPause();  continue;
/*     */         } 
/*  46 */         System.out.println("Received different special objective:  " + specialId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void missionCompleted(QuestModel questModel, ClientQuestData mission) {
/*  54 */     super.questCompleted(questModel, mission);
/*     */ 
/*     */     
/*  57 */     for (ClientObjectiveTracker objective : mission.getQuestObjectives()) {
/*  58 */       if (objective.getObjective() instanceof SpecialTutorialObjective) {
/*  59 */         String specialId = ((SpecialTutorialObjective)objective.getObjective()).getSpecialId();
/*  60 */         if (specialId.equals("end-tutorial")) {
/*  61 */           System.out.println("received end tutorial");
/*  62 */           closeTutorialWindow(); continue;
/*     */         } 
/*  64 */         System.out.println("Received different special objective: " + specialId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void questCompleted(QuestModel questModel, ClientQuestData quest) {
/*  72 */     super.questCompleted(questModel, quest);
/*     */ 
/*     */     
/*  75 */     for (ClientObjectiveTracker objective : quest.getQuestObjectives()) {
/*  76 */       if (objective.getObjective() instanceof SpecialTutorialObjective) {
/*  77 */         String specialId = ((SpecialTutorialObjective)objective.getObjective()).getSpecialId();
/*  78 */         if (specialId.equals("end-tutorial")) {
/*  79 */           System.out.println("received end tutorial");
/*  80 */           closeTutorialWindow(); continue;
/*     */         } 
/*  82 */         System.out.println("Received different special objective: " + specialId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void highlightPetButton(int tutorialId) {
/*  89 */     TcgGame.setPetTutorial(true);
/*  90 */     TcgGame.setPetTutorialId(tutorialId);
/*  91 */     if (MainGameState.getMainHud() != null) {
/*  92 */       int noahX = MainGameState.getNoahTutorialWindow().getX();
/*  93 */       int noahY = DisplaySystem.getDisplaySystem().getHeight() / 2 - 170;
/*  94 */       MainGameState.getNoahTutorialWindow().setLocation(noahX, noahY);
/*  95 */       MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.pet.before-1", new String[0]), QuestTextType.UPDATE);
/*     */ 
/*     */       
/*  98 */       HighlightedButton highlightedButton = MainGameState.getMainHud().getPetsButton();
/*  99 */       MainGameState.getMainHud().getPetsButton().setHighlighted(true);
/* 100 */       MainGameState.getArrowWindow().setLocation(highlightedButton.getAbsoluteX() + highlightedButton.getWidth() / 2 - 100, highlightedButton.getAbsoluteY() + highlightedButton.getHeight());
/*     */ 
/*     */       
/* 103 */       MainGameState.getArrowWindow().setVisible(true);
/* 104 */       MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.DOWN);
/* 105 */       MainGameState.getArrowWindow().updateInfoText("");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void highlightEquipmentButton() {
/* 110 */     TcgGame.setEquipmentTutorial(true);
/* 111 */     if (MainGameState.getMainHud() != null) {
/* 112 */       int noahX = MainGameState.getNoahTutorialWindow().getX();
/* 113 */       int noahY = DisplaySystem.getDisplaySystem().getHeight() / 2 - 170;
/* 114 */       MainGameState.getNoahTutorialWindow().setLocation(noahX, noahY);
/* 115 */       MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.equip.before", new String[0]), QuestTextType.UPDATE);
/*     */ 
/*     */       
/* 118 */       HighlightedButton highlightedButton = MainGameState.getMainHud().getCharacterButton();
/* 119 */       MainGameState.getArrowWindow().setLocation(highlightedButton.getAbsoluteX() + highlightedButton.getWidth() / 2 - 100, highlightedButton.getAbsoluteY() + highlightedButton.getHeight());
/*     */ 
/*     */       
/* 122 */       MainGameState.getArrowWindow().setVisible(true);
/* 123 */       MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.DOWN);
/* 124 */       MainGameState.getArrowWindow().updateInfoText("");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closeTutorialWindow() {
/* 129 */     if (MainGameState.getNoahTutorialWindow() != null)
/* 130 */       MainGameState.getNoahTutorialWindow().dismiss(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest\TutorialQuestListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */