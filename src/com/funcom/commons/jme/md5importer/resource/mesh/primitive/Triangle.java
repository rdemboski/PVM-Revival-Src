/*     */ package com.funcom.commons.jme.md5importer.resource.mesh.primitive;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Mesh;
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
/*     */ public class Triangle
/*     */   implements Serializable, Savable
/*     */ {
/*     */   private static final long serialVersionUID = -6234457193386375719L;
/*     */   private Mesh mesh;
/*     */   private int[] vertexIndices;
/*     */   
/*     */   public Triangle() {}
/*     */   
/*     */   public Triangle(Mesh mesh) {
/*  34 */     this.mesh = mesh;
/*  35 */     this.vertexIndices = new int[3];
/*     */   }
/*     */ 
/*     */   
/*     */   public void processNormal() {
/*  40 */     Vector3f temp1 = new Vector3f();
/*  41 */     Vector3f temp2 = new Vector3f();
/*  42 */     Vertex vert1 = this.mesh.getVertex(this.vertexIndices[0]);
/*  43 */     Vertex vert2 = this.mesh.getVertex(this.vertexIndices[1]);
/*  44 */     Vertex vert3 = this.mesh.getVertex(this.vertexIndices[2]);
/*  45 */     temp1.set(vert2.getPosition()).subtractLocal(vert1.getPosition());
/*  46 */     temp2.set(vert3.getPosition()).subtractLocal(vert2.getPosition());
/*  47 */     temp1.crossLocal(temp2);
/*  48 */     temp1.normalizeLocal();
/*  49 */     vert1.setNormal(temp2.set(temp1).multLocal(1.0F / vert1.getUsedTimes()));
/*  50 */     vert2.setNormal(temp2.set(temp1).multLocal(1.0F / vert2.getUsedTimes()));
/*  51 */     vert3.setNormal(temp1.multLocal(1.0F / vert3.getUsedTimes()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVertexIndex(int index, int vertex) {
/*  61 */     this.vertexIndices[index] = vertex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVertexIndex(int index) {
/*  71 */     return this.vertexIndices[index];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getClassTag() {
/*  77 */     return Triangle.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(JMEImporter im) throws IOException {
/*  82 */     InputCapsule ic = im.getCapsule(this);
/*  83 */     this.mesh = (Mesh)ic.readSavable("Mesh", null);
/*  84 */     this.vertexIndices = ic.readIntArray("VertexIndices", null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JMEExporter ex) throws IOException {
/*  89 */     OutputCapsule oc = ex.getCapsule(this);
/*  90 */     oc.write((Savable)this.mesh, "Mesh", null);
/*  91 */     oc.write(this.vertexIndices, "VertexIndices", null);
/*     */   }
/*     */   
/*     */   public Triangle clone(Mesh mesh) {
/*  95 */     Triangle clone = new Triangle(mesh);
/*  96 */     clone.vertexIndices = (int[])this.vertexIndices.clone();
/*  97 */     return clone;
/*     */   }
/*     */   
/*     */   public Triangle staticClone(Mesh mesh) {
/* 101 */     Triangle clone = new Triangle(mesh);
/* 102 */     clone.vertexIndices = this.vertexIndices;
/* 103 */     return clone;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\resource\mesh\primitive\Triangle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */