/*     */ package com.funcom.gameengine.jme;
/*     */ 
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class RuntimeContentPassNode
/*     */   extends Node
/*     */   implements PassDrawable
/*     */ {
/*  15 */   private static final Logger LOGGER = Logger.getLogger(RuntimeContentPassNode.class.getName());
/*     */   private Node transparentSpatials;
/*     */   
/*     */   public RuntimeContentPassNode() {
/*  19 */     super("RuntimeContent");
/*     */     
/*  21 */     this.transparentSpatials = new Node("RuntimeTransparentSpatials");
/*  22 */     attachChild((Spatial)this.transparentSpatials);
/*     */     
/*  24 */     setCullHint(Spatial.CullHint.Dynamic);
/*     */   }
/*     */ 
/*     */   
/*     */   public int attachChild(Spatial child) {
/*  29 */     if (isTransparent(child)) {
/*  30 */       return this.transparentSpatials.attachChild(child);
/*     */     }
/*  32 */     return super.attachChild(child);
/*     */   }
/*     */ 
/*     */   
/*     */   public int attachChildAt(Spatial child, int index) {
/*  37 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int detachChild(Spatial child) {
/*  42 */     if (isTransparent(child)) {
/*  43 */       return this.transparentSpatials.detachChild(child);
/*     */     }
/*  45 */     return super.detachChild(child);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDrawByState(Renderer r, DrawPassState drawPassState) {
/*  50 */     if (this.children == null) {
/*     */       return;
/*     */     }
/*  53 */     if (drawPassState.getStateIndex() == 0) {
/*     */       
/*  55 */       DrawPassType type = drawPassState.getType();
/*  56 */       if (type == DrawPassType.RUNTIME_CONTENT) {
/*  57 */         onDrawByStateChildren(r, drawPassState);
/*  58 */       } else if (type == DrawPassType.TRANSPARENT_CONTENT) {
/*  59 */         onDrawByStateChildren(r, drawPassState);
/*  60 */         this.transparentSpatials.onDraw(r);
/*  61 */       } else if (type == DrawPassType.UNKNOWN_PASS_TYPE) {
/*  62 */         for (int i = 0, cSize = this.children.size(); i < cSize; i++) {
/*  63 */           Spatial child = this.children.get(i);
/*  64 */           if (child != this.transparentSpatials && !(child instanceof PassDrawable)) {
/*     */             
/*  66 */             child.onDraw(r);
/*  67 */             LOGGER.log((Priority)Level.INFO, "Found Unknown Pass Type Spatial: " + child);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onDrawByStateChildren(Renderer r, DrawPassState state) {
/*  76 */     for (int i = 0, cSize = this.children.size(); i < cSize; i++) {
/*  77 */       Spatial child = this.children.get(i);
/*  78 */       if (child != this.transparentSpatials && child instanceof PassDrawable) {
/*  79 */         ((PassDrawable)child).onDrawByState(r, state);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isTransparent(Spatial child) {
/*  85 */     return (child instanceof com.jme.scene.BillboardNode || child instanceof com.funcom.gameengine.view.FloatingText2D || child instanceof com.funcom.gameengine.view.FloatingTextSpatial);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void detachAllContent() {
/*  91 */     for (int i = this.children.size() - 1; i >= 0; i--) {
/*  92 */       Spatial child = this.children.get(i);
/*  93 */       if (child != this.transparentSpatials) {
/*  94 */         unlockSpatialTree(child);
/*  95 */         detachChildAt(i);
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     List<Spatial> transparentChildren = this.transparentSpatials.getChildren();
/* 100 */     if (transparentChildren != null) {
/* 101 */       for (Spatial transparentChild : transparentChildren) {
/* 102 */         unlockSpatialTree(transparentChild);
/*     */       }
/*     */     }
/* 105 */     this.transparentSpatials.detachAllChildren();
/*     */   }
/*     */   
/*     */   private void unlockSpatialTree(Spatial root) {
/* 109 */     if (root instanceof Node) {
/* 110 */       Node n = (Node)root;
/* 111 */       List<Spatial> children = n.getChildren();
/* 112 */       if (children != null) {
/* 113 */         for (Spatial child : children) {
/* 114 */           unlockSpatialTree(child);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 119 */     root.unlock();
/*     */   }
/*     */ 
/*     */   
/*     */   public void detachAllChildren() {
/* 124 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\RuntimeContentPassNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */