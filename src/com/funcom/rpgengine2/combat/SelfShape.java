/*    */ package com.funcom.rpgengine2.combat;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.rpgengine2.RPGFactionFilterType;
/*    */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*    */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.creatures.TargetValidationSupport;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelfShape
/*    */   extends AbstractShape
/*    */ {
/*    */   public void init(String shapeId, String shapeName, float offsetX, float offsetY, float angleStartDegrees, float angleEndDegrees, float distance, boolean isMelee, RPGFactionFilterType rpgFactionFilterType) {
/* 19 */     super.init(shapeId, shapeName, offsetX, offsetY, angleStartDegrees, angleEndDegrees, distance, isMelee, rpgFactionFilterType);
/*    */ 
/*    */     
/* 22 */     this.upperLeftCorner = (new WorldCoordinate()).addOffset(-0.6000000238418579D, -0.6000000238418579D);
/* 23 */     this.lowerRightCorner = (new WorldCoordinate()).addOffset(0.6000000238418579D, 0.6000000238418579D);
/*    */   }
/*    */   
/*    */   public boolean canHit(Item item, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 27 */     return isValidSelf(source, target);
/*    */   }
/*    */   
/*    */   protected boolean isValidSelf(RpgEntity source, RpgEntity target) {
/* 31 */     TargetValidationSupport validationSupport = (TargetValidationSupport)source.getSupport(TargetValidationSupport.class);
/* 32 */     return (validationSupport != null && validationSupport.isValidSelf(target));
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(Item item, ShapeListener listener, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 37 */     int size = targetProviders.size();
/* 38 */     for (int i = 0; i < size; i++) {
/* 39 */       TargetProvider target = targetProviders.get(i);
/* 40 */       if (canHit(item, evaluator, sourceProvider.getSourceObject(), target.getTargetObject(), usageParams)) {
/* 41 */         listener.notifyHit(item, evaluator, sourceProvider, target, usageParams);
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\SelfShape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */