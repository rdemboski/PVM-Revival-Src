/*    */ package com.funcom.gameengine.view.water;
/*    */ 
/*    */ import com.jme.math.FastMath;
/*    */ import com.jme.scene.Controller;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SinusShoreController
/*    */   extends Controller
/*    */ {
/*    */   private WaterMesh waterMesh;
/*    */   private float speed;
/*    */   private float actualSpeed;
/*    */   private float position;
/*    */   
/*    */   public SinusShoreController(WaterMesh waterMesh, float speed) {
/* 20 */     this.waterMesh = waterMesh;
/* 21 */     setSpeed(speed);
/* 22 */     this.position = 0.0F;
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 26 */     this.position += time * this.actualSpeed;
/* 27 */     if (this.position > 6.2831855F) {
/* 28 */       this.position %= 6.2831855F;
/*    */     }
/* 30 */     this.waterMesh.setShorePosition((FastMath.sin(this.position) + 1.0F) * 0.5F);
/*    */   }
/*    */   
/*    */   public float getSpeed() {
/* 34 */     return this.speed;
/*    */   }
/*    */   
/*    */   public void setSpeed(float speed) {
/* 38 */     this.speed = speed;
/* 39 */     this.actualSpeed = speed * 6.2831855F;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\water\SinusShoreController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */