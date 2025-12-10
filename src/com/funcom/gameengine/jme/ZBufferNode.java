/*     */ package com.funcom.gameengine.jme;
/*     */ 
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ZBufferNode
/*     */   extends Node {
/*  25 */   private static final Logger LOGGER = Logger.getLogger(ZBufferNode.class.getName());
/*  26 */   public static final ZBufferNode INSTANCE = new ZBufferNode();
/*     */   
/*     */   private ZBufferState zStateOff;
/*     */   private TextureState textureStateOff;
/*     */   protected BlendState blendStateOff;
/*     */   protected double min;
/*     */   protected double max;
/*     */   ByteBuffer buf;
/*     */   int tmp;
/*     */   
/*     */   private ZBufferNode() {
/*  37 */     Renderer r = DisplaySystem.getDisplaySystem().getRenderer();
/*  38 */     this.zStateOff = r.createZBufferState();
/*  39 */     this.zStateOff.setEnabled(false);
/*  40 */     this.textureStateOff = r.createTextureState();
/*  41 */     this.textureStateOff.setEnabled(false);
/*  42 */     this.blendStateOff = r.createBlendState();
/*  43 */     this.blendStateOff.setBlendEnabled(false);
/*     */ 
/*     */     
/*  46 */     setRenderQueueMode(4);
/*  47 */     setCullHint(Spatial.CullHint.Always);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Renderer r) {
/*  54 */     if (!r.isProcessingQueue() && 
/*  55 */       r.checkAndAdd((Spatial)this)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  61 */     int w = r.getWidth();
/*  62 */     int h = r.getHeight();
/*  63 */     if (this.buf == null) {
/*  64 */       this.buf = BufferUtils.createByteBuffer(4 * w * h);
/*     */     }
/*  66 */     GL11.glReadPixels(0, 0, w, h, 6402, 5126, this.buf);
/*  67 */     FloatBuffer floatBuf = this.buf.asFloatBuffer();
/*  68 */     int len = floatBuf.limit();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     double _min = Double.MAX_VALUE;
/*  77 */     double _max = Double.MIN_VALUE;
/*  78 */     double range = this.max - this.min;
/*  79 */     IntBuffer intBuf = this.buf.asIntBuffer();
/*  80 */     intBuf.rewind();
/*  81 */     for (int i = len - 1; i >= 0; i--) {
/*  82 */       int g, depthScore = 0;
/*  83 */       float zValue = floatBuf.get(i);
/*     */       
/*  85 */       _min = Math.min(_min, zValue);
/*  86 */       _max = Math.max(_max, zValue);
/*     */       
/*  88 */       if (range > 0.0D) {
/*  89 */         depthScore = 767 - (int)((zValue - this.min) * 767.0D / range);
/*     */       }
/*     */       
/*  92 */       if (depthScore < 0) {
/*  93 */         depthScore = 0;
/*  94 */       } else if (depthScore > 767) {
/*  95 */         depthScore = 767;
/*     */       } 
/*     */ 
/*     */       
/*  99 */       int red = 0;
/* 100 */       int b = 0;
/* 101 */       if (depthScore >= 512) {
/* 102 */         g = 255;
/* 103 */         red = 255;
/* 104 */         b = depthScore - 512;
/* 105 */       } else if (depthScore >= 256) {
/* 106 */         g = 255;
/* 107 */         red = depthScore - 256;
/*     */       } else {
/* 109 */         g = depthScore;
/*     */       } 
/*     */       
/* 112 */       int color = 0xFF000000 | b << 16 | g << 8 | red;
/* 113 */       intBuf.put(i, color);
/*     */     } 
/* 115 */     this.min = _min;
/* 116 */     this.max = _max;
/*     */     
/* 118 */     int widthMin = (int)(w * this.min);
/* 119 */     int widthMax = (int)(w * this.max); int x;
/* 120 */     for (x = 0; x < widthMax; x++) {
/* 121 */       intBuf.put(x + (h - 1) * w, -65281);
/* 122 */       intBuf.put(x + (h - 2) * w, -65281);
/*     */     } 
/* 124 */     for (x = 0; x < widthMin; x++) {
/* 125 */       intBuf.put(x + (h - 4) * w, -16776961);
/* 126 */       intBuf.put(x + (h - 5) * w, -16776961);
/*     */     } 
/*     */     
/* 129 */     this.blendStateOff.apply();
/* 130 */     this.zStateOff.apply();
/* 131 */     this.textureStateOff.apply();
/*     */     
/* 133 */     GL11.glDrawPixels(w, h, 6408, 5121, this.buf);
/*     */     
/* 135 */     if (this.tmp == 1) {
/* 136 */       saveImage(w, h, intBuf, "C:/tmp.png");
/* 137 */       LOGGER.log((Priority)Level.INFO, "Saved.");
/* 138 */       this.tmp++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveImage(int width, int height, IntBuffer buf, String file) {
/* 143 */     BufferedImage img = new BufferedImage(width, height, 1);
/*     */ 
/*     */     
/* 146 */     for (int x = 0; x < width; x++) {
/* 147 */       for (int y = 0; y < height; y++) {
/*     */         
/* 149 */         int index = (height - y - 1) * width + x;
/* 150 */         int color = buf.get(index);
/*     */         
/* 152 */         img.setRGB(x, y, color);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 157 */     LOGGER.log((Priority)Level.INFO, "TCGWorld.takeScreenShot: " + file);
/*     */     try {
/* 159 */       ImageIO.write(img, "png", new File(file));
/* 160 */     } catch (IOException e) {
/* 161 */       e.printStackTrace();
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
/*     */   public void setVisible(boolean visible) {
/* 180 */     if (visible) {
/* 181 */       setCullHint(Spatial.CullHint.Never);
/*     */     } else {
/* 183 */       setCullHint(Spatial.CullHint.Always);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\ZBufferNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */