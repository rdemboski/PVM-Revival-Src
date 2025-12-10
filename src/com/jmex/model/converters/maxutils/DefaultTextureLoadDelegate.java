/*    */ package com.jmex.model.converters.maxutils;
/*    */ 
/*    */ import com.jme.image.Image;
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.image.Texture2D;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.util.TextureKey;
/*    */ import com.jme.util.TextureManager;
/*    */ import com.jme.util.resource.ResourceLocatorTool;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultTextureLoadDelegate
/*    */   implements TextureLoadDelegate
/*    */ {
/*    */   public Texture createTexture(String modelPath, TextureChunk tc) {
/* 18 */     Texture2D texture2D = new Texture2D();
/*    */     
/* 20 */     URL url = ResourceLocatorTool.locateResource("texture", tc.texName);
/*    */ 
/*    */     
/* 23 */     if (url != null) {
/* 24 */       texture2D.setImageLocation(url.toString());
/*    */     }
/*    */     
/* 27 */     texture2D.setTextureKey(new TextureKey(url, true, TextureManager.COMPRESS_BY_DEFAULT ? Image.Format.Guess : Image.Format.GuessNoCompression));
/*    */ 
/*    */     
/* 30 */     texture2D.setAnisotropicFilterPercent(0.0F);
/* 31 */     texture2D.setMinificationFilter(Texture.MinificationFilter.BilinearNearestMipMap);
/* 32 */     texture2D.setMagnificationFilter(Texture.MagnificationFilter.Bilinear);
/*    */     
/* 34 */     texture2D.setWrap(Texture.WrapMode.Repeat);
/* 35 */     float vScale = tc.vScale;
/* 36 */     float uScale = tc.uScale;
/* 37 */     if (uScale == 0.0F) {
/* 38 */       uScale = 1.0F;
/*    */     }
/* 40 */     if (vScale == 0.0F) {
/* 41 */       vScale = 1.0F;
/*    */     }
/* 43 */     texture2D.setScale(new Vector3f(uScale, vScale, 1.0F));
/* 44 */     return (Texture)texture2D;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\model\converters\maxutils\DefaultTextureLoadDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */