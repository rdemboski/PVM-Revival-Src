/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.jme.md5importer.resource.mesh.Mesh;
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.scene.TriMesh;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import com.jme.scene.state.TextureState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class TextureLoaderManager
/*    */ {
/* 16 */   private final Map<String, TextureLoader> loaders = new HashMap<String, TextureLoader>();
/*    */   
/*    */   public void registerLoader(String id, TextureLoader loader) {
/* 19 */     this.loaders.put(id, loader);
/*    */   }
/*    */   
/*    */   public void replaceTexture(Mesh[] meshes, ModularDescription.Part part, Map<Object, Object> runtimeParams) {
/* 23 */     if (usesTextureLoaders(part)) {
/* 24 */       for (Mesh mesh : meshes) {
/*    */         
/* 26 */         Map<String, ModularDescription.TextureLoaderDescription> loaderDescriptions = mapLoaderDescriptions(part);
/*    */ 
/*    */         
/* 29 */         String shaderId = mesh.getTexture();
/* 30 */         ModularDescription.TextureLoaderDescription loaderDescription = loaderDescriptions.get(shaderId);
/* 31 */         if (loaderDescription == null) {
/* 32 */           throw new RuntimeException("Texture not foud for shader: " + shaderId);
/*    */         }
/*    */         
/* 35 */         TextureLoader loader = this.loaders.get(loaderDescription.getId());
/* 36 */         if (loader == null) {
/* 37 */           throw new RuntimeException("Loader not foud for description: " + loaderDescription.getId() + "\n" + "shaderId: " + shaderId);
/*    */         }
/* 39 */         TextureState textureState = ensureTextureState(mesh.getTrimesh());
/* 40 */         Texture texture = loader.load(shaderId, loaderDescription.getParams(), runtimeParams);
/* 41 */         textureState.setTexture(texture);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   private Map<String, ModularDescription.TextureLoaderDescription> mapLoaderDescriptions(ModularDescription.Part part) {
/* 47 */     Map<String, ModularDescription.TextureLoaderDescription> ret = new HashMap<String, ModularDescription.TextureLoaderDescription>();
/* 48 */     for (ModularDescription.TexturePart texturePart : part.getTextureParts()) {
/* 49 */       if (texturePart.getTextureLoaderDescription() != null) {
/* 50 */         ret.put(texturePart.getTextureMap(), texturePart.getTextureLoaderDescription());
/*    */       }
/*    */     } 
/* 53 */     return ret;
/*    */   }
/*    */   
/*    */   private TextureState ensureTextureState(TriMesh triMesh) {
/* 57 */     TextureState textureState = (TextureState)triMesh.getRenderState(5);
/* 58 */     if (textureState == null) {
/* 59 */       textureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 60 */       triMesh.setRenderState((RenderState)textureState);
/*    */     } 
/* 62 */     return textureState;
/*    */   }
/*    */   
/*    */   private boolean usesTextureLoaders(ModularDescription.Part part) {
/* 66 */     for (ModularDescription.TexturePart texturePart : part.getTextureParts()) {
/* 67 */       if (texturePart.getTextureLoaderDescription() != null) {
/* 68 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TextureLoaderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */