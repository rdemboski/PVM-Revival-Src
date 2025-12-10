/*    */ package com.jmex.bui.layout;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.util.Dimension;
/*    */ import com.jmex.bui.util.Insets;
/*    */ 
/*    */ public class CenterLayout
/*    */   extends BLayoutManager {
/*    */   public Dimension computePreferredSize(BContainer target, int whint, int hhint) {
/* 11 */     Dimension result = new Dimension(0, 0);
/*    */     
/* 13 */     Insets insets = target.getInsets();
/* 14 */     int targetWidth = target.getWidth() - insets.getHorizontal();
/* 15 */     int targetHeight = target.getHeight() - insets.getHorizontal();
/*    */     
/* 17 */     for (int i = 0; i < target.getComponentCount(); i++) {
/* 18 */       BComponent component = target.getComponent(i);
/* 19 */       Dimension preferredSize = component.getPreferredSize(targetWidth, targetHeight);
/* 20 */       result.width = Math.max(result.width, preferredSize.width);
/* 21 */       result.height = Math.max(result.height, preferredSize.height);
/*    */     } 
/*    */     
/* 24 */     return result;
/*    */   }
/*    */   
/*    */   public void layoutContainer(BContainer target) {
/* 28 */     Insets insets = target.getInsets();
/* 29 */     int targetWidth = target.getWidth() - insets.getHorizontal();
/* 30 */     int targetHeight = target.getHeight() - insets.getHorizontal();
/*    */     
/* 32 */     for (int i = 0; i < target.getComponentCount(); i++) {
/* 33 */       BComponent component = target.getComponent(i);
/* 34 */       Dimension preferredSize = component.getPreferredSize(targetWidth, targetHeight);
/*    */       
/* 36 */       int width = Math.min(preferredSize.width, targetWidth);
/* 37 */       int height = Math.min(preferredSize.height, targetHeight);
/* 38 */       int x = (targetWidth - width) / 2 + insets.left;
/* 39 */       int y = (targetHeight - height) / 2 + insets.bottom;
/*    */       
/* 41 */       component.setBounds(x, y, width, height);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\layout\CenterLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */