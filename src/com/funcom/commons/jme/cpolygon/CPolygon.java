/*     */ package com.funcom.commons.jme.cpolygon;
/*     */ 
/*     */ 
/*     */ public class CPolygon
/*     */ {
/*     */   private CPoint2D[] m_aVertices;
/*     */   
/*     */   public enum PolygonDirection
/*     */   {
/*  10 */     Unknown,
/*  11 */     Clockwise,
/*  12 */     Count_Clockwise;
/*     */   }
/*     */   
/*     */   public enum PolygonType {
/*  16 */     Unknown,
/*  17 */     Convex,
/*  18 */     Concave;
/*     */   }
/*     */   
/*     */   public enum VertexType {
/*  22 */     ErrorPoint,
/*  23 */     ConvexPoint,
/*  24 */     ConcavePoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVertex(int index, CPoint2D v) {
/*  31 */     this.m_aVertices[index] = v;
/*     */   }
/*     */   
/*     */   public CPoint2D getVertex(int index) {
/*  35 */     return this.m_aVertices[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public CPolygon() {}
/*     */ 
/*     */   
/*     */   public CPolygon(CPoint2D[] points) {
/*  43 */     int nNumOfPoitns = points.length;
/*  44 */     if (nNumOfPoitns < 3) {
/*  45 */       throw new IllegalArgumentException("InvalidInputGeometryDataException");
/*     */     }
/*  47 */     this.m_aVertices = new CPoint2D[nNumOfPoitns];
/*  48 */     for (int i = 0; i < nNumOfPoitns; i++) {
/*  49 */       this.m_aVertices[i] = points[i];
/*     */     }
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
/*     */   public int VertexIndex(CPoint2D vertex) {
/*  62 */     int nIndex = -1;
/*     */     
/*  64 */     int nNumPts = this.m_aVertices.length;
/*  65 */     for (int i = 0; i < nNumPts; i++) {
/*     */       
/*  67 */       if (CPoint2D.SamePoints(this.m_aVertices[i], vertex))
/*  68 */         nIndex = i; 
/*     */     } 
/*  70 */     return nIndex;
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
/*     */   public CPoint2D PreviousPoint(CPoint2D vertex) {
/*  85 */     int nIndex = VertexIndex(vertex);
/*  86 */     if (nIndex == -1) {
/*  87 */       return null;
/*     */     }
/*     */     
/*  90 */     if (nIndex == 0) {
/*     */       
/*  92 */       int nPoints = this.m_aVertices.length;
/*  93 */       return this.m_aVertices[nPoints - 1];
/*     */     } 
/*  95 */     return this.m_aVertices[nIndex - 1];
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
/*     */   public CPoint2D NextPoint(CPoint2D vertex) {
/* 109 */     CPoint2D nextPt = new CPoint2D();
/*     */ 
/*     */     
/* 112 */     int nIndex = VertexIndex(vertex);
/* 113 */     if (nIndex == -1) {
/* 114 */       return null;
/*     */     }
/*     */     
/* 117 */     int nNumOfPt = this.m_aVertices.length;
/* 118 */     if (nIndex == nNumOfPt - 1)
/*     */     {
/* 120 */       return this.m_aVertices[0];
/*     */     }
/* 122 */     return this.m_aVertices[nIndex + 1];
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
/*     */   public double PolygonArea() {
/* 141 */     double dblArea = 0.0D;
/* 142 */     int nNumOfVertices = this.m_aVertices.length;
/*     */ 
/*     */     
/* 145 */     for (int i = 0; i < nNumOfVertices; i++) {
/* 146 */       int j = (i + 1) % nNumOfVertices;
/* 147 */       dblArea += this.m_aVertices[i].getX() * this.m_aVertices[j].getY();
/* 148 */       dblArea -= this.m_aVertices[i].getY() * this.m_aVertices[j].getX();
/*     */     } 
/*     */     
/* 151 */     dblArea /= 2.0D;
/* 152 */     return Math.abs(dblArea);
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
/*     */   public static double PolygonArea(CPoint2D[] points) {
/* 174 */     double dblArea = 0.0D;
/* 175 */     int nNumOfPts = points.length;
/*     */ 
/*     */     
/* 178 */     for (int i = 0; i < nNumOfPts; i++) {
/* 179 */       int j = (i + 1) % nNumOfPts;
/* 180 */       dblArea += points[i].getX() * points[j].getY();
/* 181 */       dblArea -= points[i].getY() * points[j].getX();
/*     */     } 
/*     */     
/* 184 */     dblArea /= 2.0D;
/* 185 */     return dblArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexType PolygonVertexType(CPoint2D vertex) {
/* 196 */     VertexType vertexType = VertexType.ErrorPoint;
/*     */     
/* 198 */     if (PolygonVertex(vertex)) {
/* 199 */       CPoint2D pti = vertex;
/* 200 */       CPoint2D ptj = PreviousPoint(vertex);
/* 201 */       CPoint2D ptk = NextPoint(vertex);
/*     */       
/* 203 */       double dArea = PolygonArea(new CPoint2D[] { ptj, pti, ptk });
/*     */       
/* 205 */       if (dArea < 0.0D) {
/* 206 */         vertexType = VertexType.ConvexPoint;
/* 207 */       } else if (dArea > 0.0D) {
/* 208 */         vertexType = VertexType.ConcavePoint;
/*     */       } 
/* 210 */     }  return vertexType;
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
/*     */   public boolean Diagonal(CPoint2D vertex1, CPoint2D vertex2) {
/* 228 */     boolean bDiagonal = false;
/* 229 */     int nNumOfVertices = this.m_aVertices.length;
/* 230 */     int j = 0;
/* 231 */     for (int i = 0; i < nNumOfVertices; i++) {
/*     */       
/* 233 */       bDiagonal = true;
/* 234 */       j = (i + 1) % nNumOfVertices;
/*     */ 
/*     */       
/* 237 */       double x1 = vertex1.getX();
/* 238 */       double y1 = vertex1.getY();
/* 239 */       double x2 = vertex1.getX();
/* 240 */       double y2 = vertex1.getY();
/*     */ 
/*     */       
/* 243 */       double x3 = this.m_aVertices[i].getX();
/* 244 */       double y3 = this.m_aVertices[i].getY();
/* 245 */       double x4 = this.m_aVertices[j].getX();
/* 246 */       double y4 = this.m_aVertices[j].getY();
/*     */       
/* 248 */       double de = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
/* 249 */       double ub = -1.0D;
/*     */       
/* 251 */       if (Math.abs(de - 0.0D) > CPoint2D.smallValue) {
/* 252 */         ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / de;
/*     */       }
/* 254 */       if (ub > 0.0D && ub < 1.0D) {
/* 255 */         bDiagonal = false;
/*     */       }
/*     */     } 
/* 258 */     return bDiagonal;
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
/*     */   public PolygonType GetPolygonType() {
/* 273 */     int nNumOfVertices = this.m_aVertices.length;
/* 274 */     boolean bSignChanged = false;
/* 275 */     int nCount = 0;
/* 276 */     int j = 0, k = 0;
/*     */     
/* 278 */     for (int i = 0; i < nNumOfVertices; i++) {
/* 279 */       j = (i + 1) % nNumOfVertices;
/* 280 */       k = (i + 2) % nNumOfVertices;
/*     */       
/* 282 */       double crossProduct = (this.m_aVertices[j].getX() - this.m_aVertices[i].getX()) * (this.m_aVertices[k].getY() - this.m_aVertices[j].getY());
/*     */       
/* 284 */       crossProduct -= (this.m_aVertices[j].getY() - this.m_aVertices[i].getY()) * (this.m_aVertices[k].getX() - this.m_aVertices[j].getX());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       if (crossProduct > 0.0D && nCount == 0) {
/* 291 */         nCount = 1;
/* 292 */       } else if (crossProduct < 0.0D && nCount == 0) {
/* 293 */         nCount = -1;
/*     */       } 
/* 295 */       if ((nCount == 1 && crossProduct < 0.0D) || (nCount == -1 && crossProduct > 0.0D))
/*     */       {
/* 297 */         bSignChanged = true;
/*     */       }
/*     */     } 
/* 300 */     if (bSignChanged) {
/* 301 */       return PolygonType.Concave;
/*     */     }
/* 303 */     return PolygonType.Convex;
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
/*     */   public boolean PrincipalVertex(CPoint2D vertex) {
/* 317 */     boolean bPrincipal = false;
/* 318 */     if (PolygonVertex(vertex)) {
/*     */       
/* 320 */       CPoint2D pt1 = PreviousPoint(vertex);
/* 321 */       CPoint2D pt2 = NextPoint(vertex);
/*     */       
/* 323 */       if (Diagonal(pt1, pt2))
/* 324 */         bPrincipal = true; 
/*     */     } 
/* 326 */     return bPrincipal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean PolygonVertex(CPoint2D point) {
/* 335 */     boolean bVertex = false;
/* 336 */     int nIndex = VertexIndex(point);
/*     */     
/* 338 */     if (nIndex >= 0 && nIndex <= this.m_aVertices.length - 1) {
/* 339 */       bVertex = true;
/*     */     }
/* 341 */     return bVertex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ReverseVerticesDirection() {
/* 351 */     int nVertices = this.m_aVertices.length;
/* 352 */     CPoint2D[] aTempPts = new CPoint2D[nVertices];
/*     */     int i;
/* 354 */     for (i = 0; i < nVertices; i++) {
/* 355 */       aTempPts[i] = this.m_aVertices[i];
/*     */     }
/* 357 */     for (i = 0; i < nVertices; i++) {
/* 358 */       this.m_aVertices[i] = aTempPts[nVertices - 1 - i];
/*     */     }
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
/*     */   public PolygonDirection VerticesDirection() {
/* 372 */     int nCount = 0, j = 0, k = 0;
/* 373 */     int nVertices = this.m_aVertices.length;
/*     */     
/* 375 */     for (int i = 0; i < nVertices; i++) {
/* 376 */       j = (i + 1) % nVertices;
/* 377 */       k = (i + 2) % nVertices;
/*     */       
/* 379 */       double crossProduct = (this.m_aVertices[j].getX() - this.m_aVertices[i].getX()) * (this.m_aVertices[k].getY() - this.m_aVertices[j].getY());
/*     */       
/* 381 */       crossProduct -= (this.m_aVertices[j].getY() - this.m_aVertices[i].getY()) * (this.m_aVertices[k].getX() - this.m_aVertices[j].getX());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 386 */       if (crossProduct > 0.0D) {
/* 387 */         nCount++;
/*     */       } else {
/* 389 */         nCount--;
/*     */       } 
/*     */     } 
/* 392 */     if (nCount < 0)
/* 393 */       return PolygonDirection.Count_Clockwise; 
/* 394 */     if (nCount > 0) {
/* 395 */       return PolygonDirection.Clockwise;
/*     */     }
/* 397 */     return PolygonDirection.Unknown;
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
/*     */   public static PolygonDirection PointsDirection(CPoint2D[] points) {
/* 411 */     int nCount = 0, j = 0, k = 0;
/* 412 */     int nPoints = points.length;
/*     */     
/* 414 */     if (nPoints < 3) {
/* 415 */       return PolygonDirection.Unknown;
/*     */     }
/* 417 */     for (int i = 0; i < nPoints; i++) {
/* 418 */       j = (i + 1) % nPoints;
/* 419 */       k = (i + 2) % nPoints;
/*     */       
/* 421 */       double crossProduct = (points[j].getX() - points[i].getX()) * (points[k].getY() - points[j].getY());
/*     */       
/* 423 */       crossProduct -= (points[j].getY() - points[i].getY()) * (points[k].getX() - points[j].getX());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 428 */       if (crossProduct > 0.0D) {
/* 429 */         nCount++;
/*     */       } else {
/* 431 */         nCount--;
/*     */       } 
/*     */     } 
/* 434 */     if (nCount < 0)
/* 435 */       return PolygonDirection.Count_Clockwise; 
/* 436 */     if (nCount > 0) {
/* 437 */       return PolygonDirection.Clockwise;
/*     */     }
/* 439 */     return PolygonDirection.Unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ReversePointsDirection(CPoint2D[] points) {
/* 449 */     int nVertices = points.length;
/* 450 */     CPoint2D[] aTempPts = new CPoint2D[nVertices];
/*     */     int i;
/* 452 */     for (i = 0; i < nVertices; i++) {
/* 453 */       aTempPts[i] = points[i];
/*     */     }
/* 455 */     for (i = 0; i < nVertices; i++)
/* 456 */       points[i] = aTempPts[nVertices - 1 - i]; 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\cpolygon\CPolygon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */