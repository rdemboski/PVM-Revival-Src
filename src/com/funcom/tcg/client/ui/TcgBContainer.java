/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.util.Insets;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ public class TcgBContainer extends BContainer {
/*    */   private BComponent backgroundComp;
/*    */   
/*    */   public TcgBContainer() {
/* 14 */     super((BLayoutManager)null);
/*    */     
/* 16 */     this.backgroundComp = new ViewOnlyComponent("-background");
/* 17 */     super.add(0, this.backgroundComp, null);
/*    */     
/* 19 */     this.contentPane = new ClippedContainer("-contentpane");
/* 20 */     super.add(1, (BComponent)this.contentPane, null);
/*    */     
/* 22 */     this.overlayComp = new ViewOnlyComponent("-overlay");
/* 23 */     super.add(2, this.overlayComp, null);
/*    */   }
/*    */   private BComponent overlayComp; private BContainer contentPane;
/*    */   
/*    */   protected String getDefaultStyleClass() {
/* 28 */     return "tcgcontainer";
/*    */   }
/*    */ 
/*    */   
/*    */   protected void layout() {
/* 33 */     Insets insets = this.overlayComp.getInsets();
/* 34 */     this.backgroundComp.setBounds(insets.left, insets.bottom, getWidth() - insets.getHorizontal(), getHeight() - insets.getVertical());
/*    */     
/* 36 */     this.contentPane.setBounds(insets.left, insets.bottom, getWidth() - insets.getHorizontal(), getHeight() - insets.getVertical());
/*    */     
/* 38 */     this.contentPane.invalidate();
/* 39 */     this.contentPane.validate();
/*    */     
/* 41 */     this.overlayComp.setBounds(0, 0, getWidth(), getHeight());
/*    */   }
/*    */   
/*    */   public BContainer getContentPane() {
/* 45 */     return this.contentPane;
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(int index, BComponent child, Object constraints) {
/* 50 */     throw new UnsupportedOperationException("add to the content pane");
/*    */   }
/*    */   
/*    */   private class ViewOnlyComponent
/*    */     extends BComponent {
/*    */     private final String styleClassPostfix;
/*    */     
/*    */     public ViewOnlyComponent(String styleClassPostfix) {
/* 58 */       this.styleClassPostfix = styleClassPostfix;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getStyleClass() {
/* 63 */       return TcgBContainer.this.getStyleClass() + this.styleClassPostfix;
/*    */     }
/*    */ 
/*    */     
/*    */     public BComponent getHitComponent(int mx, int my) {
/* 68 */       return null;
/*    */     }
/*    */   }
/*    */   
/*    */   private class ClippedContainer extends BContainer {
/*    */     private final String styleClassPostfix;
/*    */     
/*    */     public ClippedContainer(String styleClassPostfix) {
/* 76 */       super((BLayoutManager)new FreeAbsoluteLayout());
/* 77 */       this.styleClassPostfix = styleClassPostfix;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getStyleClass() {
/* 82 */       return TcgBContainer.this.getStyleClass() + this.styleClassPostfix;
/*    */     }
/*    */ 
/*    */     
/*    */     public void render(Renderer renderer) {
/* 87 */       Insets insets = getInsets();
/* 88 */       Rectangle scissorBox = new Rectangle();
/* 89 */       boolean scissored = intersectScissorBox(scissorBox, getAbsoluteX() + insets.left, getAbsoluteY() + insets.bottom, getWidth() - insets.getHorizontal(), getHeight() - insets.getVertical());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       try {
/* 96 */         super.render(renderer);
/*    */       } finally {
/* 98 */         restoreScissorState(scissored, scissorBox);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\TcgBContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */