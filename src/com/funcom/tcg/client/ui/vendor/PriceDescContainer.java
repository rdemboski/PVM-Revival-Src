/*    */ package com.funcom.tcg.client.ui.vendor;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.layout.GroupLayout;
/*    */ import com.jmex.bui.layout.Justification;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class PriceDescContainer
/*    */   extends BContainer
/*    */ {
/*    */   public PriceDescContainer(Set<PriceDesc> priceDescSet) {
/* 17 */     super((BLayoutManager)GroupLayout.makeHoriz(Justification.RIGHT));
/* 18 */     setPriceDescSet(priceDescSet);
/*    */   }
/*    */   
/*    */   public void setPriceDescSet(Set<PriceDesc> priceDescSet) {
/* 22 */     removeAll();
/*    */ 
/*    */     
/* 25 */     List<PriceDesc> priceDescList = new ArrayList<PriceDesc>(priceDescSet.size());
/* 26 */     priceDescList.addAll(priceDescSet);
/* 27 */     Collections.sort(priceDescList, new PriceDescComparator());
/*    */     
/* 29 */     for (PriceDesc priceDesc : priceDescList) {
/* 30 */       PriceDescLabel priceDescLabel = new PriceDescLabel(priceDesc);
/* 31 */       add((BComponent)priceDescLabel);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getDefaultStyleClass() {
/* 38 */     return "vendorwindow.prices.container";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\PriceDescContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */