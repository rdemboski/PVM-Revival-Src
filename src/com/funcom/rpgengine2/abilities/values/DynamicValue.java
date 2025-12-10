/*    */ package com.funcom.rpgengine2.abilities.values;
/*    */ 
/*    */ import com.funcom.rpgengine2.StatEffect;
/*    */ 
/*    */ public class DynamicValue
/*    */   implements Value
/*    */ {
/*    */   private String str;
/*    */   
/*    */   public DynamicValue(String str) {
/* 11 */     this.str = str.intern();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInteger() {
/* 16 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt(StatEffect statEffect) {
/* 21 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFloat(StatEffect statEffect) {
/* 26 */     if (statEffect.hasAbilityValue(this.str)) {
/* 27 */       return statEffect.getAbilityValue(this.str);
/*    */     }
/* 29 */     return 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\values\DynamicValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */