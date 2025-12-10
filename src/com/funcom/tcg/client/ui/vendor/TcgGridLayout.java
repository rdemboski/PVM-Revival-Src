/*    */ package com.funcom.tcg.client.ui.vendor;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.layout.GridLayout;
/*    */ import com.jmex.bui.util.Dimension;
/*    */ import com.jmex.bui.util.Insets;
/*    */ 
/*    */ public class TcgGridLayout
/*    */   extends GridLayout
/*    */ {
/*    */   public TcgGridLayout(int columns) {
/* 13 */     super(columns);
/*    */   }
/*    */   
/*    */   public TcgGridLayout(int columns, int horizontalGap, int verticalGap) {
/* 17 */     super(columns, horizontalGap, verticalGap);
/*    */   }
/*    */   
/*    */   public TcgGridLayout(int columns, int rows) {
/* 21 */     super(columns, rows);
/*    */   }
/*    */   
/*    */   public TcgGridLayout(int columns, int rows, int horizontalGap, int verticalGap) {
/* 25 */     super(columns, rows, horizontalGap, verticalGap);
/*    */   }
/*    */ 
/*    */   
/*    */   public Dimension computePreferredSize(BContainer target, int whint, int hhint) {
/* 30 */     Insets insets = target.getInsets();
/* 31 */     int currentRows = Math.max(this.rows, computeRowCount(target));
/* 32 */     int targetHeight = target.getParent().getHeight() - insets.getVertical();
/* 33 */     int currentHeight = targetHeight * currentRows / this.rows;
/* 34 */     return new Dimension(target.getWidth() - insets.getHorizontal(), currentHeight);
/*    */   }
/*    */ 
/*    */   
/*    */   public void layoutContainer(BContainer target) {
/* 39 */     if (target.getComponentCount() == 0) {
/*    */       return;
/*    */     }
/*    */     
/* 43 */     Insets insets = target.getInsets();
/*    */     
/* 45 */     int rows = this.rows;
/* 46 */     if (rows == 0) {
/* 47 */       rows = computeRowCount(target);
/*    */     }
/*    */     
/* 50 */     int compWidth = (target.getParent().getWidth() - insets.getHorizontal() - this.hgap) / this.columns;
/* 51 */     int compHeight = (target.getParent().getHeight() - insets.getHorizontal() - this.vgap) / rows;
/*    */     
/* 53 */     int x = insets.left;
/* 54 */     int y = target.getHeight() - insets.top - compHeight;
/* 55 */     int colNum = 0;
/* 56 */     for (int i = 0; i < target.getComponentCount(); i++) {
/* 57 */       BComponent component = target.getComponent(i);
/* 58 */       if (component.isVisible()) {
/*    */         
/* 60 */         component.setBounds(x, y, compWidth, compHeight);
/*    */         
/* 62 */         colNum++;
/* 63 */         if (colNum >= this.columns) {
/* 64 */           x = insets.left;
/* 65 */           y -= compHeight + this.vgap;
/* 66 */           colNum = 0;
/*    */         } else {
/* 68 */           x += compWidth + this.hgap;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\TcgGridLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */