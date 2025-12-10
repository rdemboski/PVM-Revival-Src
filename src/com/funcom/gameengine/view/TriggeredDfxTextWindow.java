/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ 
/*    */ public class TriggeredDfxTextWindow
/*    */   extends DfxTextWindow
/*    */ {
/*    */   private TriggeredSpatialAndTime trigger;
/*    */   
/*    */   public TriggeredDfxTextWindow(ResourceManager resourceManager, float screenX, float screenY, float[] colorRBGA, float scale) {
/* 13 */     super(resourceManager, screenX, screenY, 1, colorRBGA, scale);
/*    */   }
/*    */   
/*    */   public void triggerKillText() {
/* 17 */     if (this.trigger != null) {
/* 18 */       this.trigger.triggerEnd();
/*    */     }
/*    */   }
/*    */   
/*    */   public int attachChild(Spatial child) {
/* 23 */     this.trigger = new TriggeredSpatialAndTime(0.0F, child);
/* 24 */     return attachChild(child, this.trigger);
/*    */   }
/*    */   
/*    */   private class TriggeredSpatialAndTime extends FloatingSpatials.SpatialAndTime {
/*    */     private boolean alive = true;
/* 29 */     private float triggerTime = TriggeredDfxTextWindow.this.arriveingDuration + TriggeredDfxTextWindow.this.stableDuration;
/*    */     
/*    */     protected TriggeredSpatialAndTime(float time, Spatial spatial) {
/* 32 */       super(time, spatial);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isDying() {
/* 37 */       return (this.time <= this.triggerTime + TriggeredDfxTextWindow.this.dissapearingDuration);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isAlive() {
/* 42 */       return (this.alive || this.time <= TriggeredDfxTextWindow.this.arriveingDuration + TriggeredDfxTextWindow.this.stableDuration);
/*    */     }
/*    */     
/*    */     public void triggerEnd() {
/* 46 */       this.alive = false;
/* 47 */       this.triggerTime = this.time;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TriggeredDfxTextWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */