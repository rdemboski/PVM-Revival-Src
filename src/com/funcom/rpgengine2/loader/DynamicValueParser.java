/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.values.DynamicValue;
/*    */ import com.funcom.rpgengine2.abilities.values.Value;
/*    */ import com.funcom.rpgengine2.abilities.values.ValueParser;
/*    */ 
/*    */ 
/*    */ public class DynamicValueParser
/*    */   implements ValueParser
/*    */ {
/*    */   public Value parse(String str, RpgLoader loader) {
/* 12 */     return (Value)new DynamicValue(str);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\DynamicValueParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */