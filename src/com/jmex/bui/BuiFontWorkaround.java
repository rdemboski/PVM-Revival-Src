/*    */ package com.jmex.bui;
/*    */ 
/*    */ public class BuiFontWorkaround {
/*    */   public static void reconfigureLabels(BComponent component) {
/*  5 */     if (component instanceof BContainer) {
/*  6 */       BContainer container = (BContainer)component;
/*  7 */       int compCount = container.getComponentCount();
/*  8 */       for (int i = 0; i < compCount; i++) {
/*  9 */         reconfigureLabels(container.getComponent(i));
/*    */       }
/*    */     } else {
/* 12 */       replaceLabelObject(component);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static void replaceLabelObject(BComponent component) {
/* 18 */     if (component instanceof BLabel) {
/* 19 */       replaceLabel((BLabel)component);
/*    */     }
/*    */   }
/*    */   
/*    */   private static void replaceLabel(BLabel bLabel) {
/* 24 */     if (!(bLabel._label instanceof TcgLabel)) {
/* 25 */       Label oldLabel = bLabel._label;
/*    */       
/* 27 */       TcgLabel tcgLabel = new TcgLabel((BTextComponent)bLabel);
/* 28 */       tcgLabel.copyFrom(oldLabel);
/* 29 */       bLabel._label = tcgLabel;
/*    */       
/* 31 */       bLabel.invalidate();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BuiFontWorkaround.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */