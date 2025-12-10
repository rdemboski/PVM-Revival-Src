/*    */ package com.funcom.rpgengine2.combat;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*    */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleShape
/*    */   extends AbstractShape
/*    */ {
/*    */   public boolean canHit(Item item, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 19 */     double evalDistanceSqr = evaluator.evalDistance(source, target, this.offsetX, this.offsetY, usageParams);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 25 */     float userRadius = 0.0F;
/* 26 */     if (isMelee()) {
/* 27 */       userRadius = evaluator.getRadius(source);
/*    */     }
/* 29 */     float targetRadius = evaluator.getRadius(target);
/* 30 */     if (evalDistanceSqr - targetRadius <= 0.0D) {
/* 31 */       return true;
/*    */     }
/* 33 */     if (evalDistanceSqr <= this.distance + targetRadius + userRadius) {
/*    */ 
/*    */ 
/*    */       
/* 37 */       double angle = Math.abs((this.angleEnd - this.angleStart + 6.283185307179586D) % 6.283185307179586D) / 2.0D;
/* 38 */       if (angle == 0.0D)
/* 39 */         return true; 
/* 40 */       double height = targetRadius;
/* 41 */       if (angle < 1.5707963267948966D) {
/* 42 */         height /= Math.tan(angle);
/*    */       }
/*    */       
/* 45 */       double zeroAngle = this.angleStart + angle;
/* 46 */       WorldCoordinate testOffset = new WorldCoordinate(0, 0, -height, 0.0D, "", 0);
/* 47 */       testOffset.rotate(zeroAngle);
/*    */       
/* 49 */       double xOffset = this.offsetX + testOffset.getX().doubleValue();
/* 50 */       double yOffset = this.offsetY + testOffset.getY().doubleValue();
/* 51 */       float sourceAngle = (usageParams.getAngleType() == UsageParams.AngleType.SOURCE) ? evaluator.getAngle(source) : usageParams.getDefinedAngle();
/* 52 */       double sourceLocalAngle = evaluator.evalAngleLocal(sourceAngle, source, target, xOffset, yOffset, usageParams);
/* 53 */       if (isWithinArc(this.angleStart, this.angleEnd, sourceLocalAngle) && (height == targetRadius || evaluator.evalDistance(source, target, xOffset, yOffset, usageParams) > height))
/*    */       {
/* 55 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(Item item, ShapeListener listener, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 64 */     RpgEntity source = sourceProvider.getSourceObject();
/* 65 */     int size = targetProviders.size();
/* 66 */     double minDistance = Double.MAX_VALUE;
/* 67 */     TargetProvider closest = null;
/*    */     
/* 69 */     for (int i = size - 1; i >= 0; i--) {
/* 70 */       TargetProvider targetProvider = targetProviders.get(i);
/* 71 */       RpgEntity target = targetProvider.getTargetObject();
/*    */       
/* 73 */       if (isValidTarget(sourceProvider.getSourceHandler(), targetProvider.getTargetObject()) && canHit(item, evaluator, source, target, usageParams)) {
/* 74 */         double evalDistance = evaluator.evalDistance(source, target, this.offsetX, this.offsetY, usageParams);
/* 75 */         if (evalDistance < minDistance) {
/* 76 */           minDistance = evalDistance;
/* 77 */           closest = targetProvider;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 82 */     if (closest != null)
/* 83 */       listener.notifyHit(item, evaluator, sourceProvider, closest, usageParams); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\SingleShape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */