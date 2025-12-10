/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.gameengine.jme.TextSpatial;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class SpatialRow
/*     */ {
/*     */   private static BlendState blendState;
/*     */   
/*     */   private RenderState blending() {
/*  19 */     if (blendState == null) {
/*  20 */       blendState = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/*  21 */       blendState.setBlendEnabled(true);
/*     */     } 
/*  23 */     return (RenderState)blendState;
/*     */   }
/*     */   
/*  26 */   private List<Spatial> spatials = new ArrayList<Spatial>();
/*     */   
/*     */   public void add(Spatial spatial) {
/*  29 */     this.spatials.add(spatial);
/*     */   }
/*     */   
/*     */   public void detachAllFromParent() {
/*  33 */     for (Spatial spatial : this.spatials) {
/*  34 */       spatial.removeFromParent();
/*     */     }
/*     */   }
/*     */   
/*     */   public float getRowHeight() {
/*  39 */     return getMaxHeight(this.spatials);
/*     */   }
/*     */   
/*     */   public void translateY(float height) {
/*  43 */     for (Spatial spatial : this.spatials) {
/*  44 */       (spatial.getLocalTranslation()).y += height;
/*     */     }
/*     */   }
/*     */   
/*     */   private float getMaxHeight(List<Spatial> spatials) {
/*  49 */     float maxHeight = 0.0F;
/*     */     
/*  51 */     for (Spatial spatial : spatials) {
/*  52 */       float height = getHeight(spatial);
/*  53 */       if (height > maxHeight) {
/*  54 */         maxHeight = height;
/*     */       }
/*     */     } 
/*  57 */     return maxHeight;
/*     */   }
/*     */   
/*     */   private float getHeight(Spatial spatial) {
/*  61 */     float height = 0.0F;
/*  62 */     if (spatial instanceof TextSpatial) {
/*  63 */       height = ((TextSpatial)spatial).getHeight();
/*  64 */     } else if (spatial instanceof Quad) {
/*  65 */       height = ((Quad)spatial).getHeight() * (spatial.getLocalScale()).y;
/*     */     } 
/*  67 */     return height;
/*     */   }
/*     */   
/*     */   float getTotalWidth() {
/*  71 */     float tWidth = 0.0F;
/*     */     
/*  73 */     for (Spatial spatial : this.spatials) {
/*  74 */       if (spatial instanceof TextSpatial) {
/*  75 */         tWidth += ((TextSpatial)spatial).getWidth(); continue;
/*  76 */       }  if (spatial instanceof Quad) {
/*  77 */         tWidth += ((Quad)spatial).getWidth() * (spatial.getLocalScale()).x;
/*     */       }
/*     */     } 
/*  80 */     return tWidth;
/*     */   }
/*     */   
/*     */   void layout(Vector3f screenCoords, FloatingSpatials floatingSpatials) {
/*  84 */     screenCoords.z = 0.0F;
/*  85 */     float tWidth = getTotalWidth();
/*  86 */     float rowHeight = getRowHeight();
/*     */     
/*  88 */     screenCoords.x -= tWidth / 2.0F;
/*     */     
/*  90 */     for (Spatial spatial : this.spatials) {
/*  91 */       floatingSpatials.modifyWithDisplacer(screenCoords);
/*  92 */       floatingSpatials.modifyWithRandomDisplacement(screenCoords);
/*     */       
/*  94 */       Vector3f coords = screenCoords.clone();
/*     */       
/*  96 */       float spatialWidth = 0.0F;
/*  97 */       if (spatial instanceof TextSpatial) {
/*  98 */         spatialWidth = ((TextSpatial)spatial).getWidth();
/*  99 */       } else if (spatial instanceof Quad) {
/* 100 */         Quad quad = (Quad)spatial;
/*     */         
/* 102 */         coords.y += quad.getHeight() * (quad.getLocalScale()).y / 2.0F;
/* 103 */         coords.x += quad.getWidth() * (quad.getLocalScale()).x / 2.0F;
/*     */         
/* 105 */         spatialWidth = quad.getWidth() * (quad.getLocalScale()).x;
/*     */       } 
/*     */       
/* 108 */       spatial.setLocalTranslation(coords);
/* 109 */       spatial.setRenderState(blending());
/* 110 */       spatial.updateRenderState();
/* 111 */       floatingSpatials.attachChild(spatial);
/* 112 */       screenCoords.x += spatialWidth;
/*     */       
/* 114 */       float height = getHeight(spatial);
/* 115 */       (spatial.getLocalTranslation()).y += (rowHeight - height) / 2.0F;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\SpatialRow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */