/*     */ package com.funcom.commons.geom;
/*     */ 
/*     */ import com.funcom.commons.MathUtils;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ public class PolygonWC
/*     */ {
/*  22 */   private List<WorldCoordinate> vertices = new ArrayList<WorldCoordinate>();
/*     */   public PolygonWC() {}
/*     */   
/*     */   public PolygonWC(WorldCoordinate... vertices) {
/*  26 */     this();
/*  27 */     this.vertices.addAll(Arrays.asList(vertices));
/*     */   }
/*     */   
/*     */   public PolygonWC(int capacity) {
/*  31 */     this();
/*  32 */     for (int i = 0; i < capacity; i++) {
/*  33 */       this.vertices.add(new WorldCoordinate());
/*     */     }
/*     */   }
/*     */   
/*     */   public void addVertex(WorldCoordinate worldCoordinate) {
/*  38 */     this.vertices.add(worldCoordinate);
/*     */   }
/*     */   
/*     */   public void addAllVertices(Collection<? extends WorldCoordinate> c) {
/*  42 */     this.vertices.addAll(c);
/*     */   }
/*     */   
/*     */   public void setVertex(int index, WorldCoordinate worldCoordinate) {
/*  46 */     ((WorldCoordinate)this.vertices.get(index)).set(worldCoordinate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsPoint(WorldCoordinate point) {
/*  57 */     for (int i = 0; i < this.vertices.size() - 1; i++) {
/*  58 */       if (MathUtils.pointOrentationToLine(point, this.vertices.get(i), this.vertices.get(i + 1)) <= 0.0D) {
/*  59 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  63 */     if (MathUtils.pointOrentationToLine(point, this.vertices.get(this.vertices.size() - 1), this.vertices.get(0)) <= 0.0D) {
/*  64 */       return false;
/*     */     }
/*     */     
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   public boolean intersectsLine(LineWC line) {
/*  71 */     if (containsPoint(line.getWC1())) {
/*  72 */       return true;
/*     */     }
/*  74 */     if (containsPoint(line.getWC2())) {
/*  75 */       return true;
/*     */     }
/*     */     
/*  78 */     LineWC polyLine = new LineWC();
/*  79 */     for (int i = 0; i < this.vertices.size() - 1; i++) {
/*  80 */       polyLine.setLine(this.vertices.get(i), this.vertices.get(i + 1));
/*  81 */       if (line.intersectsLine(polyLine)) {
/*  82 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  86 */     polyLine.setLine(this.vertices.get(this.vertices.size() - 1), this.vertices.get(0));
/*  87 */     if (line.intersectsLine(polyLine)) {
/*  88 */       return true;
/*     */     }
/*     */     
/*  91 */     return false;
/*     */   }
/*     */   
/*     */   public RectangleWC getBoundsWC() {
/*  95 */     if (this.vertices.isEmpty()) {
/*  96 */       throw new IllegalStateException("Vertices has not been defined for this polygon");
/*     */     }
/*     */     
/*  99 */     WorldCoordinate origin = null;
/* 100 */     WorldCoordinate extent = null;
/*     */     
/* 102 */     for (WorldCoordinate vertex : this.vertices) {
/* 103 */       if (origin == null) {
/* 104 */         origin = new WorldCoordinate(vertex);
/* 105 */         extent = new WorldCoordinate(vertex); continue;
/*     */       } 
/* 107 */       if (WorldUtils.compareToHorizontal(vertex, origin) < 0) {
/* 108 */         origin.set((vertex.getTileCoord()).x, (origin.getTileCoord()).y, vertex.getTileOffset().getX(), origin.getTileOffset().getY());
/* 109 */       } else if (WorldUtils.compareToHorizontal(vertex, extent) > 0) {
/* 110 */         extent.set((vertex.getTileCoord()).x, (extent.getTileCoord()).y, vertex.getTileOffset().getX(), extent.getTileOffset().getY());
/*     */       } 
/*     */       
/* 113 */       if (WorldUtils.compareToVertical(vertex, origin) < 0) {
/* 114 */         origin.set((origin.getTileCoord()).x, (vertex.getTileCoord()).y, origin.getTileOffset().getX(), vertex.getTileOffset().getY()); continue;
/* 115 */       }  if (WorldUtils.compareToVertical(vertex, extent) > 0) {
/* 116 */         extent.set((extent.getTileCoord()).x, (vertex.getTileCoord()).y, extent.getTileOffset().getX(), vertex.getTileOffset().getY());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 121 */     return new RectangleWC(origin, extent);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\geom\PolygonWC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */