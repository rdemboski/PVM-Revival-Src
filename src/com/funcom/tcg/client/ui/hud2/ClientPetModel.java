/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ 
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.PetSlot;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ 
/*     */ 
/*     */ public class ClientPetModel
/*     */   implements PetButtonModel
/*     */ {
/*     */   public static final String UNKNOWN_IMAGE_PATH = "gui/v2/icons/interface/slots/slot_brass_empty.png";
/*     */   private ClientPlayer clientPlayer;
/*     */   private int slot;
/*     */   
/*     */   public ClientPetModel(ClientPlayer clientPlayer, int slot) {
/*  17 */     this.clientPlayer = clientPlayer;
/*  18 */     this.slot = slot;
/*     */   }
/*     */   
/*     */   public String getClassId() {
/*  22 */     ClientPet pet = getPet();
/*  23 */     if (pet != null) {
/*  24 */       return pet.getClassId();
/*     */     }
/*  26 */     return "";
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  30 */     ClientPet pet = getPet();
/*  31 */     if (pet != null) {
/*  32 */       return pet.getDescription();
/*     */     }
/*  34 */     return "";
/*     */   }
/*     */   
/*     */   public String getFamily() {
/*  38 */     ClientPet pet = getPet();
/*  39 */     if (pet != null) {
/*  40 */       return pet.getFamily();
/*     */     }
/*  42 */     return "";
/*     */   }
/*     */   
/*     */   public String getElementId() {
/*  46 */     ClientPet pet = getPet();
/*  47 */     if (pet != null) {
/*  48 */       return pet.getElementId();
/*     */     }
/*  50 */     return "";
/*     */   }
/*     */   
/*     */   public String getRarity() {
/*  54 */     ClientPet pet = getPet();
/*  55 */     if (pet != null) {
/*  56 */       return pet.getRarity();
/*     */     }
/*  58 */     return "";
/*     */   }
/*     */   
/*     */   public int getTier() {
/*  62 */     ClientPet pet = getPet();
/*  63 */     if (pet != null) {
/*  64 */       return pet.getTier();
/*     */     }
/*  66 */     return 0;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  70 */     ClientPet pet = getPet();
/*  71 */     if (pet != null) {
/*  72 */       return pet.getType();
/*     */     }
/*  74 */     return "";
/*     */   }
/*     */   
/*     */   public String getHtml() {
/*  78 */     ClientPet pet = getPet();
/*  79 */     if (pet != null) {
/*  80 */       return MainGameState.getToolTipManager().getPetHtml(pet.getClassId());
/*     */     }
/*  82 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public PetSlot getPetSlot() {
/*  87 */     return this.clientPlayer.getPetSlot(this.slot);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  91 */     ClientPet pet = getPet();
/*  92 */     if (pet != null) {
/*  93 */       return pet.getName();
/*     */     }
/*  95 */     return "";
/*     */   }
/*     */   
/*     */   public String getIcon() {
/*  99 */     ClientPet pet = getPet();
/* 100 */     if (pet != null) {
/* 101 */       return pet.getIcon();
/*     */     }
/* 103 */     return "gui/v2/icons/interface/slots/slot_brass_empty.png";
/*     */   }
/*     */   
/*     */   private ClientPet getPet() {
/* 107 */     return this.clientPlayer.getSelectedPet(this.slot);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\ClientPetModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */