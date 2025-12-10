/*    */ package com.funcom.tcg.client.rpg;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.commons.dfx.NoSuchDFXException;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.items.ItemManager;
/*    */ import com.funcom.rpgengine2.loader.AbilityFactory;
/*    */ import com.funcom.rpgengine2.loader.ConfigErrors;
/*    */ import com.funcom.rpgengine2.loader.RawData;
/*    */ import com.funcom.tcg.rpg.AbstractTCGRpgLoader;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TCGClientRpgLoader
/*    */   extends AbstractTCGRpgLoader {
/*    */   private TCGClientTownPortalFactory clientTownPortalFactory;
/*    */   
/*    */   public TCGClientRpgLoader(ConfigErrors configErrors, ResourceManager resourceManager) {
/* 22 */     super(configErrors, resourceManager);
/* 23 */     this.clientTownPortalFactory = new TCGClientTownPortalFactory();
/* 24 */     setValueAccumulatorFactory(new ClientValueAccumulatorFactory());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setQuestManager() {
/* 29 */     this.questManager = new ClientQuestManager();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadAbilitiesData(Map<String, RawData> returnData) throws IOException {
/* 34 */     super.loadAbilitiesData(returnData);
/*    */     
/* 36 */     addTownPortalAbility(returnData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void validateItems(ItemManager itemManager) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void notifyMissingAbility(ItemDescription itemDescription, String missingAbilityId) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void addTownPortalAbility(Map<String, RawData> returnData) {
/* 56 */     String id = "town-portal";
/* 57 */     returnData.put("town-portal", new RawData(getClientTownPortalFactory()));
/*    */   }
/*    */   
/*    */   public AbilityFactory getClientTownPortalFactory() {
/* 61 */     return this.clientTownPortalFactory;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setItemDfxDescriptions(String dFXScript, String impactDFX, ItemDescription itemDescription) throws NoSuchDFXException {
/* 66 */     if (LoadingManager.USE) {
/* 67 */       LoadingManager.INSTANCE.submit(new LoadDfxLMToken(dFXScript, itemDescription, false, this.dfxDescriptionFactory, this.configErrors));
/* 68 */       LoadingManager.INSTANCE.submit(new LoadDfxLMToken(impactDFX, itemDescription, true, this.dfxDescriptionFactory, this.configErrors));
/*    */     } else {
/*    */       DireEffectDescription direEffectDescription1, direEffectDescription2;
/*    */       
/*    */       try {
/* 73 */         direEffectDescription1 = this.dfxDescriptionFactory.getDireEffectDescription(dFXScript, false);
/* 74 */       } catch (NoSuchDFXException e) {
/* 75 */         this.configErrors.addError("Missing DFX", "itemId=" + itemDescription.getId() + " dfxScript=" + e.getDFXScript());
/*    */         
/* 77 */         direEffectDescription1 = this.dfxDescriptionFactory.getDireEffectDescription("", false);
/*    */       } 
/* 79 */       itemDescription.setDfxDescription(direEffectDescription1);
/*    */ 
/*    */       
/*    */       try {
/* 83 */         if (impactDFX.isEmpty()) {
/* 84 */           direEffectDescription2 = DireEffectDescriptionFactory.EMPTY_DFX;
/*    */         } else {
/* 86 */           direEffectDescription2 = this.dfxDescriptionFactory.getDireEffectDescription(impactDFX, true);
/*    */         } 
/* 88 */       } catch (NoSuchDFXException e) {
/* 89 */         this.configErrors.addError("Missing Impact DFX", "itemId=" + itemDescription.getId() + " dfxScript=" + e.getDFXScript());
/*    */         
/* 91 */         direEffectDescription2 = DireEffectDescriptionFactory.EMPTY_DFX;
/*    */       } 
/* 93 */       itemDescription.setImpactDfxDescription(direEffectDescription2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\rpg\TCGClientRpgLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */