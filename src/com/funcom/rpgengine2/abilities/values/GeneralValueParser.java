/*    */ package com.funcom.rpgengine2.abilities.values;
/*    */ 
/*    */ import com.funcom.rpgengine2.StatEffect;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeneralValueParser
/*    */   extends AbstractValueParser
/*    */ {
/*    */   protected Value parseValue(String str) {
/*    */     try {
/* 12 */       return new FixedIntValue(Integer.parseInt(str));
/* 13 */     } catch (NumberFormatException ignore) {
/*    */ 
/*    */       
/*    */       try {
/* 17 */         String tmp = str.trim().replace(',', '.');
/* 18 */         return new FixedFloatValue(Float.parseFloat(tmp));
/* 19 */       } catch (NumberFormatException numberFormatException) {
/*    */ 
/*    */         
/* 22 */         return null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private static class FixedIntValue implements Value {
/*    */     public FixedIntValue(int i) {
/* 29 */       this.i = i;
/*    */     }
/*    */     private int i;
/*    */     public boolean isInteger() {
/* 33 */       return true;
/*    */     }
/*    */     
/*    */     public int getInt(StatEffect statEffect) {
/* 37 */       return this.i;
/*    */     }
/*    */     
/*    */     public float getFloat(StatEffect statEffect) {
/* 41 */       return this.i;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 46 */       return String.valueOf(Math.abs(this.i));
/*    */     }
/*    */   }
/*    */   
/*    */   private static class FixedFloatValue implements Value {
/*    */     private float f;
/*    */     
/*    */     public FixedFloatValue(float f) {
/* 54 */       this.f = f;
/*    */     }
/*    */     
/*    */     public boolean isInteger() {
/* 58 */       return false;
/*    */     }
/*    */     
/*    */     public int getInt(StatEffect statEffect) {
/* 62 */       return (int)this.f;
/*    */     }
/*    */     
/*    */     public float getFloat(StatEffect statEffect) {
/* 66 */       return this.f;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 71 */       return String.valueOf(Math.abs(this.f));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\values\GeneralValueParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */