/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CriticalQuad
/*     */   extends Quad
/*     */   implements Updated
/*     */ {
/*     */   private float growingTime;
/*     */   private float dyingTime;
/*     */   private float currentTime;
/*     */   private float lifetimeSecs;
/*     */   private float translationDisplacementLimit;
/*     */   private float rotationDisplacementLimitRads;
/*     */   
/*     */   public CriticalQuad(Vector2f size, Texture texture, float lifetimeSecs, float lifetimeGrowingFraction, float lifetimeDyingFraction, float translationDisplacementLimit, float rotationDisplacementLimitRads) {
/*  36 */     super("critical-quad", size.x, size.y);
/*  37 */     this.lifetimeSecs = lifetimeSecs;
/*  38 */     this.translationDisplacementLimit = translationDisplacementLimit;
/*  39 */     this.rotationDisplacementLimitRads = rotationDisplacementLimitRads;
/*  40 */     setRenderQueueMode(3);
/*  41 */     setupTextureState(texture);
/*  42 */     setupAlphaState();
/*  43 */     setSolidColor(ColorRGBA.white);
/*  44 */     updateRenderState();
/*     */     
/*  46 */     displaceRandomly();
/*     */     
/*  48 */     this.currentTime = 0.0F;
/*  49 */     this.growingTime = lifetimeSecs * lifetimeGrowingFraction;
/*  50 */     this.dyingTime = lifetimeSecs * lifetimeDyingFraction;
/*     */   }
/*     */   
/*     */   private void displaceRandomly() {
/*  54 */     setLocalTranslation(FastMath.rand.nextFloat() * this.translationDisplacementLimit, FastMath.rand.nextFloat() * this.translationDisplacementLimit, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     Quaternion q = new Quaternion();
/*  60 */     q.fromAngles(0.0F, 0.0F, FastMath.rand.nextFloat() * this.rotationDisplacementLimitRads * FastMath.sign(FastMath.rand.nextFloat() - 0.5F));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     setLocalRotation(q);
/*     */   }
/*     */   
/*     */   public Class<? extends Spatial> getClassTag() {
/*  70 */     return (Class)getClass();
/*     */   }
/*     */   
/*     */   private void setupAlphaState() {
/*  74 */     BlendState as = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/*  75 */     as.setEnabled(true);
/*  76 */     as.setBlendEnabled(true);
/*  77 */     setRenderState((RenderState)as);
/*     */   }
/*     */   
/*     */   private void setupTextureState(Texture texture) {
/*  81 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  82 */     ts.setEnabled(true);
/*  83 */     ts.setTexture(texture);
/*  84 */     setRenderState((RenderState)ts);
/*     */   }
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
/*     */   public void update(float time) {
/*  97 */     if (isFinished()) {
/*  98 */       removeFromParent();
/*  99 */     } else if (isGrowing()) {
/* 100 */       wooble();
/* 101 */     } else if (isDying()) {
/* 102 */       fade();
/*     */     } 
/* 104 */     this.currentTime += time;
/*     */   }
/*     */   
/*     */   private void wooble() {
/* 108 */     float fraction = this.currentTime / this.growingTime;
/* 109 */     Vector3f ls = getLocalScale();
/* 110 */     ls.x = FastMath.sin(fraction * 1.9707963F);
/* 111 */     ls.y = FastMath.sin(fraction * 1.9707963F);
/* 112 */     setLocalScale(ls);
/*     */   }
/*     */   
/*     */   private void fade() {
/* 116 */     float dyingTimeStart = this.lifetimeSecs - this.dyingTime;
/* 117 */     float newAlpha = 1.0F - (this.currentTime - dyingTimeStart) / this.dyingTime;
/*     */     
/* 119 */     ColorRGBA colorClone = new ColorRGBA(ColorRGBA.white);
/* 120 */     colorClone.a = newAlpha;
/* 121 */     setSolidColor(colorClone);
/*     */   }
/*     */   
/*     */   private boolean isFinished() {
/* 125 */     return (this.currentTime >= this.lifetimeSecs);
/*     */   }
/*     */   
/*     */   private boolean isGrowing() {
/* 129 */     return (this.currentTime < this.growingTime);
/*     */   }
/*     */   
/*     */   private boolean isDying() {
/* 133 */     float dyingTimeStart = this.lifetimeSecs - this.dyingTime;
/* 134 */     return (this.currentTime > dyingTimeStart && this.currentTime < this.lifetimeSecs);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\CriticalQuad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */