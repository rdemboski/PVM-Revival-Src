/*     */ package com.funcom.tcg.client.ui.maps;
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.tcg.maps.IngameMapUtils;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import java.awt.Color;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.List;
/*     */ 
/*     */ public class MapRegionLabel extends BLabel {
/*  19 */   private String regionID = ""; private MapModel mapModel; private int tileHeight; private int tileWidth;
/*     */   private int minX;
/*     */   private int minY;
/*     */   private int maxX;
/*     */   private int maxY;
/*  24 */   private Rectangle2D hitBounds = new Rectangle2D.Double();
/*     */   
/*     */   public MapRegionLabel() {
/*  27 */     super("");
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/*  32 */     Vector2d vc = new Vector2d(mx, my);
/*  33 */     vc.setY((getHeight() - my));
/*     */     
/*  35 */     WorldCoordinate resultBuf = new WorldCoordinate();
/*  36 */     IngameMapUtils.convertToWorldMap(this.mapModel.getIngameMapData(), getWidth(), getHeight(), vc, resultBuf);
/*     */     
/*  38 */     if (this.hitBounds.contains(resultBuf.getTileX(), resultBuf.getTileY())) {
/*  39 */       return super.getHitComponent(mx, my);
/*     */     }
/*     */     
/*  42 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRegionID() {
/*  47 */     return this.regionID;
/*     */   }
/*     */   
/*     */   public MapRegionLabel createNewRegionImage(MapModel mapModel, String regionID, float newImageHeight, float newImageWidth) {
/*  51 */     this.mapModel = mapModel;
/*  52 */     this.regionID = regionID;
/*     */     
/*  54 */     this.minX = Integer.MAX_VALUE;
/*  55 */     this.minY = Integer.MAX_VALUE;
/*  56 */     this.maxX = 0;
/*  57 */     this.maxY = 0;
/*     */     
/*  59 */     List<WorldCoordinate> coordinateList = (List<WorldCoordinate>)mapModel.getChunkWorldInfo().getRegionMap().get(regionID);
/*  60 */     if (coordinateList != null) {
/*     */       
/*  62 */       BufferedImage tmpImage = new BufferedImage((int)newImageWidth, (int)newImageHeight, 2);
/*     */       
/*  64 */       Graphics2D graphics2D = tmpImage.createGraphics();
/*  65 */       graphics2D.setPaint(new GradientPaint(0.0F, 0.0F, Color.green, 1.0F, 1.0F, Color.yellow, true));
/*  66 */       graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */       
/*  68 */       setTileSize(mapModel, newImageHeight, newImageWidth);
/*     */ 
/*     */       
/*  71 */       for (WorldCoordinate worldCoordinate : coordinateList) {
/*  72 */         updateBounds(worldCoordinate.getTileX(), worldCoordinate.getTileY());
/*     */         
/*  74 */         Vector2d resultVec = new Vector2d();
/*  75 */         IngameMapUtils.convertToIngameMap(mapModel.getIngameMapData(), (int)newImageWidth, (int)newImageHeight, worldCoordinate, resultVec);
/*  76 */         graphics2D.fillOval((int)resultVec.getX() - this.tileWidth / 2, (int)resultVec.getY() - this.tileHeight / 2, this.tileWidth, this.tileHeight);
/*     */       } 
/*     */       
/*  79 */       BImage regionImage = new BImage(tmpImage);
/*     */       
/*  81 */       ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, regionImage);
/*  82 */       setBackground(0, (BBackground)imageBackground);
/*  83 */       setBackground(1, (BBackground)imageBackground);
/*  84 */       setAlpha(0.5F);
/*  85 */       graphics2D.dispose();
/*     */     } 
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   private void updateBounds(double x, double y) {
/*  91 */     this.minX = (int)Math.min(this.minX, x);
/*  92 */     this.minY = (int)Math.min(this.minY, y);
/*  93 */     this.maxX = (int)Math.max(this.maxX, x);
/*  94 */     this.maxY = (int)Math.max(this.maxY, y);
/*     */     
/*  96 */     this.hitBounds.setFrame(this.minX, this.minY, (this.maxX - this.minX), (this.maxY - this.minY));
/*     */   }
/*     */   
/*     */   private void setTileSize(MapModel mapModel, float newImageHeight, float newImageWidth) {
/* 100 */     Vector2d mapObjectCoordsZero = new Vector2d();
/* 101 */     IngameMapUtils.convertToIngameMap(mapModel.getIngameMapData(), (int)newImageWidth, (int)newImageHeight, new WorldCoordinate(0, 0, 0.0D, 0.0D, "", 0), mapObjectCoordsZero);
/*     */ 
/*     */ 
/*     */     
/* 105 */     Vector2d mapObjectCoordsOne = new Vector2d();
/* 106 */     IngameMapUtils.convertToIngameMap(mapModel.getIngameMapData(), (int)newImageWidth, (int)newImageHeight, new WorldCoordinate(0, 1, 0.0D, 0.0D, "", 0), mapObjectCoordsOne);
/*     */ 
/*     */ 
/*     */     
/* 110 */     Vector2d mapObjectCoordstwo = new Vector2d();
/* 111 */     IngameMapUtils.convertToIngameMap(mapModel.getIngameMapData(), (int)newImageWidth, (int)newImageHeight, new WorldCoordinate(0, 0, 1.0D, 0.0D, "", 0), mapObjectCoordstwo);
/*     */ 
/*     */ 
/*     */     
/* 115 */     Vector2d mapObjectCoordsthree = new Vector2d();
/* 116 */     IngameMapUtils.convertToIngameMap(mapModel.getIngameMapData(), (int)newImageWidth, (int)newImageHeight, new WorldCoordinate(0, 1, 1.0D, 0.0D, "", 0), mapObjectCoordsthree);
/*     */ 
/*     */ 
/*     */     
/* 120 */     int minimumX = Math.min(Math.min((int)mapObjectCoordsZero.getX(), (int)mapObjectCoordsOne.getX()), Math.min((int)mapObjectCoordstwo.getX(), (int)mapObjectCoordsthree.getX()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     int minimumY = Math.min(Math.min((int)mapObjectCoordsZero.getY(), (int)mapObjectCoordsOne.getY()), Math.min((int)mapObjectCoordstwo.getY(), (int)mapObjectCoordsthree.getY()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     int maximumX = Math.max(Math.max((int)mapObjectCoordsZero.getX(), (int)mapObjectCoordsOne.getX()), Math.max((int)mapObjectCoordstwo.getX(), (int)mapObjectCoordsthree.getX()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     int maximumY = Math.max(Math.max((int)mapObjectCoordsZero.getY(), (int)mapObjectCoordsOne.getY()), Math.max((int)mapObjectCoordstwo.getY(), (int)mapObjectCoordsthree.getY()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     this.tileWidth = maximumX - minimumX + 1;
/* 141 */     this.tileHeight = maximumY - minimumY + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTooltipText() {
/* 146 */     return this.regionID;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTooltipRelativeToMouse() {
/* 151 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\maps\MapRegionLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */