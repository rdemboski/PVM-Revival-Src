/*    */ package com.funcom.commons.jme;
/*    */ 
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ 
/*    */ public class RandomMoveController
/*    */   extends Controller
/*    */ {
/*    */   private static final float DIFF_TOLERANCE = 0.1F;
/*    */   private float minx;
/*    */   private float maxx;
/*    */   private float miny;
/*    */   private float maxy;
/*    */   private float minz;
/*    */   private float maxz;
/*    */   private Spatial spatial;
/*    */   private Vector3f currentPos;
/*    */   private Vector3f newPos;
/*    */   
/*    */   public RandomMoveController(float minx, float maxx, float miny, float maxy, float minz, float maxz, Spatial spatial) {
/* 23 */     this.minx = minx;
/* 24 */     this.maxx = maxx;
/* 25 */     this.miny = miny;
/* 26 */     this.maxy = maxy;
/* 27 */     this.minz = minz;
/* 28 */     this.maxz = maxz;
/* 29 */     this.spatial = spatial;
/*    */     
/* 31 */     this.currentPos = new Vector3f();
/* 32 */     this.newPos = new Vector3f();
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 36 */     if (Math.abs(this.currentPos.x - this.newPos.x) < 0.1F && Math.abs(this.currentPos.y - this.newPos.y) < 0.1F && Math.abs(this.currentPos.z - this.newPos.z) < 0.1F) {
/*    */ 
/*    */       
/* 39 */       this.newPos.x = (float)Math.random() * (this.maxx - this.minx) + this.minx;
/* 40 */       this.newPos.y = (float)Math.random() * (this.maxy - this.miny) + this.miny;
/* 41 */       this.newPos.z = (float)Math.random() * (this.maxz - this.minz) + this.minz;
/*    */     } 
/*    */     
/* 44 */     this.currentPos.x -= (this.currentPos.x - this.newPos.x) * time;
/* 45 */     this.currentPos.y -= (this.currentPos.y - this.newPos.y) * time;
/* 46 */     this.currentPos.z -= (this.currentPos.z - this.newPos.z) * time;
/*    */     
/* 48 */     this.spatial.setLocalTranslation(this.currentPos);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\RandomMoveController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */