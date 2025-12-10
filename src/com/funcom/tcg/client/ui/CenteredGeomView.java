/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ 
/*    */ public class CenteredGeomView
/*    */   extends UpdatedGeomView {
/*    */   public CenteredGeomView(boolean isPet) {
/* 10 */     super(isPet);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBackground(int state, BBackground background) {
/* 15 */     super.setBackground(state, background);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderBackground(Renderer renderer) {
/* 20 */     super.renderBackground(renderer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void configureStyle(BStyleSheet style) {
/* 25 */     for (int i = 0; i < this._backgrounds.length; i++) {
/* 26 */       if (this._backgrounds[i] != null) {
/* 27 */         this._backgrounds[i].wasRemoved();
/*    */       }
/* 29 */       this._backgrounds[i] = null;
/*    */     } 
/* 31 */     super.configureStyle(style);
/* 32 */     for (BBackground _background : this._backgrounds) {
/* 33 */       if (_background != null) {
/* 34 */         _background.wasAdded();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setStyleClass(String styleClass) {
/* 40 */     this._styleClass = styleClass;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void calculateCameraPosition() {
/* 45 */     super.calculateCameraPosition();
/*    */     
/* 47 */     this.lookAtVector.setX(0.0F);
/* 48 */     this.lookAtVector.setZ(0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\CenteredGeomView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */