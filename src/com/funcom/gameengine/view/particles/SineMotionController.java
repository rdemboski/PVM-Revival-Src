/*    */ package com.funcom.gameengine.view.particles;
/*    */ 
/*    */ import com.jme.math.FastMath;
/*    */ import com.jme.math.Vector2f;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ public class SineMotionController
/*    */   implements GuiParticleJoint.GuiParticleMotionController {
/*    */   public static final String TYPE = "sine";
/* 13 */   private static final Logger LOGGER = Logger.getLogger(SineMotionController.class);
/*    */   
/*    */   private GuiParticleRelPos start;
/*    */   private GuiParticleRelPos end;
/*    */   private Vector2f functionRange;
/*    */   private Vector2f currentResolutionVector;
/*    */   private Vector2f store;
/*    */   private float timeSecs;
/*    */   private float elapsedTime;
/*    */   private float radsStart;
/*    */   private float radsEnd;
/*    */   
/*    */   public SineMotionController(GuiParticleRelPos start, GuiParticleRelPos end, Vector2f functionRange, float timeSecs) {
/* 26 */     if (start == null)
/* 27 */       throw new IllegalArgumentException("start = null"); 
/* 28 */     if (end == null)
/* 29 */       throw new IllegalArgumentException("end = null"); 
/* 30 */     if (functionRange == null)
/* 31 */       throw new IllegalArgumentException("functionRange = null"); 
/* 32 */     if (timeSecs < 0.0D)
/* 33 */       throw new IllegalArgumentException("Time can't be less than 0"); 
/* 34 */     this.start = start;
/* 35 */     this.end = end;
/* 36 */     this.functionRange = functionRange;
/* 37 */     this.timeSecs = timeSecs;
/*    */     
/* 39 */     this.store = new Vector2f();
/* 40 */     this.currentResolutionVector = new Vector2f();
/* 41 */     this.elapsedTime = 0.0F;
/* 42 */     this.radsStart = FastMath.sin(functionRange.x);
/* 43 */     this.radsEnd = FastMath.sin(functionRange.y);
/*    */   }
/*    */   
/*    */   public void resetTime() {
/* 47 */     this.elapsedTime = 0.0F;
/*    */   }
/*    */   
/*    */   public Vector2f getPosition(float time) {
/* 51 */     DisplaySystem displaySystem = DisplaySystem.getDisplaySystem();
/* 52 */     this.currentResolutionVector.set(displaySystem.getWidth(), displaySystem.getHeight());
/*    */     
/* 54 */     this.elapsedTime += time;
/* 55 */     float elapsedFraction = this.elapsedTime / this.timeSecs;
/*    */ 
/*    */     
/* 58 */     float functionPosition = (this.functionRange.y - this.functionRange.x) * elapsedFraction + this.functionRange.x;
/* 59 */     float functionValue = FastMath.sin(functionPosition);
/*    */     
/* 61 */     float scaleFactor = (functionValue - this.radsStart) / (this.radsEnd - this.radsStart);
/* 62 */     if (LOGGER.isEnabledFor((Priority)Level.DEBUG)) {
/* 63 */       LOGGER.debug(Float.valueOf(scaleFactor));
/*    */     }
/* 65 */     Vector2f startVec = this.start.getPos(this.currentResolutionVector);
/* 66 */     Vector2f endVec = this.end.getPos(this.currentResolutionVector);
/*    */ 
/*    */     
/* 69 */     endVec.subtract(startVec, this.store).multLocal(scaleFactor).addLocal(startVec);
/*    */ 
/*    */ 
/*    */     
/* 73 */     return this.store;
/*    */   }
/*    */   
/*    */   public boolean isDone() {
/* 77 */     return (this.elapsedTime > this.timeSecs);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 81 */     return "LinearMotionController{start=" + this.start + ", end=" + this.end + ", store=" + this.store + ", timeSecs=" + this.timeSecs + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\particles\SineMotionController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */