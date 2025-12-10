/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldOrigin;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import com.funcom.gameengine.jme.ResourceManagedFontFactory;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.font2d.Text2D;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class FloatingText2D
/*     */   extends Node
/*     */   implements Updated
/*     */ {
/*     */   public static final String FONT_REFERENCE = "gui/font/tcgfont.png";
/*     */   protected float arriveingDuration;
/*     */   protected float stableDuration;
/*     */   protected float dissapearingDuration;
/*     */   private float size;
/*     */   private float defaultScale;
/*     */   private Vector2f displacer;
/*     */   private boolean centerText;
/*     */   private ResourceManagedFontFactory factory;
/*     */   private List<TextAndTime> texts;
/*     */   
/*     */   public FloatingText2D(ResourceGetter resourceGetter) {
/*  45 */     super(FloatingText2D.class.getSimpleName());
/*  46 */     this.factory = new ResourceManagedFontFactory(resourceGetter, "gui/font/tcgfont.png");
/*  47 */     this.arriveingDuration = 0.0F;
/*  48 */     this.stableDuration = 0.7F;
/*  49 */     this.dissapearingDuration = 0.3F;
/*  50 */     this.size = 3.5F;
/*  51 */     this.defaultScale = 3.5F;
/*  52 */     this.displacer = new Vector2f(0.0F, 0.0F);
/*  53 */     this.texts = new LinkedList<TextAndTime>();
/*     */   }
/*     */   
/*     */   public void showText(String text, ColorRGBA color, PropNode owner) {
/*  57 */     showTextAtScreenCoordinate(text, color, owner, this.defaultScale);
/*     */   }
/*     */   
/*     */   public void showText(String text, ColorRGBA color, PropNode owner, float scale) {
/*  61 */     showTextAtScreenCoordinate(text, color, owner, scale);
/*     */   }
/*     */   
/*     */   protected void showTextAtScreenCoordinate(String text, ColorRGBA color, PropNode owner, float scale) {
/*  65 */     Text2D textSpatial = this.factory.createText(text, this.size, 0);
/*  66 */     ColorRGBA textColor = new ColorRGBA(color);
/*  67 */     textColor.a = 0.0F;
/*  68 */     textSpatial.setTextColor(textColor);
/*  69 */     textSpatial.setLocalScale(scale);
/*  70 */     showTextAtScreenCoordinate(textSpatial, owner, true, new Vector3f(Vector3f.ZERO));
/*     */   }
/*     */   
/*     */   public void showText(String text, ColorRGBA color, PropNode owner, float scale, Vector3f accDisplacer, boolean modifyPosition) {
/*  74 */     Text2D textSpatial = this.factory.createText(text, this.size, 0);
/*  75 */     color.a = 0.0F;
/*  76 */     textSpatial.setTextColor(new ColorRGBA(color));
/*  77 */     textSpatial.setLocalScale(scale);
/*  78 */     showTextAtScreenCoordinate(textSpatial, owner, modifyPosition, accDisplacer);
/*     */   }
/*     */   
/*     */   protected void showTextAtScreenCoordinate(Text2D textSpatial, PropNode owner, boolean modifyPosition, Vector3f accDisplacer) {
/*  82 */     if (this.centerText && modifyPosition)
/*  83 */       modifyToCenterText(accDisplacer, textSpatial); 
/*  84 */     if (modifyPosition) {
/*  85 */       modifyWithDisplacer(accDisplacer);
/*  86 */       modifyWithRandomDisplacement(accDisplacer);
/*     */     } 
/*     */     
/*  89 */     textSpatial.setRenderState(blending());
/*  90 */     textSpatial.updateRenderState();
/*  91 */     attachChild((Spatial)textSpatial);
/*     */     
/*  93 */     this.texts.add(new TextAndTime(0.0F, textSpatial, owner.getPosition().clone(), accDisplacer));
/*     */   }
/*     */   
/*     */   protected void modifyWithRandomDisplacement(Vector3f screenCoords) {
/*  97 */     screenCoords.x += FastMath.rand.nextInt(50) - 25.0F;
/*  98 */     screenCoords.y += FastMath.rand.nextInt(50) - 25.0F;
/*     */   }
/*     */   
/*     */   private void modifyWithDisplacer(Vector3f screenCoords) {
/* 102 */     screenCoords.x += this.displacer.x;
/* 103 */     screenCoords.y += this.displacer.y;
/*     */   }
/*     */   
/*     */   private void modifyToCenterText(Vector3f accDisplacer, Text2D textSpatial) {
/* 107 */     accDisplacer.x -= textSpatial.getWidth() / 2.0F;
/*     */   }
/*     */   
/*     */   private RenderState blending() {
/* 111 */     BlendState bs = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/* 112 */     bs.setBlendEnabled(true);
/* 113 */     return (RenderState)bs;
/*     */   }
/*     */   
/*     */   public void update(float time) {
/* 117 */     Iterator<TextAndTime> it = this.texts.iterator();
/* 118 */     while (it.hasNext()) {
/* 119 */       TextAndTime textAndTime = it.next();
/* 120 */       textAndTime.time += time;
/*     */       
/* 122 */       updatePosition(textAndTime, time);
/*     */       
/* 124 */       if (textAndTime.isArriving()) {
/* 125 */         modifyTranslucencyArriving(textAndTime); continue;
/* 126 */       }  if (textAndTime.isAlive()) {
/*     */         
/* 128 */         (textAndTime.text.getTextColor()).a = 1.0F; continue;
/* 129 */       }  if (textAndTime.isDying()) {
/* 130 */         modifyTranslucencyDying(textAndTime); continue;
/*     */       } 
/* 132 */       it.remove();
/* 133 */       removeText(textAndTime.text);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updatePosition(TextAndTime textAndTime, float time) {
/* 140 */     float x = WorldUtils.getScreenX(textAndTime.ownerPosition, WorldOrigin.instance().getX());
/* 141 */     float y = WorldUtils.getScreenY(textAndTime.ownerPosition, WorldOrigin.instance().getY());
/*     */     
/* 143 */     Vector3f coords = new Vector3f(x, 0.0F, y);
/*     */     
/* 145 */     Vector3f screenCoords = DisplaySystem.getDisplaySystem().getScreenCoordinates(coords);
/* 146 */     screenCoords.z = 0.0F;
/* 147 */     textAndTime.accumulatedDisplacers.setY(textAndTime.accumulatedDisplacers.getY() + time * 37.5F);
/* 148 */     screenCoords.addLocal(textAndTime.accumulatedDisplacers);
/* 149 */     textAndTime.text.setLocalTranslation(screenCoords);
/*     */   }
/*     */   
/*     */   protected void removeText(Text2D text) {
/* 153 */     detachChild((Spatial)text);
/*     */   }
/*     */   
/*     */   private void modifyTranslucencyArriving(TextAndTime textAndTime) {
/* 157 */     float newTranslucency = textAndTime.time / this.arriveingDuration;
/* 158 */     ColorRGBA textColor = textAndTime.text.getTextColor();
/* 159 */     textColor.a = newTranslucency;
/*     */   }
/*     */   
/*     */   private void modifyTranslucencyDying(TextAndTime textAndTime) {
/* 163 */     float newTranslucency = 1.0F - (textAndTime.time - this.stableDuration + this.arriveingDuration) / this.dissapearingDuration;
/* 164 */     ColorRGBA textColor = textAndTime.text.getTextColor();
/* 165 */     textColor.a = newTranslucency;
/*     */   }
/*     */   
/*     */   public float getStableDuration() {
/* 169 */     return this.stableDuration;
/*     */   }
/*     */   
/*     */   public void setStableDuration(float stableDuration) {
/* 173 */     this.stableDuration = stableDuration;
/*     */   }
/*     */   
/*     */   public float getDissapearingDuration() {
/* 177 */     return this.dissapearingDuration;
/*     */   }
/*     */   
/*     */   public void setDissapearingDuration(float dissapearingDuration) {
/* 181 */     this.dissapearingDuration = dissapearingDuration;
/*     */   }
/*     */   
/*     */   public float getArriveingDuration() {
/* 185 */     return this.arriveingDuration;
/*     */   }
/*     */   
/*     */   public void setArriveingDuration(float arriveingDuration) {
/* 189 */     this.arriveingDuration = arriveingDuration;
/*     */   }
/*     */   
/*     */   public float getSize() {
/* 193 */     return this.size;
/*     */   }
/*     */   
/*     */   public void setSize(float size) {
/* 197 */     this.size = size;
/*     */   }
/*     */   
/*     */   public float getScale() {
/* 201 */     return this.defaultScale;
/*     */   }
/*     */   
/*     */   public void setScale(float scale) {
/* 205 */     this.defaultScale = scale;
/*     */   }
/*     */   
/*     */   public Vector2f getDisplacer() {
/* 209 */     return this.displacer;
/*     */   }
/*     */   
/*     */   public void setDisplacer(Vector2f displacer) {
/* 213 */     this.displacer.set(displacer);
/*     */   }
/*     */   
/*     */   public void setDisplacer(float x, float y) {
/* 217 */     this.displacer.set(x, y);
/*     */   }
/*     */   
/*     */   public void setCenterText(boolean centerText) {
/* 221 */     this.centerText = centerText;
/*     */   }
/*     */   
/*     */   public boolean isCenterText() {
/* 225 */     return this.centerText;
/*     */   }
/*     */   
/*     */   private class TextAndTime {
/*     */     public float time;
/*     */     public Text2D text;
/*     */     public WorldCoordinate ownerPosition;
/*     */     public Vector3f accumulatedDisplacers;
/*     */     
/*     */     private TextAndTime(float time, Text2D text, WorldCoordinate ownerPosition, Vector3f accumulatedDisplacers) {
/* 235 */       this.time = time;
/* 236 */       this.text = text;
/* 237 */       this.ownerPosition = ownerPosition;
/* 238 */       this.accumulatedDisplacers = accumulatedDisplacers;
/*     */     }
/*     */     
/*     */     public boolean isArriving() {
/* 242 */       return (this.time <= FloatingText2D.this.arriveingDuration);
/*     */     }
/*     */     
/*     */     public boolean isDying() {
/* 246 */       return (this.time > FloatingText2D.this.stableDuration + FloatingText2D.this.arriveingDuration && this.time <= FloatingText2D.this.dissapearingDuration + FloatingText2D.this.stableDuration + FloatingText2D.this.arriveingDuration);
/*     */     }
/*     */     
/*     */     public boolean isAlive() {
/* 250 */       return (this.time > FloatingText2D.this.arriveingDuration && this.time <= FloatingText2D.this.stableDuration + FloatingText2D.this.arriveingDuration);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\FloatingText2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */