/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ 
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.PetEventsListener;
/*     */ import com.funcom.tcg.client.model.rpg.PetSlot;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.funcom.tcg.client.ui.pets3.AbstractPetButton;
/*     */ import com.funcom.tcg.client.ui.pets3.PetWindowButtonModel;
/*     */ import com.funcom.tcg.client.ui.pets3.PetWindowPet;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ 
/*     */ 
/*     */ public class PetCardButton
/*     */   extends AbstractPetButton
/*     */ {
/*     */   private static final float SIZE_INC_HOVER = 0.06F;
/*     */   private static final float SIZE_INC_SELECTED = 0.06F;
/*     */   private PetButtonModel petModel;
/*     */   
/*  25 */   private PetSlot.PetSlotListener listener = new PetSlot.PetSlotListener()
/*     */     {
/*     */       public void selectableChanged(boolean selectable) {
/*  28 */         PetCardButton.this.selectableChanged(selectable);
/*     */       }
/*     */     };
/*     */   
/*     */   public PetCardButton(PetWindowButtonModel model, TCGToolTipManager tooltipManager, boolean subscriber, PetButtonModel petModel) {
/*  33 */     super(model, tooltipManager, subscriber);
/*  34 */     this.petModel = petModel;
/*     */     
/*  36 */     setSizeIncHover(0.06F);
/*  37 */     setSizeIncSelected(0.06F);
/*     */     
/*  39 */     updatePetButton();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initialize(boolean subscriber) {
/*  44 */     this.petIconLabel.setStyleClass("pet-label-" + (MainGameState.getPlayerModel().isSubscriber() ? "" : "not") + "subscriber");
/*     */     
/*  46 */     addListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addListeners() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateSizes() {
/*  56 */     super.updateSizes();
/*  57 */     this.trialLabel.setVisible(false);
/*     */   }
/*     */   
/*     */   public void selectableChanged(boolean selectable) {
/*  61 */     if (selectable && this.collected) {
/*  62 */       wasCollected(true);
/*     */     } else {
/*  64 */       this.petIconLabel.setStyleClass("pet-label-" + (selectable ? "" : "not") + "subscriber");
/*  65 */       this.petIconLabel.invalidate();
/*  66 */       configureStyle(this.style, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dismiss() {
/*  71 */     if (this.petModel != null) {
/*  72 */       this.petModel.getPetSlot().removeListener(this.listener);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPetModel(PetButtonModel petModel) {
/*  77 */     if (this.petModel != null) {
/*  78 */       this.petModel.getPetSlot().removeListener(this.listener);
/*     */     }
/*  80 */     this.petModel = petModel;
/*  81 */     PetSlot slot = petModel.getPetSlot();
/*  82 */     if (slot != null) {
/*  83 */       slot.addListener(this.listener);
/*     */     }
/*  85 */     updatePetButton();
/*     */   }
/*     */   
/*     */   private void updateListener() {
/*  89 */     this.model.getPet().addPetEventsListener(new PetEventsListener()
/*     */         {
/*     */           public void newSkillAcquired(ClientPet clientPet, ClientItem aSkill) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void skillLost(ClientPet clientPet, ClientItem aSkill) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void selectedSkillsChanged(ClientPet clientPet) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void levelChanged(ClientPet clientPet, int lastLevel) {
/* 107 */             if (PetCardButton.this.model.getLevel() >= 40) {
/* 108 */               ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, PetCardButton.this.model.getIcon());
/* 109 */               PetCardButton.this.petIconLabel.setBackground(0, (BBackground)imageBackground);
/* 110 */               PetCardButton.this.setStyleClass("pet-button-epic");
/* 111 */               if (PetCardButton.this.style != null) {
/* 112 */                 PetCardButton.this.configureStyle(PetCardButton.this.style);
/* 113 */                 PetCardButton.this.invalidate();
/* 114 */                 PetCardButton.this.layout();
/*     */               } 
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void expChanged(ClientPet clientPet) {}
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePetButton() {
/* 126 */     if (this.model.getPet() != null) {
/* 127 */       this.model.getPet().killListener();
/*     */     }
/* 129 */     if (this.petModel.getPetSlot().getPet() != null) {
/* 130 */       ClientPet pet = this.petModel.getPetSlot().getPet();
/* 131 */       PetWindowPet windowPet = new PetWindowPet(pet.getClientPetDescription());
/* 132 */       windowPet.setPlayerPet(pet);
/* 133 */       this.model.setPet(windowPet);
/* 134 */       wasCollected(true);
/* 135 */       update(10000L);
/*     */       
/* 137 */       updateListener();
/*     */     } else {
/*     */       
/* 140 */       this.model.setPet(null);
/* 141 */       wasCollected(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void wasCollected(boolean value) {
/* 147 */     this.collected = value;
/* 148 */     if (value) {
/* 149 */       ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, this.model.getIcon());
/* 150 */       this.petIconLabel.setBackground(0, (BBackground)imageBackground);
/* 151 */       this.petTrialTime = this.model.getPet().getPlayerPet().getPetTrialExpireTime();
/* 152 */       this.xpBar.setVisible(false);
/* 153 */       if (this.model.getLevel() >= 40) {
/* 154 */         setStyleClass("pet-button-epic");
/* 155 */         if (this.style != null) {
/* 156 */           configureStyle(this.style);
/*     */         }
/*     */       } else {
/* 159 */         setStyleClass(getDefaultStyleClass());
/* 160 */         if (this.style != null) {
/* 161 */           configureStyle(this.style);
/*     */         }
/*     */       } 
/*     */     } else {
/* 165 */       this.petTrialTime = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSelected(boolean val) {
/* 170 */     ((SkillBarPetButtonModel)this.model).setSelectedState(val);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMouseEntered() {
/* 175 */     super.onMouseEntered();
/* 176 */     update(10000L);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMouseExited() {
/* 181 */     super.onMouseExited();
/* 182 */     update(10000L);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\PetCardButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */