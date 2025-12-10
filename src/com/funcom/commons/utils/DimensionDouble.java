/*    */ package com.funcom.commons.utils;
/*    */ 
/*    */ import java.awt.geom.Dimension2D;
/*    */ 
/*    */ public class DimensionDouble
/*    */   extends Dimension2D {
/*    */   private double width;
/*    */   private double height;
/*    */   
/*    */   public DimensionDouble(double width, double height) {
/* 11 */     this.width = width;
/* 12 */     this.height = height;
/*    */   }
/*    */   
/*    */   public double getWidth() {
/* 16 */     return this.width;
/*    */   }
/*    */   
/*    */   public double getHeight() {
/* 20 */     return this.height;
/*    */   }
/*    */   
/*    */   public void setSize(double width, double height) {
/* 24 */     this.width = width;
/* 25 */     this.height = height;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\DimensionDouble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */