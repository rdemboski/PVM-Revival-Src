/*     */ package com.funcom.commons.jme.md5importer.importer.resource;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*     */ import com.funcom.commons.jme.md5importer.exception.InvalidVersionException;
/*     */ import com.funcom.commons.jme.md5importer.importer.resource.token.Md5NumberToken;
/*     */ import com.funcom.commons.jme.md5importer.importer.resource.token.Md5StringToken;
/*     */ import com.funcom.commons.jme.md5importer.importer.resource.token.Md5Token;
/*     */ import com.funcom.commons.jme.md5importer.resource.anim.Frame;
/*     */ import java.io.IOException;
/*     */ import java.io.StreamTokenizer;
/*     */ import java.util.BitSet;
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
/*     */ public class AnimImporter
/*     */ {
/*     */   private Md5Stream md5stream;
/*     */   private float frameRate;
/*     */   private String[] idHierarchy;
/*     */   private int[] parentHierarchy;
/*     */   private BitSet frameflags;
/*     */   private int[] startIndex;
/*     */   private int numJoints;
/*     */   private int numAnimatedComponents;
/*     */   private Frame baseframe;
/*     */   private Frame[] frames;
/*     */   private JointAnimation animation;
/*     */   
/*     */   public AnimImporter(StreamTokenizer reader) {
/*  51 */     this.md5stream = new Md5Stream(reader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JointAnimation loadAnim(String name) throws IOException {
/*  62 */     this.animation = new JointAnimation(name);
/*  63 */     processAnim();
/*  64 */     constructAnimation();
/*  65 */     return this.animation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processAnim() throws IOException {
/*     */     TokenizedLine line;
/*  77 */     while ((line = this.md5stream.getNextTokenizedLine()) != null) {
/*  78 */       if (line.get(0) instanceof Md5StringToken) {
/*  79 */         String keyword = ((Md5StringToken)line.get(0)).getValue().toLowerCase();
/*  80 */         if (keyword.equals("md5version")) {
/*  81 */           if ((int)((Md5NumberToken)line.get(1)).getValue() != 10)
/*  82 */             throw new InvalidVersionException((int)((Md5NumberToken)line.get(1)).getValue());  continue;
/*  83 */         }  if (keyword.equals("numframes")) {
/*  84 */           this.frames = new Frame[(int)((Md5NumberToken)line.get(1)).getValue()]; continue;
/*  85 */         }  if (keyword.equals("numjoints")) {
/*  86 */           this.numJoints = (int)((Md5NumberToken)line.get(1)).getValue();
/*  87 */           this.idHierarchy = new String[this.numJoints];
/*  88 */           this.parentHierarchy = new int[this.numJoints];
/*  89 */           this.startIndex = new int[this.numJoints]; continue;
/*  90 */         }  if (keyword.equals("framerate")) {
/*  91 */           this.frameRate = (float)((Md5NumberToken)line.get(1)).getValue(); continue;
/*  92 */         }  if (keyword.equals("hierarchy")) {
/*  93 */           processHierarchy(); continue;
/*  94 */         }  if (keyword.equals("bounds"))
/*     */           continue; 
/*  96 */         if (keyword.equals("baseframe")) {
/*  97 */           processBaseframe(); continue;
/*  98 */         }  if (keyword.equals("frame")) {
/*  99 */           processFrame((int)((Md5NumberToken)line.get(1)).getValue()); continue;
/* 100 */         }  if (keyword.equals("commandline"))
/*     */           continue; 
/* 102 */         if (keyword.equals("numanimatedcomponents")) {
/* 103 */           this.numAnimatedComponents = (int)((Md5NumberToken)line.get(1)).getValue();
/*     */         }
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
/*     */   private void processHierarchy() throws IOException {
/* 116 */     this.frameflags = new BitSet();
/* 117 */     Block block = this.md5stream.getNextBlock();
/*     */     
/* 119 */     int jointIndex = 0;
/* 120 */     for (TokenizedLine line : block.getTokenizedLines()) {
/* 121 */       this.idHierarchy[jointIndex] = ((Md5StringToken)line.get(0)).getValue();
/*     */       
/*     */       try {
/* 124 */         this.parentHierarchy[jointIndex] = (int)((Md5NumberToken)line.get(1)).getValue();
/* 125 */         this.startIndex[jointIndex] = (int)((Md5NumberToken)line.get(3)).getValue();
/* 126 */         int flag = (int)((Md5NumberToken)line.get(2)).getValue();
/* 127 */         for (int i = 0; i < 6; i++) {
/* 128 */           this.frameflags.set(jointIndex * 6 + i, ((flag & 1 << i) != 0));
/*     */         }
/* 130 */       } catch (NumberFormatException e) {}
/*     */ 
/*     */       
/* 133 */       jointIndex++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processBaseframe() throws IOException {
/* 143 */     Block block = this.md5stream.getNextBlock();
/* 144 */     this.baseframe = new Frame(true, this.numJoints);
/* 145 */     this.baseframe.setParents(this.parentHierarchy);
/*     */     
/* 147 */     int jointIndex = 0;
/* 148 */     for (TokenizedLine line : block.getTokenizedLines()) {
/* 149 */       for (int tokenIndex = 0; tokenIndex < 6; tokenIndex++) {
/* 150 */         this.baseframe.setTransform(jointIndex, tokenIndex, (float)((Md5NumberToken)line.get(tokenIndex)).getValue());
/*     */       }
/* 152 */       jointIndex++;
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
/*     */ 
/*     */   
/*     */   private void processFrame(int index) throws IOException {
/* 170 */     Block block = this.md5stream.getNextBlock();
/* 171 */     this.frames[index] = new Frame(false, this.numJoints);
/* 172 */     this.frames[index].setParents(this.parentHierarchy);
/*     */     
/* 174 */     float[] replacementValues = new float[6 * this.numAnimatedComponents];
/* 175 */     int r = 0;
/* 176 */     for (TokenizedLine line : block.getTokenizedLines()) {
/* 177 */       for (Md5Token token : line.getAllTokens()) {
/* 178 */         replacementValues[r++] = (float)((Md5NumberToken)token).getValue();
/*     */       }
/*     */     } 
/*     */     
/* 182 */     for (int j = 0; j < this.numJoints; j++) {
/* 183 */       float[] values = new float[6];
/* 184 */       r = 0; int f;
/* 185 */       for (f = 0; f < 6; f++) {
/* 186 */         if (this.frameflags.get(j * 6 + f)) {
/* 187 */           values[f] = replacementValues[this.startIndex[j] + r++];
/*     */         } else {
/* 189 */           values[f] = this.baseframe.getTransformValue(j, f);
/*     */         } 
/*     */       } 
/* 192 */       if (this.parentHierarchy[j] < 0) {
/* 193 */         this.frames[index].setTransform(j, 0, values[2]);
/* 194 */         this.frames[index].setTransform(j, 1, values[1]);
/* 195 */         this.frames[index].setTransform(j, 2, values[0]);
/* 196 */         this.frames[index].setTransform(j, 3, values[5]);
/* 197 */         this.frames[index].setTransform(j, 4, values[4]);
/* 198 */         this.frames[index].setTransform(j, 5, values[3]);
/*     */       } else {
/* 200 */         for (f = 0; f < values.length; f++) {
/* 201 */           this.frames[index].setTransform(j, f, values[f]);
/*     */         }
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
/*     */   private void constructAnimation() {
/* 235 */     this.animation.setJointIDs(this.idHierarchy);
/* 236 */     this.animation.setFrames(this.frames);
/* 237 */     this.animation.setInitialFrameRate(this.frameRate);
/* 238 */     this.idHierarchy = null;
/* 239 */     this.parentHierarchy = null;
/* 240 */     this.frameflags = null;
/* 241 */     this.baseframe = null;
/* 242 */     this.frames = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\resource\AnimImporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */