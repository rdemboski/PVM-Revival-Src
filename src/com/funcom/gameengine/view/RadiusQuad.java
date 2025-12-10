/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.jme.bounding.BoundingBox;
/*    */ import com.jme.bounding.BoundingVolume;
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.math.Vector2f;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.TexCoords;
/*    */ import com.jme.scene.shape.Quad;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import com.jme.scene.state.TextureState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import com.jme.util.geom.BufferUtils;
/*    */ 
/*    */ public class RadiusQuad extends Quad {
/* 18 */   private double oldRadius = Double.MAX_VALUE;
/*    */ 
/*    */   
/* 21 */   private static final Vector3f[] TILE_NORMALS = new Vector3f[] { new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   private static final ColorRGBA[] COLORS = new ColorRGBA[] { new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F), new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F), new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F), new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   private static final Vector2f[] TEX_COORDS = new Vector2f[] { new Vector2f(0.0F, 1.0F), new Vector2f(0.0F, 0.0F), new Vector2f(1.0F, 1.0F), new Vector2f(1.0F, 0.0F) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   private static final int[] INDEXES = new int[] { 0, 1, 2, 2, 1, 3 };
/*    */ 
/*    */ 
/*    */   
/*    */   public RadiusQuad(float radius, Texture radiusTexture) {
/* 48 */     super("RadiusQuad");
/* 49 */     setModelBound((BoundingVolume)new BoundingBox());
/* 50 */     setRadius(radius);
/*    */ 
/*    */     
/* 53 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*    */     
/* 55 */     ts.setEnabled(true);
/*    */     
/* 57 */     ts.setTexture(radiusTexture, 0);
/*    */     
/* 59 */     setRenderState((RenderState)ts);
/* 60 */     setRenderState((RenderState)TransparentAlphaState.get());
/*    */   }
/*    */   
/*    */   public Class<? extends Spatial> getClassTag() {
/* 64 */     return (Class)getClass();
/*    */   }
/*    */   
/*    */   public void setRadius(float radius) {
/* 68 */     float x = 0.0F;
/* 69 */     float y = 0.0F;
/*    */ 
/*    */     
/* 72 */     radius /= this.worldScale.x;
/*    */ 
/*    */     
/* 75 */     Vector3f[] tileVertexes = { new Vector3f(x - radius, 0.1F, y - radius), new Vector3f(x - radius, 0.1F, y + radius), new Vector3f(x + radius, 0.1F, y - radius), new Vector3f(x + radius, 0.1F, y + radius) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     reconstruct(BufferUtils.createFloatBuffer(tileVertexes), BufferUtils.createFloatBuffer(TILE_NORMALS), BufferUtils.createFloatBuffer(COLORS), new TexCoords(BufferUtils.createFloatBuffer(TEX_COORDS)), BufferUtils.createIntBuffer(INDEXES));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 88 */     updateRenderState();
/* 89 */     updateModelBound();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\RadiusQuad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */