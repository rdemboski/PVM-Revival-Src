/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.layout.AbsoluteLayout;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FreeAbsoluteLayout
/*    */   extends AbsoluteLayout
/*    */ {
/*    */   public void addLayoutComponent(BComponent comp, Object constraints) {
/* 12 */     addUncheckedConstraints(comp, constraints);
/*    */   }
/*    */   
/*    */   private void addUncheckedConstraints(BComponent comp, Object constraints) {
/* 16 */     this._spots.put(comp, constraints);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\FreeAbsoluteLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */