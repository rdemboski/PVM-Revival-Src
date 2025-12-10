/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ public class GUIParticleDescription
/*    */   extends AbstractEffectDescription {
/*    */   private String referencePoint;
/*    */   private int x;
/*    */   private int y;
/*    */   private boolean cacheDFX;
/*    */   private String name;
/*    */   
/*    */   public void setReferencePoint(String referencePoint) {
/* 12 */     this.referencePoint = referencePoint;
/*    */   }
/*    */   
/*    */   public void setOffset(int x, int y) {
/* 16 */     this.x = x;
/* 17 */     this.y = y;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 21 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 25 */     return this.y;
/*    */   }
/*    */   
/*    */   public String getReferencePoint() {
/* 29 */     return this.referencePoint;
/*    */   }
/*    */   
/*    */   public void setDFXCache(boolean cacheDFX) {
/* 33 */     this.cacheDFX = cacheDFX;
/*    */   }
/*    */   
/*    */   public boolean isCacheDFX() {
/* 37 */     return this.cacheDFX;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 41 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 45 */     this.name = name;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\GUIParticleDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */