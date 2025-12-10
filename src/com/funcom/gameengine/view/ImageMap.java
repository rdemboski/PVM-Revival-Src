/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.Vector2f;
/*     */ import java.awt.Dimension;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class ImageMap
/*     */ {
/*     */   private Texture texture;
/*     */   private Dimension frameSize;
/*     */   private int numberOfFrames;
/*     */   private Map<Integer, Vector2f[]> rectanglesForFrames;
/*     */   
/*     */   public ImageMap(Texture texture, Dimension frameSize, int numberOfFrames) {
/*  27 */     this.texture = texture;
/*  28 */     this.frameSize = frameSize;
/*  29 */     this.numberOfFrames = numberOfFrames;
/*  30 */     this.rectanglesForFrames = (Map)new HashMap<Integer, Vector2f>();
/*  31 */     recalculateAll();
/*     */   }
/*     */   
/*     */   public void setTexture(Texture texture) {
/*  35 */     this.texture = texture;
/*  36 */     recalculateAll();
/*     */   }
/*     */   
/*     */   public void setFrameSize(Dimension frameSize) {
/*  40 */     this.frameSize = frameSize;
/*  41 */     recalculateAll();
/*     */   }
/*     */   
/*     */   public void setNumberOfFrames(int numberOfFrames) {
/*  45 */     this.numberOfFrames = numberOfFrames;
/*  46 */     recalculateAll();
/*     */   }
/*     */   
/*     */   private void recalculateAll() {
/*  50 */     this.rectanglesForFrames.clear();
/*     */     
/*  52 */     int imageWidth = this.texture.getImage().getWidth();
/*  53 */     int imageHeight = this.texture.getImage().getHeight();
/*     */ 
/*     */     
/*  56 */     for (int frame = 0; frame < this.numberOfFrames; frame++) {
/*  57 */       this.rectanglesForFrames.put(Integer.valueOf(frame), new Vector2f[] { new Vector2f((frame * this.frameSize.width) / imageWidth, (this.frameSize.height + this.frameSize.height) / imageHeight), new Vector2f((frame * this.frameSize.width) / imageWidth, this.frameSize.height / imageHeight), new Vector2f((frame * this.frameSize.width + this.frameSize.width) / imageWidth, this.frameSize.height / imageHeight), new Vector2f((frame * this.frameSize.width + this.frameSize.width) / imageWidth, (this.frameSize.height + this.frameSize.height) / imageHeight) });
/*     */     } 
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
/*     */   public Vector2f[] getUVForFrame(int frame) {
/*  86 */     if (!this.rectanglesForFrames.containsKey(Integer.valueOf(frame)))
/*  87 */       throw new IllegalArgumentException("ImageMap with texture '" + this.texture.getImageLocation() + "' contains no frame: " + frame); 
/*  88 */     return this.rectanglesForFrames.get(Integer.valueOf(frame));
/*     */   }
/*     */   
/*     */   public int getLastFrameIndex() {
/*  92 */     return this.numberOfFrames - 1;
/*     */   }
/*     */   
/*     */   public int getNumberOfFrames() {
/*  96 */     return this.numberOfFrames;
/*     */   }
/*     */   
/*     */   public Texture getTexture() {
/* 100 */     return this.texture;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\ImageMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */