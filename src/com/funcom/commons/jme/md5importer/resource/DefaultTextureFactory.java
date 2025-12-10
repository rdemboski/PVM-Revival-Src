/*    */ package com.funcom.commons.jme.md5importer.resource;
/*    */ 
/*    */ import com.funcom.commons.jme.md5importer.MD5Config;
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.scene.state.TextureState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import com.jme.util.TextureManager;
/*    */ import com.jme.util.resource.ResourceLocatorTool;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class DefaultTextureFactory
/*    */   implements TextureFactory
/*    */ {
/*    */   public TextureState createTexture(String resourceName, String[] extensions, Texture.MinificationFilter minFilter, Texture.MagnificationFilter magFilter, float anisoLevel, boolean flipped) {
/* 15 */     URL url = null;
/* 16 */     for (int i = 0; i < extensions.length && url == null; i++) {
/* 17 */       url = ResourceLocatorTool.locateResource("texture", resourceName + extensions[i]);
/*    */     }
/*    */     
/* 20 */     Texture texture = TextureManager.loadTexture(url, minFilter, magFilter, anisoLevel, flipped);
/* 21 */     if (MD5Config.textureWrapping)
/* 22 */       texture.setWrap(Texture.WrapMode.Repeat); 
/* 23 */     TextureState state = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 24 */     state.setTexture(texture);
/* 25 */     return state;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\resource\DefaultTextureFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */