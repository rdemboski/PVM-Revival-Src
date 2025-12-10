/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.jme.bounding.BoundingVolume;
/*    */ import com.jme.intersection.CollisionResults;
/*    */ import com.jme.intersection.PickResults;
/*    */ import com.jme.math.Ray;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.scene.Node;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jmex.bui.text.BText;
/*    */ 
/*    */ public class TextSpatial extends Spatial {
/*    */   private final BText text;
/*    */   private float alpha;
/*    */   
/*    */   public TextSpatial(BText text) {
/* 17 */     this.text = text;
/* 18 */     setRenderQueueMode(4);
/* 19 */     this.alpha = 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setParent(Node parent) {
/* 24 */     super.setParent(parent);
/* 25 */     if (parent != null) {
/* 26 */       this.text.wasAdded();
/*    */     } else {
/* 28 */       this.text.wasRemoved();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateModelBound() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void setModelBound(BoundingVolume modelBound) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void findCollisions(Spatial scene, CollisionResults results, int requiredOnBits) {}
/*    */ 
/*    */   
/*    */   public boolean hasCollision(Spatial scene, boolean checkTriangles, int requiredOnBits) {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void findPick(Ray toTest, PickResults results, int requiredOnBits) {}
/*    */ 
/*    */   
/*    */   public int getVertexCount() {
/* 55 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTriangleCount() {
/* 60 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(Renderer r) {
/* 65 */     if (!r.isProcessingQueue() && 
/* 66 */       r.checkAndAdd(this)) {
/*    */       return;
/*    */     }
/*    */     
/* 70 */     this.text.render(r, (int)this.localTranslation.x, (int)this.localTranslation.y, this.alpha);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateWorldBound() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAlpha(float alpha) {
/* 80 */     this.alpha = alpha;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 84 */     return (this.text.getSize()).width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 88 */     return (this.text.getSize()).height;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\TextSpatial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */