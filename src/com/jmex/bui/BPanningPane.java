/*     */ package com.jmex.bui;
/*     */ 
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseAdapter;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ 
/*     */ public class BPanningPane
/*     */   extends BContainer
/*     */ {
/*     */   private BComponent target;
/*     */   
/*     */   public BPanningPane(BComponent target) {
/*  18 */     this.target = target;
/*  19 */     addListener((ComponentListener)new MousePanningListener(this));
/*  20 */     add(target);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/*  26 */     Insets insets = getInsets();
/*  27 */     Rectangle scissorBox = new Rectangle();
/*  28 */     boolean scissored = intersectScissorBox(scissorBox, getAbsoluteX() + insets.left, getAbsoluteY() + insets.bottom, getWidth() - insets.getHorizontal(), getHeight() - insets.getVertical());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  35 */       this.target.render(renderer);
/*     */     } finally {
/*  37 */       restoreScissorState(scissored, scissorBox);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getYOffset() {
/*  42 */     return this.target._y;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Dimension computePreferredSize(int whint, int hhint) {
/*  47 */     return new Dimension(this.target.getPreferredSize(whint, hhint));
/*     */   }
/*     */   
/*     */   public int getXOffset() {
/*  51 */     return this.target._x;
/*     */   }
/*     */   
/*     */   public void setYOffset(int newOffsetY) {
/*  55 */     if (getHeight() - getTarget().getHeight() > 0)
/*     */       return; 
/*  57 */     newOffsetY = clampY(newOffsetY);
/*  58 */     this.target.setLocation(this.target._x, newOffsetY);
/*     */   }
/*     */   
/*     */   protected int clampY(int newOffsetY) {
/*  62 */     if (newOffsetY > 0) {
/*  63 */       newOffsetY = 0;
/*  64 */     } else if (newOffsetY < getHeight() - getTarget().getHeight()) {
/*  65 */       newOffsetY = getHeight() - getTarget().getHeight();
/*  66 */     }  return newOffsetY;
/*     */   }
/*     */   
/*     */   public void setXOffset(int newOffsetX) {
/*  70 */     if (getWidth() - getTarget().getWidth() > 0)
/*     */       return; 
/*  72 */     newOffsetX = clampX(newOffsetX);
/*  73 */     this.target.setLocation(newOffsetX, this.target._y);
/*     */   }
/*     */   
/*     */   protected int clampX(int newOffsetX) {
/*  77 */     if (newOffsetX > 0) {
/*  78 */       newOffsetX = 0;
/*  79 */     } else if (newOffsetX < getWidth() - getTarget().getWidth()) {
/*  80 */       newOffsetX = getWidth() - getTarget().getWidth();
/*  81 */     }  return newOffsetX;
/*     */   }
/*     */   
/*     */   public BComponent getTarget() {
/*  85 */     return this.target;
/*     */   }
/*     */   
/*     */   private static class MousePanningListener extends MouseAdapter {
/*     */     private BPanningPane bPanningPane;
/*     */     private int downX;
/*     */     private int downY;
/*     */     
/*     */     public MousePanningListener(BPanningPane bPanningPane) {
/*  94 */       this.bPanningPane = bPanningPane;
/*     */     }
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent mouseEvent) {
/*  99 */       super.mousePressed(mouseEvent);
/* 100 */       this.downX = mouseEvent.getX();
/* 101 */       this.downY = mouseEvent.getY();
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent mouseEvent) {
/* 106 */       super.mouseReleased(mouseEvent);
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseDragged(MouseEvent mouseEvent) {
/* 111 */       super.mouseDragged(mouseEvent);
/* 112 */       int newOffsetX = this.bPanningPane.getXOffset() + mouseEvent.getX() - this.downX;
/* 113 */       this.bPanningPane.setXOffset(newOffsetX);
/* 114 */       int newOffsetY = this.bPanningPane.getYOffset() + mouseEvent.getY() - this.downY;
/* 115 */       this.bPanningPane.setYOffset(newOffsetY);
/* 116 */       this.downX = mouseEvent.getX();
/* 117 */       this.downY = mouseEvent.getY();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BPanningPane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */