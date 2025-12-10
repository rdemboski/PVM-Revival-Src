/*    */ package com.funcom.rpgengine2.abilities.values;
/*    */ 
/*    */ import com.funcom.rpgengine2.StatEffect;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ 
/*    */ 
/*    */ public class EffectValueParser
/*    */   implements ValueParser
/*    */ {
/* 10 */   private static final OriginalValue ORIGINAL_VALUE = new OriginalValue();
/* 11 */   private static final BonusValue BONUS_VALUE = new BonusValue();
/* 12 */   private static final SumValue SUM_VALUE = new SumValue();
/*    */ 
/*    */   
/*    */   public Value parse(String str, RpgLoader loader) {
/* 16 */     if ("original".equalsIgnoreCase(str) || "org".equalsIgnoreCase(str))
/*    */     {
/* 18 */       return ORIGINAL_VALUE;
/*    */     }
/* 20 */     if ("bonus".equalsIgnoreCase(str)) {
/* 21 */       return BONUS_VALUE;
/*    */     }
/* 23 */     if ("sum".equalsIgnoreCase(str)) {
/* 24 */       return SUM_VALUE;
/*    */     }
/*    */     
/* 27 */     throw new RuntimeException("Unknown effect type: type=" + str);
/*    */   }
/*    */   
/*    */   private static class OriginalValue implements Value {
/*    */     public boolean isInteger() {
/* 32 */       return true;
/*    */     }
/*    */     private OriginalValue() {}
/*    */     public int getInt(StatEffect statEffect) {
/* 36 */       return statEffect.getOriginalValue();
/*    */     }
/*    */     
/*    */     public float getFloat(StatEffect statEffect) {
/* 40 */       return statEffect.getOriginalValue();
/*    */     } }
/*    */   
/*    */   private static class BonusValue implements Value { private BonusValue() {}
/*    */     
/*    */     public boolean isInteger() {
/* 46 */       return true;
/*    */     }
/*    */     
/*    */     public int getInt(StatEffect statEffect) {
/* 50 */       return statEffect.getBonusValue();
/*    */     }
/*    */     
/*    */     public float getFloat(StatEffect statEffect) {
/* 54 */       return statEffect.getBonusValue();
/*    */     } }
/*    */   
/*    */   private static class SumValue implements Value { private SumValue() {}
/*    */     
/*    */     public boolean isInteger() {
/* 60 */       return true;
/*    */     }
/*    */     
/*    */     public int getInt(StatEffect statEffect) {
/* 64 */       return statEffect.getSum();
/*    */     }
/*    */     
/*    */     public float getFloat(StatEffect statEffect) {
/* 68 */       return statEffect.getSum();
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\values\EffectValueParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */