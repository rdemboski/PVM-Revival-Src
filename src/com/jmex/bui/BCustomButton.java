/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jmex.bui.event.BEvent;
/*    */ import com.jmex.bui.event.MouseEvent;
/*    */ 
/*    */ public abstract class BCustomButton
/*    */   extends BTcgButton
/*    */ {
/*    */   protected void mouseReleased(MouseEvent mev) {
/* 10 */     if (this.armed && this.pressed) {
/*    */       
/* 12 */       fireAction(mev.getWhen(), mev.getModifiers());
/* 13 */       this.armed = false;
/*    */     } 
/* 15 */     this.pressed = false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mousePressed(MouseEvent mev) {
/* 20 */     if (mev.getButton() == 0) {
/* 21 */       this.pressed = true;
/* 22 */       this.armed = true;
/* 23 */     } else if (mev.getButton() == 1) {
/*    */ 
/*    */       
/* 26 */       this.armed = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean mouseExited(MouseEvent mev) {
/* 32 */     this.armed = false;
/*    */     
/* 34 */     return dispatchEventSuper((BEvent)mev);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean mouseEntered(MouseEvent event) {
/* 39 */     this.armed = this.pressed;
/*    */     
/* 41 */     return dispatchEventSuper((BEvent)event);
/*    */   }
/*    */ 
/*    */   
/*    */   public BComponent getHitComponent(int x, int y) {
/* 46 */     if (super.getHitComponent(x, y) == null) {
/* 47 */       return null;
/*    */     }
/* 49 */     return (BComponent)this;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BCustomButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */