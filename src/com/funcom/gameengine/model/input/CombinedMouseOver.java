/*    */ package com.funcom.gameengine.model.input;
/*    */ 
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CombinedMouseOver
/*    */   extends MouseOver
/*    */ {
/*    */   private MouseOver[] mouseOvers;
/*    */   
/*    */   public CombinedMouseOver(MouseOver... mouseOvers) {
/* 32 */     this.mouseOvers = mouseOvers;
/*    */   }
/*    */   
/*    */   public void setOwnerPropNode(PropNode ownerPropNode) {
/* 36 */     super.setOwnerPropNode(ownerPropNode);
/* 37 */     for (MouseOver mouseOver : this.mouseOvers)
/* 38 */       mouseOver.setOwnerPropNode(ownerPropNode); 
/*    */   }
/*    */   
/*    */   public void mouseEntered() {
/* 42 */     for (MouseOver mouseOver : this.mouseOvers)
/* 43 */       mouseOver.mouseEntered(); 
/*    */   }
/*    */   
/*    */   public void mouseExited() {
/* 47 */     for (MouseOver mouseOver : this.mouseOvers)
/* 48 */       mouseOver.mouseExited(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\input\CombinedMouseOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */