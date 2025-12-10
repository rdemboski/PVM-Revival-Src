/*    */ package com.funcom.tcg.client.controllers;
/*    */ 
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Geometry;
/*    */ import com.jme.scene.TexCoords;
/*    */ 
/*    */ public class GeometryFrameAnimationController
/*    */   extends Controller {
/*    */   private Geometry geometry;
/*    */   private int frames;
/*    */   private int fps;
/*    */   private float currentTime;
/*    */   private float frameWidth;
/*    */   private int frame;
/*    */   
/*    */   public GeometryFrameAnimationController(Geometry geometry) {
/* 17 */     this.geometry = geometry;
/* 18 */     this.currentTime = 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   private void updateTextures() {
/* 23 */     for (TexCoords textureBuffer : this.geometry.getTextureCoords()) {
/*    */       
/* 25 */       for (int i = 0, n = textureBuffer.coords.capacity(); i < n; i += 2) {
/* 26 */         textureBuffer.coords.put(i, textureBuffer.coords.get(i) / this.frames);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setFrames(int frames) {
/* 32 */     this.frames = frames;
/* 33 */     this.frameWidth = 1.0F / frames;
/* 34 */     updateTextures();
/*    */   }
/*    */   
/*    */   public int getFrames() {
/* 38 */     return this.frames;
/*    */   }
/*    */   
/*    */   public void setFps(int fps) {
/* 42 */     this.fps = fps;
/*    */   }
/*    */   
/*    */   public int getFps() {
/* 46 */     return this.fps;
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 50 */     this.currentTime += time;
/* 51 */     float totalTime = this.frames / this.fps;
/*    */     
/* 53 */     while (this.currentTime > totalTime) {
/* 54 */       this.currentTime -= totalTime;
/*    */     }
/*    */     
/* 57 */     int prevFrame = this.frame;
/* 58 */     this.frame = (int)(this.currentTime * this.fps);
/* 59 */     int frameDiff = this.frame - prevFrame;
/*    */ 
/*    */     
/* 62 */     for (TexCoords textureBuffer : this.geometry.getTextureCoords()) {
/*    */       
/* 64 */       for (int i = 0, n = textureBuffer.coords.capacity(); i < n; i += 2)
/* 65 */         textureBuffer.coords.put(i, textureBuffer.coords.get(i) + this.frameWidth * frameDiff); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\controllers\GeometryFrameAnimationController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */