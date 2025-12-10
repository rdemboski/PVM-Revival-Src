/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ public class ParticleEffectDescription
/*    */   extends PositionalEffectDescription
/*    */ {
/*    */   private boolean disconnectParticles;
/*    */   private boolean disconnectEmitter;
/*    */   private boolean killInstantly;
/*    */   private boolean cache;
/*    */   
/*    */   public boolean isKillInstantly() {
/* 12 */     return this.killInstantly;
/*    */   }
/*    */   
/*    */   public void setKillInstantly(boolean killInstantly) {
/* 16 */     this.killInstantly = killInstantly;
/*    */   }
/*    */   
/*    */   public void setDisconnectParticles(boolean disconnectParticles) {
/* 20 */     this.disconnectParticles = disconnectParticles;
/*    */   }
/*    */   
/*    */   public boolean isDisconnectParticles() {
/* 24 */     return this.disconnectParticles;
/*    */   }
/*    */   
/*    */   public void setDisconnectEmitter(boolean disconnectEmitter) {
/* 28 */     this.disconnectEmitter = disconnectEmitter;
/*    */   }
/*    */   
/*    */   public boolean isDisconnectEmitter() {
/* 32 */     return this.disconnectEmitter;
/*    */   }
/*    */   
/*    */   public boolean isCache() {
/* 36 */     return this.cache;
/*    */   }
/*    */   
/*    */   public void setCache(boolean cache) {
/* 40 */     this.cache = cache;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\ParticleEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */