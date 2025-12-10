/*     */ package com.funcom.gameengine.view.water;
/*     */ 
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.GLSLShaderObjectsState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jdom.DataConversionException;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaterFactory
/*     */ {
/*     */   public static TextureState createTextureState(ResourceGetter resourceGetter, String baseResource, String overlayResource, String shoreResource) {
/*  20 */     Texture baseTexture = resourceGetter.getTextureCopy(baseResource);
/*  21 */     baseTexture.setWrap(Texture.WrapMode.BorderClamp);
/*     */     
/*  23 */     Texture overlayTexture = resourceGetter.getTextureCopy(overlayResource);
/*  24 */     overlayTexture.setWrap(Texture.WrapMode.BorderClamp);
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
/*  45 */     Texture shoreTexture = resourceGetter.getTextureCopy(shoreResource);
/*  46 */     shoreTexture.setWrap(Texture.WrapMode.BorderClamp);
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
/*  66 */     TextureState waterTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  67 */     waterTextureState.setTexture(baseTexture, 0);
/*  68 */     waterTextureState.setTexture(overlayTexture, 1);
/*  69 */     waterTextureState.setTexture(shoreTexture, 2);
/*     */     
/*  71 */     return waterTextureState;
/*     */   }
/*     */   
/*     */   public static void assignWaterShaders(Spatial spatial, ResourceGetter resourceGetter) {
/*  75 */     GLSLShaderObjectsState so = resourceGetter.getShader("water.glsl");
/*     */     
/*  77 */     so.setUniform("baseTexure", 0);
/*  78 */     so.setUniform("overlayTexure", 1);
/*  79 */     so.setUniform("shoreTexture", 2);
/*  80 */     so.setUniform("transparency", 0.8F);
/*     */     
/*  82 */     spatial.setRenderState((RenderState)so);
/*     */   }
/*     */   
/*     */   public static void setWaterTextureTransparency(Spatial spatial, float transparency) {
/*  86 */     TextureState textureState = (TextureState)spatial.getRenderState(5);
/*  87 */     textureState.getTexture(1).getBlendColor().set(0.0F, 0.0F, 0.0F, transparency);
/*  88 */     spatial.updateRenderState();
/*     */   }
/*     */   
/*     */   public static float getWaterTextureTransparency(Spatial spatial) {
/*  92 */     TextureState textureState = (TextureState)spatial.getRenderState(5);
/*  93 */     return (textureState.getTexture(1).getBlendColor()).a;
/*     */   }
/*     */   
/*     */   public static List<WaterLineCoordinateSet> readWaterLineCoordinates(Element waterLineElement) {
/*     */     try {
/*  98 */       List<WaterLineCoordinateSet> coordSets = new ArrayList<WaterLineCoordinateSet>();
/*  99 */       List<Element> elementSets = waterLineElement.getChildren("coordinate-set");
/* 100 */       for (Element elementSet : elementSets) {
/* 101 */         coordSets.add(new WaterLineCoordinateSet(elementSet));
/*     */       }
/*     */       
/* 104 */       return coordSets;
/* 105 */     } catch (DataConversionException e) {
/* 106 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<WaterPondCoordinateSet> readWaterPondCoordinates(Element waterPondElement) {
/*     */     try {
/* 112 */       List<WaterPondCoordinateSet> coordinateSets = new ArrayList<WaterPondCoordinateSet>();
/* 113 */       List<Element> elementSets = waterPondElement.getChildren("coordinate-set");
/* 114 */       for (Element elementSet : elementSets) {
/* 115 */         coordinateSets.add(new WaterPondCoordinateSet(elementSet));
/*     */       }
/*     */       
/* 118 */       return coordinateSets;
/* 119 */     } catch (DataConversionException e) {
/* 120 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void translateWaterLineCoordinates(List<WaterLineCoordinateSet> coordList, int x, int y) {
/* 125 */     for (WaterLineCoordinateSet coordinateSet : coordList) {
/* 126 */       coordinateSet.addTiles(x, y);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void translateWaterPondCoordinates(List<WaterPondCoordinateSet> coordList, int x, int y) {
/* 131 */     for (WaterPondCoordinateSet coordinateSet : coordList)
/* 132 */       coordinateSet.addTiles(x, y); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\water\WaterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */