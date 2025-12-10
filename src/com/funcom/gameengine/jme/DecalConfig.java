/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DecalConfig
/*    */ {
/*    */   public static final String FILENAME = "decalConfig.xml";
/*    */   private int layerId;
/* 10 */   private BlendMode blendMode = BlendMode.NORMAL;
/*    */   
/*    */   public int getLayerId() {
/* 13 */     return this.layerId;
/*    */   }
/*    */   
/*    */   public void setLayerId(int layerId) {
/* 17 */     this.layerId = layerId;
/*    */   }
/*    */   
/*    */   public BlendMode getBlendMode() {
/* 21 */     return this.blendMode;
/*    */   }
/*    */   
/*    */   public void setBlendMode(BlendMode blendMode) {
/* 25 */     this.blendMode = blendMode;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\DecalConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */