/*     */ package com.funcom.commons.jme.md5importer.controller;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*     */ import com.funcom.commons.jme.md5importer.exception.InvalidAnimationException;
/*     */ import com.funcom.commons.jme.md5importer.resource.anim.Frame;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Joint;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.util.export.InputCapsule;
/*     */ import com.jme.util.export.JMEExporter;
/*     */ import com.jme.util.export.JMEImporter;
/*     */ import com.jme.util.export.OutputCapsule;
/*     */ import com.jme.util.export.Savable;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JointController
/*     */   extends Controller
/*     */ {
/*     */   private static final long serialVersionUID = 1029065355427370006L;
/*  29 */   private static final Logger logger = Logger.getLogger(JointController.class.getName());
/*     */ 
/*     */   
/*     */   private float time;
/*     */   
/*     */   private Joint[] joints;
/*     */   
/*     */   private JointAnimation activeAnimation;
/*     */   
/*     */   private HashMap<String, JointAnimation> animations;
/*     */   
/*     */   private float interpolation;
/*     */   
/*     */   private Vector3f translation;
/*     */   
/*     */   private Quaternion orientation;
/*     */   
/*     */   private boolean fading;
/*     */   
/*     */   private float fadingTime;
/*     */   
/*     */   private boolean initialized;
/*     */ 
/*     */   
/*     */   public JointController() {
/*  54 */     this.translation = new Vector3f();
/*  55 */     this.orientation = new Quaternion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JointController(Joint[] joints) {
/*  64 */     this.joints = joints;
/*  65 */     this.animations = new HashMap<String, JointAnimation>();
/*  66 */     this.translation = new Vector3f();
/*  67 */     this.orientation = new Quaternion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(float time) {
/*  78 */     this.time += time * getSpeed();
/*  79 */     if (!this.fading) {
/*  80 */       if (this.activeAnimation != null) {
/*  81 */         this.activeAnimation.update(time, getRepeatType(), getSpeed());
/*     */       }
/*  83 */       this.interpolation = getInterpolation();
/*  84 */       for (int i = 0; i < this.joints.length; i++) {
/*  85 */         this.translation.interpolate(this.activeAnimation.getPreviousFrame().getTranslation(i), this.activeAnimation.getNextFrame().getTranslation(i), this.interpolation);
/*     */         
/*  87 */         this.orientation.slerp(this.activeAnimation.getPreviousFrame().getOrientation(i), this.activeAnimation.getNextFrame().getOrientation(i), this.interpolation);
/*     */         
/*  89 */         this.joints[i].updateTransform(this.translation, this.orientation);
/*     */       } 
/*     */     } else {
/*  92 */       this.interpolation = getInterpolation();
/*  93 */       for (int i = 0; i < this.joints.length; i++) {
/*  94 */         this.translation.interpolate(this.joints[i].getTranslation(), this.activeAnimation.getPreviousFrame().getTranslation(i), this.interpolation);
/*     */         
/*  96 */         this.orientation.slerp(this.joints[i].getOrientation(), this.activeAnimation.getPreviousFrame().getOrientation(i), this.interpolation);
/*     */         
/*  98 */         this.joints[i].updateTransform(this.translation, this.orientation);
/*     */       } 
/* 100 */       if (this.interpolation >= 1.0F) {
/* 101 */         this.fading = false;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getInterpolation() {
/*     */     float interpolation;
/* 113 */     if (!this.fading) {
/* 114 */       float prev = this.activeAnimation.getPreviousTime();
/* 115 */       float next = this.activeAnimation.getNextTime();
/* 116 */       if (prev == next) return 0.0F; 
/* 117 */       interpolation = (this.time - prev) / (next - prev);
/*     */     } else {
/* 119 */       interpolation = this.time / this.fadingTime;
/*     */     } 
/* 121 */     if (interpolation < 0.0F)
/* 122 */       return 0.0F; 
/* 123 */     if (interpolation > 1.0F) {
/* 124 */       return 1.0F;
/*     */     }
/* 126 */     return interpolation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validateAnimation(JointAnimation animation) {
/* 137 */     return (this.joints.length == (animation.getJointIDs()).length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimation(JointAnimation animation) {
/* 146 */     addAnimation(animation.getName(), animation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimation(String name, JointAnimation animation) {
/* 156 */     if (validateAnimation(animation))
/* 157 */     { this.animations.put(name, animation);
/* 158 */       if (this.activeAnimation == null)
/* 159 */         this.activeAnimation = animation;  }
/* 160 */     else { throw new InvalidAnimationException(this.joints.length, (animation.getJointIDs()).length); }
/*     */   
/*     */   }
/*     */   
/*     */   public void removeAllAnimations() {
/* 165 */     this.activeAnimation = null;
/* 166 */     this.animations.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActiveAnimation(String name) {
/* 175 */     if (this.animations.containsKey(name))
/* 176 */     { JointAnimation animation = this.animations.get(name);
/* 177 */       setActiveAnimation(animation); }
/* 178 */     else { logger.info("Invalid animation name: " + name); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActiveAnimation(JointAnimation animation) {
/* 187 */     String name = animation.getName();
/* 188 */     if (this.activeAnimation == null || !this.activeAnimation.getName().equals(name) || !this.animations.containsKey(name) || !this.activeAnimation.equals(this.animations.get(name)))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 193 */       if (this.animations.containsValue(animation)) {
/* 194 */         this.time = 0.0F;
/* 195 */         animation.restart();
/* 196 */         this.activeAnimation = animation;
/*     */       } else {
/* 198 */         addAnimation(animation);
/*     */       } 
/*     */     }
/*     */     
/* 202 */     initOnce();
/*     */   }
/*     */   
/*     */   private void initOnce() {
/* 206 */     if (!this.initialized && this.activeAnimation != null && this.activeAnimation.hasNextFrame()) {
/*     */ 
/*     */       
/* 209 */       setToFrame(this.activeAnimation.getNextFrame());
/* 210 */       this.initialized = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setToFrame(Frame srcFrame) {
/* 215 */     for (int i = 0; i < this.joints.length; i++) {
/* 216 */       this.joints[i].updateTransform(srcFrame.getTranslation(i), srcFrame.getOrientation(i));
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
/*     */   public void setActiveAnimation(String name, float fadingTime) {
/* 228 */     enabledFading(fadingTime);
/* 229 */     setActiveAnimation(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActiveAnimation(JointAnimation animation, float fadingTime) {
/* 239 */     enabledFading(fadingTime);
/* 240 */     setActiveAnimation(animation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void enabledFading(float fadingTime) {
/* 249 */     this.fading = true;
/* 250 */     this.fadingTime = fadingTime;
/* 251 */     this.time = 0.0F;
/*     */   }
/*     */   
/*     */   public Map<String, JointAnimation> getAnimations() {
/* 255 */     return Collections.unmodifiableMap(this.animations);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getClassTag() {
/* 261 */     return JointController.class;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(JMEImporter e) throws IOException {
/* 267 */     super.read(e);
/* 268 */     InputCapsule ic = e.getCapsule((Savable)this);
/* 269 */     Savable[] temp = ic.readSavableArray("Joints", null);
/* 270 */     this.joints = new Joint[temp.length];
/* 271 */     for (int i = 0; i < temp.length; i++) {
/* 272 */       this.joints[i] = (Joint)temp[i];
/*     */     }
/* 274 */     this.activeAnimation = (JointAnimation)ic.readSavable("ActiveAnimation", null);
/* 275 */     this.animations = (HashMap<String, JointAnimation>)ic.readStringSavableMap("Animations", null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JMEExporter e) throws IOException {
/* 280 */     super.write(e);
/* 281 */     OutputCapsule oc = e.getCapsule((Savable)this);
/* 282 */     oc.write((Savable[])this.joints, "Joints", null);
/* 283 */     oc.write((Savable)this.activeAnimation, "ActiveAnimation", null);
/* 284 */     oc.writeStringSavableMap(this.animations, "Animations", null);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\controller\JointController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */