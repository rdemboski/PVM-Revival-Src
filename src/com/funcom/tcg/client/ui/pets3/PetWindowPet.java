/*     */ package com.funcom.tcg.client.ui.pets3;
/*     */ 
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*     */ import com.funcom.tcg.client.model.rpg.PetEventsListener;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PetWindowPet
/*     */   implements Comparable<PetWindowPet>
/*     */ {
/*     */   private ClientPet playerPet;
/*     */   private ClientPetDescription petDescription;
/*     */   private Set<PetEventsListener> listeners;
/*     */   private PetEventsListener petEventsListener;
/*     */   
/*     */   public PetWindowPet(ClientPetDescription petDescription) {
/*  21 */     this.petDescription = petDescription;
/*     */     
/*  23 */     this.petEventsListener = new PetEventsListener()
/*     */       {
/*     */         public void newSkillAcquired(ClientPet clientPet, ClientItem aSkill) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void skillLost(ClientPet clientPet, ClientItem aSkill) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void selectedSkillsChanged(ClientPet clientPet) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void levelChanged(ClientPet clientPet, int lastLevel) {
/*  41 */           PetWindowPet.this.fireLevelChanged(lastLevel);
/*     */         }
/*     */ 
/*     */         
/*     */         public void expChanged(ClientPet clientPet) {
/*  46 */           PetWindowPet.this.fireExpChanged();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void addPetEventsListener(PetEventsListener petEventsListener) {
/*  52 */     if (this.listeners == null)
/*  53 */       this.listeners = new HashSet<PetEventsListener>(); 
/*  54 */     this.listeners.add(petEventsListener);
/*     */   }
/*     */   
/*     */   public void removePetEventsListener(PetEventsListener petEventsListener) {
/*  58 */     if (this.listeners != null)
/*  59 */       this.listeners.remove(petEventsListener); 
/*     */   }
/*     */   
/*     */   private void fireLevelChanged(int lastLevel) {
/*  63 */     if (this.listeners != null)
/*  64 */       for (PetEventsListener listener : this.listeners)
/*  65 */         listener.levelChanged(this.playerPet, lastLevel);  
/*     */   }
/*     */   
/*     */   private void fireExpChanged() {
/*  69 */     if (this.listeners != null)
/*  70 */       for (PetEventsListener listener : this.listeners)
/*  71 */         listener.expChanged(this.playerPet);  
/*     */   }
/*     */   
/*     */   public void setPlayerPet(ClientPet playerPet) {
/*  75 */     killListener();
/*  76 */     this.playerPet = playerPet;
/*  77 */     playerPet.addPetEventsListener(this.petEventsListener);
/*     */   }
/*     */   
/*     */   public void killListener() {
/*  81 */     if (this.playerPet != null) {
/*  82 */       this.playerPet.removePetEventsListener(this.petEventsListener);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasPlayerPet() {
/*  87 */     return (this.playerPet != null);
/*     */   }
/*     */   
/*     */   public int getLevel() {
/*  91 */     if (hasPlayerPet())
/*  92 */       return this.playerPet.getLevel(); 
/*  93 */     return this.petDescription.getTier();
/*     */   }
/*     */   
/*     */   public int getSortLevel() {
/*  97 */     return this.petDescription.getTier();
/*     */   }
/*     */   
/*     */   public int getExp() {
/* 101 */     if (hasPlayerPet())
/* 102 */       return this.playerPet.getExp(); 
/* 103 */     return 0;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 107 */     return this.petDescription.getName();
/*     */   }
/*     */   
/*     */   public ClientPetDescription getPetDescription() {
/* 111 */     return this.petDescription;
/*     */   }
/*     */   
/*     */   public ClientPet getPlayerPet() {
/* 115 */     return this.playerPet;
/*     */   }
/*     */   
/*     */   public boolean isOnTrial() {
/* 119 */     return (hasPlayerPet() && this.playerPet.isOnTrial());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(PetWindowPet o) {
/* 125 */     if (o.getName().compareTo(getName()) != 0) return o.getName().compareTo(getName()); 
/* 126 */     if (o.getLevel() != getLevel()) return o.getLevel() - getLevel(); 
/* 127 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\PetWindowPet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */