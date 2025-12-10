/*    */ package com.turborilla.jops.jme;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleTextureLoaderInstance
/*    */ {
/*    */   private static ParticleTextureLoader loader;
/*    */   
/*    */   public static ParticleTextureLoader getInstance() {
/* 10 */     return loader;
/*    */   }
/*    */   
/*    */   public static void setLoader(ParticleTextureLoader loader) {
/* 14 */     ParticleTextureLoaderInstance.loader = loader;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\ParticleTextureLoaderInstance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */