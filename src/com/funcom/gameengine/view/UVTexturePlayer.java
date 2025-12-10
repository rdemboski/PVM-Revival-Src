/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Geometry;
/*     */ import com.jme.scene.TexCoords;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.awt.Dimension;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class UVTexturePlayer
/*     */   extends Controller
/*     */   implements AnimationPlayer {
/*  23 */   private static final Logger LOGGER = Logger.getLogger(UVTexturePlayer.class.getName());
/*     */   
/*     */   private static final int DEFAULT_FPS = 30;
/*     */   
/*     */   private static final int DEFAULT_TEXTUREUNIT = 0;
/*     */   private Map<String, ImageMap> animationBinds;
/*     */   private int fps;
/*     */   private ImageMap playedAnimation;
/*     */   private float currentTime;
/*     */   private float oneFrameTime;
/*     */   private int lastFrame;
/*     */   private Geometry geometry;
/*     */   private int textureUnit;
/*     */   
/*     */   public UVTexturePlayer(Geometry geometry) {
/*  38 */     this(geometry, 30, 0);
/*     */   }
/*     */   
/*     */   public UVTexturePlayer(Geometry geometry, int fps, int textureUnit) {
/*  42 */     this.currentTime = 0.0F;
/*  43 */     this.geometry = geometry;
/*  44 */     this.textureUnit = textureUnit;
/*  45 */     this.animationBinds = new HashMap<String, ImageMap>();
/*  46 */     geometry.addController(this);
/*  47 */     setFps(fps);
/*     */   }
/*     */   
/*     */   public void registerAnimationBind(String name, Texture texture, Dimension frameSize, int numberOfFrames) {
/*  51 */     registerAnimationBind(name, new ImageMap(texture, frameSize, numberOfFrames));
/*     */   }
/*     */   
/*     */   public void registerAnimationBind(String name, ImageMap map) {
/*  55 */     if (this.animationBinds.containsKey(name))
/*  56 */       this.animationBinds.remove(name); 
/*  57 */     this.animationBinds.put(name, map);
/*     */   }
/*     */   
/*     */   public int getFps() {
/*  61 */     return this.fps;
/*     */   }
/*     */   
/*     */   public void setFps(int fps) {
/*  65 */     this.fps = fps;
/*  66 */     this.oneFrameTime = 1.0F / fps;
/*     */   }
/*     */   
/*     */   public void play(String animationName, boolean override) {
/*  70 */     this.playedAnimation = this.animationBinds.get(animationName);
/*     */     
/*  72 */     LOGGER.log((Priority)Level.INFO, "Playing: " + this.playedAnimation.getTexture().getImageLocation());
/*     */   }
/*     */   
/*     */   private void updateQuadTexture() {
/*  76 */     TextureState ts = (TextureState)this.geometry.getRenderState(5);
/*  77 */     if (ts == null) {
/*  78 */       ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*     */     }
/*  80 */     if (ts.getTexture() == null || !ts.getTexture().equals(this.playedAnimation.getTexture())) {
/*  81 */       ts.setTexture(this.playedAnimation.getTexture());
/*  82 */       this.geometry.updateRenderState();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<String> getAnimationNames() {
/*  87 */     return this.animationBinds.keySet();
/*     */   }
/*     */   
/*     */   public void update(float v) {
/*  91 */     if (this.playedAnimation == null) {
/*     */       return;
/*     */     }
/*  94 */     this.currentTime += v;
/*  95 */     int currentFrame = currentFrame();
/*     */     
/*  97 */     if (currentFrame != this.lastFrame)
/*  98 */       updateQuadUV(currentFrame); 
/*  99 */     if (currentFrame == this.playedAnimation.getLastFrameIndex())
/* 100 */       reset(); 
/*     */   }
/*     */   
/*     */   private void reset() {
/* 104 */     this.playedAnimation = null;
/* 105 */     this.currentTime = 0.0F;
/*     */   }
/*     */   
/*     */   private void updateQuadUV(int currentFrame) {
/* 109 */     this.lastFrame = currentFrame;
/* 110 */     Vector2f[] uv = this.playedAnimation.getUVForFrame(currentFrame);
/*     */     
/* 112 */     TexCoords tb = this.geometry.getTextureCoords(this.textureUnit);
/* 113 */     if (tb == null) {
/* 114 */       tb = new TexCoords(BufferUtils.createVector2Buffer(4));
/*     */     }
/*     */     
/* 117 */     tb.coords.rewind();
/* 118 */     for (Vector2f vector2f : uv)
/* 119 */       tb.coords.put(vector2f.x).put(vector2f.y); 
/* 120 */     tb.coords.flip();
/*     */     
/* 122 */     LOGGER.log((Priority)Level.INFO, "Playing frame: " + currentFrame + ", current time: " + this.currentTime);
/* 123 */     LOGGER.log((Priority)Level.INFO, "UV Vectors: " + Arrays.toString((Object[])uv));
/*     */   }
/*     */ 
/*     */   
/*     */   private int currentFrame() {
/* 128 */     return (int)Math.floor((this.currentTime / this.oneFrameTime));
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\UVTexturePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */