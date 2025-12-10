/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.Effect;
/*    */ import com.funcom.commons.dfx.EffectHandler;
/*    */ import com.funcom.commons.dfx.TextEffectDescription;
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ 
/*    */ 
/*    */ public class TextEffectHandler
/*    */   implements EffectHandler
/*    */ {
/*    */   private RepresentationalNode source;
/*    */   
/*    */   public TextEffectHandler(RepresentationalNode source) {
/* 16 */     this.source = source;
/*    */   }
/*    */ 
/*    */   
/*    */   public void startEffect(Effect sourceEffect) {
/* 21 */     TextEffectDescription textEffectDescription = (TextEffectDescription)sourceEffect.getDescription();
/* 22 */     DfxTextWindow window = DfxTextWindowManager.instance().getWindow(textEffectDescription.getWindow());
/*    */     
/* 24 */     if (!textEffectDescription.isBroadcast() && !DfxTextWindowManager.instance().isUser(this.source))
/*    */       return; 
/* 26 */     if (window != null) {
/* 27 */       window.showText(JavaLocalization.getInstance().getLocalizedRPGText(textEffectDescription.getResource()), new ColorRGBA(textEffectDescription.getR(), textEffectDescription.getG(), textEffectDescription.getB(), 1.0F), textEffectDescription.getScale());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect sourceEffect) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFinished() {
/* 40 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TextEffectHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */