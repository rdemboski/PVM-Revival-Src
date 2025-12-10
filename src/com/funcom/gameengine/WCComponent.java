/*    */ package com.funcom.gameengine;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WCComponent
/*    */   extends Number
/*    */ {
/*    */   private int tileCoord;
/*    */   private double tileOffset;
/*    */   
/*    */   public WCComponent(int tileCoord, double tileOffset) {
/* 17 */     this.tileCoord = tileCoord;
/* 18 */     this.tileOffset = tileOffset;
/*    */   }
/*    */   
/*    */   public WCComponent(WCComponent spinnerWCModel) {
/* 22 */     this.tileCoord = spinnerWCModel.tileCoord;
/* 23 */     this.tileOffset = spinnerWCModel.tileOffset;
/*    */   }
/*    */ 
/*    */   
/*    */   public WCComponent() {}
/*    */ 
/*    */   
/*    */   public WCComponent(BigDecimal bigDecimalValue) {
/* 31 */     this.tileCoord = bigDecimalValue.intValue();
/* 32 */     BigDecimal remainder = bigDecimalValue.remainder(new BigDecimal("1"));
/* 33 */     this.tileOffset = remainder.doubleValue();
/*    */   }
/*    */   
/*    */   public int getTileCoord() {
/* 37 */     return this.tileCoord;
/*    */   }
/*    */   
/*    */   public void setTileCoord(int tileCoord) {
/* 41 */     this.tileCoord = tileCoord;
/*    */   }
/*    */   
/*    */   public double getTileOffset() {
/* 45 */     return this.tileOffset;
/*    */   }
/*    */   
/*    */   public void setTileOffset(double tileOffset) {
/* 49 */     this.tileOffset = tileOffset;
/*    */   }
/*    */   
/*    */   public int intValue() {
/* 53 */     return this.tileCoord;
/*    */   }
/*    */   
/*    */   public long longValue() {
/* 57 */     return this.tileCoord;
/*    */   }
/*    */   
/*    */   public float floatValue() {
/* 61 */     return (float)(this.tileCoord + this.tileOffset);
/*    */   }
/*    */   
/*    */   public double doubleValue() {
/* 65 */     return this.tileCoord + this.tileOffset;
/*    */   }
/*    */   
/*    */   public WCComponent add(WCComponent wCComponent1) {
/* 69 */     this.tileCoord += wCComponent1.getTileCoord();
/* 70 */     this.tileOffset += wCComponent1.getTileOffset();
/* 71 */     return this;
/*    */   }
/*    */   
/*    */   public WCComponent sub(WCComponent wCComponent1) {
/* 75 */     this.tileCoord -= wCComponent1.getTileCoord();
/* 76 */     this.tileOffset -= wCComponent1.getTileOffset();
/* 77 */     return this;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 81 */     if (!(obj instanceof WCComponent))
/* 82 */       return false; 
/* 83 */     WCComponent other = (WCComponent)obj;
/* 84 */     if (other.intValue() == intValue() && other.floatValue() == floatValue())
/* 85 */       return true; 
/* 86 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\gameengine\WCComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */