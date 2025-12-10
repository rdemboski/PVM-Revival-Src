/*    */ package com.funcom.tcg.client.model.rpg;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PetSlot
/*    */ {
/* 10 */   private List<PetSlotListener> listeners = new ArrayList<PetSlotListener>();
/*    */   private ClientPet pet;
/*    */   private boolean selectable;
/*    */   
/*    */   public PetSlot(ClientPet pet, boolean selectable) {
/* 15 */     this.pet = pet;
/* 16 */     this.selectable = selectable;
/*    */   }
/*    */   
/*    */   public ClientPet getPet() {
/* 20 */     return this.pet;
/*    */   }
/*    */   
/*    */   public void setPet(ClientPet pet) {
/* 24 */     this.pet = pet;
/*    */   }
/*    */   
/*    */   public boolean isSelectable() {
/* 28 */     return this.selectable;
/*    */   }
/*    */   
/*    */   public void setSelectable(boolean selectable) {
/* 32 */     this.selectable = selectable;
/* 33 */     for (PetSlotListener listener : this.listeners)
/* 34 */       listener.selectableChanged(selectable); 
/*    */   }
/*    */   
/*    */   public void addListener(PetSlotListener listener) {
/* 38 */     this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void removeListener(PetSlotListener listener) {
/* 42 */     this.listeners.remove(listener);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return "PetSlot{pet=" + ((this.pet == null) ? "null" : this.pet.toString()) + ", selectable=" + this.selectable + '}';
/*    */   }
/*    */   
/*    */   public static interface PetSlotListener {
/*    */     void selectableChanged(boolean param1Boolean);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\PetSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */