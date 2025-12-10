/*     */ package com.funcom.commons;
/*     */ 
/*     */ import com.funcom.commons.geom.LineWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.Collection;
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
/*     */ public class MathUtils
/*     */ {
/*     */   public static final double TWO_PI = 6.283185307179586D;
/*     */   public static final double HALF_PI = 1.5707963267948966D;
/*     */   public static final double QUARTER_PI = 0.7853981633974483D;
/*     */   private static final float DEFAULT_FLOAT_COMPARISON_TOLERANCE = 1.0E-6F;
/*     */   
/*     */   public static double getAngleDiff(double angleFrom, double angleTo) {
/*  24 */     double diff = angleTo - angleFrom;
/*  25 */     while (diff < -3.141592653589793D) {
/*  26 */       diff += 6.283185307179586D;
/*     */     }
/*  28 */     while (diff > Math.PI) {
/*  29 */       diff -= 6.283185307179586D;
/*     */     }
/*     */     
/*  32 */     return diff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getLineAngle(LineWC line) {
/*  42 */     return Math.atan2(((line.getWC2().getTileCoord()).y - (line.getWC1().getTileCoord()).y) + line.getWC2().getTileOffset().getY() - line.getWC1().getTileOffset().getY(), ((line.getWC2().getTileCoord()).x - (line.getWC1().getTileCoord()).x) + line.getWC2().getTileOffset().getX() - line.getWC1().getTileOffset().getX());
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
/*     */   public static double getPointAngle(WorldCoordinate fromPoint, WorldCoordinate toPoint) {
/*  56 */     return Math.atan2(((toPoint.getTileCoord()).y - (fromPoint.getTileCoord()).y) + toPoint.getTileOffset().getY() - fromPoint.getTileOffset().getY(), ((toPoint.getTileCoord()).x - (fromPoint.getTileCoord()).x) + toPoint.getTileOffset().getX() - fromPoint.getTileOffset().getX());
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
/*     */   public static double distancePointToSegment(WorldCoordinate point, LineWC line) {
/*  71 */     return nearestPointOnSegment(point, line).getPoint().distanceTo(point);
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
/*     */   public static NearestPointWC nearestPointOnSegment(WorldCoordinate point, LineWC line) {
/*  85 */     WorldCoordinate v = (new WorldCoordinate(line.getWC2())).subtract(line.getWC1());
/*  86 */     WorldCoordinate w = (new WorldCoordinate(point)).subtract(line.getWC1());
/*     */     
/*  88 */     double c1 = w.dotProduct(v);
/*  89 */     if (c1 <= 0.0D) {
/*  90 */       return new NearestPointWC(line.getWC1(), line, 0);
/*     */     }
/*     */     
/*  93 */     double c2 = v.dotProduct(v);
/*  94 */     if (c2 <= c1) {
/*  95 */       return new NearestPointWC(line.getWC2(), line, 0);
/*     */     }
/*     */ 
/*     */     
/*  99 */     Double b = new Double(c1 / c2);
/*     */     
/* 101 */     return new NearestPointWC((new WorldCoordinate(line.getWC1())).add(v.multLocal(b.doubleValue())), line, 1);
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
/*     */   public static NearestPointWC nearestPointOnSegment(WorldCoordinate point, WorldCoordinate line1, WorldCoordinate line2) {
/* 119 */     return nearestPointOnSegment(point, new LineWC(line1, line2));
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
/*     */   public static double pointOrentationToLine(WorldCoordinate point, WorldCoordinate line1, WorldCoordinate line2) {
/* 133 */     return (((point.getTileCoord()).y - (line1.getTileCoord()).y) + point.getTileOffset().getY() - line1.getTileOffset().getY()) * (((line2.getTileCoord()).x - (line1.getTileCoord()).x) + line2.getTileOffset().getX() - line1.getTileOffset().getX()) - (((point.getTileCoord()).x - (line1.getTileCoord()).x) + point.getTileOffset().getX() - line1.getTileOffset().getX()) * (((line2.getTileCoord()).y - (line1.getTileCoord()).y) + line2.getTileOffset().getY() - line1.getTileOffset().getY());
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
/*     */   public static double pointOrentationToLine(WorldCoordinate point, LineWC line) {
/* 149 */     return pointOrentationToLine(point, line.getWC1(), line.getWC2());
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
/*     */   public static int signum(double value) {
/* 161 */     if (value < 0.0D) {
/* 162 */       return -1;
/*     */     }
/* 164 */     if (value > 0.0D) {
/* 165 */       return 1;
/*     */     }
/* 167 */     return 0;
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
/*     */   public static boolean compareFloats(float a, float b) {
/* 179 */     return compareFloats(a, b, 1.0E-6F);
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
/*     */   public static boolean compareFloats(float a, float b, float tolerance) {
/* 192 */     return (Math.abs(a - b) < tolerance);
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
/*     */   public static WorldCoordinate getCenterCoord(WorldCoordinate coord1, WorldCoordinate coord2) {
/* 204 */     return new WorldCoordinate(((coord1.getTileCoord()).x + (coord2.getTileCoord()).x) / 2, ((coord1.getTileCoord()).y + (coord2.getTileCoord()).y) / 2, (coord1.getTileOffset().getX() + coord2.getTileOffset().getX() + (((coord1.getTileCoord()).x + (coord2.getTileCoord()).x) % 2)) / 2.0D, (coord1.getTileOffset().getY() + coord2.getTileOffset().getY() + (((coord1.getTileCoord()).y + (coord2.getTileCoord()).y) % 2)) / 2.0D, coord1.getMapId(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Collection<T> setDisjunction(Collection<T> a, Collection<T> b) {
/* 215 */     Class<? extends Collection> classOfA = (Class)a.getClass();
/*     */     try {
/* 217 */       Collection<T> returnableCollection = classOfA.newInstance();
/* 218 */       for (T o : a) {
/* 219 */         if (!b.contains(o)) {
/* 220 */           returnableCollection.add(o);
/*     */         }
/*     */       } 
/* 223 */       for (T o : b) {
/* 224 */         if (!a.contains(o)) {
/* 225 */           returnableCollection.add(o);
/*     */         }
/*     */       } 
/* 228 */       return returnableCollection;
/* 229 */     } catch (InstantiationException e) {
/* 230 */       throw new RuntimeException(e);
/* 231 */     } catch (IllegalAccessException e) {
/* 232 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\MathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */