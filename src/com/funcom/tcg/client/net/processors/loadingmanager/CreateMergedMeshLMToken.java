/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Mesh;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.OptimizedMesh;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.primitive.Triangle;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.primitive.Vertex;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.primitive.Weight;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TexCoords;
/*     */ import com.jme.scene.TriMesh;
/*     */ import com.jme.scene.geometryinstancing.GeometryBatchInstance;
/*     */ import com.jme.scene.geometryinstancing.GeometryBatchInstanceAttributes;
/*     */ import com.jme.scene.geometryinstancing.instance.GeometryBatchCreator;
/*     */ import com.jme.scene.geometryinstancing.instance.GeometryInstance;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
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
/*     */ public class CreateMergedMeshLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*     */   ArrayList<Element> meshElements;
/*     */   TokenTargetNode tokenTargetNode;
/*     */   ResourceGetter resourceGetter;
/*     */   DireEffectDescriptionFactory effectDescriptionFactory;
/*     */   
/*     */   public CreateMergedMeshLMToken(ArrayList<Element> meshElements, TokenTargetNode tokenTargetNode, ResourceGetter resourceGetter, DireEffectDescriptionFactory effectDescriptionFactory) {
/*  88 */     this.meshElements = meshElements;
/*  89 */     this.tokenTargetNode = tokenTargetNode;
/*  90 */     this.resourceGetter = resourceGetter;
/*  91 */     this.effectDescriptionFactory = effectDescriptionFactory;
/*     */   }
/*     */   
/*     */   public boolean processRequestAssets() throws Exception {
/*  95 */     return true;
/*     */   }
/*     */   
/*     */   public boolean processWaitingAssets() throws Exception {
/*  99 */     return true;
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
/*     */   public boolean processGame() throws Exception {
/* 113 */     GeometryBatchCreator g = new GeometryBatchCreator();
/* 114 */     String name = "unknown";
/* 115 */     String meshPath = null;
/* 116 */     ModelNode modelNode = null;
/*     */     
/* 118 */     int nNumItems = this.meshElements.size();
/* 119 */     for (int n = 0; n < nNumItems; n++) {
/* 120 */       Element element = this.meshElements.get(n);
/*     */       
/* 122 */       int tx = element.getAttribute("x").getIntValue();
/* 123 */       int ty = element.getAttribute("y").getIntValue();
/* 124 */       float ox = element.getAttribute("x-offset").getFloatValue();
/* 125 */       float oy = element.getAttribute("y-offset").getFloatValue();
/* 126 */       float x = tx + ox, y = 0.0F, z = ty + oy;
/*     */       
/* 128 */       float scale = element.getAttribute("scale").getFloatValue();
/* 129 */       float angle = element.getAttribute("rotation").getFloatValue();
/*     */       
/* 131 */       Attribute zAtt = element.getAttribute("z-value");
/* 132 */       if (zAtt != null) z = zAtt.getFloatValue();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       if (meshPath == null) {
/* 138 */         Element resourceElement = element.getChild("resource");
/* 139 */         String resourceName = resourceElement.getAttributeValue("name");
/* 140 */         name = element.getAttributeValue("name");
/* 141 */         name = name.replace("(", "_");
/* 142 */         name = name.replace(")", "_");
/*     */         
/* 144 */         Document document = this.resourceGetter.getDocument(resourceName, CacheType.CACHE_TEMPORARILY);
/* 145 */         Element rootElement = document.getRootElement();
/* 146 */         meshPath = rootElement.getChild("submodel").getChild("mesh").getContent(0).getValue();
/* 147 */         modelNode = this.resourceGetter.getModelNode(meshPath);
/*     */       } 
/*     */       
/* 150 */       for (Mesh mesh1 : modelNode.getMeshes()) {
/*     */         
/* 152 */         TriMesh temp = null;
/*     */         
/* 154 */         if (mesh1.getClass().toString().compareTo(OptimizedMesh.class.toString()) == 0) {
/*     */           
/* 156 */           Vertex[] verticesSrc = mesh1.getVertices();
/* 157 */           int nNumVertices = verticesSrc.length;
/*     */           
/* 159 */           String meshName = "";
/* 160 */           FloatBuffer vertices = FloatBuffer.allocate(nNumVertices * 3);
/* 161 */           FloatBuffer normals = FloatBuffer.allocate(nNumVertices * 3);
/* 162 */           FloatBuffer colors = null;
/* 163 */           TexCoords coords = new TexCoords(BufferUtils.createVector2Buffer(nNumVertices));
/*     */           
/* 165 */           Weight[] weights = mesh1.getWeights();
/*     */ 
/*     */           
/* 168 */           for (int nVertex = 0; nVertex < nNumVertices; nVertex++) {
/* 169 */             vertices.put((verticesSrc[nVertex].getPosition()).x * (weights[nVertex].getPosition()).x);
/* 170 */             vertices.put((verticesSrc[nVertex].getPosition()).y * (weights[nVertex].getPosition()).y);
/* 171 */             vertices.put((verticesSrc[nVertex].getPosition()).z * (weights[nVertex].getPosition()).z);
/*     */             
/* 173 */             normals.put((verticesSrc[nVertex].getNormal()).x);
/* 174 */             normals.put((verticesSrc[nVertex].getNormal()).y);
/* 175 */             normals.put((verticesSrc[nVertex].getNormal()).z);
/*     */             
/* 177 */             coords.coords.put((verticesSrc[nVertex].getTextureCoords()).x);
/* 178 */             coords.coords.put((verticesSrc[nVertex].getTextureCoords()).y);
/*     */           } 
/*     */           
/* 181 */           Triangle[] triangles = mesh1.getTriangles();
/* 182 */           int nNumIndices = triangles.length;
/* 183 */           IntBuffer indices = BufferUtils.createIntBuffer(nNumIndices * 3);
/* 184 */           for (int nIndex = 0; nIndex < nNumIndices; nIndex++) {
/* 185 */             indices.put(triangles[nIndex].getVertexIndex(0));
/* 186 */             indices.put(triangles[nIndex].getVertexIndex(1));
/* 187 */             indices.put(triangles[nIndex].getVertexIndex(2));
/*     */           } 
/*     */           
/* 190 */           temp = new TriMesh(name, vertices, normals, colors, coords, indices);
/*     */         } else {
/*     */           
/* 193 */           temp = mesh1.getTrimesh();
/*     */         } 
/*     */         
/* 196 */         float[] angles = { 0.0F, angle, 0.0F };
/* 197 */         GeometryBatchInstanceAttributes attributes = new GeometryBatchInstanceAttributes(new Vector3f(x, z, y), new Vector3f(scale, scale, scale), new Quaternion(angles), new ColorRGBA(1.0F, 1.0F, 1.0F, 0.0F));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 203 */         GeometryBatchInstance inst = new GeometryBatchInstance(temp, attributes);
/* 204 */         g.addInstance((GeometryInstance)inst);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 209 */     TriMesh mesh = new TriMesh();
/* 210 */     mesh.setModelBound((BoundingVolume)new BoundingBox());
/*     */ 
/*     */ 
/*     */     
/* 214 */     mesh.setIndexBuffer(BufferUtils.createIntBuffer(g.getNumIndices()));
/* 215 */     mesh.setVertexBuffer(BufferUtils.createVector3Buffer(g.getNumVertices()));
/* 216 */     mesh.setNormalBuffer(BufferUtils.createVector3Buffer(g.getNumVertices()));
/* 217 */     mesh.setTextureCoords(new TexCoords(BufferUtils.createVector2Buffer(g.getNumVertices())), 0);
/* 218 */     mesh.setColorBuffer(BufferUtils.createFloatBuffer(g.getNumVertices() * 4));
/*     */     
/* 220 */     g.commit(mesh);
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
/* 236 */     this.tokenTargetNode.attachStaticChild((Spatial)mesh);
/*     */     
/* 238 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\CreateMergedMeshLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */