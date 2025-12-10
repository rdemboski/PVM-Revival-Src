/*    */ package com.funcom.rpgengine2.dfx;
/*    */ 
/*    */ import com.funcom.commons.dfx.EffectHandler;
/*    */ import com.funcom.commons.dfx.EffectHandlerFactory;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ 
/*    */ public class ItemDFXHandlerFactory implements EffectHandlerFactory {
/*    */   private ShapeDataEvaluator evaluator;
/*    */   
/*    */   public ItemDFXHandlerFactory(ShapeDataEvaluator evaluator) {
/* 13 */     this.evaluator = evaluator;
/*    */   }
/*    */   
/*    */   public EffectHandler createHandler(Object handlerSource, Object instanceData) {
/* 17 */     return new ItemDFXHandler((Item)handlerSource, this.evaluator, (UsageParams)instanceData);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\dfx\ItemDFXHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */