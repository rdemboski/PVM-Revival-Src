/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.funcom.gameengine.view.ContentIndentifiable;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.shape.Quad;
/*    */ import com.jme.scene.state.RenderState;
/*    */ 
/*    */ public class DecalQuad
/*    */   extends Quad implements LayeredElement, ContentIndentifiable {
/*    */   private DecalConfig config;
/*    */   
/*    */   public DecalQuad(String name) {
/* 13 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateGeometry(float width, float height) {}
/*    */ 
/*    */   
/*    */   public Class<? extends Spatial> getClassTag() {
/* 22 */     return (Class)getClass();
/*    */   }
/*    */   
/*    */   public void setConfig(DecalConfig config) {
/* 26 */     this.config = config;
/*    */   }
/*    */   
/*    */   public int getLayerId() {
/* 30 */     return this.config.getLayerId();
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderState getRenderState(int type) {
/* 35 */     if (type == 0 && this.config != null) {
/* 36 */       return this.config.getBlendMode().getRenderState();
/*    */     }
/*    */     
/* 39 */     return super.getRenderState(type);
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderState getRenderState(RenderState.StateType type) {
/* 44 */     if (type == RenderState.StateType.Blend && this.config != null) {
/* 45 */       return this.config.getBlendMode().getRenderState();
/*    */     }
/*    */     
/* 48 */     return super.getRenderState(type);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getContentType() {
/* 53 */     return 8;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\DecalQuad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */