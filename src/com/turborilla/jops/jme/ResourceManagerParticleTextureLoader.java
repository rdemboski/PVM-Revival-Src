/*    */ package com.turborilla.jops.jme;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.jme.image.Texture;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ResourceManagerParticleTextureLoader
/*    */   implements ParticleTextureLoader {
/*    */   private static final Map<Object, Object> borderClampProps;
/*    */   
/*    */   static {
/* 15 */     HashMap<Object, Object> tmp = new HashMap<Object, Object>();
/*    */     
/* 17 */     tmp.put(Texture.ApplyMode.class, Texture.ApplyMode.Modulate);
/* 18 */     tmp.put(Texture.WrapMode.class, Texture.WrapMode.BorderClamp);
/*    */     
/* 20 */     borderClampProps = Collections.unmodifiableMap(tmp);
/*    */   }
/*    */   
/* 23 */   private String relativePath = "particles/jops/textures/";
/*    */   private ResourceManager resourceManager;
/*    */   
/*    */   public ResourceManagerParticleTextureLoader(ResourceManager resourceManager) {
/* 27 */     this.resourceManager = resourceManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public Texture loadTexture(String textureName) {
/* 32 */     return (Texture)this.resourceManager.getResource(Texture.class, this.relativePath + textureName, CacheType.CACHE_TEMPORARILY, borderClampProps);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\ResourceManagerParticleTextureLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */