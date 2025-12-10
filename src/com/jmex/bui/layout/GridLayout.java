/*     */ package com.jmex.bui.layout;
/*     */ 
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import com.jmex.bui.util.Insets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GridLayout
/*     */   extends BLayoutManager
/*     */ {
/*     */   protected int columns;
/*     */   protected int rows;
/*     */   protected int hgap;
/*     */   protected int vgap;
/*     */   
/*     */   public GridLayout(int columns) {
/*  21 */     this(columns, 0, 0);
/*     */   }
/*     */   
/*     */   public GridLayout(int columns, int horizontalGap, int verticalGap) {
/*  25 */     this(columns, 0, horizontalGap, verticalGap);
/*     */   }
/*     */   
/*     */   public GridLayout(int columns, int rows) {
/*  29 */     this(columns, rows, 0, 0);
/*     */   }
/*     */   
/*     */   public GridLayout(int columns, int rows, int horizontalGap, int verticalGap) {
/*  33 */     if (columns < 1) {
/*  34 */       throw new IllegalArgumentException("Number of columns cannot be 0 or less.");
/*     */     }
/*     */     
/*  37 */     this.columns = columns;
/*  38 */     this.rows = rows;
/*  39 */     this.hgap = horizontalGap;
/*  40 */     this.vgap = verticalGap;
/*     */   }
/*     */   
/*     */   public Dimension computePreferredSize(BContainer target, int whint, int hhint) {
/*  44 */     Insets insets = target.getInsets();
/*  45 */     return new Dimension(target.getWidth() - insets.getHorizontal(), target.getHeight() - insets.getVertical());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layoutContainer(BContainer target) {
/*  72 */     if (target.getComponentCount() == 0) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     Insets insets = target.getInsets();
/*     */     
/*  78 */     int rows = this.rows;
/*  79 */     if (rows == 0) {
/*  80 */       rows = computeRowCount(target);
/*     */     }
/*     */     
/*  83 */     int compWidth = (target.getWidth() - insets.getHorizontal() - this.hgap) / this.columns;
/*  84 */     int compHeight = (target.getHeight() - insets.getHorizontal() - this.vgap) / rows;
/*     */     
/*  86 */     int x = insets.left;
/*  87 */     int y = target.getHeight() - insets.top - compHeight;
/*  88 */     int colNum = 0;
/*  89 */     for (int i = 0; i < target.getComponentCount(); i++) {
/*  90 */       BComponent component = target.getComponent(i);
/*  91 */       component.setBounds(x, y, compWidth, compHeight);
/*     */       
/*  93 */       colNum++;
/*  94 */       if (colNum >= this.columns) {
/*  95 */         x = insets.left;
/*  96 */         y -= compHeight + this.vgap;
/*  97 */         colNum = 0;
/*     */       } else {
/*  99 */         x += compWidth + this.hgap;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int computeRowCount(BContainer target) {
/* 105 */     int count = target.getComponentCount();
/* 106 */     int rows = count / this.columns;
/* 107 */     if (count % this.columns > 0) {
/* 108 */       rows++;
/*     */     }
/* 110 */     return rows;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\layout\GridLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */