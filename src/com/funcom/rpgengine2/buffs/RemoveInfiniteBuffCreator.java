/*    */ package com.funcom.rpgengine2.buffs;
import java.util.List;

/*    */ import com.funcom.rpgengine2.abilities.AbilityContainer;
import com.funcom.rpgengine2.abilities.AbstractAbility;
import com.funcom.rpgengine2.abilities.ActiveAbility;
/*    */ import com.funcom.rpgengine2.abilities.BuffCreator;
/*    */ import com.funcom.rpgengine2.abilities.BuffType;
import com.funcom.rpgengine2.abilities.ShapedAbility;
/*    */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*    */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.rpgengine2.combat.Shape;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
import com.funcom.rpgengine2.combat.ShapeListener;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.funcom.rpgengine2.creatures.BuffQueue;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.items.Item;
import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
import com.funcom.rpgengine2.loader.LoaderUtils;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ 
/*    */ public class RemoveInfiniteBuffCreator extends AbstractAbility implements ActiveAbility, ShapeListener, ShapedAbility {
/* 19 */   private static final Class[] SUPPORTED_ABILITIES = new Class[] { BuffCreator.class };
/*    */   
/*    */   private BuffCreator targetBuffAbility;
/*    */   
/*    */   private Element cureElement;
/*    */   
/*    */   private Shape shape;
/*    */   
/*    */   private String cureStatus;
/*    */   private String targetId;
/*    */   
/*    */   public RemoveInfiniteBuffCreator(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 31 */     while (params.next()) {
/* 32 */       init(params);
/*    */       
/* 34 */       this.targetId = params.getStr(AbilityParams.ParamName.TARGET_BUFF);
/* 35 */       if (this.targetId != null && !this.targetId.isEmpty()) {
/* 36 */         this.targetBuffAbility = (BuffCreator)LoaderUtils.loadAbility(this.targetId, parentContainer, params, evaluator, loader, factory, SUPPORTED_ABILITIES);
/*    */       }
/* 38 */       this.cureElement = (Element)params.getEnumOptional(AbilityParams.ParamName.ELEMENT, (Enum[])Element.values(), null);
/* 39 */       this.cureStatus = params.getStr(AbilityParams.ParamName.STATUS).toLowerCase();
/*    */       
/* 41 */       String shapeRef = params.getStr(AbilityParams.ParamName.SHAPE_REF);
/* 42 */       this.shape = loader.getShapeManager().getShape(shapeRef);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDamage(ItemDescription description, RpgEntity rpgEntity, short statToLookFor) {
/* 48 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void useIfHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 53 */     this.shape.process(parent, this, evaluator, sourceProvider, targetProviders, usageParams);
/*    */   }
/*    */ 
/*    */   
/*    */   public void useAlways(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/* 58 */     notifyHit(parent, evaluator, sourceProvider, targetProvider, usageParams);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canHit(Item parentItem, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 63 */     return (this.shape.isValidTarget(source, target) && this.shape.canHit(parentItem, evaluator, source, target, usageParams));
/*    */   }
/*    */ 
/*    */   
/*    */   public void notifyHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/* 68 */     if (this.targetBuffAbility == null) {
/* 69 */       BuffType[] buffTypes = BuffType.ALL;
/* 70 */       for (BuffType buffType : buffTypes) {
/* 71 */         ((BuffQueue)targetProvider.getTargetHandler().getSupport(BuffQueue.class)).enqueueFinish(buffType, this.cureElement, this.cureStatus);
/*    */       }
/*    */     } else {
/*    */       
/* 75 */       ((BuffQueue)targetProvider.getTargetHandler().getSupport(BuffQueue.class)).enqueueFinnishBuff(this.targetId);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Shape getShape() {
/* 81 */     return this.shape;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\buffs\RemoveInfiniteBuffCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */