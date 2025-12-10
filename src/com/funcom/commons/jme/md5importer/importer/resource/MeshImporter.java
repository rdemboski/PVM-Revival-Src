/*     */ package com.funcom.commons.jme.md5importer.importer.resource;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.MD5Config;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.exception.InvalidVersionException;
/*     */ import com.funcom.commons.jme.md5importer.importer.MD5Importer;
/*     */ import com.funcom.commons.jme.md5importer.importer.resource.token.Md5NumberToken;
/*     */ import com.funcom.commons.jme.md5importer.importer.resource.token.Md5StringToken;
/*     */ import com.funcom.commons.jme.md5importer.resource.TextureFactory;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Joint;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Mesh;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.OptimizedMesh;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.primitive.Triangle;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.primitive.Vertex;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.primitive.Weight;
/*     */ import com.jme.util.NanoTimer;
/*     */ import java.io.IOException;
/*     */ import java.io.StreamTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeshImporter
/*     */ {
/*  30 */   private static int[] vertexIndex = new int[] { 0, 2, 1 };
/*     */ 
/*     */   
/*     */   private Md5Stream md5stream;
/*     */ 
/*     */   
/*     */   protected Joint[] joints;
/*     */ 
/*     */   
/*     */   protected Mesh[] meshes;
/*     */ 
/*     */   
/*     */   protected ModelNode modelNode;
/*     */ 
/*     */   
/*     */   private TextureFactory resourceLoader;
/*     */ 
/*     */   
/*     */   public MeshImporter(StreamTokenizer reader, TextureFactory resourceLoader) {
/*  49 */     this.resourceLoader = resourceLoader;
/*  50 */     this.md5stream = new Md5Stream(reader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode loadMesh(String name) throws IOException {
/*  61 */     this.modelNode = new ModelNode(name);
/*  62 */     processSkin();
/*  63 */     constructSkinMesh();
/*  64 */     return this.modelNode;
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
/*     */   private void processSkin() throws IOException {
/*  76 */     boolean bTimeout = (Runtime.getRuntime().availableProcessors() <= 1 && Thread.currentThread().getName().compareTo("main") != 0);
/*  77 */     NanoTimer nanoTimer = new NanoTimer();
/*  78 */     long nCurrentTime = nanoTimer.getTime();
/*  79 */     long nPreviousTime = nCurrentTime; TokenizedLine line;
/*  80 */     while ((line = this.md5stream.getNextTokenizedLine()) != null) {
/*     */ 
/*     */ 
/*     */       
/*  84 */       nCurrentTime = nanoTimer.getTime();
/*  85 */       if (bTimeout && nCurrentTime - nPreviousTime > 1000000L) {
/*  86 */         nPreviousTime = nCurrentTime;
/*  87 */         Thread.yield();
/*     */       } 
/*     */       
/*  90 */       String keyword = ((Md5StringToken)line.get(0)).getValue().toLowerCase();
/*  91 */       if (keyword.equals("md5version")) {
/*  92 */         if ((int)((Md5NumberToken)line.get(1)).getValue() != 10)
/*  93 */           throw new InvalidVersionException((int)((Md5NumberToken)line.get(1)).getValue());  continue;
/*  94 */       }  if (keyword.equals("numjoints")) {
/*  95 */         this.joints = new Joint[(int)((Md5NumberToken)line.get(1)).getValue()]; continue;
/*  96 */       }  if (keyword.equals("nummeshes")) {
/*  97 */         this.meshes = new Mesh[(int)((Md5NumberToken)line.get(1)).getValue()]; continue;
/*  98 */       }  if (keyword.equals("joints")) {
/*  99 */         processJoints(); continue;
/* 100 */       }  if (keyword.equals("mesh")) {
/* 101 */         processMesh();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processJoints() throws IOException {
/* 112 */     Block block = this.md5stream.getNextBlock();
/* 113 */     int jointIndex = 0;
/*     */     
/* 115 */     for (TokenizedLine line : block.getTokenizedLines()) {
/* 116 */       this.joints[jointIndex] = new Joint(((Md5StringToken)line.get(0)).getValue(), this.modelNode);
/* 117 */       this.joints[jointIndex].setParent((int)((Md5NumberToken)line.get(1)).getValue());
/* 118 */       for (int transIndex = 0; transIndex < 6; transIndex++)
/* 119 */         this.joints[jointIndex].setTransform(transIndex, (float)((Md5NumberToken)line.get(transIndex + 2)).getValue()); 
/* 120 */       jointIndex++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processMesh() throws IOException {
/* 130 */     Block block = this.md5stream.getNextBlock();
/*     */     
/* 132 */     int meshIndex = -1;
/* 133 */     for (int i = 0; i < this.meshes.length && meshIndex == -1; i++) {
/* 134 */       if (this.meshes[i] == null) {
/*     */         
/* 136 */         if (MD5Config.useOptimizedMeshes) {
/* 137 */           this.meshes[i] = (Mesh)new OptimizedMesh(this.modelNode, this.resourceLoader);
/*     */         } else {
/* 139 */           this.meshes[i] = new Mesh(this.modelNode, this.resourceLoader);
/*     */         } 
/*     */         
/* 142 */         meshIndex = i;
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     for (TokenizedLine line : block.getTokenizedLines()) {
/* 147 */       String keyword = ((Md5StringToken)line.get(0)).getValue().toLowerCase();
/*     */       
/* 149 */       if (keyword.equals("shader")) {
/* 150 */         this.meshes[meshIndex].setTexture(((Md5StringToken)line.get(1)).getValue()); continue;
/* 151 */       }  if (keyword.equals("numverts")) {
/* 152 */         int val = (int)((Md5NumberToken)line.get(1)).getValue();
/* 153 */         this.meshes[meshIndex].setVertexCount(val); continue;
/* 154 */       }  if (keyword.equals("vert")) {
/* 155 */         processVertex(line, this.meshes[meshIndex]); continue;
/* 156 */       }  if (keyword.equals("numtris")) {
/* 157 */         int val = (int)((Md5NumberToken)line.get(1)).getValue();
/* 158 */         this.meshes[meshIndex].setTrianglesCount(val); continue;
/* 159 */       }  if (keyword.equals("tri")) {
/* 160 */         processTriangle(line, this.meshes[meshIndex]); continue;
/* 161 */       }  if (keyword.equals("numweights")) {
/* 162 */         int val = (int)((Md5NumberToken)line.get(1)).getValue();
/* 163 */         this.meshes[meshIndex].setWeightCount(val); continue;
/* 164 */       }  if (keyword.equals("weight")) {
/* 165 */         processWeight(line, this.meshes[meshIndex]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processVertex(TokenizedLine line, Mesh mesh) {
/* 177 */     Vertex vertex = new Vertex(mesh);
/* 178 */     int index = (int)((Md5NumberToken)line.get(1)).getValue();
/* 179 */     float u = (float)((Md5NumberToken)line.get(2)).getValue();
/* 180 */     float v = (float)((Md5NumberToken)line.get(3)).getValue();
/* 181 */     int start = (int)((Md5NumberToken)line.get(4)).getValue();
/* 182 */     int length = (int)((Md5NumberToken)line.get(5)).getValue();
/*     */     
/* 184 */     vertex.setTextureCoords(u, v);
/* 185 */     vertex.setWeightIndices(start, length);
/* 186 */     mesh.setVertex(index, vertex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processTriangle(TokenizedLine line, Mesh mesh) {
/* 196 */     Triangle triangle = new Triangle(mesh);
/*     */     
/* 198 */     for (int i = 0; i < 3; i++) {
/* 199 */       int v = (int)((Md5NumberToken)line.get(i + 2)).getValue();
/* 200 */       triangle.setVertexIndex(vertexIndex[i], v);
/* 201 */       mesh.getVertex(v).incrementUsedTimes();
/*     */     } 
/*     */     
/* 204 */     mesh.setTriangle((int)((Md5NumberToken)line.get(1)).getValue(), triangle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processWeight(TokenizedLine line, Mesh mesh) {
/* 214 */     Weight weight = new Weight();
/* 215 */     weight.setJointIndex((int)((Md5NumberToken)line.get(2)).getValue());
/* 216 */     weight.setWeightValue((float)((Md5NumberToken)line.get(3)).getValue());
/* 217 */     for (int i = 0; i < 3; i++)
/* 218 */       weight.setPosition(i, (float)((Md5NumberToken)line.get(i + 4)).getValue()); 
/* 219 */     mesh.setWeight((int)((Md5NumberToken)line.get(1)).getValue(), weight);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void constructSkinMesh() {
/* 225 */     for (int i = this.joints.length - 1; i >= 0; i--) {
/* 226 */       if (this.joints[i].getParent() < 0) { this.joints[i].processTransform(null, null); }
/*     */       else
/* 228 */       { Joint parent = this.joints[this.joints[i].getParent()];
/* 229 */         this.joints[i].processTransform(parent.getTranslation(), parent.getOrientation()); }
/*     */     
/*     */     } 
/* 232 */     for (Joint joint : this.joints) {
/* 233 */       if (joint.getParent() < 0) {
/* 234 */         joint.getOrientation().set(MD5Importer.base.mult(joint.getOrientation()));
/*     */       }
/*     */     } 
/* 237 */     this.modelNode.setJoints(this.joints);
/* 238 */     this.modelNode.setMeshes(this.meshes);
/* 239 */     this.modelNode.initialize();
/* 240 */     this.joints = null;
/* 241 */     this.meshes = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\resource\MeshImporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */