/*     */ package com.funcom.tcg.client.ui.reward;
/*     */ 
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RewardWindowController
/*     */   implements Updated
/*     */ {
/*     */   private RewardWindow rewardWindow;
/*  15 */   private LinkedList<Object> rewardQueue = new LinkedList();
/*     */   private boolean isActive;
/*  17 */   private float currentTime = 0.0F;
/*  18 */   private float dropTime = 1.0F;
/*  19 */   private float topFadeInTime = 0.25F;
/*  20 */   private float bottomFadeInTime = 0.2F;
/*  21 */   private float idleTime = 2.5F;
/*  22 */   private float fadeOutTime = 0.3F;
/*     */   
/*     */   public RewardWindowController() {
/*  25 */     this.rewardWindow = new RewardWindow(TcgGame.getResourceManager(), TcgGame.getDireEffectDescriptionFactory());
/*  26 */     resetRewardWindow();
/*     */   }
/*     */   
/*     */   public void update(float time) {
/*  30 */     if (this.isActive) {
/*  31 */       this.currentTime += time;
/*  32 */       if (this.currentTime < this.dropTime) {
/*  33 */         float progress = this.currentTime / this.dropTime;
/*  34 */         progress = (float)Math.sin(progress * Math.PI / 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  45 */         this.rewardWindow.setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - this.rewardWindow.getWidth() / 2, DisplaySystem.getDisplaySystem().getHeight() - (int)(this.rewardWindow.getHeight() * progress));
/*     */ 
/*     */       
/*     */       }
/*  49 */       else if (this.currentTime < this.dropTime + this.topFadeInTime) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  60 */         this.rewardWindow.setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - this.rewardWindow.getWidth() / 2, DisplaySystem.getDisplaySystem().getHeight() - this.rewardWindow.getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  65 */         this.rewardWindow.setTopAlpha((this.currentTime - this.dropTime) / this.topFadeInTime);
/*  66 */       } else if (this.currentTime < this.dropTime + this.topFadeInTime + this.bottomFadeInTime) {
/*  67 */         this.rewardWindow.setTopAlpha(1.0F);
/*  68 */         this.rewardWindow.setBottomAlpha((this.currentTime - this.dropTime - this.topFadeInTime) / this.bottomFadeInTime);
/*  69 */       } else if (this.currentTime < this.dropTime + this.topFadeInTime + this.bottomFadeInTime + this.idleTime) {
/*  70 */         this.rewardWindow.setBottomAlpha(1.0F);
/*  71 */       } else if (this.currentTime < this.dropTime + this.topFadeInTime + this.bottomFadeInTime + this.idleTime + this.fadeOutTime) {
/*  72 */         float alpha = 1.0F - (this.currentTime - this.dropTime - this.topFadeInTime - this.bottomFadeInTime - this.idleTime) / this.fadeOutTime;
/*  73 */         this.rewardWindow.setBottomAlpha(alpha);
/*  74 */         this.rewardWindow.setTopAlpha(alpha);
/*  75 */         this.rewardWindow.setGeomAlpha(alpha);
/*     */       } else {
/*  77 */         this.isActive = false;
/*  78 */         this.currentTime = 0.0F;
/*  79 */         resetRewardWindow();
/*  80 */         BuiSystem.removeWindow(this.rewardWindow);
/*     */       }
/*     */     
/*  83 */     } else if (!this.rewardQueue.isEmpty()) {
/*  84 */       this.isActive = true;
/*  85 */       this.rewardWindow.setReward(this.rewardQueue.removeFirst());
/*  86 */       BuiSystem.addWindow(this.rewardWindow);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resetRewardWindow() {
/* 102 */     this.rewardWindow.setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - this.rewardWindow.getWidth() / 2, DisplaySystem.getDisplaySystem().getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     this.rewardWindow.setTopAlpha(0.0F);
/* 108 */     this.rewardWindow.setBottomAlpha(0.0F);
/* 109 */     this.rewardWindow.setGeomAlpha(1.0F);
/* 110 */     this.rewardWindow.reset();
/*     */   }
/*     */   
/*     */   public void addReward(Object reward) {
/* 114 */     this.rewardQueue.addLast(reward);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\reward\RewardWindowController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */