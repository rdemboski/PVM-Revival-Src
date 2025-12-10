/*     */ package com.funcom.commons.jme.md5importer.resource.mesh;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.resource.TextureFactory;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.primitive.Vertex;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OptimizedMesh
/*     */   extends Mesh
/*     */ {
/*     */   private float[] tmpVertexBuf;
/*     */   
/*     */   public OptimizedMesh() {}
/*     */   
/*     */   public OptimizedMesh(ModelNode modelNode, TextureFactory resourceLoader) {
/*  27 */     super(modelNode, resourceLoader);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getClassTag() {
/*  32 */     return OptimizedMesh.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateBatch() {
/*  37 */     this.trimesh = new ShaderTriMesh();
/*  38 */     this.trimesh.setNormalsMode(Spatial.NormalsMode.Inherit);
/*  39 */     processIndex();
/*  40 */     processTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processBounding() {
/*  45 */     this.trimesh.setModelBound((BoundingVolume)new BoundingBox());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBatch() {
/*  50 */     if (this.trimesh == null) {
/*  51 */       generateBatch();
/*     */     }
/*  53 */     if (this.trimesh.getNormalsMode() != Spatial.NormalsMode.Off) {
/*  54 */       for (Vertex vertice : this.vertices) {
/*  55 */         vertice.resetInformation();
/*     */       }
/*     */     }
/*  58 */     processVertex();
/*     */     
/*  60 */     if ((getModelNode().getJoints()).length == 1) {
/*     */       return;
/*     */     }
/*  63 */     if (this.trimesh.getNormalsMode() != Spatial.NormalsMode.Off) {
/*  64 */       processNormal();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  70 */   private FloatBuffer tmp = FloatBuffer.allocate(6);
/*     */ 
/*     */   
/*     */   protected void processVertex() {
/*  74 */     FloatBuffer vertexBuffer = this.trimesh.getVertexBuffer();
/*  75 */     if (vertexBuffer == null) {
/*  76 */       vertexBuffer = BufferUtils.createVector3Buffer(this.vertices.length);
/*  77 */       this.trimesh.setVertexBuffer(vertexBuffer);
/*  78 */       this.tmpVertexBuf = new float[vertexBuffer.capacity()];
/*     */     } 
/*  80 */     vertexBuffer.clear();
/*     */     
/*  82 */     float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY, minZ = Float.POSITIVE_INFINITY;
/*  83 */     float maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY, maxZ = Float.NEGATIVE_INFINITY;
/*     */     
/*  85 */     int ii = 0;
/*  86 */     for (Vertex vertex : this.vertices) {
/*  87 */       vertex.processPosition();
/*  88 */       Vector3f pos = vertex.getPosition();
/*  89 */       this.tmpVertexBuf[ii++] = pos.x;
/*  90 */       this.tmpVertexBuf[ii++] = pos.y;
/*  91 */       this.tmpVertexBuf[ii++] = pos.z;
/*     */       
/*  93 */       if (pos.x < minX) {
/*  94 */         minX = pos.x;
/*     */       }
/*     */       
/*  97 */       if (pos.x > maxX) {
/*  98 */         maxX = pos.x;
/*     */       }
/*     */       
/* 101 */       if (pos.y < minY) {
/* 102 */         minY = pos.y;
/*     */       }
/*     */       
/* 105 */       if (pos.y > maxY) {
/* 106 */         maxY = pos.y;
/*     */       }
/*     */       
/* 109 */       if (pos.z < minZ) {
/* 110 */         minZ = pos.z;
/*     */       }
/*     */       
/* 113 */       if (pos.z > maxZ) {
/* 114 */         maxZ = pos.z;
/*     */       }
/*     */     } 
/*     */     
/* 118 */     BoundingVolume volume = this.trimesh.getModelBound();
/* 119 */     if (volume != null) {
/* 120 */       this.tmp.clear();
/* 121 */       this.tmp.put(minX).put(minY).put(minZ);
/* 122 */       this.tmp.put(maxX).put(maxY).put(maxZ);
/* 123 */       this.tmp.flip();
/* 124 */       volume.computeFromPoints(this.tmp);
/* 125 */       this.trimesh.updateWorldBound();
/*     */     } 
/*     */     
/* 128 */     vertexBuffer.put(this.tmpVertexBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Mesh newCloneInstance(ModelNode node) {
/* 133 */     return new OptimizedMesh(node, this.resourceLoader);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\resource\mesh\OptimizedMesh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */