/*     */ package com.funcom.commons.geom;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.WorldUtils;
/*     */ import java.awt.geom.Point2D;
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
/*     */ public class LineWC
/*     */ {
/*  20 */   private WorldCoordinate wc1 = new WorldCoordinate();
/*  21 */   private WorldCoordinate wc2 = new WorldCoordinate();
/*  22 */   private RectangleWC bounds = new RectangleWC();
/*     */ 
/*     */   
/*     */   public LineWC(WorldCoordinate start, WorldCoordinate end) {
/*  26 */     this();
/*  27 */     this.wc1.set(start);
/*  28 */     this.wc2.set(end);
/*     */   }
/*     */   public LineWC() {}
/*     */   public LineWC(int txStart, int tyStart, double oxStart, double oyStart, int txEnd, int tyEnd, double oxEnd, double oyEnd) {
/*  32 */     this();
/*  33 */     this.wc1.set(txStart, tyStart, oxStart, oyStart);
/*  34 */     this.wc2.set(txEnd, tyEnd, oxEnd, oyEnd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX1() {
/*  43 */     return WorldUtils.getScreenX(this.wc1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY1() {
/*  52 */     return WorldUtils.getScreenY(this.wc1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2D getP1() {
/*  61 */     return WorldUtils.getScreenPoint(this.wc1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX2() {
/*  70 */     return WorldUtils.getScreenX(this.wc2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY2() {
/*  79 */     return WorldUtils.getScreenY(this.wc2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2D getP2() {
/*  88 */     return WorldUtils.getScreenPoint(this.wc2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCoordinate getWC1() {
/*  97 */     return this.wc1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCoordinate getWC2() {
/* 106 */     return this.wc2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLine(WorldCoordinate start, WorldCoordinate end) {
/* 116 */     this.wc1.set(start);
/* 117 */     this.wc2.set(end);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleWC getBounds() {
/* 126 */     int compareHorizontal = WorldUtils.compareToHorizontal(this.wc1, this.wc2);
/* 127 */     int compareVertical = WorldUtils.compareToVertical(this.wc1, this.wc2);
/*     */     
/* 129 */     if (compareHorizontal > 0 && compareVertical > 0) {
/* 130 */       this.bounds.setRect(this.wc2, this.wc1);
/* 131 */     } else if (compareHorizontal > 0) {
/* 132 */       this.bounds.setRect(new WorldCoordinate((this.wc2.getTileCoord()).x, (this.wc1.getTileCoord()).y, this.wc2.getTileOffset().getX(), this.wc1.getTileOffset().getY(), this.wc1.getMapId(), this.wc1.getInstanceReference()), new WorldCoordinate((this.wc1.getTileCoord()).x, (this.wc2.getTileCoord()).y, this.wc1.getTileOffset().getX(), this.wc2.getTileOffset().getY(), this.wc2.getMapId(), this.wc2.getInstanceReference()));
/*     */     
/*     */     }
/* 135 */     else if (compareVertical > 0) {
/* 136 */       this.bounds.setRect(new WorldCoordinate((this.wc1.getTileCoord()).x, (this.wc2.getTileCoord()).y, this.wc1.getTileOffset().getX(), this.wc2.getTileOffset().getY(), this.wc1.getMapId(), this.wc1.getInstanceReference()), new WorldCoordinate((this.wc2.getTileCoord()).x, (this.wc1.getTileCoord()).y, this.wc2.getTileOffset().getX(), this.wc1.getTileOffset().getY(), this.wc2.getMapId(), this.wc2.getInstanceReference()));
/*     */     }
/*     */     else {
/*     */       
/* 140 */       this.bounds.setRect(this.wc1, this.wc2);
/*     */     } 
/*     */     
/* 143 */     return this.bounds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(RectangleWC r) {
/* 154 */     return r.intersectsLine(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int relativeCCW(WorldCoordinate point, LineWC line) {
/* 190 */     double x2 = ((line.getWC2().getTileCoord()).x - (line.getWC1().getTileCoord()).x) + line.getWC2().getTileOffset().getX() - line.getWC1().getTileOffset().getX();
/*     */     
/* 192 */     double y2 = ((line.getWC2().getTileCoord()).y - (line.getWC1().getTileCoord()).y) + line.getWC2().getTileOffset().getY() - line.getWC1().getTileOffset().getY();
/*     */     
/* 194 */     double px = ((point.getTileCoord()).x - (line.getWC1().getTileCoord()).x) + point.getTileOffset().getX() - line.getWC1().getTileOffset().getX();
/*     */     
/* 196 */     double py = ((point.getTileCoord()).y - (line.getWC1().getTileCoord()).y) + point.getTileOffset().getY() - line.getWC1().getTileOffset().getY();
/*     */ 
/*     */     
/* 199 */     double ccw = px * y2 - py * x2;
/* 200 */     if (ccw == 0.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 207 */       ccw = px * x2 + py * y2;
/* 208 */       if (ccw > 0.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 216 */         px -= x2;
/* 217 */         py -= y2;
/* 218 */         ccw = px * x2 + py * y2;
/* 219 */         if (ccw < 0.0D) {
/* 220 */           ccw = 0.0D;
/*     */         }
/*     */       } 
/*     */     } 
/* 224 */     return (ccw < 0.0D) ? -1 : ((ccw > 0.0D) ? 1 : 0);
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
/*     */   public static boolean linesIntersect(LineWC line1, LineWC line2) {
/* 239 */     return (relativeCCW(line1.getWC1(), line2) * relativeCCW(line1.getWC2(), line2) <= 0 && relativeCCW(line2.getWC1(), line1) * relativeCCW(line2.getWC2(), line1) <= 0);
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
/*     */   public boolean intersectsLine(LineWC line) {
/* 254 */     return linesIntersect(this, line);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\geom\LineWC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */