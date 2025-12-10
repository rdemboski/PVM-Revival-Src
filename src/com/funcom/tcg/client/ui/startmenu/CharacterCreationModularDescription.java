/*    */ package com.funcom.tcg.client.ui.startmenu;
/*    */ 
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ import com.funcom.rpgengine2.items.PlayerDescription;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*    */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*    */ import com.funcom.tcg.client.view.modular.AbstractPlayerModularDescription;
/*    */ import com.funcom.tcg.client.view.modular.PlayerVisualFactory;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class CharacterCreationModularDescription
/*    */   extends AbstractPlayerModularDescription
/*    */ {
/*    */   private ClientPetDescription activePet;
/*    */   
/*    */   public CharacterCreationModularDescription(PlayerDescription playerDescription, VisualRegistry visualRegistry, PlayerVisualFactory playerVisualFactory) {
/* 18 */     super(playerDescription, visualRegistry, playerVisualFactory);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Set<ModularDescription.Animation> getPetAnimations() {
/* 23 */     return this.activePet.getPetDescription().getAnimations();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isCharacterMountedWithPet() {
/* 28 */     return true;
/*    */   }
/*    */   
/*    */   public ModularDescription.Part getPetModel() {
/* 32 */     return this.activePet.getPetDescription().getBodyParts().iterator().next();
/*    */   }
/*    */ 
/*    */   
/*    */   public void dispose() {}
/*    */ 
/*    */   
/*    */   public void setActivePet(ClientPetDescription activePet) {
/* 40 */     this.activePet = activePet;
/*    */   }
/*    */   
/*    */   public ClientPetDescription getActivePet() {
/* 44 */     return this.activePet;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\CharacterCreationModularDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */