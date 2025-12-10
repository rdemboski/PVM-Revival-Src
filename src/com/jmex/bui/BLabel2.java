/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BLabel2
/*    */   extends BLabel
/*    */ {
/*    */   private float rotation;
/*    */   
/*    */   public BLabel2(String text) {
/* 17 */     super(text);
/* 18 */     this.rotation = 0.0F;
/*    */   }
/*    */   
/*    */   public BLabel2(String text, String styleClass) {
/* 22 */     super(text, styleClass);
/* 23 */     this.rotation = 0.0F;
/*    */   }
/*    */   
/*    */   public BLabel2(BIcon icon) {
/* 27 */     super(icon);
/* 28 */     this.rotation = 0.0F;
/*    */   }
/*    */   
/*    */   public BLabel2(BIcon icon, String styleClass) {
/* 32 */     super(icon, styleClass);
/* 33 */     this.rotation = 0.0F;
/*    */   }
/*    */   
/*    */   public BLabel2(BIcon icon, String text, String styleClass) {
/* 37 */     super(icon, text, styleClass);
/* 38 */     this.rotation = 0.0F;
/*    */   }
/*    */   
/*    */   public void setRotation(float rotation) {
/* 42 */     this.rotation = rotation;
/*    */   }
/*    */   
/*    */   public float getRotation() {
/* 46 */     return this.rotation;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configureStyle(BStyleSheet style) {
/* 51 */     super.configureStyle(style);
/*    */ 
/*    */     
/* 54 */     if (this._label.getIcon() == null) {
/* 55 */       BIcon icon = style.getIcon((BComponent)this, getStatePseudoClass(0));
/* 56 */       if (icon != null) {
/* 57 */         this._label.setIcon(icon);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void renderComponent(Renderer renderer) {
/* 65 */     GL11.glRotatef(this.rotation, 0.0F, 0.0F, 1.0F);
/* 66 */     super.renderComponent(renderer);
/* 67 */     GL11.glRotatef(-this.rotation, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BLabel2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */