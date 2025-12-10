/*     */ package com.funcom.commons.jme.cpolygon;
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
/*     */ public class CLine
/*     */ {
/*     */   protected double a;
/*     */   protected double b;
/*     */   protected double c;
/*     */   
/*     */   private void initialize(Double angleInRad, CPoint2D point) {
/*     */     try {
/*  23 */       if (angleInRad.doubleValue() > 6.283185307179586D) {
/*  24 */         String errMsg = String.format("The input line angle %s is wrong. It should be between 0-2*PI.", new Object[] { angleInRad });
/*     */ 
/*     */         
/*  27 */         throw new Exception(errMsg);
/*     */       } 
/*  29 */       if (Math.abs(angleInRad.doubleValue() - 1.5707963267948966D) < CPoint2D.smallValue) {
/*     */         
/*  31 */         this.a = 1.0D;
/*  32 */         this.b = 0.0D;
/*  33 */         this.c = -point.getX();
/*     */       } else {
/*     */         
/*  36 */         this.a = -Math.tan(angleInRad.doubleValue());
/*  37 */         this.b = 1.0D;
/*  38 */         this.c = -this.a * point.getX() - this.b * point.getY();
/*     */       }
/*     */     
/*  41 */     } catch (Exception e) {
/*  42 */       System.out.println(e.getMessage());
/*  43 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public CLine(Double angleInRad, CPoint2D point) {
/*  48 */     initialize(angleInRad, point);
/*     */   }
/*     */   
/*     */   public CLine(CPoint2D point1, CPoint2D point2) {
/*     */     try {
/*  53 */       if (CPoint2D.SamePoints(point1, point2)) {
/*  54 */         String errMsg = "The input points are the same";
/*  55 */         throw new Exception(errMsg);
/*     */       } 
/*     */       
/*  58 */       if (Math.abs(point1.getX() - point2.getX()) < CPoint2D.smallValue) {
/*     */ 
/*     */         
/*  61 */         initialize(Double.valueOf(1.5707963267948966D), point1);
/*  62 */       } else if (Math.abs(point1.getY() - point2.getY()) < CPoint2D.smallValue) {
/*     */ 
/*     */         
/*  65 */         initialize(Double.valueOf(0.0D), point1);
/*     */       } else {
/*     */         
/*  68 */         double m = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
/*  69 */         double alphaInRad = Math.atan(m);
/*  70 */         initialize(Double.valueOf(alphaInRad), point1);
/*     */       }
/*     */     
/*  73 */     } catch (Exception e) {
/*  74 */       System.out.println(e.getMessage());
/*  75 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public CLine(CLine copiedLine) {
/*  80 */     this.a = copiedLine.a;
/*  81 */     this.b = copiedLine.b;
/*  82 */     this.c = copiedLine.c;
/*     */   }
/*     */ 
/*     */   
/*     */   public double GetDistance(CPoint2D point) {
/*  87 */     double x0 = point.getX();
/*  88 */     double y0 = point.getY();
/*  89 */     double d = Math.abs(this.a * x0 + this.b * y0 + this.c);
/*  90 */     d /= Math.sqrt(this.a * this.a + this.b * this.b);
/*  91 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double GetX(double y) {
/*     */     double d;
/*     */     try {
/*  99 */       if (Math.abs(this.a) < CPoint2D.smallValue)
/*     */       {
/* 101 */         throw new Exception("Non valid return exception!");
/*     */       }
/*     */       
/* 104 */       d = -(this.b * y + this.c) / this.a;
/*     */     }
/* 106 */     catch (Exception e) {
/*     */       
/* 108 */       d = Double.NaN;
/* 109 */       System.out.println("Not a Number!");
/* 110 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 113 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double GetY(double x) {
/*     */     double d;
/*     */     try {
/* 121 */       if (Math.abs(this.b) < CPoint2D.smallValue) {
/* 122 */         throw new Exception("Non valid return exception!");
/*     */       }
/* 124 */       d = -(this.a * x + this.c) / this.b;
/*     */     }
/* 126 */     catch (Exception e) {
/* 127 */       d = Double.NaN;
/* 128 */       System.out.println("Not a Number!");
/* 129 */       e.printStackTrace();
/*     */     } 
/* 131 */     return d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean VerticalLine() {
/* 136 */     if (Math.abs(this.b - 0.0D) < CPoint2D.smallValue) {
/* 137 */       return true;
/*     */     }
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean HorizontalLine() {
/* 144 */     if (Math.abs(this.a - 0.0D) < CPoint2D.smallValue) {
/* 145 */       return true;
/*     */     }
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public double GetLineAngle() {
/* 152 */     if (this.b == 0.0D) {
/* 153 */       return 1.5707963267948966D;
/*     */     }
/*     */     
/* 156 */     double tanA = -this.a / this.b;
/* 157 */     return Math.atan(tanA);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean Parallel(CLine line) {
/* 162 */     boolean bParallel = false;
/* 163 */     if (this.a / this.b == line.a / line.b) {
/* 164 */       bParallel = true;
/*     */     }
/* 166 */     return bParallel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CPoint2D IntersecctionWith(CLine line) {
/* 176 */     CPoint2D point = new CPoint2D();
/* 177 */     double a1 = this.a;
/* 178 */     double b1 = this.b;
/* 179 */     double c1 = this.c;
/*     */     
/* 181 */     double a2 = line.a;
/* 182 */     double b2 = line.b;
/* 183 */     double c2 = line.c;
/*     */     
/* 185 */     if (!Parallel(line)) {
/*     */       
/* 187 */       point.setX((c2 * b1 - c1 * b2) / (a1 * b2 - a2 * b1));
/* 188 */       point.setY((a1 * c2 - c1 * a2) / (a2 * b2 - a1 * b2));
/*     */     } 
/* 190 */     return point;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\cpolygon\CLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */