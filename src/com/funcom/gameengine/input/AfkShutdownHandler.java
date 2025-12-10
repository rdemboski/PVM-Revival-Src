/*    */ package com.funcom.gameengine.input;
/*    */ 
/*    */ import com.funcom.gameengine.Updated;
/*    */ import com.jme.input.KeyInput;
/*    */ import com.jme.input.KeyInputListener;
/*    */ import com.jme.input.MouseInput;
/*    */ import com.jme.input.MouseInputListener;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ 
/*    */ public class AfkShutdownHandler
/*    */   implements MouseInputListener, KeyInputListener, Updated {
/* 12 */   private static final long AFK_TIME_MILLIS = TimeUnit.MINUTES.toMillis(15L);
/*    */   
/*    */   private final Runnable shutdownRunnable;
/*    */   
/*    */   private long lastUpdateAt;
/*    */   private boolean hasShutdown;
/*    */   private boolean override;
/*    */   
/*    */   public AfkShutdownHandler(Runnable shutdownRunnable) {
/* 21 */     this.shutdownRunnable = shutdownRunnable;
/* 22 */     MouseInput.get().addListener(this);
/* 23 */     KeyInput.get().addListener(this);
/*    */     
/* 25 */     this.lastUpdateAt = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onButton(int button, boolean pressed, int x, int y) {
/* 30 */     this.lastUpdateAt = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onWheel(int wheelDelta, int x, int y) {
/* 35 */     this.lastUpdateAt = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onMove(int xDelta, int yDelta, int newX, int newY) {
/* 40 */     this.lastUpdateAt = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onKey(char character, int keyCode, boolean pressed) {
/* 45 */     this.lastUpdateAt = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public void resetHasShutdown() {
/* 49 */     this.hasShutdown = false;
/* 50 */     this.lastUpdateAt = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public void setOverride(boolean value) {
/* 54 */     this.override = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(float time) {
/* 59 */     long lastUpdateTimePeriod = System.currentTimeMillis() - this.lastUpdateAt;
/*    */     
/* 61 */     if (!this.hasShutdown && !this.override && lastUpdateTimePeriod > AFK_TIME_MILLIS) {
/*    */       
/* 63 */       this.shutdownRunnable.run();
/* 64 */       this.hasShutdown = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\input\AfkShutdownHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */