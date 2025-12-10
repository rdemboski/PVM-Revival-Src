/*     */ package com.funcom.commons.jme.md5importer;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.resource.anim.Frame;
/*     */ import com.jme.util.export.InputCapsule;
/*     */ import com.jme.util.export.JMEExporter;
/*     */ import com.jme.util.export.JMEImporter;
/*     */ import com.jme.util.export.OutputCapsule;
/*     */ import com.jme.util.export.Savable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
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
/*     */ public class JointAnimation
/*     */   implements Serializable, Savable
/*     */ {
/*     */   private static final long serialVersionUID = 3646737896444759738L;
/*     */   private String name;
/*     */   private String[] jointIDs;
/*     */   private Frame[] frames;
/*     */   private float currentFrameRate;
/*     */   private float initialFrameRate;
/*     */   private boolean backward;
/*     */   private float time;
/*     */   private int prevFrame;
/*     */   private int nextFrame;
/*     */   private ArrayList<JointAnimation> animations;
/*     */   
/*     */   public JointAnimation() {}
/*     */   
/*     */   public JointAnimation(String name) {
/*  50 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(float time, int repeat, float speed) {
/*  61 */     this.time += time * speed;
/*  62 */     switch (repeat) {
/*     */       case 0:
/*  64 */         updateClamp();
/*     */         break;
/*     */       case 2:
/*  67 */         updateCycle();
/*     */         break;
/*     */       case 1:
/*  70 */         updateWrap();
/*     */         break;
/*     */     } 
/*  73 */     if (this.animations != null) {
/*  74 */       for (JointAnimation anim : this.animations) {
/*  75 */         anim.update(time, repeat, speed);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateClamp() {
/*  82 */     float frameTime = getFrameTime();
/*  83 */     if (this.time >= frameTime) {
/*  84 */       this.prevFrame = this.nextFrame;
/*  85 */       this.nextFrame = (int)(this.nextFrame + this.time / frameTime);
/*  86 */       if (this.nextFrame >= this.frames.length) {
/*  87 */         this.nextFrame = this.frames.length - 1;
/*  88 */         this.prevFrame = this.nextFrame;
/*     */       } 
/*  90 */       this.time %= frameTime;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateCycle() {
/*  96 */     float frameTime = getFrameTime();
/*  97 */     while (this.time >= frameTime) {
/*  98 */       this.prevFrame = this.nextFrame;
/*  99 */       if (!this.backward) {
/* 100 */         this.nextFrame++;
/* 101 */         if (this.nextFrame >= this.frames.length) {
/* 102 */           this.backward = true;
/* 103 */           this.prevFrame = this.frames.length - 1;
/* 104 */           this.nextFrame = this.prevFrame - 1;
/* 105 */           if (this.nextFrame < 0) {
/* 106 */             this.nextFrame = 0;
/*     */           }
/*     */         } 
/*     */       } else {
/* 110 */         this.nextFrame--;
/* 111 */         if (this.nextFrame < 0) {
/* 112 */           this.backward = false;
/* 113 */           this.prevFrame = 0;
/* 114 */           this.nextFrame = this.prevFrame + 1;
/* 115 */           if (this.nextFrame >= this.frames.length) {
/* 116 */             this.nextFrame = this.frames.length - 1;
/*     */           }
/*     */         } 
/*     */       } 
/* 120 */       this.time -= frameTime;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateWrap() {
/* 126 */     float frameTime = getFrameTime();
/* 127 */     if (this.time >= frameTime) {
/* 128 */       this.nextFrame = (int)(this.nextFrame + this.time / frameTime);
/* 129 */       this.nextFrame %= this.frames.length;
/* 130 */       this.time %= frameTime;
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getFrameTime() {
/* 135 */     return 1.0F / this.currentFrameRate;
/*     */   }
/*     */   
/*     */   public void restart() {
/* 139 */     this.backward = false;
/* 140 */     this.prevFrame = 0;
/* 141 */     this.nextFrame = (this.frames.length > 1) ? 1 : 0;
/* 142 */     this.time = 0.0F;
/*     */     
/* 144 */     if (this.animations != null) {
/* 145 */       for (JointAnimation animation : this.animations) {
/* 146 */         animation.restart();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimation(JointAnimation animation) {
/* 156 */     if (this.animations == null) this.animations = new ArrayList<JointAnimation>(); 
/* 157 */     this.animations.add(animation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJointIDs(String[] joints) {
/* 166 */     this.jointIDs = new String[joints.length];
/* 167 */     System.arraycopy(joints, 0, this.jointIDs, 0, this.jointIDs.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrames(Frame[] frames) {
/* 176 */     this.frames = new Frame[frames.length];
/* 177 */     System.arraycopy(frames, 0, this.frames, 0, this.frames.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrameRate(float frameRate) {
/* 186 */     this.currentFrameRate = frameRate;
/*     */   }
/*     */   
/*     */   public void setInitialFrameRate(float initialFrameRate) {
/* 190 */     this.initialFrameRate = initialFrameRate;
/* 191 */     this.currentFrameRate = initialFrameRate;
/*     */   }
/*     */   
/*     */   public void resetFrameRate() {
/* 195 */     this.currentFrameRate = this.initialFrameRate;
/*     */   }
/*     */   
/*     */   public float getFrameRate() {
/* 199 */     return this.initialFrameRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Frame getPreviousFrame() {
/* 208 */     return this.frames[this.prevFrame];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPreviousTime() {
/* 217 */     return this.prevFrame * getFrameTime();
/*     */   }
/*     */   
/*     */   public boolean hasNextFrame() {
/* 221 */     return (this.nextFrame >= 0 && this.nextFrame < this.frames.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Frame getNextFrame() {
/* 230 */     if (hasNextFrame()) {
/* 231 */       return this.frames[this.nextFrame];
/*     */     }
/* 233 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getNextTime() {
/* 242 */     return this.nextFrame * getFrameTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getJointIDs() {
/* 251 */     return this.jointIDs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 260 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getClassTag() {
/* 266 */     return JointAnimation.class;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(JMEImporter im) throws IOException {
/* 272 */     InputCapsule ic = im.getCapsule(this);
/* 273 */     this.name = ic.readString("Name", null);
/* 274 */     this.jointIDs = ic.readStringArray("JointIDs", null);
/* 275 */     Savable[] temp = ic.readSavableArray("Frames", null);
/* 276 */     this.frames = new Frame[temp.length];
/* 277 */     for (int i = 0; i < temp.length; i++) {
/* 278 */       this.frames[i] = (Frame)temp[i];
/*     */     }
/* 280 */     this.initialFrameRate = ic.readFloat("FrameRate", 0.0F);
/* 281 */     this.animations = ic.readSavableArrayList("Animations", null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JMEExporter ex) throws IOException {
/* 286 */     OutputCapsule oc = ex.getCapsule(this);
/* 287 */     oc.write(this.name, "Name", null);
/* 288 */     oc.write(this.jointIDs, "JointIDs", null);
/* 289 */     oc.write((Savable[])this.frames, "Frames", null);
/* 290 */     oc.write(this.initialFrameRate, "FrameRate", 0.0F);
/* 291 */     oc.writeSavableArrayList(this.animations, "Animations", null);
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 295 */     this.name = name;
/*     */   }
/*     */   
/*     */   public Frame[] getFrames() {
/* 299 */     return this.frames;
/*     */   }
/*     */   
/*     */   public JointAnimation clone() {
/* 303 */     JointAnimation animation = new JointAnimation(this.name);
/* 304 */     animation.setInitialFrameRate(this.initialFrameRate);
/* 305 */     animation.setFrames(this.frames);
/* 306 */     animation.setJointIDs(this.jointIDs);
/*     */     
/* 308 */     return animation;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 312 */     return "JointAnimation{name='" + this.name + '\'' + ", currentFrameRate=" + this.currentFrameRate + ", initialFrameRate=" + this.initialFrameRate + ", backward=" + this.backward + ", time=" + this.time + ", prevFrame=" + this.prevFrame + ", nextFrame=" + this.nextFrame + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\JointAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */