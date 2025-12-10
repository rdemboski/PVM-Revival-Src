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
/*    */ public class AllShape
/*    */   extends AbstractShape
/*    */ {
/*    */   public void process(Item item, ShapeListener listener, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 19 */     int size = targetProviders.size();
/* 20 */     for (int i = size - 1; i >= 0; i--) {
/* 21 */       TargetProvider targetProvider = targetProviders.get(i);
/*    */       
/* 23 */       boolean isHit = (isValidTarget(sourceProvider.getSourceHandler(), targetProvider.getTargetObject()) && canHit(item, evaluator, sourceProvider.getSourceObject(), targetProvider.getTargetObject(), usageParams));
/*    */       
/* 25 */       if (isHit) {
/* 26 */         listener.notifyHit(item, evaluator, sourceProvider, targetProvider, usageParams);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canHit(Item item, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 33 */     double evalDistance = evaluator.evalDistance(source, target, this.offsetX, this.offsetY, usageParams);
/* 34 */     double userRadius = 0.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     if (isMelee()) {
/* 41 */       userRadius = evaluator.getRadius(source);
/*    */     }
/* 43 */     float targetRadius = evaluator.getRadius(target);
/* 44 */     if (evalDistance - targetRadius <= 0.0D) {
/* 45 */       return true;
/*    */     }
/* 47 */     if (evalDistance <= this.distance + targetRadius + userRadius) {
/*    */ 
/*    */       
/* 50 */       double angle = (this.angleEnd - this.angleStart + 6.283185307179586D) % 6.283185307179586D / 2.0D;
/* 51 */       if (angle == 0.0D)
/* 52 */         return true; 
/* 53 */       double height = targetRadius;
/* 54 */       if (angle < 1.5707963267948966D) {
/* 55 */         height /= Math.tan(angle);
/*    */       }
/*    */       
/* 58 */       double zeroAngle = (this.angleStart + angle) % 6.283185307179586D;
/* 59 */       WorldCoordinate testOffset = new WorldCoordinate(0, 0, -height, 0.0D, "", 0);
/* 60 */       testOffset.rotate(zeroAngle);
/*    */       
/* 62 */       double xOffset = this.offsetX + testOffset.getX().doubleValue();
/* 63 */       double yOffset = this.offsetY + testOffset.getY().doubleValue();
/* 64 */       float sourceAngle = (usageParams.getAngleType() == UsageParams.AngleType.SOURCE) ? evaluator.getAngle(source) : usageParams.getDefinedAngle();
/* 65 */       double sourceLocalAngle = evaluator.evalAngleLocal(sourceAngle, source, target, xOffset, yOffset, usageParams);
/* 66 */       if (isWithinArc(this.angleStart, this.angleEnd, sourceLocalAngle) && (height == targetRadius || evaluator.evalDistance(source, target, xOffset, yOffset, usageParams) > height))
/*    */       {
/* 68 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\AllShape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */