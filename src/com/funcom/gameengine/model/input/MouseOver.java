/*    */ package com.funcom.gameengine.model.input;
/*    */ 
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MouseOver
/*    */ {
/*    */   private PropNode ownerPropNode;
/*    */   
/*    */   public void setOwnerPropNode(PropNode ownerPropNode) {
/* 14 */     this.ownerPropNode = ownerPropNode;
/*    */   }
/*    */   
/*    */   public PropNode getOwnerPropNode() {
/* 18 */     return this.ownerPropNode;
/*    */   }
/*    */   
/*    */   public abstract void mouseEntered();
/*    */   
/*    */   public abstract void mouseExited();
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\input\MouseOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */