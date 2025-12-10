/*    */ package com.funcom.gameengine.model;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnPoint
/*    */   extends InteractibleProp
/*    */ {
/*    */   private boolean triggered = false;
/*    */   private int timer;
/*    */   private int triggerId;
/*    */   private boolean lootIsMagnetic = false;
/*    */   
/*    */   public SpawnPoint(String name, double radius) {
/* 19 */     super(-1, name, radius);
/* 20 */     super.setVisible(false);
/*    */   }
/*    */   
/*    */   public SpawnPoint(String name, WorldCoordinate position, double radius) {
/* 24 */     super(-1, name, position, radius);
/* 25 */     super.setVisible(false);
/*    */   }
/*    */   
/*    */   public void setVisible(boolean visible) {
/* 29 */     throw new IllegalStateException("SpawnPoint cannot be visible");
/*    */   }
/*    */   
/*    */   public int getTimer() {
/* 33 */     return this.timer;
/*    */   }
/*    */   
/*    */   public void setTimer(int timer) {
/* 37 */     this.timer = timer;
/*    */   }
/*    */   
/*    */   public void setTriggered(boolean triggered) {
/* 41 */     this.triggered = triggered;
/*    */   }
/*    */   
/*    */   public boolean isTriggered() {
/* 45 */     return this.triggered;
/*    */   }
/*    */   
/*    */   public int getTriggerId() {
/* 49 */     return this.triggerId;
/*    */   }
/*    */   
/*    */   public void setTriggerId(int triggerId) {
/* 53 */     this.triggerId = triggerId;
/*    */   }
/*    */   
/*    */   public boolean isLootMagnetic() {
/* 57 */     return this.lootIsMagnetic;
/*    */   }
/*    */   
/*    */   public void setLootMagnetic(boolean lootIsMagnetic) {
/* 61 */     this.lootIsMagnetic = lootIsMagnetic;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\SpawnPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */