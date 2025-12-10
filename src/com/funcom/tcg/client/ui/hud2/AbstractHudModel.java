/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ 
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractHudModel
/*     */   implements HudModel
/*     */ {
/*  16 */   private List<HudModel.ChangeListener> listeners = new LinkedList<HudModel.ChangeListener>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChangeListener(HudModel.ChangeListener listener) {
/*  21 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeChangeListener(HudModel.ChangeListener listener) {
/*  26 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void triggerItemPickup(ItemDescription itemDesc) {}
/*     */ 
/*     */   
/*     */   public void triggerPetPickup() {}
/*     */ 
/*     */   
/*     */   protected void fireHealthChanged(float fraction) {
/*  38 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  39 */       listener.healthChanged(fraction);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireManaChanged(float fraction) {
/*  44 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  45 */       listener.manaChanged(fraction);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireXpChanged(float fraction) {
/*  50 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  51 */       listener.xpChanged(fraction);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireLevelChanged(int level) {
/*  56 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  57 */       listener.levelChanged(level);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void firePetChanged(int petSlot, PetButtonModel newPet) {
/*  62 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  63 */       listener.petChanged(petSlot, newPet);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireActivePetChanged(int slot) {
/*  68 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  69 */       listener.activePetSelected(slot);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireHealthPotionsChanged(int amount) {
/*  74 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  75 */       listener.healthPotionsAmountChanged(amount);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireManaPotionsChanged(int amount) {
/*  80 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  81 */       listener.manaPotionsAmountChanged(amount);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireSkillbarItemUsed(int slot) {
/*  86 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  87 */       listener.skillbarItemUsed(slot);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireTownportalChanged(boolean visibillity) {
/*  92 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  93 */       listener.townPortalVisibilityChanged(visibillity);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void firePetPickedUp() {
/*  98 */     for (HudModel.ChangeListener listener : this.listeners) {
/*  99 */       listener.petPickedUp();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireItemPickedUp(ItemDescription itemDesc) {
/* 104 */     for (HudModel.ChangeListener listener : this.listeners) {
/* 105 */       listener.itemPickedUp(itemDesc);
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireCharacterWindowToggled() {
/* 110 */     for (HudModel.ChangeListener listener : this.listeners) {
/* 111 */       listener.characterWindowToggled();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireAchievementPending(int pendingMessages) {
/* 116 */     for (HudModel.ChangeListener listener : this.listeners) {
/* 117 */       listener.achievementPending(pendingMessages);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireChatPending(int pendingMessages) {
/* 122 */     for (HudModel.ChangeListener listener : this.listeners) {
/* 123 */       listener.chatPending(pendingMessages);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fireFriendPending(int pendingMessages) {
/* 128 */     for (HudModel.ChangeListener listener : this.listeners) {
/* 129 */       listener.friendPending(pendingMessages);
/*     */     }
/*     */   }
/*     */   
/*     */   public void firePetsWindowToggled() {
/* 134 */     for (HudModel.ChangeListener listener : this.listeners) {
/* 135 */       listener.petWindowToggled();
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireMapWindowToggled() {
/* 140 */     for (HudModel.ChangeListener listener : this.listeners) {
/* 141 */       listener.mapWindowToggled();
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireChatWindowToggled() {
/* 146 */     for (HudModel.ChangeListener listener : this.listeners) {
/* 147 */       listener.chatWindowToggled();
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireOptionsWindowToggled() {
/* 152 */     for (HudModel.ChangeListener listener : this.listeners) {
/* 153 */       listener.optionsWindowToggled();
/*     */     }
/*     */   }
/*     */   
/*     */   public void firePetSlotHover(int slot) {
/* 158 */     for (HudModel.ChangeListener listener : this.listeners)
/* 159 */       listener.petSlotHovered(slot); 
/*     */   }
/*     */   
/*     */   public static abstract class ChangeListenerAdapter implements HudModel.ChangeListener {
/*     */     public void healthChanged(float fraction) {}
/*     */     
/*     */     public void healthPotionsAmountChanged(int newAmount) {}
/*     */     
/*     */     public void manaChanged(float fraction) {}
/*     */     
/*     */     public void manaPotionsAmountChanged(int newAmount) {}
/*     */     
/*     */     public void xpChanged(float fraction) {}
/*     */     
/*     */     public void levelChanged(int level) {}
/*     */     
/*     */     public void petChanged(int slot, PetButtonModel model) {}
/*     */     
/*     */     public void activePetSelected(int slot) {}
/*     */     
/*     */     public void skillbarItemUsed(int slot) {}
/*     */     
/*     */     public void townPortalVisibilityChanged(boolean visible) {}
/*     */     
/*     */     public void petPickedUp() {}
/*     */     
/*     */     public void itemPickedUp(ItemDescription itemDesc) {}
/*     */     
/*     */     public void chatPending(int pendingMessages) {}
/*     */     
/*     */     public void friendPending(int pendingMessages) {}
/*     */     
/*     */     public void achievementPending(int pendingMessages) {}
/*     */     
/*     */     public void characterWindowToggled() {}
/*     */     
/*     */     public void petWindowToggled() {}
/*     */     
/*     */     public void chatWindowToggled() {}
/*     */     
/*     */     public void mapWindowToggled() {}
/*     */     
/*     */     public void optionsWindowToggled() {}
/*     */     
/*     */     public void petSlotHovered(int slot) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\AbstractHudModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */