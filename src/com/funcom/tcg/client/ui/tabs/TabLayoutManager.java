/*    */ package com.funcom.tcg.client.ui.tabs;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.BCustomToggleButton;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.util.Dimension;
/*    */ import com.jmex.bui.util.Insets;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TabLayoutManager
/*    */   extends BLayoutManager
/*    */ {
/*    */   public Dimension computePreferredSize(BContainer target, int whint, int hhint) {
/* 25 */     Dimension result = new Dimension(0, hhint);
/* 26 */     int leftGuide = calculateLeftGuide(target);
/* 27 */     for (int i = 0; i < target.getComponentCount(); i++) {
/* 28 */       BComponent comp = target.getComponent(i);
/* 29 */       Dimension preferredSize = comp.getPreferredSize(whint, hhint);
/* 30 */       int width = leftGuide + preferredSize.width - comp.getInsets().getHorizontal();
/* 31 */       result.width = Math.max(result.width, width);
/*    */     } 
/* 33 */     return result;
/*    */   }
/*    */   
/*    */   public void layoutContainer(BContainer target) {
/* 37 */     if (target.getComponentCount() == 0) {
/*    */       return;
/*    */     }
/*    */     
/* 41 */     Insets targetInsets = target.getInsets();
/* 42 */     int top = target.getHeight() - targetInsets.top;
/* 43 */     int indent = targetInsets.bottom;
/* 44 */     int leftGuide = calculateLeftGuide(target);
/*    */     
/* 46 */     for (int i = 0; i < target.getComponentCount(); i++) {
/* 47 */       BCustomToggleButton comp = (BCustomToggleButton)target.getComponent(i);
/* 48 */       if (comp.isSelected()) {
/* 49 */         top += indent;
/*    */       }
/*    */       
/* 52 */       Insets compInsets = comp.getInsets();
/* 53 */       Dimension preferredSize = comp.getPreferredSize(target.getWidth(), target.getHeight());
/* 54 */       int width = preferredSize.width;
/* 55 */       int height = preferredSize.height;
/* 56 */       int left = leftGuide - compInsets.left;
/* 57 */       comp.setBounds(left, top - height, width, height);
/* 58 */       top -= height;
/*    */       
/* 60 */       if (comp.isSelected()) {
/* 61 */         top += indent;
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private int calculateLeftGuide(BContainer target) {
/* 67 */     int leftGuide = 0;
/* 68 */     for (int i = 0; i < target.getComponentCount(); i++) {
/* 69 */       BComponent comp = target.getComponent(i);
/* 70 */       leftGuide = Math.max(leftGuide, (comp.getInsets()).left);
/*    */     } 
/* 72 */     return leftGuide;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\tabs\TabLayoutManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */