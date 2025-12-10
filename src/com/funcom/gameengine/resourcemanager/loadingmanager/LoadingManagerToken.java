/*     */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*     */ 
/*     */ import com.jme.util.Timer;
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
/*     */ public abstract class LoadingManagerToken
/*     */ {
/*     */   public static final int STEP_REQUEST_ASSETS = 0;
/*     */   public static final int STEP_WAIT_FOR_ASSETS = 1;
/*     */   public static final int STEP_GAME_THREAD = 2;
/*     */   public static final int STEP_LOADING_DONE = 3;
/*     */   public static final int NUM_STEPS = 4;
/*  45 */   private int Step = 0;
/*     */   
/*  47 */   private Timer timer = Timer.getTimer();
/*  48 */   private long previousTick = 0L;
/*  49 */   private Double ElapsedTime = Double.valueOf(0.0D);
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextStep() {
/*  54 */     this.Step++;
/*     */   }
/*     */   
/*     */   public int getCurrentStep() {
/*  58 */     return this.Step;
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/*  62 */     return (this.Step >= 3);
/*     */   }
/*     */   
/*     */   public int update() throws Exception {
/*  66 */     switch (getCurrentStep()) {
/*     */       
/*     */       case 0:
/*  69 */         this.previousTick = this.timer.getTime();
/*  70 */         if (processRequestAssets()) {
/*  71 */           nextStep();
/*     */           
/*  73 */           if (processWaitingAssets()) {
/*  74 */             nextStep();
/*  75 */             if (processGame()) {
/*  76 */               nextStep();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       
/*     */       case 1:
/*  82 */         if (processWaitingAssets()) {
/*  83 */           nextStep();
/*     */         } else {
/*  85 */           long currentTick = this.timer.getTime();
/*  86 */           this.ElapsedTime = Double.valueOf(this.ElapsedTime.doubleValue() + Double.longBitsToDouble(currentTick - this.previousTick) / Double.longBitsToDouble(this.timer.getResolution()));
/*  87 */           this.previousTick = currentTick;
/*     */ 
/*     */           
/*  90 */           if (this.ElapsedTime.doubleValue() >= LoadingManager.LOADING_TIMEOUT.doubleValue()) {
/*  91 */             if (LoadingManager.DEBUG_INFO)
/*  92 */               System.out.printf("CreateMeshObjectLMToken::processWaitingAssets - Warning - taking too much time loading the mesh object, cancelling it.", new Object[0]); 
/*  93 */             this.Step = 3;
/*     */           } 
/*     */         } 
/*     */       
/*     */       case 2:
/*  98 */         if (processGame()) {
/*  99 */           nextStep();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 107 */         return getCurrentStep();
/*     */     } 
/*     */     this.Step = 3;
/*     */   }
/* 111 */   public boolean processRequestAssets() throws Exception { return true; }
/* 112 */   public boolean processWaitingAssets() throws Exception { return true; } public boolean processGame() throws Exception {
/* 113 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\LoadingManagerToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */