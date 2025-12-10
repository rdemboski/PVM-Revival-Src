/*    */ package com.funcom.tcg.client.ui.pets3;
/*    */ 
/*    */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*    */ 
/*    */ public class PetViewSelectionModel {
/*    */   private final PetsWindowModel windowModel;
/*    */   private PetView currentView;
/*    */   private PetButtonContainer viewChangeListener;
/*    */   private PetChangedListener listener;
/*    */   
/*    */   public PetViewSelectionModel(PetsWindowModel windowModel) {
/* 12 */     this.windowModel = windowModel;
/* 13 */     setSelectionMode();
/*    */   }
/*    */   
/*    */   public void select(PetView petView) {
/* 17 */     this.listener.petViewSelected(petView);
/*    */   }
/*    */   
/*    */   public void setViewChangeListener(PetButtonContainer viewChangeListener) {
/* 21 */     this.viewChangeListener = viewChangeListener;
/*    */   }
/*    */   
/*    */   public void petChanged(int viewId, ClientPet pet) {
/* 25 */     if (this.currentView != null && viewId == this.currentView.getViewId()) {
/* 26 */       this.viewChangeListener.petChanged(pet);
/*    */     }
/*    */   }
/*    */   
/*    */   public void notifyPetSelected(PetWindowPet pet) {
/* 31 */     this.listener.petChanged(pet.getPlayerPet());
/*    */   }
/*    */   
/*    */   public void setPetForView(int viewId, ClientPet pet) {
/* 35 */     this.windowModel.setPet(viewId, pet);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSelected(PetWindowPet pet) {
/* 40 */     if (this.currentView != null) {
/* 41 */       ClientPet petInCurrentView = this.windowModel.getPet(this.currentView.getViewId());
/* 42 */       if (petInCurrentView != null) {
/* 43 */         return petInCurrentView.getClassId().equals(pet.getPetDescription().getClassId());
/*    */       }
/*    */     } 
/*    */     
/* 47 */     return false;
/*    */   }
/*    */   
/*    */   public void setSelectionMode() {
/* 51 */     this.listener = new SwitchPetListener();
/* 52 */     if (this.viewChangeListener != null) {
/* 53 */       this.viewChangeListener.setOverlaysVisible(false);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private class SwitchPetListener
/*    */     implements PetChangedListener
/*    */   {
/*    */     private SwitchPetListener() {}
/*    */ 
/*    */     
/*    */     public void petChanged(ClientPet pet) {
/* 66 */       if (PetViewSelectionModel.this.currentView != null && !PetViewSelectionModel.this.currentView.isSwitchLocked()) {
/* 67 */         PetViewSelectionModel.this.windowModel.setPet(PetViewSelectionModel.this.currentView.getViewId(), pet);
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     public void petViewSelected(PetView petView) {
/* 73 */       if (PetViewSelectionModel.this.currentView != petView) {
/* 74 */         if (PetViewSelectionModel.this.currentView != null) {
/* 75 */           PetViewSelectionModel.this.currentView.setSelected(false);
/*    */         }
/*    */         
/* 78 */         PetViewSelectionModel.this.currentView = petView;
/*    */         
/* 80 */         if (petView != null) {
/* 81 */           petView.setSelected(true);
/* 82 */           PetViewSelectionModel.this.windowModel.viewChanged(petView.getViewId());
/* 83 */           ClientPet petInView = PetViewSelectionModel.this.windowModel.getPet(petView.getViewId());
/* 84 */           if (petInView != null)
/* 85 */             PetViewSelectionModel.this.viewChangeListener.petChanged(petInView); 
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   private static interface PetChangedListener {
/*    */     void petChanged(ClientPet param1ClientPet);
/*    */     
/*    */     void petViewSelected(PetView param1PetView);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\PetViewSelectionModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */