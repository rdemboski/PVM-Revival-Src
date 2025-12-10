/*     */ package com.funcom.commons.jme.md5importer.resource.mesh;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.MD5Config;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.importer.MD5Importer;
/*     */ import com.funcom.commons.jme.md5importer.resource.TextureFactory;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.primitive.Triangle;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.primitive.Vertex;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.primitive.Weight;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.bounding.OrientedBoundingBox;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TexCoords;
/*     */ import com.jme.scene.TriMesh;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.export.InputCapsule;
/*     */ import com.jme.util.export.JMEExporter;
/*     */ import com.jme.util.export.JMEImporter;
/*     */ import com.jme.util.export.OutputCapsule;
/*     */ import com.jme.util.export.Savable;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
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
/*     */ public class Mesh
/*     */   implements Serializable, Savable
/*     */ {
/*     */   private static final long serialVersionUID = -6431941710991131243L;
/*     */   private ModelNode modelNode;
/*     */   private String texture;
/*     */   protected Vertex[] vertices;
/*     */   private Triangle[] triangles;
/*     */   private Weight[] weights;
/*     */   protected ShaderTriMesh trimesh;
/*     */   protected TextureFactory resourceLoader;
/*     */   
/*     */   protected Mesh() {}
/*     */   
/*     */   public Mesh(ModelNode modelNode, TextureFactory resourceLoader) {
/*  66 */     this.modelNode = modelNode;
/*  67 */     this.resourceLoader = resourceLoader;
/*     */   }
/*     */   
/*     */   public void setResourceLoader(TextureFactory resourceLoader) {
/*  71 */     this.resourceLoader = resourceLoader;
/*     */   }
/*     */   
/*     */   public Vertex[] getVertices() {
/*  75 */     return this.vertices;
/*     */   }
/*     */   
/*     */   public Triangle[] getTriangles() {
/*  79 */     return this.triangles;
/*     */   }
/*     */   
/*     */   public Weight[] getWeights() {
/*  83 */     return this.weights;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateBatch() {
/*  90 */     if (this.trimesh == null)
/*  91 */       this.trimesh = new ShaderTriMesh(); 
/*  92 */     this.trimesh.setCastsShadows(MD5Config.castsShadows);
/*  93 */     this.trimesh.setNormalsMode(Spatial.NormalsMode.Inherit);
/*  94 */     processIndex();
/*  95 */     processVertex();
/*  96 */     processTexture();
/*  97 */     processNormal();
/*  98 */     processBounding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateBatch() {
/* 105 */     if (this.trimesh == null) {
/* 106 */       generateBatch();
/*     */     }
/* 108 */     for (Vertex vertice : this.vertices) {
/* 109 */       vertice.resetInformation();
/*     */     }
/* 111 */     processVertex();
/*     */     
/* 113 */     if (MD5Config.normalGeneration && 
/* 114 */       this.trimesh.getNormalsMode() != Spatial.NormalsMode.Off) {
/* 115 */       processNormal();
/*     */     }
/*     */ 
/*     */     
/* 119 */     this.trimesh.updateModelBound();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processIndex() {
/* 126 */     IntBuffer indexBuffer = this.trimesh.getIndexBuffer();
/* 127 */     if (indexBuffer == null) {
/* 128 */       indexBuffer = BufferUtils.createIntBuffer(this.triangles.length * 3);
/*     */     }
/* 130 */     indexBuffer.clear();
/* 131 */     for (Triangle triangle : this.triangles) {
/* 132 */       for (int j = 0; j < 3; j++) {
/* 133 */         indexBuffer.put(triangle.getVertexIndex(j));
/*     */       }
/*     */     } 
/* 136 */     indexBuffer.rewind();
/* 137 */     this.trimesh.setIndexBuffer(indexBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processVertex() {
/* 144 */     FloatBuffer vertexBuffer = this.trimesh.getVertexBuffer();
/* 145 */     if (vertexBuffer == null) {
/* 146 */       vertexBuffer = BufferUtils.createVector3Buffer(this.vertices.length);
/*     */     }
/* 148 */     vertexBuffer.clear();
/* 149 */     for (int i = 0; i < this.vertices.length; i++) {
/* 150 */       this.vertices[i].processPosition();
/* 151 */       BufferUtils.setInBuffer(this.vertices[i].getPosition(), vertexBuffer, i);
/*     */     } 
/* 153 */     vertexBuffer.rewind();
/* 154 */     this.trimesh.setVertexBuffer(vertexBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processNormal() {
/* 162 */     for (Triangle triangle : this.triangles) {
/* 163 */       triangle.processNormal();
/*     */     }
/*     */     
/* 166 */     FloatBuffer normalBuffer = this.trimesh.getNormalBuffer();
/* 167 */     if (normalBuffer == null) {
/* 168 */       normalBuffer = BufferUtils.createVector3Buffer(this.vertices.length);
/*     */     }
/* 170 */     normalBuffer.clear();
/* 171 */     for (int i = 0; i < this.vertices.length; i++) {
/* 172 */       BufferUtils.setInBuffer(this.vertices[i].getNormal(), normalBuffer, i);
/*     */     }
/* 174 */     normalBuffer.rewind();
/* 175 */     this.trimesh.setNormalBuffer(normalBuffer);
/*     */     
/* 177 */     if (MD5Config.normalGeneration && MD5Config.tangentSpaceDataGeneration)
/*     */     {
/* 179 */       generateTangentialSpace();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateTangentialSpace() {
/* 185 */     FloatBuffer tangents = this.trimesh.getTangentBuffer();
/* 186 */     if (tangents == null) {
/* 187 */       tangents = BufferUtils.createFloatBuffer(this.trimesh.getVertexCount() * 3);
/*     */     }
/* 189 */     FloatBuffer binormals = this.trimesh.getBinormalBuffer();
/* 190 */     if (binormals == null) {
/* 191 */       binormals = BufferUtils.createFloatBuffer(this.trimesh.getVertexCount() * 3);
/*     */     }
/* 193 */     tangents.clear();
/* 194 */     binormals.clear();
/*     */     
/* 196 */     IntBuffer indexBuffer = this.trimesh.getIndexBuffer();
/* 197 */     FloatBuffer vertexBuffer = this.trimesh.getVertexBuffer();
/* 198 */     FloatBuffer textureBuffer = (this.trimesh.getTextureCoords(0)).coords;
/*     */     
/* 200 */     vertexBuffer.rewind();
/* 201 */     textureBuffer.rewind();
/* 202 */     indexBuffer.rewind();
/*     */     
/* 204 */     Vector3f tangent = new Vector3f();
/* 205 */     Vector3f binormal = new Vector3f();
/* 206 */     Vector3f normal = new Vector3f();
/* 207 */     Vector3f[] verts = new Vector3f[3];
/* 208 */     Vector2f[] texcoords = new Vector2f[3];
/*     */     
/* 210 */     for (int i = 0; i < 3; i++) {
/* 211 */       verts[i] = new Vector3f();
/* 212 */       texcoords[i] = new Vector2f();
/*     */     } 
/*     */     
/* 215 */     for (int t = 0; t < indexBuffer.capacity() / 3; t++) {
/*     */       
/* 217 */       int[] index = new int[3];
/*     */       int v;
/* 219 */       for (v = 0; v < 3; v++) {
/* 220 */         index[v] = indexBuffer.get();
/* 221 */         (verts[v]).x = vertexBuffer.get(index[v] * 3);
/* 222 */         (verts[v]).y = vertexBuffer.get(index[v] * 3 + 1);
/* 223 */         (verts[v]).z = vertexBuffer.get(index[v] * 3 + 2);
/*     */         
/* 225 */         (texcoords[v]).x = textureBuffer.get(index[v] * 2);
/* 226 */         (texcoords[v]).y = textureBuffer.get(index[v] * 2 + 1);
/*     */       } 
/*     */       
/* 229 */       computeTriangleTangentSpace(tangent, binormal, normal, verts, texcoords);
/*     */ 
/*     */       
/* 232 */       for (v = 0; v < 3; v++) {
/* 233 */         tangents.position(index[v] * 3);
/* 234 */         tangents.put(tangent.x);
/* 235 */         tangents.put(tangent.y);
/* 236 */         tangents.put(tangent.z);
/*     */         
/* 238 */         binormals.position(index[v] * 3);
/* 239 */         binormals.put(binormal.x);
/* 240 */         binormals.put(binormal.y);
/* 241 */         binormals.put(binormal.z);
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     tangents.rewind();
/* 246 */     binormals.rewind();
/* 247 */     this.trimesh.setTangentBuffer(tangents);
/* 248 */     this.trimesh.setBinormalBuffer(binormals);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void computeTriangleTangentSpace(Vector3f tangent, Vector3f binormal, Vector3f normal, Vector3f[] v, Vector2f[] t) {
/* 253 */     Vector3f edge1 = v[1].subtract(v[0]);
/* 254 */     Vector3f edge2 = v[2].subtract(v[0]);
/* 255 */     Vector2f edge1uv = t[1].subtract(t[0]);
/* 256 */     Vector2f edge2uv = t[2].subtract(t[0]);
/*     */     
/* 258 */     float cp = edge1uv.y * edge2uv.x - edge1uv.x * edge2uv.y;
/*     */     
/* 260 */     if (cp != 0.0F) {
/* 261 */       float mul = 1.0F / cp;
/* 262 */       tangent.set(edge1.mult(-edge2uv.y).add(edge2.mult(edge1uv.y)).mult(mul));
/*     */       
/* 264 */       binormal.set(edge1.mult(-edge2uv.x).add(edge2.mult(edge1uv.x)).mult(mul));
/*     */       
/* 266 */       tangent.normalizeLocal();
/* 267 */       binormal.normalizeLocal();
/*     */     } 
/* 269 */     edge1.cross(edge2, normal);
/* 270 */     normal.normalizeLocal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processTexture() {
/* 278 */     MD5Importer instance = MD5Importer.getInstance();
/*     */     
/* 280 */     TexCoords texcoords = this.trimesh.getTextureCoords(0);
/* 281 */     if (texcoords == null) {
/* 282 */       texcoords = new TexCoords(BufferUtils.createVector2Buffer(this.vertices.length));
/*     */     } else {
/* 284 */       FloatBuffer oldBuff = texcoords.coords;
/* 285 */       texcoords = new TexCoords(oldBuff);
/*     */     } 
/*     */     
/* 288 */     texcoords.coords.clear();
/*     */     
/* 290 */     float maxU = 1.0F;
/* 291 */     float maxV = 1.0F;
/* 292 */     float minU = 0.0F;
/* 293 */     float minV = 0.0F;
/*     */ 
/*     */     
/* 296 */     for (int i = 0; i < this.vertices.length; i++) {
/* 297 */       BufferUtils.setInBuffer(this.vertices[i].getTextureCoords(), texcoords.coords, i);
/* 298 */       if ((this.vertices[i].getTextureCoords()).x > maxU) {
/* 299 */         maxU = (this.vertices[i].getTextureCoords()).x;
/* 300 */       } else if ((this.vertices[i].getTextureCoords()).x < minU) {
/* 301 */         minU = (this.vertices[i].getTextureCoords()).x;
/* 302 */       }  if ((this.vertices[i].getTextureCoords()).y > maxV) {
/* 303 */         maxV = (this.vertices[i].getTextureCoords()).y;
/* 304 */       } else if ((this.vertices[i].getTextureCoords()).y < minV) {
/* 305 */         minV = (this.vertices[i].getTextureCoords()).y;
/*     */       } 
/*     */     } 
/* 308 */     texcoords.coords.rewind();
/* 309 */     this.trimesh.setTextureCoords(texcoords, 0);
/* 310 */     TextureState state = this.resourceLoader.createTexture(this.texture, instance.getExtensions(), instance.getMMFilter(), instance.getFMFilter(), instance.getAnisotropic(), true);
/* 311 */     this.trimesh.setRenderState((RenderState)state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processBounding() {
/* 318 */     if (MD5Importer.getInstance().isOriented()) {
/* 319 */       this.trimesh.setModelBound((BoundingVolume)new OrientedBoundingBox());
/*     */     } else {
/* 321 */       this.trimesh.setModelBound((BoundingVolume)new BoundingBox());
/*     */     } 
/* 323 */     this.trimesh.updateModelBound();
/* 324 */     this.trimesh.updateGeometricState(0.0F, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTexture(String texture) {
/* 333 */     this.texture = texture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTexture() {
/* 342 */     return this.texture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVertexCount(int count) {
/* 351 */     this.vertices = new Vertex[count];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVertex(int index, Vertex vertex) {
/* 361 */     this.vertices[index] = vertex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTrianglesCount(int count) {
/* 370 */     this.triangles = new Triangle[count];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTriangle(int index, Triangle triangle) {
/* 380 */     this.triangles[index] = triangle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWeightCount(int count) {
/* 389 */     this.weights = new Weight[count];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeightCount() {
/* 398 */     return this.weights.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWeight(int index, Weight weight) {
/* 408 */     this.weights[index] = weight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode getModelNode() {
/* 417 */     return this.modelNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vertex getVertex(int index) {
/* 427 */     return this.vertices[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Weight getWeight(int index) {
/* 437 */     return this.weights[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TriMesh getTrimesh() {
/* 446 */     return this.trimesh;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getClassTag() {
/* 452 */     return Mesh.class;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(JMEImporter im) throws IOException {
/* 458 */     InputCapsule ic = im.getCapsule(this);
/* 459 */     this.modelNode = (ModelNode)ic.readSavable("ModelNode", null);
/* 460 */     Savable[] temp = ic.readSavableArray("Vertices", null);
/* 461 */     this.vertices = new Vertex[temp.length]; int i;
/* 462 */     for (i = 0; i < temp.length; i++) {
/* 463 */       this.vertices[i] = (Vertex)temp[i];
/*     */     }
/* 465 */     temp = ic.readSavableArray("Triangles", null);
/* 466 */     this.triangles = new Triangle[temp.length];
/* 467 */     for (i = 0; i < temp.length; i++) {
/* 468 */       this.triangles[i] = (Triangle)temp[i];
/*     */     }
/* 470 */     temp = ic.readSavableArray("Weights", null);
/* 471 */     this.weights = new Weight[temp.length];
/* 472 */     for (i = 0; i < temp.length; i++) {
/* 473 */       this.weights[i] = (Weight)temp[i];
/*     */     }
/* 475 */     this.trimesh = (ShaderTriMesh)ic.readSavable("Batch", null);
/* 476 */     this.texture = ic.readString("Texture", null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JMEExporter ex) throws IOException {
/* 481 */     OutputCapsule oc = ex.getCapsule(this);
/* 482 */     oc.write((Savable)this.modelNode, "ModelNode", null);
/* 483 */     oc.write((Savable[])this.vertices, "Vertices", null);
/* 484 */     oc.write((Savable[])this.triangles, "Triangles", null);
/* 485 */     oc.write((Savable[])this.weights, "Weights", null);
/* 486 */     oc.write((Savable)this.trimesh, "Batch", null);
/* 487 */     oc.write(this.texture, "Texture", null);
/*     */   }
/*     */   
/*     */   public Mesh simpleClone(ModelNode node) {
/* 491 */     Mesh clone = newCloneInstance(node);
/* 492 */     clone.texture = this.texture;
/* 493 */     clone.vertices = new Vertex[this.vertices.length]; int i;
/* 494 */     for (i = 0; i < this.vertices.length; i++) {
/* 495 */       clone.vertices[i] = this.vertices[i].simpleClone(clone);
/*     */     }
/*     */     
/* 498 */     clone.triangles = new Triangle[this.triangles.length];
/* 499 */     for (i = 0; i < this.triangles.length; i++) {
/* 500 */       clone.triangles[i] = this.triangles[i].clone(clone);
/*     */     }
/*     */     
/* 503 */     clone.weights = this.weights;
/*     */     
/* 505 */     clone.trimesh = new ShaderTriMesh();
/* 506 */     clone.trimesh.setNormalsMode(this.trimesh.getNormalsMode());
/* 507 */     clone.trimesh.setIndexBuffer(this.trimesh.getIndexBuffer());
/*     */ 
/*     */ 
/*     */     
/* 511 */     clone.trimesh.setTextureCoords(this.trimesh.getTextureCoords());
/*     */ 
/*     */     
/* 514 */     TextureState textureState = (TextureState)this.trimesh.getRenderState(5);
/* 515 */     TextureState state = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 516 */     state.setTexture(textureState.getTexture());
/* 517 */     clone.trimesh.setRenderState((RenderState)state);
/* 518 */     clone.processBounding();
/*     */     
/* 520 */     return clone;
/*     */   }
/*     */   
/*     */   public Mesh clone(ModelNode node) {
/* 524 */     Mesh clone = newCloneInstance(node);
/* 525 */     clone.texture = this.texture;
/* 526 */     clone.vertices = new Vertex[this.vertices.length];
/* 527 */     for (int i = 0; i < this.vertices.length; i++) {
/* 528 */       clone.vertices[i] = this.vertices[i].clone(clone);
/*     */     }
/* 530 */     clone.triangles = this.triangles;
/* 531 */     clone.weights = this.weights;
/* 532 */     clone.generateBatch();
/* 533 */     clone.trimesh.setNormalsMode(this.trimesh.getNormalsMode());
/*     */     
/* 535 */     return clone;
/*     */   }
/*     */   
/*     */   protected Mesh newCloneInstance(ModelNode node) {
/* 539 */     return new Mesh(node, this.resourceLoader);
/*     */   }
/*     */ 
/*     */   
/*     */   public Mesh cloneStatic(ModelNode node) {
/* 544 */     if (this.trimesh == null) {
/* 545 */       generateBatch();
/*     */     }
/* 547 */     Mesh clone = new Mesh(node, this.resourceLoader);
/* 548 */     clone.texture = this.texture;
/* 549 */     clone.vertices = new Vertex[this.vertices.length]; int i;
/* 550 */     for (i = 0; i < this.vertices.length; i++) {
/* 551 */       clone.vertices[i] = this.vertices[i].staticClone(clone);
/*     */     }
/*     */     
/* 554 */     clone.triangles = new Triangle[this.triangles.length];
/* 555 */     for (i = 0; i < this.triangles.length; i++) {
/* 556 */       clone.triangles[i] = this.triangles[i].staticClone(clone);
/*     */     }
/*     */     
/* 559 */     clone.weights = this.weights;
/*     */     
/* 561 */     clone.trimesh = new ShaderTriMesh();
/* 562 */     clone.trimesh.setNormalsMode(this.trimesh.getNormalsMode());
/* 563 */     clone.trimesh.setIndexBuffer(this.trimesh.getIndexBuffer());
/*     */ 
/*     */     
/* 566 */     clone.trimesh.setNormalBuffer(this.trimesh.getNormalBuffer());
/* 567 */     clone.trimesh.setBinormalBuffer(this.trimesh.getBinormalBuffer());
/* 568 */     clone.trimesh.setTangentBuffer(this.trimesh.getTangentBuffer());
/*     */     
/* 570 */     clone.trimesh.setTextureCoords(this.trimesh.getTextureCoords());
/*     */ 
/*     */ 
/*     */     
/* 574 */     TextureState textureState = (TextureState)this.trimesh.getRenderState(RenderState.StateType.Texture);
/* 575 */     TextureState state = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 576 */     for (int j = 0; j < TextureState.getNumberOfFixedUnits(); j++) {
/* 577 */       state.setTexture(textureState.getTexture(j), j);
/*     */     }
/* 579 */     clone.trimesh.setRenderState((RenderState)state);
/* 580 */     clone.processBounding();
/*     */     
/* 582 */     return clone;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\resource\mesh\Mesh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */