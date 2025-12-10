/*    */ package com.jmex.model.converters.maxutils;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.math.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReducioLoadDelegate
/*    */   implements TextureLoadDelegate
/*    */ {
/*    */   private ResourceManager resourceManager;
/*    */   
/*    */   public ReducioLoadDelegate(ResourceManager resourceManager) {
/* 19 */     if (resourceManager == null)
/* 20 */       throw new IllegalArgumentException("resourceManager = null"); 
/* 21 */     this.resourceManager = resourceManager;
/*    */   }
/*    */   
/*    */   public Texture createTexture(String modelPath, TextureChunk tc) {
/* 25 */     Texture t = (Texture)this.resourceManager.getResource(Texture.class, convertToTextureName(modelPath), CacheType.CACHE_TEMPORARILY);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     float vScale = tc.vScale;
/* 33 */     float uScale = tc.uScale;
/* 34 */     if (uScale == 0.0F)
/* 35 */       uScale = 1.0F; 
/* 36 */     if (vScale == 0.0F) {
/* 37 */       vScale = 1.0F;
/*    */     }
/* 39 */     t.setScale(new Vector3f(uScale, vScale, 1.0F));
/* 40 */     return t;
/*    */   }
/*    */   
/*    */   private String convertToTextureName(String modelPath) {
/* 44 */     return modelPath.substring(0, modelPath.lastIndexOf('.')) + ".png";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\jmex\model\converters\maxutils\ReducioLoadDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */