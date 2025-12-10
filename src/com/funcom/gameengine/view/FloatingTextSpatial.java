/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import com.funcom.gameengine.jme.InGameFontFactory;
/*     */ import com.funcom.gameengine.jme.TextSpatial;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.ResourceGetterImpl;
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class FloatingTextSpatial
/*     */   extends Node implements Updated {
/*     */   private static final String FONT_ALIAS = "statEffectFontAlias";
/*     */   protected float arriveingDuration;
/*     */   protected float stableDuration;
/*     */   protected float dissapearingDuration;
/*     */   private float defaultScale;
/*     */   private Vector2f displacer;
/*     */   private boolean centerText;
/*     */   private List<TextAndTime> texts;
/*     */   private InGameFontFactory inGameFontfactory;
/*     */   
/*     */   public FloatingTextSpatial(ResourceGetter resourceGetter) {
/*  37 */     super(FloatingTextSpatial.class.getSimpleName());
/*  38 */     this.inGameFontfactory = new InGameFontFactory(((ResourceGetterImpl)resourceGetter).getResourceManager(), "statEffectFontAlias");
/*  39 */     this.arriveingDuration = 0.0F;
/*  40 */     this.stableDuration = 0.7F;
/*  41 */     this.dissapearingDuration = 0.3F;
/*  42 */     this.defaultScale = 2.5F;
/*  43 */     this.displacer = new Vector2f(0.0F, 0.0F);
/*  44 */     this.texts = new LinkedList<TextAndTime>();
/*     */   }
/*     */   
/*     */   public void showText(String text, ColorRGBA color, PropNode owner) {
/*  48 */     showTextAtScreenCoordinate(text, color, owner, this.defaultScale);
/*     */   }
/*     */   
/*     */   public void showText(String text, ColorRGBA color, PropNode owner, float scale) {
/*  52 */     showTextAtScreenCoordinate(text, color, owner, scale);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void showTextAtScreenCoordinate(String text, ColorRGBA color, PropNode owner, float scale) {
/*  57 */     TextSpatial spatial = this.inGameFontfactory.createText(text, color, scale);
/*  58 */     spatial.setAlpha(0.0F);
/*  59 */     spatial.setLocalScale(scale);
/*  60 */     showTextAtScreenCoordinate(spatial, owner, true, new Vector3f(Vector3f.ZERO));
/*     */   }
/*     */   
/*     */   public void showText(String text, ColorRGBA color, PropNode owner, float scale, Vector3f accDisplacer, boolean modifyPosition) {
/*  64 */     TextSpatial spatial = this.inGameFontfactory.createText(text, color, scale);
/*  65 */     spatial.setAlpha(0.0F);
/*  66 */     spatial.setLocalScale(scale);
/*  67 */     showTextAtScreenCoordinate(spatial, owner, modifyPosition, accDisplacer);
/*     */   }
/*     */   
/*     */   protected void showTextAtScreenCoordinate(TextSpatial textSpatial, PropNode owner, boolean modifyPosition, Vector3f accDisplacer) {
/*  71 */     if (this.centerText && modifyPosition)
/*  72 */       modifyToCenterText(accDisplacer, textSpatial); 
/*  73 */     if (modifyPosition) {
/*  74 */       modifyWithDisplacer(accDisplacer);
/*  75 */       modifyWithRandomDisplacement(accDisplacer);
/*     */     } 
/*     */     
/*  78 */     textSpatial.setRenderState(blending());
/*  79 */     textSpatial.updateRenderState();
/*  80 */     attachChild((Spatial)textSpatial);
/*     */     
/*  82 */     this.texts.add(new TextAndTime(0.0F, textSpatial, owner.getPosition().clone(), accDisplacer));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void modifyWithRandomDisplacement(Vector3f screenCoords) {
/*  87 */     screenCoords.x += FastMath.rand.nextInt(50) - 25.0F;
/*  88 */     screenCoords.y += FastMath.rand.nextInt(50) - 25.0F;
/*     */   }
/*     */   
/*     */   private void modifyWithDisplacer(Vector3f screenCoords) {
/*  92 */     screenCoords.x += this.displacer.x;
/*  93 */     screenCoords.y += this.displacer.y;
/*     */   }
/*     */   
/*     */   private void modifyToCenterText(Vector3f accDisplacer, TextSpatial spatial) {
/*  97 */     accDisplacer.x -= spatial.getWidth() / 2.0F;
/*     */   }
/*     */   
/*     */   private RenderState blending() {
/* 101 */     BlendState bs = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/* 102 */     bs.setBlendEnabled(true);
/* 103 */     return (RenderState)bs;
/*     */   }
/*     */   
/*     */   public void update(float time) {
/* 107 */     Iterator<TextAndTime> it = this.texts.iterator();
/* 108 */     while (it.hasNext()) {
/* 109 */       TextAndTime textAndTime = it.next();
/* 110 */       textAndTime.time += time;
/*     */       
/* 112 */       updatePosition(textAndTime, time);
/*     */       
/* 114 */       if (textAndTime.isArriving()) {
/* 115 */         modifyTranslucencyArriving(textAndTime); continue;
/* 116 */       }  if (textAndTime.isAlive()) {
/*     */         
/* 118 */         textAndTime.text.setAlpha(1.0F); continue;
/* 119 */       }  if (textAndTime.isDying()) {
/* 120 */         modifyTranslucencyDying(textAndTime); continue;
/*     */       } 
/* 122 */       it.remove();
/* 123 */       removeText(textAndTime.text);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updatePosition(TextAndTime textAndTime, float time) {
/* 130 */     float x = WorldUtils.getScreenX(textAndTime.ownerPosition, WorldOrigin.instance().getX());
/* 131 */     float y = WorldUtils.getScreenY(textAndTime.ownerPosition, WorldOrigin.instance().getY());
/*     */     
/* 133 */     Vector3f coords = new Vector3f(x, 0.0F, y);
/*     */     
/* 135 */     Vector3f screenCoords = DisplaySystem.getDisplaySystem().getScreenCoordinates(coords);
/* 136 */     screenCoords.z = 0.0F;
/* 137 */     textAndTime.accumulatedDisplacers.setY(textAndTime.accumulatedDisplacers.getY() + time * 37.5F);
/* 138 */     screenCoords.addLocal(textAndTime.accumulatedDisplacers);
/* 139 */     textAndTime.text.setLocalTranslation(screenCoords);
/*     */   }
/*     */   
/*     */   protected void removeText(TextSpatial text) {
/* 143 */     detachChild((Spatial)text);
/*     */   }
/*     */   
/*     */   private void modifyTranslucencyArriving(TextAndTime textAndTime) {
/* 147 */     float newTranslucency = textAndTime.time / this.arriveingDuration;
/* 148 */     textAndTime.text.setAlpha(newTranslucency);
/*     */   }
/*     */   
/*     */   private void modifyTranslucencyDying(TextAndTime textAndTime) {
/* 152 */     float newTranslucency = 1.0F - (textAndTime.time - this.stableDuration + this.arriveingDuration) / this.dissapearingDuration;
/* 153 */     textAndTime.text.setAlpha(newTranslucency);
/*     */   }
/*     */   
/*     */   public float getStableDuration() {
/* 157 */     return this.stableDuration;
/*     */   }
/*     */   
/*     */   public void setStableDuration(float stableDuration) {
/* 161 */     this.stableDuration = stableDuration;
/*     */   }
/*     */   
/*     */   public float getDissapearingDuration() {
/* 165 */     return this.dissapearingDuration;
/*     */   }
/*     */   
/*     */   public void setDissapearingDuration(float dissapearingDuration) {
/* 169 */     this.dissapearingDuration = dissapearingDuration;
/*     */   }
/*     */   
/*     */   public float getArriveingDuration() {
/* 173 */     return this.arriveingDuration;
/*     */   }
/*     */   
/*     */   public void setArriveingDuration(float arriveingDuration) {
/* 177 */     this.arriveingDuration = arriveingDuration;
/*     */   }
/*     */   
/*     */   public float getScale() {
/* 181 */     return this.defaultScale;
/*     */   }
/*     */   
/*     */   public void setScale(float scale) {
/* 185 */     this.defaultScale = scale;
/*     */   }
/*     */   
/*     */   public Vector2f getDisplacer() {
/* 189 */     return this.displacer;
/*     */   }
/*     */   
/*     */   public void setDisplacer(Vector2f displacer) {
/* 193 */     this.displacer.set(displacer);
/*     */   }
/*     */   
/*     */   public void setDisplacer(float x, float y) {
/* 197 */     this.displacer.set(x, y);
/*     */   }
/*     */   
/*     */   public void setCenterText(boolean centerText) {
/* 201 */     this.centerText = centerText;
/*     */   }
/*     */   
/*     */   public boolean isCenterText() {
/* 205 */     return this.centerText;
/*     */   }
/*     */   
/*     */   private class TextAndTime {
/*     */     public float time;
/*     */     public TextSpatial text;
/*     */     public WorldCoordinate ownerPosition;
/*     */     public Vector3f accumulatedDisplacers;
/*     */     
/*     */     private TextAndTime(float time, TextSpatial text, WorldCoordinate ownerPosition, Vector3f accumulatedDisplacers) {
/* 215 */       this.time = time;
/* 216 */       this.text = text;
/* 217 */       this.ownerPosition = ownerPosition;
/* 218 */       this.accumulatedDisplacers = accumulatedDisplacers;
/*     */     }
/*     */     
/*     */     public boolean isArriving() {
/* 222 */       return (this.time <= FloatingTextSpatial.this.arriveingDuration);
/*     */     }
/*     */     
/*     */     public boolean isDying() {
/* 226 */       return (this.time > FloatingTextSpatial.this.stableDuration + FloatingTextSpatial.this.arriveingDuration && this.time <= FloatingTextSpatial.this.dissapearingDuration + FloatingTextSpatial.this.stableDuration + FloatingTextSpatial.this.arriveingDuration);
/*     */     }
/*     */     
/*     */     public boolean isAlive() {
/* 230 */       return (this.time > FloatingTextSpatial.this.arriveingDuration && this.time <= FloatingTextSpatial.this.stableDuration + FloatingTextSpatial.this.arriveingDuration);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\FloatingTextSpatial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */