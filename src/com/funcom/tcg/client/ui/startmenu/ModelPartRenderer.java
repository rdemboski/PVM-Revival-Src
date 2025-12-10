/*    */ package com.funcom.tcg.client.ui.startmenu;
/*    */ 
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ import com.funcom.tcg.client.ui.SelectorRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelPartRenderer
/*    */   implements SelectorRenderer
/*    */ {
/*    */   public String render(Object o) {
/* 16 */     if (o == null)
/* 17 */       return "<NULL>"; 
/* 18 */     return ((ModularDescription.Part)o).getPartName();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\ModelPartRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */