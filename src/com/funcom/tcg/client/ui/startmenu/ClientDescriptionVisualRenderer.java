/*    */ package com.funcom.tcg.client.ui.startmenu;
/*    */ 
/*    */ import com.funcom.tcg.client.ui.SelectorRenderer;
/*    */ import com.funcom.tcg.rpg.ClientDescriptionVisual;
/*    */ 
/*    */ public class ClientDescriptionVisualRenderer
/*    */   implements SelectorRenderer {
/*    */   public String render(Object o) {
/*  9 */     if (o == null)
/* 10 */       return "<NULL>"; 
/* 11 */     return ((ClientDescriptionVisual)o).getLocalizedName();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\ClientDescriptionVisualRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */