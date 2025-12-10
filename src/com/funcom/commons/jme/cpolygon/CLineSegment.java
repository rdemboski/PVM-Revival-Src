/*     */ package com.funcom.commons.jme.cpolygon;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CLineSegment
/*     */   extends CLine
/*     */ {
/*     */   private CPoint2D startPoint;
/*     */   private CPoint2D endPoint;
/*     */   
/*     */   public CPoint2D getStartPoint() {
/*  12 */     return this.startPoint;
/*     */   }
/*     */   
/*     */   public void setStartPoint(CPoint2D startPoint) {
/*  16 */     this.startPoint = startPoint;
/*     */   }
/*     */   
/*     */   public CPoint2D getEndPoint() {
/*  20 */     return this.endPoint;
/*     */   }
/*     */   
/*     */   public void setEndPoint(CPoint2D endPoint) {
/*  24 */     this.endPoint = endPoint;
/*     */   }
/*     */   
/*     */   public CLineSegment(CPoint2D startPoint, CPoint2D endPoint) {
/*  28 */     super(startPoint, endPoint);
/*  29 */     this.startPoint = startPoint;
/*  30 */     this.endPoint = endPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void ChangeLineDirection() {
/*  36 */     CPoint2D tempPt = this.startPoint;
/*  37 */     this.startPoint = this.endPoint;
/*  38 */     this.endPoint = tempPt;
/*     */   }
/*     */ 
/*     */   
/*     */   public double GetLineSegmentLength() {
/*  43 */     double d = (this.endPoint.getX() - this.startPoint.getX()) * (this.endPoint.getX() - this.startPoint.getX());
/*  44 */     d += (this.endPoint.getY() - this.startPoint.getY()) * (this.endPoint.getY() - this.startPoint.getY());
/*  45 */     d = Math.sqrt(d);
/*     */     
/*  47 */     return d;
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
/*     */   public int GetPointLocation(CPoint2D point) {
/*  62 */     double Bx = this.endPoint.getX();
/*  63 */     double By = this.endPoint.getY();
/*     */     
/*  65 */     double Ax = this.startPoint.getX();
/*  66 */     double Ay = this.startPoint.getY();
/*     */     
/*  68 */     double Cx = point.getX();
/*  69 */     double Cy = point.getY();
/*     */     
/*  71 */     if (HorizontalLine()) {
/*  72 */       if (Math.abs(Ay - Cy) < CPoint2D.smallValue)
/*  73 */         return 0; 
/*  74 */       if (Ay > Cy) {
/*  75 */         return -1;
/*     */       }
/*  77 */       return 1;
/*     */     } 
/*     */ 
/*     */     
/*  81 */     if (this.endPoint.getY() > this.startPoint.getY()) {
/*  82 */       ChangeLineDirection();
/*     */     }
/*  84 */     double L = GetLineSegmentLength();
/*  85 */     double s = ((Ay - Cy) * (Bx - Ax) - (Ax - Cx) * (By - Ay)) / L * L;
/*     */ 
/*     */     
/*  88 */     if (Math.abs(s - 0.0D) < CPoint2D.smallValue)
/*  89 */       return 0; 
/*  90 */     if (s > 0.0D) {
/*  91 */       return -1;
/*     */     }
/*  93 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double GetXmin() {
/*  99 */     return Math.min(this.startPoint.getX(), this.endPoint.getX());
/*     */   }
/*     */ 
/*     */   
/*     */   public double GetXmax() {
/* 104 */     return Math.max(this.startPoint.getX(), this.endPoint.getX());
/*     */   }
/*     */ 
/*     */   
/*     */   public double GetYmin() {
/* 109 */     return Math.min(this.startPoint.getY(), this.endPoint.getY());
/*     */   }
/*     */ 
/*     */   
/*     */   public double GetYmax() {
/* 114 */     return Math.max(this.startPoint.getY(), this.endPoint.getY());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean InLine(CLineSegment longerLineSegment) {
/* 119 */     boolean bInLine = false;
/* 120 */     if (this.startPoint.InLine(longerLineSegment) && this.endPoint.InLine(longerLineSegment))
/*     */     {
/* 122 */       bInLine = true; } 
/* 123 */     return bInLine;
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
/*     */   public CLineSegment OffsetLine(double distance, boolean rightOrDown) {
/*     */     CLineSegment line;
/* 139 */     CPoint2D newStartPoint = new CPoint2D();
/* 140 */     CPoint2D newEndPoint = new CPoint2D();
/*     */     
/* 142 */     double alphaInRad = GetLineAngle();
/* 143 */     if (rightOrDown) {
/* 144 */       if (HorizontalLine()) {
/*     */         
/* 146 */         newStartPoint.setX(this.startPoint.getX());
/* 147 */         newStartPoint.setY(this.startPoint.getY() + distance);
/*     */         
/* 149 */         newEndPoint.setX(this.endPoint.getX());
/* 150 */         newEndPoint.setY(this.endPoint.getY() + distance);
/* 151 */         line = new CLineSegment(newStartPoint, newEndPoint);
/*     */       
/*     */       }
/* 154 */       else if (Math.sin(alphaInRad) > 0.0D) {
/* 155 */         newStartPoint.setX(this.startPoint.getX() + Math.abs(distance * Math.sin(alphaInRad)));
/* 156 */         newStartPoint.setY(this.startPoint.getY() - Math.abs(distance * Math.cos(alphaInRad)));
/*     */         
/* 158 */         newEndPoint.setX(this.endPoint.getX() + Math.abs(distance * Math.sin(alphaInRad)));
/* 159 */         newEndPoint.setY(this.endPoint.getY() - Math.abs(distance * Math.cos(alphaInRad)));
/*     */         
/* 161 */         line = new CLineSegment(newStartPoint, newEndPoint);
/*     */       }
/*     */       else {
/*     */         
/* 165 */         newStartPoint.setX(this.startPoint.getX() + Math.abs(distance * Math.sin(alphaInRad)));
/* 166 */         newStartPoint.setY(this.startPoint.getY() + Math.abs(distance * Math.cos(alphaInRad)));
/* 167 */         newEndPoint.setX(this.endPoint.getX() + Math.abs(distance * Math.sin(alphaInRad)));
/* 168 */         newEndPoint.setY(this.endPoint.getY() + Math.abs(distance * Math.cos(alphaInRad)));
/*     */         
/* 170 */         line = new CLineSegment(newStartPoint, newEndPoint);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 177 */     else if (HorizontalLine()) {
/*     */       
/* 179 */       newStartPoint.setX(this.startPoint.getX());
/* 180 */       newStartPoint.setY(this.startPoint.getY() - distance);
/*     */       
/* 182 */       newEndPoint.setX(this.endPoint.getX());
/* 183 */       newEndPoint.setY(this.endPoint.getY() - distance);
/* 184 */       line = new CLineSegment(newStartPoint, newEndPoint);
/*     */ 
/*     */     
/*     */     }
/* 188 */     else if (Math.sin(alphaInRad) >= 0.0D) {
/* 189 */       newStartPoint.setX(this.startPoint.getX() - Math.abs(distance * Math.sin(alphaInRad)));
/* 190 */       newStartPoint.setY(this.startPoint.getY() + Math.abs(distance * Math.cos(alphaInRad)));
/* 191 */       newEndPoint.setX(this.endPoint.getX() - Math.abs(distance * Math.sin(alphaInRad)));
/* 192 */       newEndPoint.setY(this.endPoint.getY() + Math.abs(distance * Math.cos(alphaInRad)));
/*     */       
/* 194 */       line = new CLineSegment(newStartPoint, newEndPoint);
/*     */     }
/*     */     else {
/*     */       
/* 198 */       newStartPoint.setX(this.startPoint.getX() - Math.abs(distance * Math.sin(alphaInRad)));
/* 199 */       newStartPoint.setY(this.startPoint.getY() - Math.abs(distance * Math.cos(alphaInRad)));
/* 200 */       newEndPoint.setX(this.endPoint.getX() - Math.abs(distance * Math.sin(alphaInRad)));
/* 201 */       newEndPoint.setY(this.endPoint.getY() - Math.abs(distance * Math.cos(alphaInRad)));
/*     */       
/* 203 */       line = new CLineSegment(newStartPoint, newEndPoint);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 208 */     return line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean IntersectedWith(CLineSegment line) {
/* 217 */     double x1 = this.startPoint.getX();
/* 218 */     double y1 = this.startPoint.getY();
/* 219 */     double x2 = this.endPoint.getX();
/* 220 */     double y2 = this.endPoint.getY();
/* 221 */     double x3 = line.startPoint.getX();
/* 222 */     double y3 = line.startPoint.getY();
/* 223 */     double x4 = line.endPoint.getX();
/* 224 */     double y4 = line.endPoint.getY();
/*     */     
/* 226 */     double de = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
/*     */     
/* 228 */     if (Math.abs(de - 0.0D) < CPoint2D.smallValue) {
/*     */       
/* 230 */       double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / de;
/* 231 */       double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / de;
/*     */       
/* 233 */       if (ub > 0.0D && ub < 1.0D) {
/* 234 */         return true;
/*     */       }
/* 236 */       return false;
/*     */     } 
/* 238 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\cpolygon\CLineSegment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */