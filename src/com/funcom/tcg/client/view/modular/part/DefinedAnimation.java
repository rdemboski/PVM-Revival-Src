/*    */ package com.funcom.tcg.client.view.modular.part;
/*    */ 
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ 
/*    */ public class DefinedAnimation
/*    */   implements ModularDescription.Animation {
/*    */   private String animationName;
/*    */   private String animationPath;
/*    */   
/*    */   public DefinedAnimation(String animationName, String animationPath) {
/* 11 */     this.animationName = animationName;
/* 12 */     this.animationPath = animationPath;
/*    */   }
/*    */   
/*    */   public String getAnimationName() {
/* 16 */     return this.animationName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPetAnimationPath() {
/* 21 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPlayerAnimationPath() {
/* 26 */     return this.animationPath;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPlayerFrameRate() {
/* 31 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPetFrameRate() {
/* 36 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\part\DefinedAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */