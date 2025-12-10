/*     */ package com.funcom.gameengine.collisiondetection;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.jme.cpolygon.CPoint2D;
/*     */ import com.funcom.commons.jme.cpolygon.CPolygonShape;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.TransparentAlphaState;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TriMesh;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Area extends PropNode {
/*     */   private ArrayList<CPoint2D> points;
/*  22 */   private CPoint2D boundBox2DMin = new CPoint2D();
/*  23 */   private CPoint2D boundBox2DMax = new CPoint2D();
/*     */   private TriMesh polygon;
/*     */   
/*     */   public TriMesh getPolygon() {
/*  27 */     return this.polygon;
/*     */   }
/*     */   
/*     */   public CPoint2D getBoundBox2DMin() {
/*  31 */     return this.boundBox2DMin;
/*     */   }
/*     */   
/*     */   public CPoint2D getBoundBox2DMax() {
/*  35 */     return this.boundBox2DMax;
/*     */   }
/*     */   
/*     */   private void initBoundingBox(CPoint2D node) {
/*  39 */     this.boundBox2DMin.setX(node.getX());
/*  40 */     this.boundBox2DMin.setY(node.getY());
/*  41 */     this.boundBox2DMax.setX(node.getX());
/*  42 */     this.boundBox2DMax.setY(node.getY());
/*     */   }
/*     */   
/*     */   private void updateBoundingBox() {
/*  46 */     if (this.points.size() > 0) {
/*  47 */       initBoundingBox(this.points.get(0));
/*  48 */       for (int i = 1; i < this.points.size(); i++) {
/*  49 */         this.boundBox2DMin.setX(Math.min(this.boundBox2DMin.getX(), ((CPoint2D)this.points.get(i)).getX()));
/*  50 */         this.boundBox2DMin.setY(Math.min(this.boundBox2DMin.getY(), ((CPoint2D)this.points.get(i)).getY()));
/*  51 */         this.boundBox2DMax.setX(Math.max(this.boundBox2DMax.getX(), ((CPoint2D)this.points.get(i)).getX()));
/*  52 */         this.boundBox2DMax.setY(Math.max(this.boundBox2DMax.getY(), ((CPoint2D)this.points.get(i)).getY()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void constructPolygon() {
/*  61 */     CPolygonShape cutPolygon = new CPolygonShape(this.points.<CPoint2D>toArray(new CPoint2D[0]));
/*  62 */     cutPolygon.CutEar();
/*     */     
/*  64 */     int nTriangles = 0;
/*  65 */     for (int i = 0; i < cutPolygon.GetNumberOfPolygons(); i++) {
/*  66 */       int nPoints = (cutPolygon.Polygons(i)).length;
/*  67 */       nTriangles += (nPoints >= 3) ? (nPoints - 2) : 0;
/*     */     } 
/*  69 */     ColorRGBA color = new ColorRGBA(0.0F, 0.0F, 1.0F, 0.3F);
/*  70 */     this.polygon.setRenderState((RenderState)TransparentAlphaState.get());
/*  71 */     ArrayList<ColorRGBA> colors = new ArrayList<ColorRGBA>();
/*  72 */     ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
/*  73 */     IntBuffer indices = IntBuffer.allocate(nTriangles * 3);
/*  74 */     for (int j = 0; j < cutPolygon.GetNumberOfPolygons(); j++) {
/*  75 */       int nPoints = (cutPolygon.Polygons(j)).length;
/*  76 */       CPoint2D[] iPolygon = cutPolygon.Polygons(j);
/*  77 */       int nIndexBase = vertices.size();
/*     */       
/*  79 */       if (nPoints >= 3) {
/*  80 */         vertices.add(new Vector3f((float)iPolygon[0].getX(), 0.005F, (float)iPolygon[0].getY()));
/*  81 */         colors.add(color);
/*  82 */         vertices.add(new Vector3f((float)iPolygon[1].getX(), 0.005F, (float)iPolygon[1].getY()));
/*  83 */         colors.add(color);
/*  84 */         for (int k = 2; k < nPoints; k++) {
/*  85 */           vertices.add(new Vector3f((float)iPolygon[k].getX(), 0.005F, (float)iPolygon[k].getY()));
/*  86 */           colors.add(color);
/*  87 */           indices.put(nIndexBase);
/*  88 */           indices.put(nIndexBase + k - 1);
/*  89 */           indices.put(nIndexBase + k);
/*     */         } 
/*     */       } 
/*     */     } 
/*  93 */     this.polygon.reconstruct(BufferUtils.createFloatBuffer(vertices.<Vector3f>toArray(new Vector3f[0])), null, BufferUtils.createFloatBuffer(colors.<ColorRGBA>toArray(new ColorRGBA[0])), null, indices);
/*     */   }
/*     */ 
/*     */   
/*     */   public Area(Prop prop, String resourceName, List<CPoint2D> points, DireEffectDescriptionFactory effectDescriptionFactory) {
/*  98 */     super(prop, 0, resourceName, effectDescriptionFactory);
/*  99 */     this.polygon = new TriMesh(prop.getName());
/* 100 */     attachRepresentation((Spatial)this.polygon);
/* 101 */     this.points = new ArrayList<CPoint2D>(points);
/* 102 */     updateBoundingBox();
/* 103 */     constructPolygon();
/*     */   }
/*     */   
/*     */   public static boolean boundingBoxesCollision2D(CPoint2D box1Min, CPoint2D box1Max, CPoint2D box2Min, CPoint2D box2Max) {
/* 107 */     CPoint2D boxIntersectMin = new CPoint2D();
/* 108 */     boxIntersectMin.setX(Math.max(box1Min.getX(), box2Min.getX()));
/* 109 */     boxIntersectMin.setY(Math.max(box1Min.getY(), box2Min.getY()));
/* 110 */     CPoint2D boxIntersectMax = new CPoint2D();
/* 111 */     boxIntersectMax.setX(Math.min(box1Max.getX(), box2Max.getX()));
/* 112 */     boxIntersectMax.setY(Math.min(box1Max.getY(), box2Max.getY()));
/* 113 */     return (boxIntersectMin.getX() <= boxIntersectMax.getX() && boxIntersectMin.getY() <= boxIntersectMax.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean trianglesCollision2D(TriMesh polygon1, TriMesh polygon2) {
/* 120 */     Vector3f[] triVertsGroupPolygon1 = polygon1.getMeshAsTrianglesVertices(new Vector3f[0]);
/* 121 */     for (int i = 0; i < triVertsGroupPolygon1.length / 3; i++) {
/* 122 */       Vector2f p1 = new Vector2f((triVertsGroupPolygon1[i * 3]).x, (triVertsGroupPolygon1[i * 3]).z);
/* 123 */       Vector2f q1 = new Vector2f((triVertsGroupPolygon1[i * 3 + 1]).x, (triVertsGroupPolygon1[i * 3 + 1]).z);
/* 124 */       Vector2f r1 = new Vector2f((triVertsGroupPolygon1[i * 3 + 2]).x, (triVertsGroupPolygon1[i * 3 + 2]).z);
/*     */ 
/*     */       
/* 127 */       Vector3f[] triVertsGroupPolygon2 = polygon2.getMeshAsTrianglesVertices(new Vector3f[0]);
/* 128 */       for (int j = 0; j < triVertsGroupPolygon2.length / 3; j++) {
/* 129 */         Vector2f p2 = new Vector2f((triVertsGroupPolygon2[j * 3]).x, (triVertsGroupPolygon2[j * 3]).z);
/* 130 */         Vector2f q2 = new Vector2f((triVertsGroupPolygon2[j * 3 + 1]).x, (triVertsGroupPolygon2[j * 3 + 1]).z);
/* 131 */         Vector2f r2 = new Vector2f((triVertsGroupPolygon2[j * 3 + 2]).x, (triVertsGroupPolygon2[j * 3 + 2]).z);
/* 132 */         if (triTriOverlapTest2D(p1, q1, r1, p2, q2, r2))
/* 133 */           return true; 
/*     */       } 
/*     */     } 
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean lineTriangleCollision2D(Vector2f lineStart, Vector2f lineEnd, TriMesh polygon) {
/* 141 */     Vector3f[] triVertsGroupPolygon = polygon.getMeshAsTrianglesVertices(new Vector3f[0]);
/* 142 */     for (int j = 0; j < triVertsGroupPolygon.length / 3; j++) {
/* 143 */       Vector2f p2 = new Vector2f((triVertsGroupPolygon[j * 3]).x, (triVertsGroupPolygon[j * 3]).z);
/* 144 */       Vector2f q2 = new Vector2f((triVertsGroupPolygon[j * 3 + 1]).x, (triVertsGroupPolygon[j * 3 + 1]).z);
/* 145 */       Vector2f r2 = new Vector2f((triVertsGroupPolygon[j * 3 + 2]).x, (triVertsGroupPolygon[j * 3 + 2]).z);
/* 146 */       if (triTriOverlapTest2D(lineStart, lineEnd, lineStart, p2, q2, r2))
/* 147 */         return true; 
/*     */     } 
/* 149 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean pointCollision(Vector2f point, TriMesh polygon) {
/* 153 */     Vector3f[] triVertsGroupPolygon = polygon.getMeshAsTrianglesVertices(new Vector3f[0]);
/* 154 */     for (int j = 0; j < triVertsGroupPolygon.length / 3; j++) {
/* 155 */       Vector2f p2 = new Vector2f((triVertsGroupPolygon[j * 3]).x, (triVertsGroupPolygon[j * 3]).z);
/* 156 */       Vector2f q2 = new Vector2f((triVertsGroupPolygon[j * 3 + 1]).x, (triVertsGroupPolygon[j * 3 + 1]).z);
/* 157 */       Vector2f r2 = new Vector2f((triVertsGroupPolygon[j * 3 + 2]).x, (triVertsGroupPolygon[j * 3 + 2]).z);
/* 158 */       if (triTriOverlapTest2D(point, point, point, p2, q2, r2))
/* 159 */         return true; 
/*     */     } 
/* 161 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean triTriOverlapTest2D(Vector2f p1, Vector2f q1, Vector2f r1, Vector2f p2, Vector2f q2, Vector2f r2) {
/* 165 */     if (orient2D(p1, q1, r1) < 0.0F) {
/* 166 */       if (orient2D(p2, q2, r2) < 0.0F) {
/* 167 */         return ccwTriTriIntersection2D(p1, r1, q1, p2, r2, q2);
/*     */       }
/* 169 */       return ccwTriTriIntersection2D(p1, r1, q1, p2, q2, r2);
/* 170 */     }  if (orient2D(p2, q2, r2) < 0.0F) {
/* 171 */       return ccwTriTriIntersection2D(p1, q1, r1, p2, r2, q2);
/*     */     }
/* 173 */     return ccwTriTriIntersection2D(p1, q1, r1, p2, q2, r2);
/*     */   }
/*     */   
/*     */   public static float orient2D(Vector2f vert1, Vector2f vert2, Vector2f vert3) {
/* 177 */     return (vert1.x - vert3.x) * (vert2.y - vert3.y) - (vert1.y - vert3.y) * (vert2.x - vert3.x);
/*     */   }
/*     */   
/*     */   public static boolean intersectionTestVertex(Vector2f P1, Vector2f Q1, Vector2f R1, Vector2f P2, Vector2f Q2, Vector2f R2) {
/* 181 */     if (orient2D(R2, P2, Q1) >= 0.0F) {
/* 182 */       if (orient2D(R2, Q2, Q1) <= 0.0F) {
/* 183 */         if (orient2D(P1, P2, Q1) > 0.0F) {
/* 184 */           return (orient2D(P1, Q2, Q1) <= 0.0F);
/*     */         }
/* 186 */         return (orient2D(P1, P2, R1) >= 0.0F && orient2D(Q1, R1, P2) >= 0.0F);
/*     */       } 
/* 188 */       return (orient2D(P1, Q2, Q1) <= 0.0F && orient2D(R2, Q2, R1) <= 0.0F && orient2D(Q1, R1, Q2) >= 0.0F);
/* 189 */     }  if (orient2D(R2, P2, R1) >= 0.0F) {
/* 190 */       if (orient2D(Q1, R1, R2) >= 0.0F)
/* 191 */         return (orient2D(P1, P2, R1) >= 0.0F); 
/* 192 */       return (orient2D(Q1, R1, Q2) >= 0.0F && orient2D(R2, R1, Q2) >= 0.0F);
/*     */     } 
/* 194 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean intersectionTestEdge(Vector2f P1, Vector2f Q1, Vector2f R1, Vector2f P2, Vector2f Q2, Vector2f R2) {
/* 198 */     if (orient2D(R2, P2, Q1) >= 0.0F) {
/* 199 */       if (orient2D(P1, P2, Q1) >= 0.0F) {
/* 200 */         return (orient2D(P1, Q1, R2) >= 0.0F);
/*     */       }
/* 202 */       return (orient2D(Q1, R1, P2) >= 0.0F && orient2D(R1, P1, P2) >= 0.0F);
/*     */     } 
/*     */     
/* 205 */     return (orient2D(R2, P2, R1) >= 0.0F && orient2D(P1, P2, R1) >= 0.0F && (orient2D(P1, R1, R2) >= 0.0F || orient2D(Q1, R1, R2) >= 0.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean ccwTriTriIntersection2D(Vector2f p1, Vector2f q1, Vector2f r1, Vector2f p2, Vector2f q2, Vector2f r2) {
/* 211 */     if (orient2D(p2, q2, p1) >= 0.0F) {
/* 212 */       if (orient2D(q2, r2, p1) >= 0.0F) {
/* 213 */         return (orient2D(r2, p2, p1) >= 0.0F || intersectionTestEdge(p1, q1, r1, p2, q2, r2));
/*     */       }
/* 215 */       if (orient2D(r2, p2, p1) >= 0.0F) {
/* 216 */         return intersectionTestEdge(p1, q1, r1, r2, p2, q2);
/*     */       }
/* 218 */       return intersectionTestVertex(p1, q1, r1, p2, q2, r2);
/*     */     } 
/*     */     
/* 221 */     if (orient2D(q2, r2, p1) >= 0.0F) {
/* 222 */       if (orient2D(r2, p2, p1) >= 0.0F) {
/* 223 */         return intersectionTestEdge(p1, q1, r1, q2, r2, p2);
/*     */       }
/* 225 */       return intersectionTestVertex(p1, q1, r1, q2, r2, p2);
/*     */     } 
/* 227 */     return intersectionTestVertex(p1, q1, r1, r2, p2, q2);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\collisiondetection\Area.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */