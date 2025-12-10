/*     */ package com.funcom.gameengine.jme;
/*     */ 
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DrawPassState
/*     */ {
/*  22 */   private static final Logger LOGGER = Logger.getLogger(DrawPassState.class.getName());
/*     */   
/*     */   private final DrawPassType[] types;
/*     */   private DrawPassType type;
/*     */   private int stateIndex;
/*     */   private boolean keepState;
/*     */   
/*     */   public DrawPassState() {
/*  30 */     this(DrawPassType.DEFAULT_STATES);
/*     */   }
/*     */   
/*     */   public DrawPassState(DrawPassType[] types) {
/*  34 */     this.types = types;
/*  35 */     this.type = types[0];
/*     */   }
/*     */   
/*     */   public DrawPassState(DrawPassType type) {
/*  39 */     this(type, 0);
/*     */   }
/*     */   
/*     */   public DrawPassState(DrawPassType type, int stateIndex) {
/*  43 */     this.types = new DrawPassType[] { type };
/*  44 */     this.type = type;
/*  45 */     this.stateIndex = stateIndex;
/*     */   }
/*     */   
/*     */   public DrawPassType getType() {
/*  49 */     return this.type;
/*     */   }
/*     */   
/*     */   public boolean hasMoreStates() {
/*  53 */     return (this.type != null);
/*     */   }
/*     */   
/*     */   public int getStateIndex() {
/*  57 */     return this.stateIndex;
/*     */   }
/*     */   
/*     */   public boolean nextState() {
/*  61 */     if (this.keepState) {
/*  62 */       this.stateIndex++;
/*  63 */       this.keepState = false;
/*  64 */       return false;
/*     */     } 
/*  66 */     int nextStateIndex = this.types.length;
/*  67 */     int length = this.types.length;
/*  68 */     for (int i = 0; i < length; i++) {
/*  69 */       if (this.type == this.types[i]) {
/*  70 */         nextStateIndex = i + 1;
/*     */       }
/*     */     } 
/*  73 */     this.type = null;
/*  74 */     this.stateIndex = 0;
/*  75 */     if (nextStateIndex < this.types.length) {
/*  76 */       this.type = this.types[nextStateIndex];
/*     */     }
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keepState() {
/*  87 */     this.keepState = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNewState() {
/*  97 */     return (this.stateIndex == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDrawCurrentState(Renderer r, List<Spatial> children) {
/* 102 */     if (children == null) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     int childCount = children.size();
/* 107 */     if (hasMoreStates()) {
/* 108 */       for (int i = 0; i < childCount; i++) {
/* 109 */         Spatial child = children.get(i);
/* 110 */         if (child instanceof PassDrawable) {
/* 111 */           ((PassDrawable)child).onDrawByState(r, this);
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
/*     */   public void onDrawComplete(Renderer r, List<Spatial> children) {
/* 125 */     boolean debug = false;
/* 126 */     if (children == null) {
/*     */       return;
/*     */     }
/* 129 */     Spatial child = null;
/*     */     
/* 131 */     int childCount = children.size();
/* 132 */     while (hasMoreStates()) {
/* 133 */       DrawPassType drawPassType = getType();
/*     */       
/* 135 */       if (isNewState()) {
/* 136 */         drawPassType.init(r);
/*     */       }
/*     */       
/* 139 */       for (int i = 0; i < childCount; i++) {
/* 140 */         child = children.get(i);
/* 141 */         if (child instanceof PassDrawable) {
/* 142 */           ((PassDrawable)child).onDrawByState(r, this);
/*     */         }
/*     */       } 
/*     */       
/* 146 */       if (drawPassType.isRenderQueueFlushed()) {
/* 147 */         r.renderQueue();
/*     */       }
/*     */       
/* 150 */       if (nextState()) {
/* 151 */         drawPassType.cleanup(r);
/* 152 */         if (debug) {
/* 153 */           swapAndPause(drawPassType);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (debug) {
/* 165 */       swapAndPause(null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 171 */       for (int i = 0; i < childCount; i++) {
/* 172 */         child = children.get(i);
/* 173 */         if (!(child instanceof PassDrawable)) {
/* 174 */           child.onDraw(r);
/*     */         }
/*     */       } 
/* 177 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 178 */       System.err.println("child=" + child);
/* 179 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void swapAndPause(DrawPassType drawPassType) {
/*     */     try {
/* 185 */       Display.swapBuffers();
/* 186 */       LOGGER.log((Priority)Level.INFO, "drawPassType = " + drawPassType);
/* 187 */       BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
/* 188 */       in.readLine();
/* 189 */     } catch (LWJGLException e) {
/* 190 */       e.printStackTrace();
/* 191 */     } catch (IOException e) {
/* 192 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 198 */     if (this == o) {
/* 199 */       return true;
/*     */     }
/* 201 */     if (!(o instanceof DrawPassState)) {
/* 202 */       return false;
/*     */     }
/*     */     
/* 205 */     DrawPassState passState = (DrawPassState)o;
/*     */     
/* 207 */     if (this.stateIndex != passState.stateIndex) {
/* 208 */       return false;
/*     */     }
/* 210 */     if (this.type != passState.type) {
/* 211 */       return false;
/*     */     }
/*     */     
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 219 */     int result = (this.type != null) ? this.type.hashCode() : 0;
/* 220 */     result = 31 * result + this.stateIndex;
/* 221 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 226 */     return "DrawPassState{type=" + this.type + ", stateIndex=" + this.stateIndex + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\DrawPassState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */