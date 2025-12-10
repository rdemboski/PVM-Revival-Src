/*     */ package com.funcom.commons.jme.md5importer.resource.anim;
/*     */ 
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.util.export.InputCapsule;
/*     */ import com.jme.util.export.JMEExporter;
/*     */ import com.jme.util.export.JMEImporter;
/*     */ import com.jme.util.export.OutputCapsule;
/*     */ import com.jme.util.export.Savable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ public class Frame
/*     */   implements Serializable, Savable
/*     */ {
/*     */   private static final long serialVersionUID = 8891271219195292580L;
/*     */   private boolean baseframe;
/*     */   private int[] parents;
/*     */   private Vector3f[] translations;
/*     */   private Quaternion[] orientations;
/*     */   
/*     */   public Frame() {}
/*     */   
/*     */   public Frame(boolean baseframe, int numJoints) {
/*  45 */     this.baseframe = baseframe;
/*  46 */     this.translations = new Vector3f[numJoints];
/*  47 */     this.orientations = new Quaternion[numJoints];
/*  48 */     for (int i = 0; i < this.translations.length && i < this.orientations.length; i++) {
/*  49 */       this.translations[i] = new Vector3f();
/*  50 */       this.orientations[i] = new Quaternion();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParents(int[] parents) {
/*  60 */     this.parents = parents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransform(int jointIndex, int index, float value) {
/*  71 */     switch (index) {
/*     */       case 0:
/*  73 */         (this.translations[jointIndex]).x = value;
/*     */         break;
/*     */       case 1:
/*  76 */         (this.translations[jointIndex]).y = value;
/*     */         break;
/*     */       case 2:
/*  79 */         (this.translations[jointIndex]).z = value;
/*     */         break;
/*     */       case 3:
/*  82 */         (this.orientations[jointIndex]).x = value;
/*     */         break;
/*     */       case 4:
/*  85 */         (this.orientations[jointIndex]).y = value;
/*     */         break;
/*     */       case 5:
/*  88 */         (this.orientations[jointIndex]).z = value;
/*  89 */         processOrientation(this.orientations[jointIndex]);
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
/*     */   private void processOrientation(Quaternion raw) {
/* 102 */     float t = 1.0F - raw.x * raw.x - raw.y * raw.y - raw.z * raw.z;
/* 103 */     if (t < 0.0F) { raw.w = 0.0F; }
/* 104 */     else { raw.w = -FastMath.sqrt(t); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParent(int index) {
/* 114 */     return this.parents[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTransformValue(int jointIndex, int transIndex) {
/* 125 */     switch (transIndex) {
/*     */       case 0:
/* 127 */         return (this.translations[jointIndex]).x;
/*     */       case 1:
/* 129 */         return (this.translations[jointIndex]).y;
/*     */       case 2:
/* 131 */         return (this.translations[jointIndex]).z;
/*     */       case 3:
/* 133 */         return (this.orientations[jointIndex]).x;
/*     */       case 4:
/* 135 */         return (this.orientations[jointIndex]).y;
/*     */       case 5:
/* 137 */         return (this.orientations[jointIndex]).z;
/*     */     } 
/* 139 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f getTranslation(int jointIndex) {
/* 150 */     return this.translations[jointIndex];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quaternion getOrientation(int jointIndex) {
/* 160 */     return this.orientations[jointIndex];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getClassTag() {
/* 166 */     return Frame.class;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(JMEImporter im) throws IOException {
/* 172 */     InputCapsule ic = im.getCapsule(this);
/* 173 */     this.baseframe = ic.readBoolean("Baseframe", false);
/* 174 */     this.parents = ic.readIntArray("Parents", null);
/* 175 */     Savable[] temp = ic.readSavableArray("Translations", null);
/* 176 */     this.translations = new Vector3f[temp.length]; int i;
/* 177 */     for (i = 0; i < temp.length; i++) {
/* 178 */       this.translations[i] = (Vector3f)temp[i];
/*     */     }
/* 180 */     temp = ic.readSavableArray("Orientations", null);
/* 181 */     this.orientations = new Quaternion[temp.length];
/* 182 */     for (i = 0; i < temp.length; i++) {
/* 183 */       this.orientations[i] = (Quaternion)temp[i];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JMEExporter ex) throws IOException {
/* 189 */     OutputCapsule oc = ex.getCapsule(this);
/* 190 */     oc.write(this.baseframe, "Baseframe", false);
/* 191 */     oc.write(this.parents, "Parents", null);
/* 192 */     oc.write((Savable[])this.translations, "Translations", null);
/* 193 */     oc.write((Savable[])this.orientations, "Orientations", null);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 197 */     return "Frame{baseframe=" + this.baseframe + ", parents=" + this.parents + ", translations=" + ((this.translations == null) ? null : (String)Arrays.<Vector3f>asList(this.translations)) + ", orientations=" + ((this.orientations == null) ? null : (String)Arrays.<Quaternion>asList(this.orientations)) + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\resource\anim\Frame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */