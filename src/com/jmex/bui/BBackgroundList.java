/*     */ package com.jmex.bui;
/*     */ 
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class BBackgroundList
/*     */   extends BBackground
/*     */ {
/*  22 */   private List<ManagedBackground> backgrounds = new LinkedList<ManagedBackground>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBackground(BBackground background) {
/*  32 */     ManagedBackground managedBackground = new ManagedBackground(background);
/*  33 */     this.backgrounds.add(managedBackground);
/*  34 */     managedBackground.wasAdded();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBackgroundFirst(BBackground background) {
/*  43 */     ManagedBackground managedBackground = new ManagedBackground(background);
/*  44 */     this.backgrounds.add(0, managedBackground);
/*  45 */     managedBackground.wasAdded();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeBackground(BBackground background) {
/*  50 */     for (int i = 0; i < this.backgrounds.size(); i++) {
/*  51 */       ManagedBackground managedBackground = this.backgrounds.get(i);
/*  52 */       if (managedBackground.background == background) {
/*  53 */         this.backgrounds.remove(i);
/*  54 */         managedBackground.wasRemoved();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeAllBackgrounds() {
/*  61 */     int size = this.backgrounds.size();
/*  62 */     for (int i = size - 1; i >= 0; i--) {
/*  63 */       ManagedBackground managedBackground = this.backgrounds.get(i);
/*  64 */       this.backgrounds.remove(i);
/*  65 */       managedBackground.wasRemoved();
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<BBackground> getBackgrounds() {
/*  70 */     List<BBackground> result = new LinkedList<BBackground>();
/*  71 */     for (ManagedBackground background : this.backgrounds) {
/*  72 */       result.add(background.background);
/*     */     }
/*  74 */     return Collections.unmodifiableList(result);
/*     */   }
/*     */   
/*     */   public int getBackgroundCount() {
/*  78 */     return this.backgrounds.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinimumWidth() {
/*  83 */     int minimumWidth = super.getMinimumWidth();
/*  84 */     for (ManagedBackground background : this.backgrounds) {
/*  85 */       minimumWidth = Math.max(minimumWidth, background.background.getMinimumWidth());
/*     */     }
/*  87 */     return minimumWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinimumHeight() {
/*  92 */     int minimumHeight = super.getMinimumWidth();
/*  93 */     for (ManagedBackground background : this.backgrounds) {
/*  94 */       minimumHeight = Math.max(minimumHeight, background.background.getMinimumHeight());
/*     */     }
/*  96 */     return minimumHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer, int x, int y, int width, int height, float alpha) {
/* 101 */     super.render(renderer, x, y, width, height, alpha);
/*     */     
/* 103 */     for (ManagedBackground background : this.backgrounds) {
/* 104 */       background.background.render(renderer, x, y, width, height, alpha);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void wasAdded() {
/* 110 */     super.wasAdded();
/*     */     
/* 112 */     for (ManagedBackground background : this.backgrounds) {
/* 113 */       background.wasAdded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void wasRemoved() {
/* 119 */     super.wasRemoved();
/*     */     
/* 121 */     for (ManagedBackground background : this.backgrounds)
/* 122 */       background.wasRemoved(); 
/*     */   }
/*     */   
/*     */   private static class ManagedBackground
/*     */   {
/*     */     private BBackground background;
/*     */     private boolean added;
/*     */     
/*     */     private ManagedBackground(BBackground background) {
/* 131 */       this.background = background;
/* 132 */       this.added = false;
/*     */     }
/*     */     
/*     */     private void wasAdded() {
/* 136 */       if (!this.added) {
/* 137 */         this.background.wasAdded();
/* 138 */         this.added = true;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void wasRemoved() {
/* 143 */       if (this.added) {
/* 144 */         this.background.wasRemoved();
/* 145 */         this.added = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BBackgroundList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */