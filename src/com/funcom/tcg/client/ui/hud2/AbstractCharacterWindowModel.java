/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractCharacterWindowModel
/*    */   implements CharacterWindowModel
/*    */ {
/* 11 */   private List<CharacterWindowModel.ChangeListener> listeners = new LinkedList<CharacterWindowModel.ChangeListener>();
/*    */ 
/*    */   
/*    */   public void addChangeListener(CharacterWindowModel.ChangeListener changeListener) {
/* 15 */     this.listeners.add(changeListener);
/*    */   }
/*    */   
/*    */   public void removeChangeListener(CharacterWindowModel.ChangeListener changeListener) {
/* 19 */     this.listeners.remove(changeListener);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getActivePetDfx() {
/* 24 */     return "";
/*    */   }
/*    */   
/*    */   public void dispose() {
/* 28 */     this.listeners.clear();
/*    */   }
/*    */   
/*    */   protected void firePetChange(int slot, String newPetId) {
/* 32 */     for (CharacterWindowModel.ChangeListener listener : this.listeners) {
/* 33 */       listener.petChange(slot, newPetId);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void fireEquipmentChange(int slot, String newItemId, int tier) {
/* 38 */     for (CharacterWindowModel.ChangeListener listener : this.listeners) {
/* 39 */       listener.equipmentChange(slot, newItemId, tier);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void fireEquipmentRemoved(int slot) {
/* 44 */     for (CharacterWindowModel.ChangeListener listener : this.listeners) {
/* 45 */       listener.equipmentRemoved(slot);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void fireActivePetChanged() {
/* 50 */     for (CharacterWindowModel.ChangeListener listener : this.listeners) {
/* 51 */       listener.activePetChanged();
/*    */     }
/*    */   }
/*    */   
/*    */   protected void fireResistancesChange() {
/* 56 */     for (CharacterWindowModel.ChangeListener listener : this.listeners)
/* 57 */       listener.resistancesChange(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\AbstractCharacterWindowModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */