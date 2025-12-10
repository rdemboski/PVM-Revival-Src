/*    */ package com.funcom.tcg.client.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.AbstractAbility;
/*    */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*    */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*    */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientTownPortalAbility
/*    */   extends AbstractAbility
/*    */   implements ActiveAbility
/*    */ {
/*    */   public void useIfHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {}
/*    */   
/*    */   public void useAlways(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {}
/*    */   
/*    */   public boolean canHit(Item parentItem, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 44 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\rpg\ClientTownPortalAbility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */