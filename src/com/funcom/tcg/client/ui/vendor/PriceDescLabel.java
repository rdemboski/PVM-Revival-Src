/*    */ package com.funcom.tcg.client.ui.vendor;
/*    */ 
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.jmex.bui.BLabel;
/*    */ 
/*    */ public class PriceDescLabel
/*    */   extends BLabel {
/*    */   public PriceDescLabel(PriceDesc priceDesc) {
/*  9 */     super(String.valueOf(priceDesc.getAmount()));
/* 10 */     setIcon(TcgUI.getIconProvider().getIconForPriceDesc(priceDesc));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getDefaultStyleClass() {
/* 16 */     return "vendorwindow.prices.label";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\PriceDescLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */