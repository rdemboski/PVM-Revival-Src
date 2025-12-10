/*    */ package com.funcom.gameengine.model.input;
/*    */ 
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionExistsDecorator
/*    */   extends MouseOver
/*    */ {
/*    */   private MouseOver decoratee;
/*    */   private InteractibleProp interactibleProp;
/*    */   private String action;
/*    */   
/*    */   public ActionExistsDecorator(InteractibleProp interactibleProp, String action, MouseOver decoratee) {
/* 18 */     this.decoratee = decoratee;
/* 19 */     this.interactibleProp = interactibleProp;
/* 20 */     this.action = action;
/*    */   }
/*    */   
/*    */   public void mouseEntered() {
/* 24 */     if (this.interactibleProp.hasAction(this.action))
/* 25 */       this.decoratee.mouseEntered(); 
/*    */   }
/*    */   
/*    */   public void mouseExited() {
/* 29 */     if (this.interactibleProp.hasAction(this.action))
/* 30 */       this.decoratee.mouseExited(); 
/*    */   }
/*    */   
/*    */   public void setOwnerPropNode(PropNode ownerPropNode) {
/* 34 */     super.setOwnerPropNode(ownerPropNode);
/* 35 */     this.decoratee.setOwnerPropNode(ownerPropNode);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\input\ActionExistsDecorator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */