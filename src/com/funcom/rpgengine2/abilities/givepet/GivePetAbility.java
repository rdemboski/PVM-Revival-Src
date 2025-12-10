/*    */ package com.funcom.rpgengine2.abilities.givepet;
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
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import com.funcom.rpgengine2.pets.PetManager;
/*    */ import com.funcom.rpgengine2.pets.PetSupport;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GivePetAbility
/*    */   extends AbstractAbility
/*    */   implements ActiveAbility
/*    */ {
/*    */   private PetManager petManager;
/*    */   private String petId;
/*    */   
/*    */   public GivePetAbility(AbilityParams params, RpgLoader loader) {
/* 29 */     this.petManager = loader.getPetManager();
/*    */ 
/*    */     
/* 32 */     while (params.next()) {
/* 33 */       init(params);
/*    */ 
/*    */       
/* 36 */       this.petId = params.getStr(AbilityParams.ParamName.ABILITY_ID);
/*    */       
/* 38 */       this.initialized = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getPetId() {
/* 43 */     return this.petId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDamage(ItemDescription description, RpgEntity rpgEntity, short statToLookFor) {
/* 48 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void useIfHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 53 */     TargetProvider targetProvider = null;
/* 54 */     if (!targetProviders.isEmpty()) {
/* 55 */       targetProvider = targetProviders.get(0);
/*    */     }
/* 57 */     useAlways(parent, evaluator, sourceProvider, targetProvider, usageParams);
/*    */   }
/*    */ 
/*    */   
/*    */   public void useAlways(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/* 62 */     ((PetSupport)sourceProvider.getSourceHandler().getSupport(PetSupport.class)).collectNewPetAndNotifyClient(this.petManager.getPetDescription(this.petId));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canHit(Item parentItem, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 67 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\givepet\GivePetAbility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */