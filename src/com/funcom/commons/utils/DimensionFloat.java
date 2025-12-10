/*    */ package com.funcom.commons.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DimensionFloat
/*    */ {
/*    */   private float width;
/*    */   private float height;
/*    */   
/*    */   public DimensionFloat(float width, float height) {
/* 11 */     this.width = width;
/* 12 */     this.height = height;
/*    */   }
/*    */   
/*    */   public float getWidth() {
/* 16 */     return this.width;
/*    */   }
/*    */   
/*    */   public float getHeight() {
/* 20 */     return this.height;
/*    */   }
/*    */   
/*    */   public void setSize(float width, float height) {
/* 24 */     this.width = width;
/* 25 */     this.height = height;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 29 */     StringBuilder sb = new StringBuilder();
/* 30 */     sb.append(DimensionFloat.class.getName()).append("@").append(hashCode());
/* 31 */     sb.append("{ ");
/* 32 */     sb.append("width: ").append(this.width);
/* 33 */     sb.append("height: ").append(this.height);
/* 34 */     sb.append("};");
/* 35 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\DimensionFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */