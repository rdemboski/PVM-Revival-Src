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
            public int update() throws Exception {
                switch (getCurrentStep()) {
                
                case 0:
                    this.previousTick = this.timer.getTime();
                    if (processRequestAssets()) {
                    nextStep();
                    
                    if (processWaitingAssets()) {
                        nextStep();
                        if (processGame()) {
                        nextStep();
                        }
                    } 
                    } 
                    break;
                
                case 1:
                    if (processWaitingAssets()) {
                    nextStep();
                    } else {
                    long currentTick = this.timer.getTime();
                    this.ElapsedTime = Double.valueOf(this.ElapsedTime.doubleValue() + Double.longBitsToDouble(currentTick - this.previousTick) / Double.longBitsToDouble(this.timer.getResolution()));
                    this.previousTick = currentTick;

                    
                    if (this.ElapsedTime.doubleValue() >= LoadingManager.LOADING_TIMEOUT.doubleValue()) {
                        if (LoadingManager.DEBUG_INFO)
                        System.out.printf("CreateMeshObjectLMToken::processWaitingAssets - Warning - taking too much time loading the mesh object, cancelling it.", new Object[0]); 
                        this.Step = 3;
                    } 
                    } 
                    break;
                
                case 2:
                    if (processGame()) {
                    nextStep();
                    }
                    break;

                case 3:
                    return getCurrentStep();
                } 
                return getCurrentStep();
            }
/* 111 */   public boolean processRequestAssets() throws Exception { return true; }
/* 112 */   public boolean processWaitingAssets() throws Exception { return true; } public boolean processGame() throws Exception {
/* 113 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\LoadingManagerToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */