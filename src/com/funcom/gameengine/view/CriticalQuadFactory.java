/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.math.Vector2f;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class CriticalQuadFactory
/*     */ {
/*     */   private float lifetimeSecs;
/*     */   private float lifetimeGrowingFraction;
/*     */   private float lifetimeDyingFraction;
/*     */   private float translationDisplacementLimit;
/*     */   private float rotationDisplacementLimitRads;
/*     */   private Vector2f size;
/*     */   private List<String> textures;
/*     */   private ResourceGetter resourceGetter;
/*     */   
/*     */   public CriticalQuadFactory(ResourceGetter resourceGetter) {
/*  34 */     this.resourceGetter = resourceGetter;
/*  35 */     this.size = new Vector2f(5.0F, 5.0F);
/*  36 */     this.lifetimeSecs = 1.0F;
/*  37 */     this.lifetimeGrowingFraction = 0.2F;
/*  38 */     this.lifetimeDyingFraction = 0.2F;
/*  39 */     this.translationDisplacementLimit = 3.0F;
/*  40 */     this.rotationDisplacementLimitRads = 0.7853982F;
/*  41 */     this.textures = new ArrayList<String>();
/*     */   }
/*     */   
/*     */   public CriticalQuad createNew() {
/*  45 */     if (this.textures.isEmpty()) {
/*  46 */       throw new IllegalStateException("Critical quads cannot be instantiated without textures!");
/*     */     }
/*  48 */     return new CriticalQuad(this.size, randomTexture(), this.lifetimeSecs, this.lifetimeGrowingFraction, this.lifetimeDyingFraction, this.translationDisplacementLimit, this.rotationDisplacementLimitRads);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Texture randomTexture() {
/*  59 */     int index = FastMath.rand.nextInt(this.textures.size());
/*  60 */     return this.resourceGetter.getTexture(this.textures.get(index), CacheType.CACHE_PERMANENTLY);
/*     */   }
/*     */   
/*     */   public float getLifetimeSecs() {
/*  64 */     return this.lifetimeSecs;
/*     */   }
/*     */   
/*     */   public void setLifetimeSecs(float lifetimeSecs) {
/*  68 */     this.lifetimeSecs = lifetimeSecs;
/*     */   }
/*     */   
/*     */   public float getLifetimeGrowingFraction() {
/*  72 */     return this.lifetimeGrowingFraction;
/*     */   }
/*     */   
/*     */   public void setLifetimeGrowingFraction(float lifetimeGrowingFraction) {
/*  76 */     this.lifetimeGrowingFraction = lifetimeGrowingFraction;
/*     */   }
/*     */   
/*     */   public float getLifetimeDyingFraction() {
/*  80 */     return this.lifetimeDyingFraction;
/*     */   }
/*     */   
/*     */   public void setLifetimeDyingFraction(float lifetimeDyingFraction) {
/*  84 */     this.lifetimeDyingFraction = lifetimeDyingFraction;
/*     */   }
/*     */   
/*     */   public float getTranslationDisplacementLimit() {
/*  88 */     return this.translationDisplacementLimit;
/*     */   }
/*     */   
/*     */   public void setTranslationDisplacementLimit(float translationDisplacementLimit) {
/*  92 */     this.translationDisplacementLimit = translationDisplacementLimit;
/*     */   }
/*     */   
/*     */   public float getRotationDisplacementLimitRads() {
/*  96 */     return this.rotationDisplacementLimitRads;
/*     */   }
/*     */   
/*     */   public void setRotationDisplacementLimitRads(float rotationDisplacementLimitRads) {
/* 100 */     this.rotationDisplacementLimitRads = rotationDisplacementLimitRads;
/*     */   }
/*     */   
/*     */   public Vector2f getSize() {
/* 104 */     return this.size;
/*     */   }
/*     */   
/*     */   public void setSize(Vector2f size) {
/* 108 */     this.size.set(size);
/*     */   }
/*     */   
/*     */   public void setSize(float width, float height) {
/* 112 */     this.size.x = width;
/* 113 */     this.size.y = height;
/*     */   }
/*     */   
/*     */   public void addTexture(String textureId) {
/* 117 */     this.textures.add(textureId);
/*     */   }
/*     */   
/*     */   public void removeTexture(String textureId) {
/* 121 */     this.textures.remove(textureId);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\CriticalQuadFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */