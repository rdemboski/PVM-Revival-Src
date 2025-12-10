/*    */ package com.funcom.rpgengine2.pets;
/*    */ 
/*    */ import com.funcom.rpgengine2.SkillId;
/*    */ import com.funcom.rpgengine2.creatures.RpgSourceProviderEntity;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import com.funcom.rpgengine2.items.ItemManager;
/*    */ import com.funcom.rpgengine2.items.SkillBar;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Pet
/*    */ {
/*    */   private static final int SKILL_SLOTS = 5;
/*    */   private PetDescription petDescription;
/*    */   private SkillBar skillItems;
/*    */   
/*    */   public Pet(PetDescription petDescription, RpgSourceProviderEntity owner, ItemManager itemManager) {
/* 17 */     updatePetDescription(petDescription, owner, itemManager);
/*    */   }
/*    */   
/*    */   public void updatePetDescription(PetDescription petDescription, RpgSourceProviderEntity owner, ItemManager itemManager) {
/* 21 */     this.petDescription = petDescription;
/* 22 */     this.skillItems = new SkillBar(0, 5);
/* 23 */     this.skillItems.setOwner(owner);
/* 24 */     Map<Integer, SkillId> skillMap = petDescription.getDefaultSkills();
/* 25 */     for (int slot = 0; slot < 5; slot++) {
/* 26 */       if (skillMap.get(Integer.valueOf(slot)) != null) {
/* 27 */         SkillId id = skillMap.get(Integer.valueOf(slot));
/* 28 */         Item skillItem = itemManager.getDescription(id.getItemId(), id.getTier()).createInstance();
/* 29 */         this.skillItems.putItem(slot, skillItem);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public PetDescription getPetDescription() {
/* 36 */     return this.petDescription;
/*    */   }
/*    */   
/*    */   public SkillBar getSelectedSkills() {
/* 40 */     return this.skillItems;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\pets\Pet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */