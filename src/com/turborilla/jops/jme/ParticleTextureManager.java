/*    */ package com.turborilla.jops.jme;
/*    */ 
/*    */ import com.funcom.commons.utils.ApplicationRelativePathUtil;
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.util.TextureManager;
/*    */ import com.jme.util.resource.ResourceLocator;
/*    */ import com.jme.util.resource.ResourceLocatorTool;
/*    */ import com.jme.util.resource.SimpleResourceLocator;
/*    */ import java.io.File;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ParticleTextureManager
/*    */   implements ParticleTextureLoader {
/* 15 */   static Map<String, Texture> map = new HashMap<String, Texture>();
/*    */   
/*    */   static {
/* 18 */     ResourceLocatorTool.addResourceLocator("texture", (ResourceLocator)new SimpleResourceLocator((new File(ApplicationRelativePathUtil.getApplicationRelativeResourcePath(System.getProperty("tcg.resourcepath") + "/particles/jops/textures"))).toURI()));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void clearCache() {
/* 23 */     for (Texture texture : map.values()) {
/* 24 */       TextureManager.deleteTextureFromCard(texture);
/* 25 */       TextureManager.releaseTexture(texture.getTextureKey());
/*    */     } 
/* 27 */     map.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public Texture loadTexture(String textureName) {
/* 32 */     Texture tex = map.get(textureName);
/*    */     
/* 34 */     if (tex == null) {
/* 35 */       tex = _loadTexture(textureName);
/* 36 */       map.put(textureName, tex);
/*    */     } 
/* 38 */     return tex;
/*    */   }
/*    */ 
/*    */   
/*    */   private Texture _loadTexture(String textureName) {
/* 43 */     Texture tex = TextureManager.loadTexture(ResourceLocatorTool.locateResource("texture", textureName), Texture.MinificationFilter.BilinearNearestMipMap, Texture.MagnificationFilter.Bilinear);
/*    */ 
/*    */ 
/*    */     
/* 47 */     tex.setApply(Texture.ApplyMode.Modulate);
/* 48 */     tex.setWrap(Texture.WrapMode.BorderClamp);
/* 49 */     return tex;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\ParticleTextureManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */