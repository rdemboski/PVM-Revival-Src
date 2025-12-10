/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.rpgengine2.combat.ElementDescription;
/*    */ import com.funcom.rpgengine2.combat.ElementManager;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*    */ import com.funcom.tcg.client.model.rpg.PetRegistry;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.funcom.tcg.client.ui.TieredData;
/*    */ 
/*    */ 
/*    */ public class PetTierData
/*    */   implements TieredData
/*    */ {
/*    */   private String petId;
/*    */   private String iconPath;
/*    */   private String petType;
/*    */   private int tier;
/*    */   private String elementBackgroundPath;
/*    */   
/*    */   public PetTierData() {}
/*    */   
/*    */   public PetTierData(String petId, PetRegistry petRegistry, ElementManager elementManager) {
/* 25 */     this.petId = petId;
/* 26 */     this.tier = 0;
/*    */     
/* 28 */     if (petId != null) {
/*    */       
/* 30 */       ClientPetDescription pet = petRegistry.getPetForClassId(petId);
/* 31 */       this.iconPath = pet.getIcon();
/* 32 */       this.petType = pet.getType();
/* 33 */       ElementDescription elementDescription = elementManager.getElementDesc(Element.valueOf(pet.getElementId().toUpperCase()));
/* 34 */       this.elementBackgroundPath = elementDescription.getPetCardImage();
/*    */     } else {
/* 36 */       this.petType = null;
/* 37 */       this.iconPath = "";
/* 38 */       ElementDescription elementDescription = elementManager.getElementDesc(Element.PHYSICAL);
/* 39 */       this.elementBackgroundPath = elementDescription.getPetCardImage();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getPetId() {
/* 44 */     return this.petId;
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 48 */     return this.tier;
/*    */   }
/*    */   
/*    */   public String getBImagePath() {
/* 52 */     return this.iconPath;
/*    */   }
/*    */   
/*    */   public boolean isUsingCustomBackground() {
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   public String getCustomImageBackgroundPath() {
/* 60 */     return this.elementBackgroundPath;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPathForIconReplacingTierStars() {
/* 65 */     return (this.petType == null) ? null : TcgUI.getIconProvider().getPathForType(this.petType, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtml() {
/* 70 */     if (this.petId == null) return null; 
/* 71 */     return MainGameState.getToolTipManager().getPetHtml(this.petId);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\PetTierData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */