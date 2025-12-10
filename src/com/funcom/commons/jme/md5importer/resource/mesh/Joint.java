/*     */ package com.funcom.commons.jme.md5importer.resource.mesh;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.TransformMatrix;
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
/*     */ public class Joint
/*     */   implements Serializable, Savable
/*     */ {
/*     */   private static final long serialVersionUID = -926371530130383637L;
/*     */   private ModelNode modelNode;
/*     */   private String name;
/*     */   private int parent;
/*     */   private int nodeParent;
/*     */   private Vector3f translation;
/*     */   private Quaternion orientation;
/*     */   private TransformMatrix transform;
/*  37 */   private TransformMatrix tmpTransformMatrix = new TransformMatrix();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Joint() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Joint(String name, ModelNode modelNode) {
/*  51 */     this.name = name;
/*  52 */     this.modelNode = modelNode;
/*  53 */     this.parent = -1;
/*  54 */     this.nodeParent = -1;
/*  55 */     this.translation = new Vector3f();
/*  56 */     this.orientation = new Quaternion();
/*  57 */     this.transform = new TransformMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTransform(Vector3f translation, Quaternion orientation) {
/*  67 */     this.translation.set(translation);
/*  68 */     this.orientation.set(orientation);
/*  69 */     this.modelNode.flagUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processTransform(Vector3f parentTrans, Quaternion parentOrien) {
/*  80 */     if (parentTrans == null || parentOrien == null) {
/*  81 */       parentOrien = new Quaternion();
/*  82 */       parentTrans = new Vector3f();
/*     */     } 
/*  84 */     this.orientation.set(parentOrien.inverse().multLocal(this.orientation));
/*  85 */     this.translation.subtractLocal(parentTrans);
/*  86 */     parentOrien.inverse().multLocal(this.translation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processRelative() {
/*  93 */     this.transform.loadIdentity();
/*  94 */     if (this.parent >= 0) {
/*  95 */       this.transform.set(this.modelNode.getJoint(this.parent).getTransform());
/*     */     } else {
/*  97 */       assignBaseTransform(this.transform);
/*     */     } 
/*  99 */     this.tmpTransformMatrix.set(this.orientation, this.translation);
/* 100 */     this.transform.multLocal(this.tmpTransformMatrix, new Vector3f());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void assignBaseTransform(TransformMatrix transform) {
/* 107 */     if (this.nodeParent < 0) {
/* 108 */       transform.loadIdentity();
/*     */     } else {
/* 110 */       transform.loadIdentity();
/* 111 */       transform.combineWithParent(((ModelNode)this.modelNode.getParent()).getJoint(this.nodeParent).getTransform());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(int parent) {
/* 121 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNodeParent(int outerParent) {
/* 130 */     this.nodeParent = outerParent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransform(int index, float value) {
/* 140 */     switch (index) {
/*     */       case 0:
/* 142 */         this.translation.setX(value);
/*     */         break;
/*     */       case 1:
/* 145 */         this.translation.setY(value);
/*     */         break;
/*     */       case 2:
/* 148 */         this.translation.setZ(value);
/*     */         break;
/*     */       case 3:
/* 151 */         this.orientation.x = value;
/*     */         break;
/*     */       case 4:
/* 154 */         this.orientation.y = value;
/*     */         break;
/*     */       case 5:
/* 157 */         this.orientation.z = value;
/* 158 */         processOrientation();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processOrientation() {
/* 169 */     float t = 1.0F - this.orientation.x * this.orientation.x - this.orientation.y * this.orientation.y - this.orientation.z * this.orientation.z;
/* 170 */     if (t < 0.0F) { this.orientation.w = 0.0F; }
/* 171 */     else { this.orientation.w = -FastMath.sqrt(t); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f getTranslation() {
/* 180 */     return this.translation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quaternion getOrientation() {
/* 189 */     return this.orientation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformMatrix getTransform() {
/* 198 */     return this.transform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParent() {
/* 207 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 216 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getClassTag() {
/* 222 */     return Joint.class;
/*     */   }
/*     */   
/*     */   public void setModelNode(ModelNode modelNode) {
/* 226 */     this.modelNode = modelNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(JMEImporter im) throws IOException {
/* 231 */     InputCapsule ic = im.getCapsule(this);
/* 232 */     this.modelNode = (ModelNode)ic.readSavable("ModelNode", null);
/* 233 */     this.name = ic.readString("Name", null);
/* 234 */     this.parent = ic.readInt("Parent", -1);
/* 235 */     this.nodeParent = ic.readInt("NodeParent", -1);
/* 236 */     this.translation = (Vector3f)ic.readSavable("Translation", null);
/* 237 */     this.orientation = (Quaternion)ic.readSavable("Orientation", null);
/* 238 */     this.transform = (TransformMatrix)ic.readSavable("Transform", null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JMEExporter ex) throws IOException {
/* 243 */     OutputCapsule oc = ex.getCapsule(this);
/* 244 */     oc.write((Savable)this.modelNode, "ModelNode", null);
/* 245 */     oc.write(this.name, "Name", null);
/* 246 */     oc.write(this.parent, "Parent", -1);
/* 247 */     oc.write(this.nodeParent, "NodeParent", -1);
/* 248 */     oc.write((Savable)this.translation, "Translation", null);
/* 249 */     oc.write((Savable)this.orientation, "Orientation", null);
/* 250 */     oc.write((Savable)this.transform, "Transform", null);
/*     */   }
/*     */   
/*     */   public Joint clone(ModelNode model) {
/* 254 */     Joint clone = new Joint(this.name, model);
/* 255 */     clone.parent = this.parent;
/* 256 */     clone.nodeParent = this.nodeParent;
/* 257 */     clone.translation = this.translation.clone();
/* 258 */     clone.orientation = this.orientation.clone();
/* 259 */     clone.transform = this.transform.clone();
/* 260 */     return clone;
/*     */   }
/*     */   
/*     */   public Joint cloneStatic(ModelNode model) {
/* 264 */     Joint clone = new Joint(this.name, model);
/* 265 */     clone.parent = this.parent;
/* 266 */     clone.nodeParent = this.nodeParent;
/* 267 */     clone.translation = new Vector3f(this.translation);
/* 268 */     clone.orientation = new Quaternion(this.orientation);
/* 269 */     clone.transform = new TransformMatrix(this.transform);
/* 270 */     return clone;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\resource\mesh\Joint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */