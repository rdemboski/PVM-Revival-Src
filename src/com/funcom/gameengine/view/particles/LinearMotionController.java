/*    */ package com.funcom.gameengine.view.particles;
/*    */ 
/*    */ import com.jme.math.Vector2f;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class LinearMotionController
/*    */   implements GuiParticleJoint.GuiParticleMotionController
/*    */ {
/*    */   public static final String TYPE = "linear";
/* 11 */   private static final Logger LOGGER = Logger.getLogger(LinearMotionController.class);
/*    */   
/*    */   private GuiParticleRelPos start;
/*    */   private GuiParticleRelPos end;
/*    */   private Vector2f currentResolutionVector;
/*    */   private Vector2f store;
/*    */   private float timeSecs;
/*    */   private float elapsedTime;
/*    */   
/*    */   public LinearMotionController(GuiParticleRelPos start, GuiParticleRelPos end, float timeSecs) {
/* 21 */     if (start == null)
/* 22 */       throw new IllegalArgumentException("start = null"); 
/* 23 */     if (end == null)
/* 24 */       throw new IllegalArgumentException("end = null"); 
/* 25 */     if (timeSecs < 0.0D)
/* 26 */       throw new IllegalArgumentException("Time can't be less than 0"); 
/* 27 */     this.start = start;
/* 28 */     this.end = end;
/* 29 */     this.timeSecs = timeSecs;
/*    */     
/* 31 */     this.store = new Vector2f();
/* 32 */     this.currentResolutionVector = new Vector2f();
/* 33 */     this.elapsedTime = 0.0F;
/*    */   }
/*    */   
/*    */   public void resetTime() {
/* 37 */     this.elapsedTime = 0.0F;
/*    */   }
/*    */   
/*    */   public Vector2f getPosition(float time) {
/* 41 */     DisplaySystem displaySystem = DisplaySystem.getDisplaySystem();
/* 42 */     this.currentResolutionVector.set(displaySystem.getWidth(), displaySystem.getHeight());
/*    */     
/* 44 */     Vector2f startVec = this.start.getPos(this.currentResolutionVector);
/* 45 */     Vector2f endVec = this.end.getPos(this.currentResolutionVector);
/*    */     
/* 47 */     this.elapsedTime += time;
/* 48 */     float elapsedFraction = this.elapsedTime / this.timeSecs;
/* 49 */     endVec.subtract(startVec, this.store).multLocal(elapsedFraction).addLocal(startVec);
/*    */ 
/*    */ 
/*    */     
/* 53 */     return this.store;
/*    */   }
/*    */   
/*    */   public boolean isDone() {
/* 57 */     return (this.elapsedTime > this.timeSecs);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 61 */     return "LinearMotionController{start=" + this.start + ", end=" + this.end + ", store=" + this.store + ", timeSecs=" + this.timeSecs + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\particles\LinearMotionController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */