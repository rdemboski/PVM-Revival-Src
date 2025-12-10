/*     */ package com.funcom.commons.jme.md5importer.resource.mesh.primitive;
/*     */ 
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
/*     */ public class Weight
/*     */   implements Serializable, Savable
/*     */ {
/*     */   private static final long serialVersionUID = 4719214599414606855L;
/*     */   private int jointIndex;
/*     */   private float value;
/*  27 */   private Vector3f position = new Vector3f();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJointIndex(int index) {
/*  36 */     this.jointIndex = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWeightValue(float value) {
/*  45 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(int index, float value) {
/*  55 */     switch (index) {
/*     */       case 0:
/*  57 */         this.position.setX(value);
/*     */         break;
/*     */       case 1:
/*  60 */         this.position.setY(value);
/*     */         break;
/*     */       case 2:
/*  63 */         this.position.setZ(value);
/*     */         break;
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
/*     */   public int getJointIndex() {
/*  76 */     return this.jointIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWeightValue() {
/*  85 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f getPosition() {
/*  94 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getClassTag() {
/* 100 */     return Weight.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(JMEImporter im) throws IOException {
/* 105 */     InputCapsule ic = im.getCapsule(this);
/* 106 */     this.jointIndex = ic.readInt("JointIndex", -1);
/* 107 */     this.value = ic.readFloat("Value", 0.0F);
/* 108 */     this.position = (Vector3f)ic.readSavable("Position", null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JMEExporter ex) throws IOException {
/* 113 */     OutputCapsule oc = ex.getCapsule(this);
/* 114 */     oc.write(this.jointIndex, "JointIndex", -1);
/* 115 */     oc.write(this.value, "Value", 0.0F);
/* 116 */     oc.write((Savable)this.position, "Position", null);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\resource\mesh\primitive\Weight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */