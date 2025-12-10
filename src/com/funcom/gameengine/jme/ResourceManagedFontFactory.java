/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.state.BlendState;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import com.jme.scene.state.TextureState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import com.jmex.font2d.Font2D;
/*    */ import com.jmex.font2d.Text2D;
/*    */ import com.jmex.font3d.JmeText;
/*    */ 
/*    */ public class ResourceManagedFontFactory extends Font2D {
/*    */   private TextureState fontTextureState;
/*    */   
/*    */   public ResourceManagedFontFactory(ResourceGetter resourceGetter, String resourceManagerReference) {
/* 18 */     this.resourceManagerReference = resourceManagerReference;
/* 19 */     this.fontTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 20 */     this.fontTextureState.setTexture(resourceGetter.getTexture(resourceManagerReference, CacheType.CACHE_PERMANENTLY));
/* 21 */     this.fontTextureState.setEnabled(true);
/*    */   }
/*    */   private String resourceManagerReference;
/*    */   public TextureState getFontTextureState() {
/* 25 */     return this.fontTextureState;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFontBitmapFile() {
/* 31 */     return this.resourceManagerReference;
/*    */   }
/*    */   
/*    */   public Text2D createText(String text, float size, int flags) {
/* 35 */     Text2D textObj = new Text2D(this, text, size, flags);
/* 36 */     textObj.setCullHint(Spatial.CullHint.Never);
/* 37 */     textObj.setRenderState((RenderState)this.fontTextureState);
/* 38 */     textObj.setRenderState((RenderState)getFontBlend());
/* 39 */     textObj.setTextureCombineMode(Spatial.TextureCombineMode.Replace);
/* 40 */     textObj.setLightCombineMode(Spatial.LightCombineMode.Off);
/* 41 */     return textObj;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private BlendState getFontBlend() {
/* 48 */     BlendState blendState = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/* 49 */     blendState.setBlendEnabled(true);
/* 50 */     blendState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
/* 51 */     blendState.setDestinationFunction(BlendState.DestinationFunction.One);
/* 52 */     blendState.setTestEnabled(true);
/* 53 */     blendState.setTestFunction(BlendState.TestFunction.GreaterThan);
/* 54 */     return blendState;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\ResourceManagedFontFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */