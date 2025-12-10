/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BScrollBar;
/*     */ import com.jmex.bui.BScrollPane;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.BoundedRangeModel;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.enumeratedConstants.Orientation;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseAdapter;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.layout.BorderLayout;
/*     */ 
/*     */ public class BScrollPaneTcg
/*     */   extends BScrollPane {
/*     */   public BScrollPaneTcg(BComponent child) {
/*  20 */     super(child);
/*  21 */     init();
/*     */   }
/*     */   private BBackground overlay;
/*     */   public BScrollPaneTcg(BComponent child, boolean horiz) {
/*  25 */     super(child, true, horiz);
/*  26 */     init();
/*     */   }
/*     */   
/*     */   public BScrollPaneTcg(BComponent child, boolean horiz, int snap) {
/*  30 */     super(child, true, horiz, snap);
/*  31 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BScrollPaneTcg(BComponent child, boolean horiz, boolean scrollLeft, int snap) {
/*  42 */     super(child, true, horiz, snap);
/*  43 */     if (scrollLeft) {
/*  44 */       this._vbar.getParent().remove(this._vbar.getParent().getComponentIndex((BComponent)this._vbar));
/*  45 */       this._vbar = new BScrollBar(Orientation.VERTICAL, this._vport.getVModel());
/*     */       
/*  47 */       add((BComponent)this._vbar, BorderLayout.WEST);
/*  48 */       this._vbar.setBounds(this._vbar.getX(), this._vbar.getY(), 20, this._vbar.getHeight());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init() {
/*  53 */     getVerticalScrollBar().addListener((ComponentListener)new MouseAdapter()
/*     */         {
/*     */           public void mousePressed(MouseEvent mouseEvent) {
/*  56 */             super.mousePressed(mouseEvent);
/*  57 */             MainGameState.getTcgGameControlsController().guiStealingInput(true);
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseReleased(MouseEvent mouseEvent) {
/*  62 */             super.mouseReleased(mouseEvent);
/*  63 */             MainGameState.getTcgGameControlsController().guiStealingInput(false);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public BoundedRangeModel getVerticalViewportModel() {
/*  69 */     return this._vport._vmodel;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/*  74 */     super.wasAdded();
/*  75 */     if (this.overlay != null) {
/*  76 */       this.overlay.wasAdded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasRemoved() {
/*  82 */     super.wasRemoved();
/*  83 */     if (this.overlay != null) {
/*  84 */       this.overlay.wasRemoved();
/*     */     }
/*  86 */     MainGameState.getTcgGameControlsController().guiStealingInput(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  91 */     return "bscrollpanetcg";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/*  96 */     super.configureStyle(style);
/*     */     
/*  98 */     this.overlay = style.getBackground((BComponent)this, "overlay");
/*  99 */     if (this.overlay != null && isAdded()) {
/* 100 */       this.overlay.wasAdded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/* 106 */     super.renderComponent(renderer);
/*     */     
/* 108 */     if (this.overlay != null)
/* 109 */       this.overlay.render(renderer, 0, 0, this._vport.getWidth(), this._vport.getHeight(), this._alpha); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\BScrollPaneTcg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */