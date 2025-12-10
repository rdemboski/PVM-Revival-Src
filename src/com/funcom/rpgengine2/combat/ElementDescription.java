/*    */ package com.funcom.rpgengine2.combat;
/*    */ 
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ElementDescription
/*    */ {
/*    */   private String elementName;
/*    */   private String fontColor;
/*    */   private String deathDfx;
/*    */   private String petCardImage;
/*    */   
/*    */   public ElementDescription(String elementName, String fontColor, String deathDfx, String petCardImage) {
/* 15 */     this.elementName = elementName;
/* 16 */     this.fontColor = fontColor;
/* 17 */     this.deathDfx = deathDfx;
/* 18 */     this.petCardImage = petCardImage;
/*    */   }
/*    */   
/*    */   public String getElementName() {
/* 22 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.elementName);
/*    */   }
/*    */   
/*    */   public String getFontColor() {
/* 26 */     return this.fontColor;
/*    */   }
/*    */   
/*    */   public String getDeathDfx() {
/* 30 */     return this.deathDfx;
/*    */   }
/*    */   
/*    */   public String getPetCardImage() {
/* 34 */     return this.petCardImage;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\ElementDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */