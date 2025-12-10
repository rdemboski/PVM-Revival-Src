/*    */ package com.funcom.gameengine.model.input;
/*    */ 
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TintMouseOver
/*    */   extends MouseOver
/*    */ {
/*    */   private ColorRGBA tintColor;
/*    */   protected Effects.TintMode tintMode;
/*    */   
/*    */   public TintMouseOver(ColorRGBA tintColor, Effects.TintMode tintMode) {
/* 16 */     this.tintColor = tintColor;
/* 17 */     this.tintMode = tintMode;
/*    */   }
/*    */   
/*    */   public void mouseEntered() {
/* 21 */     Effects propNodeEffects = getOwnerPropNode().getEffects();
/* 22 */     propNodeEffects.setTintRbga(this.tintColor);
/* 23 */     propNodeEffects.tint(this.tintMode);
/*    */   }
/*    */   
/*    */   public void mouseExited() {
/* 27 */     getOwnerPropNode().getEffects().tint(Effects.TintMode.OFF);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\input\TintMouseOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */