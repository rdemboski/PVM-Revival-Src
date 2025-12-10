/*    */ package com.funcom.tcg.client.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.GeneralValueAccumulator;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientValueAccumulatorFactory
/*    */   implements ValueAccumulatorFactory
/*    */ {
/*    */   public GeneralValueAccumulator createValueAccumulator(RpgLoader loader, AbilityParams params) {
/* 14 */     return new ClientValueAccumulator(loader, params);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\rpg\ClientValueAccumulatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */