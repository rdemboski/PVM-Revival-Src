/*    */ package com.funcom.tcg.client.controllers;
/*    */ 
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Geometry;
/*    */ import com.jme.scene.TexCoords;
/*    */ 
/*    */ public class GeometryUVAnimationController
/*    */   extends Controller {
/*    */   private Geometry geometry;
/*    */   private Vector3f translation;
/*    */   
/*    */   public GeometryUVAnimationController(Geometry geometry) {
/* 14 */     this.geometry = geometry;
/*    */     
/* 16 */     this.translation = Vector3f.ZERO;
/*    */     
/* 18 */     geometry.unlock();
/*    */   }
/*    */   
/*    */   public void setTranslationDelta(Vector3f translation) {
/* 22 */     this.translation = translation;
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 26 */     boolean incx = false;
/* 27 */     boolean decx = false;
/* 28 */     boolean incy = false;
/* 29 */     boolean decy = false;
/*    */ 
/*    */     
/* 32 */     for (TexCoords textureBuffer : this.geometry.getTextureCoords()) {
/*    */       int i;
/*    */       int n;
/* 35 */       for (i = 0, n = textureBuffer.coords.capacity(); i < n; i += 2) {
/* 36 */         float x = textureBuffer.coords.get(i);
/* 37 */         x += this.translation.getX() * time * getSpeed();
/* 38 */         textureBuffer.coords.put(i, x);
/*    */         
/* 40 */         float y = textureBuffer.coords.get(i + 1);
/* 41 */         y += this.translation.getY() * time * getSpeed();
/* 42 */         textureBuffer.coords.put(i + 1, y);
/*    */         
/* 44 */         if (x < 0.0F) {
/* 45 */           incx = true;
/* 46 */         } else if (x > 2.0F) {
/* 47 */           decx = true;
/*    */         } 
/*    */         
/* 50 */         if (y < 0.0F) {
/* 51 */           incy = true;
/* 52 */         } else if (y > 2.0F) {
/* 53 */           decy = true;
/*    */         } 
/*    */       } 
/*    */       
/* 57 */       if (incx || decx || incy || decy)
/* 58 */         for (i = 0, n = textureBuffer.coords.capacity(); i < n; i += 2) {
/* 59 */           if (incx) {
/* 60 */             textureBuffer.coords.put(i, textureBuffer.coords.get(i) + 1.0F);
/* 61 */           } else if (decx) {
/* 62 */             textureBuffer.coords.put(i, textureBuffer.coords.get(i) - 1.0F);
/*    */           } 
/* 64 */           if (incy) {
/* 65 */             textureBuffer.coords.put(i + 1, textureBuffer.coords.get(i + 1) + 1.0F);
/* 66 */           } else if (decy) {
/* 67 */             textureBuffer.coords.put(i + 1, textureBuffer.coords.get(i + 1) - 1.0F);
/*    */           } 
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\controllers\GeometryUVAnimationController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */