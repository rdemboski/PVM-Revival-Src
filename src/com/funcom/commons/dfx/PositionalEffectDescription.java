/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ public class PositionalEffectDescription
/*    */   extends AbstractEffectDescription
/*    */ {
/*    */   private double scale;
/*    */   private String boneStr;
/*    */   private float[] offsetPos;
/*    */   private float[] offsetAngle;
/*    */   private boolean relativeScale;
/*    */   private boolean relativeOffset;
/*    */   
/*    */   public double getScale() {
/* 14 */     return this.scale;
/*    */   }
/*    */   
/*    */   public void setScale(double scale) {
/* 18 */     this.scale = scale;
/*    */   }
/*    */   
/*    */   public void setBone(String boneStr) {
/* 22 */     this.boneStr = boneStr;
/*    */   }
/*    */   
/*    */   public void setOffsetPos(float[] offsetPos) {
/* 26 */     this.offsetPos = offsetPos;
/*    */   }
/*    */   
/*    */   public void setOffsetAngle(float[] offsetAngle) {
/* 30 */     this.offsetAngle = offsetAngle;
/*    */   }
/*    */   
/*    */   public float[] getOffsetPos() {
/* 34 */     return this.offsetPos;
/*    */   }
/*    */   
/*    */   public String getBone() {
/* 38 */     return this.boneStr;
/*    */   }
/*    */   
/*    */   public boolean getRelativeScale() {
/* 42 */     return this.relativeScale;
/*    */   }
/*    */   
/*    */   public float[] getOffsetAngle() {
/* 46 */     return this.offsetAngle;
/*    */   }
/*    */   
/*    */   public void setRelativeScale(boolean relativeScale) {
/* 50 */     this.relativeScale = relativeScale;
/*    */   }
/*    */   
/*    */   public void setRelativeOffset(boolean relativeOffset) {
/* 54 */     this.relativeOffset = relativeOffset;
/*    */   }
/*    */   
/*    */   public boolean isRelativeOffset() {
/* 58 */     return this.relativeOffset;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\PositionalEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */