/*    */ package com.funcom.rpgengine2.combat;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*    */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleRectShape
/*    */   extends AbstractRectShape
/*    */ {
/*    */   public void process(Item item, ShapeListener listener, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 16 */     RpgEntity source = sourceProvider.getSourceObject();
/* 17 */     int size = targetProviders.size();
/*    */     
/* 19 */     for (int i = size - 1; i >= 0; i--) {
/* 20 */       TargetProvider targetProvider = targetProviders.get(i);
/* 21 */       RpgEntity target = targetProvider.getTargetObject();
/*    */       
/* 23 */       if (isValidTarget(sourceProvider.getSourceHandler(), targetProvider.getTargetObject()) && canHit(item, evaluator, source, target, usageParams)) {
/* 24 */         listener.notifyHit(item, evaluator, sourceProvider, targetProvider, usageParams);
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\SingleRectShape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */