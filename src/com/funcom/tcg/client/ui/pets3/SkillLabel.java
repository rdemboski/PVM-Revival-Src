/*    */ package com.funcom.tcg.client.ui.pets3;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.tcg.client.ui.ScalingSupportLabel;
/*    */ import com.jme.renderer.Renderer;
/*    */ import java.awt.Point;
/*    */ 
/*    */ class SkillLabel
/*    */   extends ScalingSupportLabel {
/*    */   public SkillLabel(ResourceManager resourceManager) {
/* 11 */     super(resourceManager);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDefaultStyleClass() {
/* 16 */     return "skill-label";
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderComponent(Renderer renderer) {
/* 21 */     super.renderComponent(renderer);
/*    */     
/* 23 */     Point pos = newRenderPos();
/* 24 */     int size = getIconSize();
/* 25 */     if (renderIcon(renderer, true, pos, size)) {
/* 26 */       moveRightOfIcon(pos, size);
/*    */     }
/*    */     
/* 29 */     pos.y = getHeight() / 2;
/* 30 */     renderText(renderer, pos, 1.0F, false, true);
/*    */   }
/*    */   
/*    */   protected void moveRightOfIcon(Point pos, int size) {
/* 34 */     pos.x += size;
/* 35 */     pos.x += getTextFactory().getHeight() / 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\SkillLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */