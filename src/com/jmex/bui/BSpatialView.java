/*     */ package com.jmex.bui;
/*     */ 
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.image.Texture2D;
/*     */ import com.jme.light.DirectionalLight;
/*     */ import com.jme.light.Light;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.Camera;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.renderer.TextureRenderer;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TriMesh;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.scene.state.LightState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.Timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BSpatialView
/*     */   extends BComponent
/*     */ {
/*     */   private static final int TEXTURE_WIDTH = 256;
/*     */   private static final int TEXTURE_HEIGHT = 256;
/*     */   private static final float DEFAULT_ROTATION_ANGLE = 0.09817477F;
/*     */   private Spatial spatial;
/*     */   private TextureRenderer textureRenderer;
/*     */   private Texture2D renderedTexture;
/*     */   private Quad componentQuad;
/*     */   private boolean rendering;
/*     */   
/*     */   public BSpatialView(Spatial spatial) {
/* 117 */     this.rendering = false; initialize(spatial); } private void initialize(Spatial spatial) { this.spatial = spatial; LightState ls = DisplaySystem.getDisplaySystem().getRenderer().createLightState(); ls.attach(light()); ls.setGlobalAmbient(new ColorRGBA(0.9F, 0.9F, 0.9F, 1.0F)); spatial.setRenderState((RenderState)ls); spatial.updateRenderState(); this.renderedTexture = new Texture2D(); this.textureRenderer = DisplaySystem.getDisplaySystem().createTextureRenderer(256, 256, TextureRenderer.Target.Texture2D); this.textureRenderer.setBackgroundColor(ColorRGBA.darkGray); this.textureRenderer.setupTexture(this.renderedTexture); centerCamera(); this.textureRenderer.getCamera().update(); recreateQuad(); } public BSpatialView(String componentName, Spatial spatial) { super(componentName); this.rendering = false; initialize(spatial); }
/*     */   private void centerCamera() { BoundingBox box = new BoundingBox(); this.spatial.setModelBound((BoundingVolume)box); this.spatial.updateModelBound(); box = getBoundingBox(box); float x = this.textureRenderer.getCamera().getFrustumRight() / this.textureRenderer.getCamera().getFrustumNear();
/*     */     float y = this.textureRenderer.getCamera().getFrustumTop() / this.textureRenderer.getCamera().getFrustumNear();
/*     */     float m = Math.max(box.yExtent / y, box.xExtent / x);
/* 121 */     this.textureRenderer.getCamera().setLocation(new Vector3f(box.getCenter().getX(), box.getCenter().getY(), box.getCenter().getZ() + box.zExtent + m)); } protected void renderComponent(Renderer renderer) { if (this.rendering)
/*     */       return; 
/* 123 */     renderer.draw((TriMesh)this.componentQuad);
/* 124 */     invalidate(); } private BoundingBox getBoundingBox(BoundingBox box) { if (this.spatial instanceof Node) { for (Spatial child : ((Node)this.spatial).getChildren()) {
/*     */         if (box.getVolume() == 0.0F)
/*     */           box = (BoundingBox)child.getWorldBound();  box = (BoundingBox)box.mergeLocal(child.getWorldBound());
/*     */       }  }
/*     */     else
/*     */     { box = (BoundingBox)this.spatial.getWorldBound(); }
/*     */      return box; }
/*     */   private void recreateQuad() { this.componentQuad = new Quad("component-quad", this._width, this._height); this.componentQuad.setLocalTranslation((this._width / 2), (this._height / 2), 0.0F); TextureState state = DisplaySystem.getDisplaySystem().getRenderer().createTextureState(); this.componentQuad.setRenderState((RenderState)state); this.componentQuad.updateRenderState(); }
/*     */   public void setBounds(int i, int i1, int i2, int i3) { super.setBounds(i, i1, i2, i3); recreateQuad(); updateTexture(); }
/* 133 */   private void updateTexture() { this.rendering = true;
/* 134 */     this.spatial.updateGeometricState(Timer.getTimer().getTimePerFrame(), true);
/* 135 */     this.textureRenderer.render(this.spatial, (Texture)this.renderedTexture);
/*     */     
/* 137 */     TextureState textureState = (TextureState)this.componentQuad.getRenderState(5);
/* 138 */     textureState.setTexture((Texture)this.renderedTexture);
/* 139 */     this.rendering = false; }
/*     */ 
/*     */ 
/*     */   
/*     */   private Light light() {
/* 144 */     DirectionalLight dlight = new DirectionalLight();
/* 145 */     dlight.setDirection(new Vector3f(-1.0F, -1.0F, -1.0F));
/* 146 */     dlight.setDiffuse(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
/* 147 */     dlight.setAmbient(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
/* 148 */     dlight.setSpecular(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
/* 149 */     dlight.setEnabled(true);
/* 150 */     return (Light)dlight;
/*     */   }
/*     */   
/*     */   public void setCamera(Camera camera) {
/* 154 */     this.textureRenderer.setCamera(camera);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BSpatialView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */