/*     */ package com.funcom.tcg.net;
/*     */ 
/*     */ import com.funcom.rpgengine2.SkillId;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemManager;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.rpgengine2.items.PlayerDescription;
/*     */ import com.funcom.rpgengine2.startupequipment.StartUpEquipmentDescription;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PlayerStartConfig
/*     */ {
/*  17 */   private static final Logger LOG = Logger.getLogger(PlayerStartConfig.class.getName());
/*     */   
/*     */   private final PlayerDescription playerDescription;
/*     */   
/*     */   private final StartingPet startingPet;
/*     */   
/*     */   private final SkillId torsoId;
/*     */   private final SkillId legsId;
/*     */   private final boolean trial;
/*     */   
/*     */   public PlayerStartConfig(PlayerDescription playerDescription, StartingPet startingPet, SkillId torsoId, SkillId legsId, boolean trial) {
/*  28 */     this.playerDescription = playerDescription;
/*  29 */     this.startingPet = startingPet;
/*  30 */     this.torsoId = torsoId;
/*  31 */     this.legsId = legsId;
/*  32 */     this.trial = trial;
/*     */   }
/*     */   
/*     */   public PlayerDescription getPlayerDescription() {
/*  36 */     return this.playerDescription;
/*     */   }
/*     */   
/*     */   public StartingPet getStartingPet() {
/*  40 */     return this.startingPet;
/*     */   }
/*     */   
/*     */   public SkillId getTorsoId() {
/*  44 */     return this.torsoId;
/*     */   }
/*     */   
/*     */   public SkillId getLegsId() {
/*  48 */     return this.legsId;
/*     */   }
/*     */   
/*     */   public boolean isTrial() {
/*  52 */     return this.trial;
/*     */   }
/*     */   
/*     */   public Collection<StartUpEquipmentDescription.StartUpPet> getStartupPets(Collection<StartUpEquipmentDescription.StartUpPet> startUpPets) {
/*  56 */     return getStartupPets(this.startingPet, startUpPets);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Collection<StartUpEquipmentDescription.StartUpPet> getStartupPets(StartingPet startingPet, Collection<StartUpEquipmentDescription.StartUpPet> startUpPets) {
/*  61 */     StartUpEquipmentDescription.StartUpPet foundPet = null;
/*  62 */     for (StartUpEquipmentDescription.StartUpPet checkPet : startUpPets) {
/*  63 */       if (startingPet.isValidPetClass(checkPet.getPetDescription().getPetItemClassId()) && (
/*  64 */         foundPet == null || (checkPet.isEquiped() && !foundPet.isEquiped())))
/*     */       {
/*  66 */         foundPet = checkPet;
/*     */       }
/*     */     } 
/*     */     
/*  70 */     if (foundPet == null) {
/*  71 */       LOG.error("cannot determine startup pet from start config: wantedPet=" + startingPet);
/*  72 */       foundPet = startUpPets.iterator().next();
/*     */     } 
/*  74 */     Collection<StartUpEquipmentDescription.StartUpPet> petsToCollect = new ArrayList<StartUpEquipmentDescription.StartUpPet>();
/*  75 */     petsToCollect.add(foundPet);
/*  76 */     return petsToCollect;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemDescription> getChosenEquipments(StartUpEquipmentDescription defaultStartUpDescription, ItemManager itemManager) {
/*  81 */     List<ItemDescription> allowedEquipments = defaultStartUpDescription.getAllEquipedItems();
/*     */     
/*  83 */     List<ItemDescription> wantedEquipments = new ArrayList<ItemDescription>();
/*  84 */     wantedEquipments.add(itemManager.getDescription(getTorsoId()));
/*  85 */     wantedEquipments.add(itemManager.getDescription(getLegsId()));
/*     */     
/*  87 */     for (Iterator<ItemDescription> iterator = wantedEquipments.iterator(); iterator.hasNext(); ) {
/*  88 */       ItemDescription checkItem = iterator.next();
/*  89 */       if (!allowedEquipments.contains(checkItem)) {
/*  90 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  96 */     List<ItemDescription> firstEquipmentOfEachType = defaultStartUpDescription.getFirstEquipedItems();
/*  97 */     for (ItemDescription allowedEquipment : firstEquipmentOfEachType) {
/*  98 */       ItemType type = allowedEquipment.getItemType();
/*  99 */       if (type != ItemType.EQUIP_LEGS && type != ItemType.EQUIP_TORSO)
/*     */       {
/* 101 */         wantedEquipments.add(allowedEquipment);
/*     */       }
/*     */     } 
/*     */     
/* 105 */     return wantedEquipments;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\PlayerStartConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */