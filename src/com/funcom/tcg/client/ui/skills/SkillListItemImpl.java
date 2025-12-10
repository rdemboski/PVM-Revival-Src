/*    */ package com.funcom.tcg.client.ui.skills;
/*    */ 
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*    */ 
/*    */ public class SkillListItemImpl
/*    */   implements SkillListItem {
/*    */   private ClientPet clientPet;
/*    */   private ClientItem petSkill;
/*    */   
/*    */   public SkillListItemImpl(ClientPet clientPet, ClientItem petSkill) {
/* 12 */     if (clientPet == null)
/* 13 */       throw new IllegalArgumentException("clientPet = null"); 
/* 14 */     if (petSkill == null) {
/* 15 */       throw new IllegalArgumentException("petSkill = null");
/*    */     }
/* 17 */     this.clientPet = clientPet;
/* 18 */     this.petSkill = petSkill;
/*    */   }
/*    */   
/*    */   public ClientPet getClientPet() {
/* 22 */     return this.clientPet;
/*    */   }
/*    */   
/*    */   public ClientItem getPetSkill() {
/* 26 */     return this.petSkill;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 30 */     return this.petSkill.getName();
/*    */   }
/*    */   
/*    */   public String getIcon() {
/* 34 */     return this.petSkill.getIcon();
/*    */   }
/*    */   
/*    */   public String getClassId() {
/* 38 */     return this.petSkill.getClassId();
/*    */   }
/*    */   
/*    */   public int getLevel() {
/* 42 */     return this.petSkill.getLevel();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 47 */     return this.petSkill.getTier();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\skills\SkillListItemImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */