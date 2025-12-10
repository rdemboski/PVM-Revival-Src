/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.tcg.client.ui.pets3.PetViewSelectionModel;
/*    */ import com.funcom.tcg.client.ui.pets3.PetWindowButtonModel;
/*    */ import com.funcom.tcg.client.ui.pets3.PetWindowPet;
/*    */ import com.funcom.tcg.client.ui.pets3.PetsWindowModel;
/*    */ import com.jmex.bui.BImage;
/*    */ 
/*    */ 
/*    */ public class SkillBarPetButtonModel
/*    */   extends PetWindowButtonModel
/*    */ {
/*    */   private ResourceManager resourceManager;
/*    */   private HudModel hudModel;
/*    */   private int slot;
/*    */   private boolean selected = false;
/*    */   
/*    */   public SkillBarPetButtonModel(ResourceManager resourceManager, PetViewSelectionModel viewSelectionModel, PetsWindowModel petWindowModel, PetWindowPet pet, HudModel hudModel, int slot) {
/* 20 */     super(resourceManager, viewSelectionModel, petWindowModel, pet);
/* 21 */     this.resourceManager = resourceManager;
/* 22 */     this.hudModel = hudModel;
/* 23 */     this.slot = slot;
/*    */   }
/*    */   
/*    */   protected void initialize(ResourceManager resourceManager) {
/* 27 */     if (this.pet != null) {
/* 28 */       String petIconPath = this.pet.getPetDescription().getPetVisuals().getIcon();
/* 29 */       this.petIcon = (BImage)resourceManager.getResource(BImage.class, petIconPath);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSelected() {
/* 35 */     return this.selected;
/*    */   }
/*    */   
/*    */   public void setSelectedState(boolean selected) {
/* 39 */     this.selected = selected;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSelected(boolean selected) {
/* 44 */     if (selected) {
/* 45 */       if (this.pet != null && this.hudModel.getPet(this.slot).getPetSlot().isSelectable()) {
/* 46 */         this.hudModel.petButtonAction(this.slot);
/*    */       }
/*    */     } else {
/* 49 */       throw new UnsupportedOperationException("cannot select a NULL pet");
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setPet(PetWindowPet pet) {
/* 54 */     super.setPet(pet);
/* 55 */     initialize(this.resourceManager);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\SkillBarPetButtonModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */