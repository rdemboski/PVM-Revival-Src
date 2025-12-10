/*    */ package com.funcom.tcg.client.ui.vendor;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PriceDescComparator
/*    */   implements Comparator<PriceDesc>
/*    */ {
/*    */   public int compare(PriceDesc price1, PriceDesc price2) {
/* 13 */     return price1.getClassId().compareTo(price2.getClassId());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\PriceDescComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */