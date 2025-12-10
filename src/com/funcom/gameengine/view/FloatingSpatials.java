/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.jme.InGameFontFactory;
/*     */ import com.funcom.gameengine.jme.TextSpatial;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.TextWithIcons;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FloatingSpatials
/*     */   extends Node
/*     */   implements Updated
/*     */ {
/*     */   protected List<SpatialAndTime> spatials;
/*     */   private InGameFontFactory inGameFontfactory;
/*     */   protected float arriveingDuration;
/*     */   protected float stableDuration;
/*     */   protected float dissapearingDuration;
/*     */   private float size;
/*     */   private float defaultScale;
/*     */   private Vector2f displacer;
/*     */   private boolean centerText;
/*     */   private ResourceManager resourceManager;
/*     */   
/*     */   public FloatingSpatials(ResourceManager resourceManager, String fontName) {
/*  43 */     super(FloatingSpatials.class.getSimpleName());
/*  44 */     this.resourceManager = resourceManager;
/*     */     
/*  46 */     this.spatials = new LinkedList<SpatialAndTime>();
/*  47 */     this.inGameFontfactory = new InGameFontFactory(resourceManager, fontName);
/*     */     
/*  49 */     this.arriveingDuration = 0.0F;
/*  50 */     this.stableDuration = 0.7F;
/*  51 */     this.dissapearingDuration = 0.3F;
/*  52 */     this.size = 3.5F;
/*  53 */     this.defaultScale = 3.5F;
/*  54 */     this.displacer = new Vector2f(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setParent(Node parent) {
/*  59 */     super.setParent(parent);
/*  60 */     if (parent == null)
/*     */     {
/*  62 */       detachAllChildren();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void showCombinedSpatialsAtScreenCoordinate(String text, ColorRGBA color, Vector3f screenCoords, float scale) {
/*  67 */     SpatialRow spatialRow = new SpatialRow();
/*  68 */     TextWithIcons textWithIcons = new TextWithIcons(text);
/*     */ 
/*     */     
/*  71 */     while (textWithIcons.hasNext()) {
/*  72 */       Spatial toAdd = null;
/*     */       
/*  74 */       if (textWithIcons.getNextType() == TextWithIcons.TokenType.IMAGE) {
/*  75 */         String string = textWithIcons.getNext();
/*  76 */         Quad quad = createQuadObject(scale, string, getTextureState(string));
/*  77 */       } else if (textWithIcons.getNextType() == TextWithIcons.TokenType.TEXT) {
/*  78 */         toAdd = createText2DObject(color, scale, textWithIcons.getNext());
/*     */       } 
/*     */       
/*  81 */       if (toAdd != null) {
/*  82 */         spatialRow.add(toAdd);
/*     */       }
/*     */     } 
/*     */     
/*  86 */     showSpatialsAtScreenCoordinate(spatialRow, screenCoords);
/*     */   }
/*     */   
/*     */   private Spatial createText2DObject(ColorRGBA color, float scale, String stringToken) {
/*  90 */     TextSpatial textSpatial = this.inGameFontfactory.createText(stringToken, color, scale);
/*  91 */     textSpatial.setAlpha(0.0F);
/*  92 */     textSpatial.setLocalScale(scale * 5.0F);
/*     */     
/*  94 */     return (Spatial)textSpatial;
/*     */   }
/*     */   
/*     */   private TextureState getTextureState(String stringToken) {
/*  98 */     Texture resource = (Texture)this.resourceManager.getResource(Texture.class, stringToken, CacheType.CACHE_TEMPORARILY);
/*  99 */     resource.setCombineFuncAlpha(Texture.CombinerFunctionAlpha.Add);
/* 100 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 101 */     ts.setTexture(resource);
/* 102 */     return ts;
/*     */   }
/*     */   
/*     */   private Quad createQuadObject(float scale, String stringToken, TextureState ts) {
/* 106 */     Quad propQuad = new Quad(stringToken, 16.0F, 16.0F);
/* 107 */     propQuad.setRenderState((RenderState)ts);
/* 108 */     propQuad.setLocalScale(scale);
/* 109 */     propQuad.setRenderQueueMode(4);
/* 110 */     propQuad.setLightCombineMode(Spatial.LightCombineMode.Off);
/* 111 */     (propQuad.getDefaultColor()).a = 0.0F;
/* 112 */     return propQuad;
/*     */   }
/*     */ 
/*     */   
/*     */   public int attachChild(Spatial child) {
/* 117 */     return attachChild(child, new SpatialAndTime(0.0F, child));
/*     */   }
/*     */   
/*     */   protected int attachChild(Spatial child, SpatialAndTime spatialandTime) {
/* 121 */     this.spatials.add(spatialandTime);
/* 122 */     return super.attachChild(child);
/*     */   }
/*     */   
/*     */   protected void showSpatialsAtScreenCoordinate(SpatialRow spatialRow, Vector3f screenCoords) {
/* 126 */     spatialRow.layout(screenCoords, this);
/*     */   }
/*     */   
/*     */   protected void modifyWithRandomDisplacement(Vector3f screenCoords) {
/* 130 */     screenCoords.x += FastMath.rand.nextInt(50) - 25.0F;
/* 131 */     screenCoords.y += FastMath.rand.nextInt(50) - 25.0F;
/*     */   }
/*     */   
/*     */   void modifyWithDisplacer(Vector3f screenCoords) {
/* 135 */     screenCoords.x += this.displacer.x;
/* 136 */     screenCoords.y += this.displacer.y;
/*     */   }
/*     */   
/*     */   public void update(float time) {
/* 140 */     Iterator<SpatialAndTime> it = this.spatials.iterator();
/* 141 */     while (it.hasNext()) {
/* 142 */       SpatialAndTime spatialAndTime = it.next();
/* 143 */       spatialAndTime.time += time;
/*     */       
/* 145 */       if (spatialAndTime.isArriving()) {
/* 146 */         modifyTranslucencyArriving(spatialAndTime); continue;
/* 147 */       }  if (spatialAndTime.isAlive()) {
/* 148 */         if (spatialAndTime.spatial instanceof TextSpatial) {
/* 149 */           ((TextSpatial)spatialAndTime.spatial).setAlpha(1.0F); continue;
/* 150 */         }  if (spatialAndTime.spatial instanceof Quad)
/* 151 */           (((Quad)spatialAndTime.spatial).getDefaultColor()).a = 1.0F;  continue;
/*     */       } 
/* 153 */       if (spatialAndTime.isDying()) {
/* 154 */         modifyTranslucencyDying(spatialAndTime); continue;
/*     */       } 
/* 156 */       it.remove();
/* 157 */       removeSpatial(spatialAndTime.spatial);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeSpatial(Spatial spatial) {
/* 164 */     detachChild(spatial);
/*     */   }
/*     */   
/*     */   public void removeAll() {
/* 168 */     for (SpatialAndTime spatialAndTime : this.spatials) {
/* 169 */       removeSpatial(spatialAndTime.spatial);
/*     */     }
/* 171 */     this.spatials.clear();
/*     */   }
/*     */   
/*     */   private void modifyTranslucencyArriving(SpatialAndTime spatialAndTime) {
/* 175 */     float newTranslucency = spatialAndTime.time / this.arriveingDuration;
/* 176 */     if (spatialAndTime.spatial instanceof TextSpatial) {
/* 177 */       ((TextSpatial)spatialAndTime.spatial).setAlpha(newTranslucency);
/* 178 */     } else if (spatialAndTime.spatial instanceof Quad) {
/* 179 */       (((Quad)spatialAndTime.spatial).getDefaultColor()).a = newTranslucency;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void modifyTranslucencyDying(SpatialAndTime spatialAndTime) {
/* 184 */     float newTranslucency = 1.0F - (spatialAndTime.time - this.stableDuration + this.arriveingDuration) / this.dissapearingDuration;
/* 185 */     if (spatialAndTime.spatial instanceof TextSpatial) {
/* 186 */       ((TextSpatial)spatialAndTime.spatial).setAlpha(newTranslucency);
/* 187 */     } else if (spatialAndTime.spatial instanceof Quad) {
/* 188 */       (((Quad)spatialAndTime.spatial).getDefaultColor()).a = newTranslucency;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getStableDuration() {
/* 193 */     return this.stableDuration;
/*     */   }
/*     */   
/*     */   public void setStableDuration(float stableDuration) {
/* 197 */     this.stableDuration = stableDuration;
/*     */   }
/*     */   
/*     */   public float getDissapearingDuration() {
/* 201 */     return this.dissapearingDuration;
/*     */   }
/*     */   
/*     */   public void setDissapearingDuration(float dissapearingDuration) {
/* 205 */     this.dissapearingDuration = dissapearingDuration;
/*     */   }
/*     */   
/*     */   public float getArriveingDuration() {
/* 209 */     return this.arriveingDuration;
/*     */   }
/*     */   
/*     */   public void setArriveingDuration(float arriveingDuration) {
/* 213 */     this.arriveingDuration = arriveingDuration;
/*     */   }
/*     */   
/*     */   public float getSize() {
/* 217 */     return this.size;
/*     */   }
/*     */   
/*     */   public void setSize(float size) {
/* 221 */     this.size = size;
/*     */   }
/*     */   
/*     */   public float getScale() {
/* 225 */     return this.defaultScale;
/*     */   }
/*     */   
/*     */   public void setScale(float scale) {
/* 229 */     this.defaultScale = scale;
/*     */   }
/*     */   
/*     */   public Vector2f getDisplacer() {
/* 233 */     return this.displacer;
/*     */   }
/*     */   
/*     */   public void setDisplacer(Vector2f displacer) {
/* 237 */     this.displacer.set(displacer);
/*     */   }
/*     */   
/*     */   public void setDisplacer(float x, float y) {
/* 241 */     this.displacer.set(x, y);
/*     */   }
/*     */   
/*     */   public void setCenterText(boolean centerText) {
/* 245 */     this.centerText = centerText;
/*     */   }
/*     */   
/*     */   public boolean isCenterText() {
/* 249 */     return this.centerText;
/*     */   }
/*     */   
/*     */   protected class SpatialAndTime {
/*     */     public float time;
/*     */     public Spatial spatial;
/*     */     
/*     */     protected SpatialAndTime(float time, Spatial spatial) {
/* 257 */       this.time = time;
/* 258 */       this.spatial = spatial;
/*     */     }
/*     */     
/*     */     public boolean isArriving() {
/* 262 */       return (this.time <= FloatingSpatials.this.arriveingDuration);
/*     */     }
/*     */     
/*     */     public boolean isDying() {
/* 266 */       return (this.time > FloatingSpatials.this.stableDuration + FloatingSpatials.this.arriveingDuration && this.time <= FloatingSpatials.this.dissapearingDuration + FloatingSpatials.this.stableDuration + FloatingSpatials.this.arriveingDuration);
/*     */     }
/*     */     
/*     */     public boolean isAlive() {
/* 270 */       return (this.time > FloatingSpatials.this.arriveingDuration && this.time <= FloatingSpatials.this.stableDuration + FloatingSpatials.this.arriveingDuration);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\FloatingSpatials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */