/*     */ package com.funcom.tcg.client.ui.pets3;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.SelectableButtonModel;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud.SubscribeWindow;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ 
/*     */ 
/*     */ public class PetWindowButtonModel
/*     */   extends SelectableButtonModel
/*     */ {
/*     */   private ResourceManager resourceManager;
/*     */   private final PetViewSelectionModel viewSelectionModel;
/*     */   private PetsWindowModel petWindowModel;
/*     */   protected PetWindowPet pet;
/*     */   protected BImage petIcon;
/*     */   protected BImage epicPetIcon;
/*     */   
/*     */   public PetWindowButtonModel(ResourceManager resourceManager, PetViewSelectionModel viewSelectionModel, PetsWindowModel petWindowModel, PetWindowPet pet) {
/*  25 */     this.resourceManager = resourceManager;
/*  26 */     this.viewSelectionModel = viewSelectionModel;
/*  27 */     this.petWindowModel = petWindowModel;
/*  28 */     this.pet = pet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initialize(ResourceManager resourceManager) {}
/*     */ 
/*     */   
/*     */   public boolean isSelected() {
/*  37 */     return this.viewSelectionModel.isSelected(this.pet);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(boolean selected) {
/*  42 */     if (selected) {
/*  43 */       if (this.pet.getPetDescription().isSubscriberOnly() && !this.petWindowModel.isSubscriber() && !this.pet.isOnTrial() && this.pet.hasPlayerPet()) {
/*     */ 
/*     */         
/*  46 */         BWindow existingWindow = TcgUI.getWindowFromClass(SubscribeWindow.class);
/*  47 */         if (existingWindow == null) {
/*  48 */           SubscribeWindow window = new SubscribeWindow(TcgGame.getResourceManager(), MainGameState.isPlayerRegistered(), MainGameState.isPlayerRegistered() ? "subscribedialog.button.subscribe" : "quitwindow.askregister.ok", "subscribedialog.button.cancel", MainGameState.isPlayerRegistered() ? "popup.select.members.item" : "popup.select.members.item.notsaved");
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  53 */           window.setLayer(101);
/*  54 */           BuiSystem.getRootNode().addWindow((BWindow)window);
/*     */         } 
/*  56 */       } else if (this.pet.hasPlayerPet()) {
/*  57 */         this.viewSelectionModel.notifyPetSelected(this.pet);
/*     */       } 
/*     */     } else {
/*  60 */       throw new UnsupportedOperationException("cannot select a NULL pet");
/*     */     } 
/*     */   }
/*     */   
/*     */   public BImage getIcon() {
/*  65 */     if (getLevel() >= 40) {
/*  66 */       if (this.epicPetIcon == null) {
/*  67 */         String petIconPath = this.pet.hasPlayerPet() ? this.pet.getPlayerPet().getPetVisuals().getIcon() : "gui/icons/pets/empty_slot.png";
/*  68 */         this.epicPetIcon = (BImage)this.resourceManager.getResource(BImage.class, petIconPath);
/*     */       } 
/*  70 */       return this.epicPetIcon;
/*     */     } 
/*  72 */     if (this.petIcon == null) {
/*  73 */       String petIconPath = this.pet.hasPlayerPet() ? this.pet.getPlayerPet().getPetVisuals().getIcon() : "gui/icons/pets/empty_slot.png";
/*  74 */       this.petIcon = (BImage)this.resourceManager.getResource(BImage.class, petIconPath);
/*     */     } 
/*  76 */     return this.petIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEpicPetIcon(BImage epicPetIcon) {
/*  81 */     this.epicPetIcon = epicPetIcon;
/*     */   }
/*     */   
/*     */   public void setPetIcon(BImage petIcon) {
/*  85 */     this.petIcon = petIcon;
/*     */   }
/*     */   
/*     */   public float getCurrentXp() {
/*  89 */     return this.pet.getExp() / this.pet.getLevel();
/*     */   }
/*     */   
/*     */   public int getLevel() {
/*  93 */     return this.pet.getLevel();
/*     */   }
/*     */   
/*     */   public String getPetClassId() {
/*  97 */     return this.pet.getPetDescription().getClassId();
/*     */   }
/*     */   
/*     */   public PetWindowPet getPet() {
/* 101 */     return this.pet;
/*     */   }
/*     */   
/*     */   public void setPet(PetWindowPet pet) {
/* 105 */     this.pet = pet;
/* 106 */     this.petIcon = null;
/* 107 */     this.epicPetIcon = null;
/*     */   }
/*     */   
/*     */   public boolean isSubscriberOnly() {
/* 111 */     return this.pet.getPetDescription().isSubscriberOnly();
/*     */   }
/*     */   
/*     */   public boolean isOnTrial() {
/* 115 */     return this.pet.isOnTrial();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\PetWindowButtonModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */