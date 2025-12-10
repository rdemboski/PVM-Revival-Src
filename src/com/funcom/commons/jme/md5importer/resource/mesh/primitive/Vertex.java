/*     */ package com.funcom.commons.jme.md5importer.resource.mesh.primitive;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Mesh;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.util.export.InputCapsule;
/*     */ import com.jme.util.export.JMEExporter;
/*     */ import com.jme.util.export.JMEImporter;
/*     */ import com.jme.util.export.OutputCapsule;
/*     */ import com.jme.util.export.Savable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ public class Vertex
/*     */   implements Serializable, Savable
/*     */ {
/*     */   private static final long serialVersionUID = 6774812007144718188L;
/*     */   private Mesh mesh;
/*     */   private Vector2f textureCoords;
/*     */   private int[] weightIndices;
/*     */   private int usedTimes;
/*     */   private Vector3f normal;
/*     */   private Vector3f position;
/*     */   private final Vector3f tmp;
/*     */   
/*     */   public Vertex() {
/*  41 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vertex(Mesh mesh) {
/*  50 */     this.mesh = mesh;
/*  51 */     this.position = new Vector3f();
/*  52 */     this.normal = new Vector3f();
/*  53 */     this.textureCoords = new Vector2f();
/*  54 */     this.tmp = new Vector3f();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processPosition() {
/*  59 */     this.position.zero();
/*     */     
/*  61 */     for (int weightIndex : this.weightIndices) {
/*  62 */       Weight weight = this.mesh.getWeight(weightIndex);
/*     */       
/*  64 */       this.tmp.set(weight.getPosition());
/*  65 */       this.mesh.getModelNode().getJoint(weight.getJointIndex()).getTransform().multPoint(this.tmp);
/*  66 */       this.tmp.multLocal(weight.getWeightValue());
/*     */       
/*  68 */       this.position.addLocal(this.tmp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetInformation() {
/*  74 */     this.normal.zero();
/*  75 */     this.position.zero();
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementUsedTimes() {
/*  80 */     this.usedTimes++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureCoords(float u, float v) {
/*  91 */     float invertV = 1.0F - v;
/*  92 */     this.textureCoords.set(u, invertV);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWeightIndices(int start, int length) {
/* 102 */     this.weightIndices = new int[length];
/* 103 */     for (int i = 0; i < this.weightIndices.length; i++) {
/* 104 */       this.weightIndices[i] = start + i;
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
/*     */ 
/*     */   
/*     */   public void setNormal(Vector3f normal) {
/* 120 */     this.normal.addLocal(normal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f getTextureCoords() {
/* 129 */     return this.textureCoords;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUsedTimes() {
/* 138 */     return this.usedTimes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f getPosition() {
/* 147 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f getNormal() {
/* 156 */     return this.normal;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getClassTag() {
/* 162 */     return Vertex.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(JMEImporter im) throws IOException {
/* 167 */     InputCapsule ic = im.getCapsule(this);
/* 168 */     this.mesh = (Mesh)ic.readSavable("Mesh", null);
/* 169 */     this.textureCoords = (Vector2f)ic.readSavable("TextureCoords", null);
/* 170 */     this.weightIndices = ic.readIntArray("WeightIndices", null);
/* 171 */     this.usedTimes = ic.readInt("UsedTimes", 0);
/* 172 */     this.normal = (Vector3f)ic.readSavable("Normal", null);
/* 173 */     this.position = (Vector3f)ic.readSavable("Position", null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JMEExporter ex) throws IOException {
/* 178 */     OutputCapsule oc = ex.getCapsule(this);
/* 179 */     oc.write((Savable)this.mesh, "Mesh", null);
/* 180 */     oc.write((Savable)this.textureCoords, "TextureCoords", null);
/* 181 */     oc.write(this.weightIndices, "WeightIndices", null);
/* 182 */     oc.write(this.usedTimes, "UsedTimes", 0);
/* 183 */     oc.write((Savable)this.normal, "Normal", null);
/* 184 */     oc.write((Savable)this.position, "Position", null);
/*     */   }
/*     */   
/*     */   public Vertex simpleClone(Mesh mesh) {
/* 188 */     Vertex clone = new Vertex(mesh);
/* 189 */     clone.textureCoords = this.textureCoords;
/* 190 */     clone.weightIndices = this.weightIndices;
/* 191 */     clone.usedTimes = this.usedTimes;
/* 192 */     clone.normal = this.normal.clone();
/* 193 */     clone.position = this.position.clone();
/* 194 */     return clone;
/*     */   }
/*     */   
/*     */   public Vertex clone(Mesh mesh) {
/* 198 */     Vertex clone = new Vertex(mesh);
/* 199 */     clone.textureCoords = this.textureCoords.clone();
/* 200 */     clone.weightIndices = (int[])this.weightIndices.clone();
/* 201 */     clone.usedTimes = this.usedTimes;
/* 202 */     clone.normal = this.normal.clone();
/* 203 */     clone.position = this.position.clone();
/* 204 */     return clone;
/*     */   }
/*     */   
/*     */   public Vertex staticClone(Mesh mesh) {
/* 208 */     Vertex clone = new Vertex(mesh);
/* 209 */     clone.textureCoords = this.textureCoords;
/* 210 */     clone.weightIndices = this.weightIndices;
/* 211 */     clone.usedTimes = this.usedTimes;
/* 212 */     clone.normal = this.normal;
/* 213 */     clone.position = this.position;
/* 214 */     return clone;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\resource\mesh\primitive\Vertex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */