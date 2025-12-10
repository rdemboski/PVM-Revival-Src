/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.jme.intersection.PickResults;
/*    */ import com.jme.math.Ray;
/*    */ import com.jme.scene.Spatial;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CollisionOnlyPassNode
/*    */   extends RuntimeContentPassNode
/*    */ {
/*    */   public int attachChild(Spatial child) {
/* 19 */     if (child != null) {
/* 20 */       if (this.children == null) {
/* 21 */         this.children = Collections.synchronizedList(new ArrayList(1));
/*    */       }
/* 23 */       this.children.add(child);
/*    */     } 
/*    */     
/* 26 */     if (this.children == null) return 0; 
/* 27 */     return this.children.size();
/*    */   }
/*    */   
/*    */   public void findPick(Ray toTest, PickResults results, int requiredOnBits) {
/* 31 */     if (this.children == null) {
/*    */       return;
/*    */     }
/* 34 */     for (int i = 0; i < getQuantity(); i++) {
/* 35 */       ((Spatial)this.children.get(i)).findPick(toTest, results);
/*    */     }
/*    */   }
/*    */   
/*    */   public void detachAllContent() {
/* 40 */     if (this.children != null)
/* 41 */       this.children.clear(); 
/* 42 */     this.children = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\CollisionOnlyPassNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */