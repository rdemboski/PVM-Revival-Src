/*     */ package com.funcom.commons;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.text.NumberFormat;
/*     */ 
/*     */ 
/*     */ public class Vector2d
/*     */   implements Cloneable
/*     */ {
/*  12 */   public static final Vector2d NULL = new Vector2d();
/*     */   public static final double NULL_TOLERANCE = 1.0E-6D;
/*     */   private double x;
/*     */   private double y;
/*     */   
/*     */   public Vector2d() {
/*  18 */     this.x = 0.0D;
/*  19 */     this.y = 0.0D;
/*     */   }
/*     */   
/*     */   public Vector2d(double x, double y) {
/*  23 */     this.x = x;
/*  24 */     this.y = y;
/*     */   }
/*     */   
/*     */   public Vector2d(Vector2d vec) {
/*  28 */     this.x = vec.x;
/*  29 */     this.y = vec.y;
/*     */   }
/*     */   
/*     */   public Vector2d(Point vec) {
/*  33 */     this.x = vec.x;
/*  34 */     this.y = vec.y;
/*     */   }
/*     */   
/*     */   public Vector2d(Point2D vec) {
/*  38 */     this.x = vec.getX();
/*  39 */     this.y = vec.getY();
/*     */   }
/*     */   
/*     */   public Vector2d(Point vecFrom, Point vecTo) {
/*  43 */     this.x = (vecTo.x - vecFrom.x);
/*  44 */     this.y = (vecTo.y - vecFrom.y);
/*     */   }
/*     */   
/*     */   public Vector2d(Line2D line) {
/*  48 */     this.x = line.getX2() - line.getX1();
/*  49 */     this.y = line.getY2() - line.getY1();
/*     */   }
/*     */   
/*     */   public double getX() {
/*  53 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  57 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/*  61 */     this.x = x;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/*  65 */     this.y = y;
/*     */   }
/*     */   
/*     */   public void set(double x, double y) {
/*  69 */     this.x = x;
/*  70 */     this.y = y;
/*     */   }
/*     */   
/*     */   public void set(Line2D line) {
/*  74 */     this.x = line.getX2() - line.getX1();
/*  75 */     this.y = line.getY2() - line.getY1();
/*     */   }
/*     */   
/*     */   public void setLocation(Vector2d vector2d) {
/*  79 */     setLocation(vector2d.getX(), vector2d.getY());
/*     */   }
/*     */   
/*     */   public void setLocation(double x, double y) {
/*  83 */     this.x = x;
/*  84 */     this.y = y;
/*     */   }
/*     */   
/*     */   public double lengthSq() {
/*  88 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */   
/*     */   public double length() {
/*  92 */     return Math.sqrt(lengthSq());
/*     */   }
/*     */   
/*     */   public void add(Vector2d vec) {
/*  96 */     add(vec.getX(), vec.getY());
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(double x, double y) {
/* 101 */     this.x += x;
/* 102 */     this.y += y;
/*     */   }
/*     */   
/*     */   public Vector2d addRet(Vector2d vec) {
/* 106 */     return new Vector2d(this.x + vec.x, this.y + vec.y);
/*     */   }
/*     */   
/*     */   public Vector2d sub(Vector2d vec) {
/* 110 */     return sub(vec.getX(), vec.getY());
/*     */   }
/*     */   
/*     */   @Operator
/*     */   public Vector2d sub(double x, double y) {
/* 115 */     this.x -= x;
/* 116 */     this.y -= y;
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   public Vector2d subRet(Vector2d vec) {
/* 121 */     return new Vector2d(this.x - vec.x, this.y - vec.y);
/*     */   }
/*     */   
/*     */   public Vector2d mult(double s) {
/* 125 */     this.x *= s;
/* 126 */     this.y *= s;
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   public Vector2d mult(double mx, double my) {
/* 131 */     this.x *= mx;
/* 132 */     this.y *= my;
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   public Vector2d multRet(double s) {
/* 137 */     return new Vector2d(this.x * s, this.y * s);
/*     */   }
/*     */   
/*     */   public void mult(Vector2d vec, double s) {
/* 141 */     this.x *= s;
/* 142 */     this.y *= s;
/*     */   }
/*     */   
/*     */   public Vector2d normalize() {
/* 146 */     if (!isNull()) {
/* 147 */       double length = length();
/* 148 */       this.x /= length;
/* 149 */       this.y /= length;
/*     */     } 
/* 151 */     return this;
/*     */   }
/*     */   
/*     */   public Vector2d normalizeRet() {
/* 155 */     if (!isNull()) {
/* 156 */       double length = length();
/* 157 */       return new Vector2d(this.x / length, this.y / length);
/*     */     } 
/* 159 */     return new Vector2d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNull() {
/* 164 */     return (Math.abs(this.x) < 1.0E-6D && Math.abs(this.y) < 1.0E-6D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNull() {
/* 169 */     this.x = 0.0D;
/* 170 */     this.y = 0.0D;
/*     */   }
/*     */   
/*     */   public double dotProduct(Vector2d vec) {
/* 174 */     return this.x * vec.x + this.y * vec.y;
/*     */   }
/*     */   
/*     */   public Vector2d setNull(Coordinate pos) {
/* 178 */     if (pos == Coordinate.coord_x) {
/* 179 */       this.x = 0.0D;
/* 180 */     } else if (pos == Coordinate.coord_y) {
/* 181 */       this.y = 0.0D;
/*     */     } 
/* 183 */     return this;
/*     */   }
/*     */   
/*     */   public Point2D asPoint() {
/* 187 */     return new Point2D.Double(this.x, this.y);
/*     */   }
/*     */   
/*     */   public void copyFrom(Vector2d vec) {
/* 191 */     this.x = vec.x;
/* 192 */     this.y = vec.y;
/*     */   }
/*     */   
/*     */   public double angle() {
/* 196 */     return Math.atan2(this.y, this.x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decompose(double targetAngle) {
/* 205 */     double thisAngle = angle();
/* 206 */     double displ = length() * Math.cos(thisAngle - targetAngle);
/* 207 */     set(Math.cos(targetAngle) * displ, Math.sin(targetAngle) * displ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decompose(Vector2d targetVector) {
/* 216 */     decompose(targetVector.angle());
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
/*     */   public void decompose(double targetAngle, double dist) {
/* 229 */     if (dist > length()) {
/*     */       return;
/*     */     }
/*     */     
/* 233 */     double thisAngle = angle();
/* 234 */     double remDist = length() - dist;
/* 235 */     double displ = remDist * Math.cos(thisAngle - targetAngle);
/*     */     
/* 237 */     normalize();
/* 238 */     mult(dist);
/* 239 */     add(Math.cos(targetAngle) * displ, Math.sin(targetAngle) * displ);
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
/*     */   public Vector2d decompose(double targetAngle, double dist, Vector2d result) {
/* 251 */     result.copyFrom(this);
/* 252 */     result.decompose(targetAngle, dist);
/* 253 */     return result;
/*     */   }
/*     */   
/*     */   public void project(Vector2d vec) {
/* 257 */     double dp = dotProduct(vec);
/* 258 */     double vsq = vec.lengthSq();
/* 259 */     this.x = dp / vsq * vec.getX();
/* 260 */     this.y = dp / vsq * vec.getY();
/*     */   }
/*     */   
/*     */   public Vector2d projectRet(Vector2d vec) {
/* 264 */     Vector2d ret = new Vector2d(this);
/* 265 */     ret.project(vec);
/* 266 */     return ret;
/*     */   }
/*     */   
/*     */   public double distanceTo(double otherx, double othery) {
/* 270 */     double offx = this.x - otherx;
/* 271 */     double offy = this.y - othery;
/*     */     
/* 273 */     return Math.sqrt(offx * offx + offy * offy);
/*     */   }
/*     */   
/*     */   public double distanceTo(Point2D point) {
/* 277 */     return distanceTo(point.getX(), point.getY());
/*     */   }
/*     */   
/*     */   public double distanceTo(Vector2d otherVector) {
/* 281 */     return distanceTo(otherVector.x, otherVector.y);
/*     */   }
/*     */   
/*     */   public double angleTo(double otherx, double othery) {
/* 285 */     return Math.atan2(this.y - othery, this.x - otherx);
/*     */   }
/*     */   
/*     */   public double angleTo(Point2D point) {
/* 289 */     return angleTo(point.getX(), point.getY());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 294 */     if (this == o) return true; 
/* 295 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 297 */     Vector2d vector2d = (Vector2d)o;
/*     */     
/* 299 */     if (Double.compare(vector2d.x, this.x) != 0) return false; 
/* 300 */     if (Double.compare(vector2d.y, this.y) != 0) return false;
/*     */     
/* 302 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 309 */     long temp = (this.x != 0.0D) ? Double.doubleToLongBits(this.x) : 0L;
/* 310 */     int result = (int)(temp ^ temp >>> 32L);
/* 311 */     temp = (this.y != 0.0D) ? Double.doubleToLongBits(this.y) : 0L;
/* 312 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 313 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Vector2d otherVector) {
/* 317 */     return (otherVector != null && Math.abs(otherVector.x - this.x) < 0.001D && Math.abs(otherVector.y - this.y) < 0.001D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Vector2d otherVector) {
/* 323 */     this.x = otherVector.x;
/* 324 */     this.y = otherVector.y;
/*     */   }
/*     */   
/*     */   public void rotate(double v) {
/* 328 */     double cos = Math.cos(v);
/* 329 */     double sin = Math.sin(v);
/* 330 */     double xm = this.x * cos - this.y * sin;
/* 331 */     double ym = this.x * sin + this.y * cos;
/* 332 */     this.x = xm;
/* 333 */     this.y = ym;
/*     */   }
/*     */   
/*     */   public enum Coordinate {
/* 337 */     coord_x, coord_y;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 341 */     return "Vector2D[x=" + this.x + ",y=" + this.y + "]";
/*     */   }
/*     */   
/*     */   public String toNiceString() {
/* 345 */     return "[x=" + NumberFormat.getNumberInstance().format(this.x) + ", y=" + NumberFormat.getNumberInstance().format(this.y) + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2d clone() {
/*     */     try {
/* 351 */       return (Vector2d)super.clone();
/* 352 */     } catch (CloneNotSupportedException e) {
/* 353 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\Vector2d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */