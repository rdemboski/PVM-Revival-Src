/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jmex.bui.event.BEvent;
/*    */ import com.jmex.bui.event.MouseEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BComponentContainingButton
/*    */   extends BTcgButton
/*    */ {
/*    */   protected void mouseReleased(MouseEvent mev) {
/* 12 */     if (this.armed && this.pressed) {
/*    */       
/* 14 */       fireAction(mev.getWhen(), mev.getModifiers());
/* 15 */       this.armed = false;
/*    */     } 
/* 17 */     this.pressed = false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mousePressed(MouseEvent mev) {
/* 22 */     if (mev.getButton() == 0) {
/* 23 */       this.pressed = true;
/* 24 */       this.armed = true;
/* 25 */     } else if (mev.getButton() == 1) {
/*    */ 
/*    */       
/* 28 */       this.armed = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean mouseExited(MouseEvent mev) {
/* 34 */     if (super.getHitComponent(mev.getX() - getParent().getAbsoluteX(), mev.getY() - getParent().getAbsoluteY()) == null) {
/* 35 */       this.armed = false;
/*    */       
/* 37 */       return dispatchEventSuper((BEvent)mev);
/*    */     } 
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean mouseEntered(MouseEvent event) {
/* 45 */     this.armed = this.pressed;
/*    */     
/* 47 */     return dispatchEventSuper((BEvent)event);
/*    */   }
/*    */ 
/*    */   
/*    */   public BComponent getHitComponent(int x, int y) {
/* 52 */     if (super.getHitComponent(x, y) == null) {
/* 53 */       return null;
/*    */     }
/* 55 */     return super.getHitComponent(x, y);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BComponentContainingButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */