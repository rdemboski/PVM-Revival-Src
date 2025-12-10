/*    */ package com.funcom.rpgengine2.abilities.valueaccumulator;
/*    */ 
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeneralValueAccumulatorFactory
/*    */   implements ValueAccumulatorFactory
/*    */ {
/*    */   public GeneralValueAccumulator createValueAccumulator(RpgLoader loader, AbilityParams params) {
/* 12 */     return new GeneralValueAccumulator(loader, params);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\valueaccumulator\GeneralValueAccumulatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */