/*    */ package com.funcom.tcg.client.ui.pets3;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.tcg.client.ui.ScalingSupportLabel;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BImage;
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import java.awt.Point;
/*    */ 
/*    */ class PetViewTitleLabel
/*    */   extends ScalingSupportLabel
/*    */ {
/*    */   private static final String PROP_FILLED_STAR = "filled_star";
/*    */   
/*    */   public PetViewTitleLabel(ResourceManager resourceManager) {
/* 17 */     super(resourceManager);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configureStyle(BStyleSheet style) {
/* 22 */     super.configureStyle(style);
/* 23 */     BImage levelStar = (BImage)style.findProperty((BComponent)this, null, "filled_star", true);
/* 24 */     if (levelStar == null) {
/* 25 */       throw new IllegalStateException("missing filled star: styleclass=" + getStyleClass() + " prop=" + "filled_star");
/*    */     }
/* 27 */     setIcon(levelStar);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDefaultStyleClass() {
/* 32 */     return "pet-title-label";
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderComponent(Renderer renderer) {
/* 37 */     super.renderComponent(renderer);
/*    */     
/* 39 */     Point pos = newRenderPos();
/* 40 */     int size = getIconSize();
/* 41 */     if (renderIcon(renderer, false, pos, size))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 48 */       moveRightOfIcon(pos, size);
/*    */     }
/*    */     
/* 51 */     pos.y = getHeight() / 2;
/* 52 */     renderText(renderer, pos, 1.0F, false, true);
/*    */   }
/*    */   
/*    */   protected void moveRightOfIcon(Point pos, int size) {
/* 56 */     pos.x += size;
/* 57 */     pos.x += getTextFactory().getHeight() / 2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void wasRemoved() {
/* 62 */     super.wasRemoved();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\PetViewTitleLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */