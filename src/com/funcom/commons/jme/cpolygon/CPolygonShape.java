/*     */ package com.funcom.commons.jme.cpolygon;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ 
/*     */ public class CPolygonShape
/*     */ {
/*  11 */   private static final Logger LOGGER = Logger.getLogger(CPolygonShape.class.getName());
/*     */   
/*     */   private CPoint2D[] inputVertices;
/*     */   
/*     */   private CPoint2D[] updatedPolygonVertices;
/*  16 */   private ArrayList earsList = new ArrayList();
/*     */   
/*     */   private CPoint2D[][] polygons;
/*     */   
/*     */   public int GetNumberOfPolygons() {
/*  21 */     return this.polygons.length;
/*     */   }
/*     */   
/*     */   public CPoint2D[] Polygons(int index) {
/*  25 */     if (index < this.polygons.length) {
/*  26 */       return this.polygons[index];
/*     */     }
/*  28 */     return null;
/*     */   }
/*     */   
/*     */   public CPolygonShape(CPoint2D[] vertices) {
/*  32 */     int nVertices = vertices.length;
/*  33 */     if (nVertices < 3) {
/*  34 */       throw new IllegalArgumentException("To make a cpolygon,  at least 3 points are required!");
/*     */     }
/*     */ 
/*     */     
/*  38 */     this.inputVertices = new CPoint2D[nVertices];
/*     */     
/*  40 */     for (int i = 0; i < nVertices; i++) {
/*  41 */       this.inputVertices[i] = vertices[i];
/*     */     }
/*     */ 
/*     */     
/*  45 */     SetUpdatedPolygonVertices();
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
/*     */   private void SetUpdatedPolygonVertices() {
/*  58 */     int nVertices = this.inputVertices.length;
/*  59 */     this.updatedPolygonVertices = new CPoint2D[nVertices];
/*     */     
/*  61 */     for (int i = 0; i < nVertices; i++) {
/*  62 */       this.updatedPolygonVertices[i] = this.inputVertices[i];
/*     */     }
/*     */     
/*  65 */     if (CPolygon.PointsDirection(this.updatedPolygonVertices) == CPolygon.PolygonDirection.Clockwise)
/*     */     {
/*  67 */       CPolygon.ReversePointsDirection(this.updatedPolygonVertices);
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
/*     */   private boolean TriangleContainsPoint(CPoint2D[] trianglePts, CPoint2D pt) {
/*  80 */     if (trianglePts.length != 3) {
/*  81 */       return false;
/*     */     }
/*  83 */     for (int i = 0; i < trianglePts.length; i++) {
/*  84 */       if (pt.EqualsPoint(trianglePts[i])) {
/*  85 */         return true;
/*     */       }
/*     */     } 
/*  88 */     boolean bIn = false;
/*     */     
/*  90 */     CLineSegment line0 = new CLineSegment(trianglePts[0], trianglePts[1]);
/*  91 */     CLineSegment line1 = new CLineSegment(trianglePts[1], trianglePts[2]);
/*  92 */     CLineSegment line2 = new CLineSegment(trianglePts[2], trianglePts[0]);
/*     */     
/*  94 */     if (pt.InLine(line0) || pt.InLine(line1) || pt.InLine(line2)) {
/*     */       
/*  96 */       bIn = true;
/*     */     } else {
/*     */       
/*  99 */       double dblArea0 = CPolygon.PolygonArea(new CPoint2D[] { trianglePts[0], trianglePts[1], pt });
/*     */       
/* 101 */       double dblArea1 = CPolygon.PolygonArea(new CPoint2D[] { trianglePts[1], trianglePts[2], pt });
/*     */       
/* 103 */       double dblArea2 = CPolygon.PolygonArea(new CPoint2D[] { trianglePts[2], trianglePts[0], pt });
/*     */ 
/*     */       
/* 106 */       if (dblArea0 > 0.0D) {
/* 107 */         if (dblArea1 > 0.0D && dblArea2 > 0.0D)
/* 108 */           bIn = true; 
/* 109 */       } else if (dblArea0 < 0.0D && 
/* 110 */         dblArea1 < 0.0D && dblArea2 < 0.0D) {
/* 111 */         bIn = true;
/*     */       } 
/*     */     } 
/* 114 */     return bIn;
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
/*     */   private boolean IsEarOfUpdatedPolygon(CPoint2D vertex) {
/* 130 */     CPolygon polygon = new CPolygon(this.updatedPolygonVertices);
/*     */     
/* 132 */     if (polygon.PolygonVertex(vertex)) {
/* 133 */       boolean bEar = true;
/* 134 */       if (polygon.PolygonVertexType(vertex) == CPolygon.VertexType.ConvexPoint) {
/* 135 */         CPoint2D pi = vertex;
/* 136 */         CPoint2D pj = polygon.PreviousPoint(vertex);
/* 137 */         CPoint2D pk = polygon.NextPoint(vertex);
/*     */         
/* 139 */         for (int i = 0; i < this.updatedPolygonVertices.length; i++) {
/* 140 */           CPoint2D pt = this.updatedPolygonVertices[i];
/* 141 */           if (!pt.EqualsPoint(pi) && !pt.EqualsPoint(pj) && !pt.EqualsPoint(pk) && 
/* 142 */             TriangleContainsPoint(new CPoint2D[] { pj, pi, pk }, pt)) {
/* 143 */             bEar = false;
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         
/* 148 */         bEar = false;
/* 149 */       }  return bEar;
/*     */     } 
/*     */     
/* 152 */     LOGGER.log((Priority)Level.INFO, "IsEarOfUpdatedPolygon: Not a cpolygon vertex  [ " + vertex.getX() + " : " + vertex.getY() + " ]");
/*     */     
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void SetPolygons() {
/* 165 */     int nPolygon = this.earsList.size() + 1;
/* 166 */     this.polygons = new CPoint2D[nPolygon][];
/*     */     int i;
/* 168 */     for (i = 0; i < nPolygon - 1; i++) {
/*     */       
/* 170 */       CPoint2D[] points = (CPoint2D[]) this.earsList.get(i);
/*     */       
/* 172 */       this.polygons[i] = new CPoint2D[3];
/* 173 */       this.polygons[i][0] = points[0];
/* 174 */       this.polygons[i][1] = points[1];
/* 175 */       this.polygons[i][2] = points[2];
/*     */     } 
/*     */ 
/*     */     
/* 179 */     this.polygons[nPolygon - 1] = new CPoint2D[this.updatedPolygonVertices.length];
/*     */ 
/*     */     
/* 182 */     for (i = 0; i < this.updatedPolygonVertices.length; i++) {
/* 183 */       this.polygons[nPolygon - 1][i] = this.updatedPolygonVertices[i];
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
/*     */   private void UpdatePolygonVertices(CPoint2D vertex) {
/* 195 */     ArrayList<CPoint2D> alTempPts = new ArrayList();
/*     */     
/* 197 */     for (int i = 0; i < this.updatedPolygonVertices.length; i++) {
/* 198 */       if (vertex.EqualsPoint(this.updatedPolygonVertices[i])) {
/*     */ 
/*     */         
/* 201 */         CPolygon polygon = new CPolygon(this.updatedPolygonVertices);
/* 202 */         CPoint2D pti = vertex;
/* 203 */         CPoint2D ptj = polygon.PreviousPoint(vertex);
/* 204 */         CPoint2D ptk = polygon.NextPoint(vertex);
/*     */         
/* 206 */         CPoint2D[] aEar = new CPoint2D[3];
/* 207 */         aEar[0] = ptj;
/* 208 */         aEar[1] = pti;
/* 209 */         aEar[2] = ptk;
/*     */         
/* 211 */         this.earsList.add(aEar);
/*     */       } else {
/* 213 */         alTempPts.add(this.updatedPolygonVertices[i]);
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     if (this.updatedPolygonVertices.length - alTempPts.size() == 1) {
/*     */       
/* 219 */       int nLength = this.updatedPolygonVertices.length;
/* 220 */       this.updatedPolygonVertices = new CPoint2D[nLength - 1];
/*     */       
/* 222 */       for (int j = 0; j < alTempPts.size(); j++) {
/* 223 */         this.updatedPolygonVertices[j] = alTempPts.get(j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void CutEar() {
/* 234 */     CPolygon polygon = new CPolygon(this.updatedPolygonVertices);
/* 235 */     boolean bFinish = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     if (this.updatedPolygonVertices.length == 3) {
/* 241 */       bFinish = true;
/*     */     }
/* 243 */     CPoint2D pt = new CPoint2D();
/* 244 */     while (!bFinish) {
/*     */       
/* 246 */       int i = 0;
/* 247 */       boolean bNotFound = true;
/*     */       
/* 249 */       while (bNotFound && i < this.updatedPolygonVertices.length) {
/*     */         
/* 251 */         pt = this.updatedPolygonVertices[i];
/* 252 */         if (IsEarOfUpdatedPolygon(pt)) {
/* 253 */           bNotFound = false; continue;
/*     */         } 
/* 255 */         i++;
/*     */       } 
/*     */       
/* 258 */       if (pt != null) {
/* 259 */         UpdatePolygonVertices(pt);
/*     */       }
/* 261 */       polygon = new CPolygon(this.updatedPolygonVertices);
/*     */ 
/*     */       
/* 264 */       if (this.updatedPolygonVertices.length == 3)
/* 265 */         bFinish = true; 
/*     */     } 
/* 267 */     SetPolygons();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\cpolygon\CPolygonShape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */