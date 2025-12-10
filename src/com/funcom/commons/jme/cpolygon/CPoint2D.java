/*     */ package com.funcom.commons.jme.cpolygon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CPoint2D
/*     */ {
/*     */   private double x;
/*     */   private double y;
/*  10 */   public static double smallValue = 1.0E-5D;
/*  11 */   public static double bigValue = 99999.0D;
/*     */ 
/*     */   
/*     */   public CPoint2D() {}
/*     */   
/*     */   public CPoint2D(double x, double y) {
/*  17 */     this.x = x;
/*  18 */     this.y = y;
/*     */   }
/*     */   
/*     */   public double getX() {
/*  22 */     return this.x;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/*  26 */     this.x = x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  30 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/*  34 */     this.y = y;
/*     */   }
/*     */   
/*     */   public static boolean SamePoints(CPoint2D point1, CPoint2D point2) {
/*  38 */     double dDeff_X = Math.abs(point1.getX() - point2.getX());
/*  39 */     double dDeff_Y = Math.abs(point1.getY() - point2.getY());
/*  40 */     if (dDeff_X < smallValue && dDeff_Y < smallValue) {
/*  41 */       return true;
/*     */     }
/*  43 */     return false;
/*     */   }
/*     */   
/*     */   public boolean EqualsPoint(CPoint2D newPoint) {
/*  47 */     double dDeff_X = Math.abs(this.x - newPoint.getX());
/*  48 */     double dDeff_Y = Math.abs(this.y - newPoint.getY());
/*     */     
/*  50 */     if (dDeff_X < smallValue && dDeff_Y < smallValue) {
/*  51 */       return true;
/*     */     }
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean InLine(CLineSegment lineSegment) {
/*  59 */     boolean inline = false;
/*     */     
/*  61 */     double Bx = lineSegment.getEndPoint().getX();
/*  62 */     double By = lineSegment.getEndPoint().getY();
/*  63 */     double Ax = lineSegment.getStartPoint().getX();
/*  64 */     double Ay = lineSegment.getStartPoint().getY();
/*  65 */     double Cx = this.x;
/*  66 */     double Cy = this.y;
/*  67 */     double L = lineSegment.GetLineSegmentLength();
/*  68 */     double s = Math.abs(((Ay - Cy) * (Bx - Ax) - (Ax - Cx) * (By - Ay)) / L * L);
/*  69 */     if (Math.abs(s - 0.0D) < smallValue)
/*  70 */       if (SamePoints(this, lineSegment.getStartPoint()) || SamePoints(this, lineSegment.getEndPoint())) {
/*     */         
/*  72 */         inline = true;
/*     */       }
/*  74 */       else if (Cx < lineSegment.GetXmax() && Cx > lineSegment.GetXmin() && Cy < lineSegment.GetYmax() && Cy > lineSegment.GetYmin()) {
/*  75 */         inline = true;
/*     */       }  
/*  77 */     return inline;
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistanceTo(CPoint2D point) {
/*  82 */     return Math.sqrt((point.getX() - getX()) * (point.getX() - getX()) + (point.getY() - getY()) * (point.getY() - getY()));
/*     */   }
/*     */   
/*     */   public boolean PointInsidePolygon(CPoint2D[] polygonVertices) {
/*  86 */     if (polygonVertices.length < 3)
/*  87 */       return false; 
/*  88 */     int nCounter = 0;
/*  89 */     int nPoints = polygonVertices.length;
/*     */     
/*  91 */     CPoint2D s1 = this;
/*  92 */     CPoint2D p1 = polygonVertices[0];
/*  93 */     for (int i = 1; i < nPoints; i++) {
/*  94 */       CPoint2D p2 = polygonVertices[i % nPoints];
/*  95 */       if (s1.getY() > Math.min(p1.getY(), p2.getY()) && 
/*  96 */         s1.getY() <= Math.max(p1.getY(), p2.getY()) && 
/*  97 */         s1.getX() <= Math.max(p1.getX(), p2.getX()) && 
/*  98 */         p1.getY() != p2.getY()) {
/*  99 */         double getXInters = (s1.getY() - p1.getY()) * (p2.getX() - p1.getX()) / (p2.getY() - p1.getY()) + p1.getX();
/* 100 */         if (p1.getX() == p2.getX() || s1.getX() <= getXInters) {
/* 101 */           nCounter++;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 107 */       p1 = p2;
/*     */     } 
/* 109 */     if (nCounter % 2 == 0) {
/* 110 */       return false;
/*     */     }
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void SortPointsByX(CPoint2D[] points) {
/* 117 */     if (points.length > 1)
/*     */     {
/* 119 */       for (int i = 0; i < points.length - 2; i++) {
/* 120 */         for (int j = i + 1; j < points.length - 1; j++) {
/* 121 */           if (points[i].getX() > points[j].getX()) {
/* 122 */             CPoint2D tempPt = points[j];
/* 123 */             points[j] = points[i];
/* 124 */             points[i] = tempPt;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void SortPointsByY(CPoint2D[] points) {
/* 133 */     if (points.length > 1)
/*     */     {
/* 135 */       for (int i = 0; i < points.length - 2; i++) {
/* 136 */         for (int j = i + 1; j < points.length - 1; j++) {
/* 137 */           if (points[i].getY() > points[j].getY()) {
/* 138 */             CPoint2D tempPt = points[j];
/* 139 */             points[j] = points[i];
/* 140 */             points[i] = tempPt;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\cpolygon\CPoint2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */