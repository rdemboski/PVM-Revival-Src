/*     */ package com.funcom.tcg.client.model.rpg;
/*     */ 
/*     */ import com.funcom.commons.MathUtils;
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.ai.Brain;
/*     */ import com.funcom.gameengine.ai.DeadBrain;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.rpgengine2.abilities.BuffType;
/*     */ import com.funcom.rpgengine2.combat.RpgStatus;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.RpgQueryableSupport;
/*     */ import com.funcom.rpgengine2.creatures.StatSupport;
/*     */ import com.funcom.rpgengine2.creatures.SupportEvent;
/*     */ import com.funcom.rpgengine2.items.PlayerDescription;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.net.ResponseCollectedPetsProcessor;
/*     */ import com.funcom.tcg.client.state.ClientChatController;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.client.ui.inventory.ListInventory;
/*     */ import com.funcom.tcg.net.DefaultSubscriptionState;
/*     */ import com.funcom.tcg.net.SubscriptionState;
/*     */ import com.funcom.tcg.net.message.LoginResponseMessage;
/*     */ import com.funcom.tcg.net.message.ResponseCollectedPetsMessage;
/*     */ import com.funcom.tcg.net.message.StateUpdateMessage;
/*     */ import com.funcom.tcg.rpg.FixedPetSupport;
/*     */ import com.funcom.tcg.rpg.ItemHolderType;
/*     */ import com.funcom.util.SizeCheckedArrayList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientPlayer
/*     */   extends Creature
/*     */   implements RpgEntity, ResponseCollectedPetsProcessor
/*     */ {
/*  55 */   private static final Logger LOGGER = Logger.getLogger(ClientPlayer.class.getName());
/*     */   
/*     */   public static final int SKILLBAR_SLOT_ID_FIRST = 0;
/*     */   
/*     */   public static final int SKILLBAR_SLOT_ID_SECOND = 1;
/*     */   
/*     */   public static final int SKILLBAR_SLOT_ID_THIRD = 2;
/*     */   public static final int SKILLBAR_SLOT_ID_FOURTH = 3;
/*     */   public static final int SKILLBAR_SLOT_ID_FIFTH = 4;
/*     */   public static final int PET_BAR_1 = 0;
/*     */   public static final int PET_BAR_2 = 1;
/*     */   public static final int PET_BAR_3 = 2;
/*     */   private Inventory inventory;
/*     */   private ClientEquipDoll equipDoll;
/*     */   protected Map<Class, Object> supportMap;
/*     */   private Map<BuffType, StateUpdateMessage.BuffData> buffSlots;
/*     */   protected List<PlayerEventsListener> playerEventsListeners;
/*     */   private Set<ClientPet> collectedPets;
/*     */   private PetSlot[] selectedPets;
/*     */   protected ClientPet activePet;
/*     */   private Brain aliveBrain;
/*     */   private boolean characterMounted;
/*     */   private PlayerDescription playerDescription;
/*     */   private Set<RpgStatus> rpgStatusList;
/*     */   private long globalCooldownUntil;
/*     */   private GiftBoxCollection giftBoxCollection;
/*     */   private int externalChatId;
/*     */   private ClientChatController chatController;
/*  83 */   private DefaultSubscriptionState subscriptionState = new DefaultSubscriptionState();
/*  84 */   private int immunityHitCounter = 0;
/*     */   protected PetEventsListener petListener;
/*     */   
/*     */   public ClientPlayer(String name, WorldCoordinate position, Brain aliveBrain, double radius) {
/*  88 */     super(-1, name, "player", position, radius);
/*  89 */     this.aliveBrain = aliveBrain;
/*  90 */     this.supportMap = (Map)new HashMap<Class<?>, Object>();
/*  91 */     StatSupport statSupport = new StatSupport(Short.valueOf((short)20));
/*  92 */     addSupport(statSupport);
/*  93 */     addSupport(new FixedPetSupport(0));
/*  94 */     this.buffSlots = new EnumMap<BuffType, StateUpdateMessage.BuffData>(BuffType.class);
/*  95 */     this.inventory = (Inventory)new ListInventory(ItemHolderType.INVENTORY.getId());
/*  96 */     this.equipDoll = new ClientEquipDoll(getId());
/*  97 */     this.selectedPets = new PetSlot[3];
/*  98 */     for (int i = 0; i < 3; i++)
/*  99 */       this.selectedPets[i] = new PetSlot(null, true); 
/* 100 */     this.collectedPets = new HashSet<ClientPet>();
/* 101 */     this.characterMounted = true;
/*     */     
/* 103 */     if (aliveBrain != null) {
/* 104 */       setBrain(aliveBrain);
/*     */     }
/*     */     
/* 107 */     this.rpgStatusList = new HashSet<RpgStatus>();
/* 108 */     this.giftBoxCollection = new GiftBoxCollection();
/*     */     
/* 110 */     this.petListener = new PetEventsListener()
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
/* 128 */           if (lastLevel < 40 && clientPet.getLevel() >= 40) {
/* 129 */             ClientPlayer.this.fireActivePetChanged();
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void expChanged(ClientPet clientPet) {}
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerForPetMessageProcessing() {
/* 140 */     NetworkHandler.instance().addResponseCollectedPetsProcessor(this);
/*     */   }
/*     */   
/*     */   public void addPlayerEventsListener(PlayerEventsListener playerEventsListener) {
/* 144 */     if (this.playerEventsListeners == null) {
/* 145 */       this.playerEventsListeners = (List<PlayerEventsListener>)new SizeCheckedArrayList(4, "playerEventsListeners", 16);
/*     */     }
/*     */     
/* 148 */     if (!this.playerEventsListeners.contains(playerEventsListener)) {
/* 149 */       this.playerEventsListeners.add(playerEventsListener);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removePlayerEventsListener(PlayerEventsListener playerEventsListener) {
/* 154 */     if (this.playerEventsListeners != null)
/* 155 */       this.playerEventsListeners.remove(playerEventsListener); 
/*     */   }
/*     */   
/*     */   public Inventory getInventory() {
/* 159 */     return this.inventory;
/*     */   }
/*     */   
/*     */   public ClientEquipDoll getEquipDoll() {
/* 163 */     return this.equipDoll;
/*     */   }
/*     */   
/*     */   public void addPet(ClientPet pet) {
/* 167 */     this.collectedPets.add(pet);
/* 168 */     fireNewPetCollected(pet);
/*     */   }
/*     */   
/*     */   public Set<ClientPet> getCollectedPets() {
/* 172 */     return Collections.unmodifiableSet(this.collectedPets);
/*     */   }
/*     */   
/*     */   public void refreshPets() {
/* 176 */     ArrayList<ClientPet> pets = new ArrayList<ClientPet>();
/* 177 */     pets.addAll(this.collectedPets);
/* 178 */     this.collectedPets.clear();
/* 179 */     for (ClientPet pet : pets) {
/* 180 */       ClientPetDescription clientPetDescription = MainGameState.getPetRegistry().getPetForClassId(pet.getClassId());
/* 181 */       ClientPetDescription updatedClientPetDescription = MainGameState.getPetRegistry().hasPetForClassId(pet.getClassId() + "-upgrade") ? MainGameState.getPetRegistry().getPetForClassId(pet.getClassId() + "-upgrade") : clientPetDescription;
/* 182 */       ClientPet petToAdd = new ClientPet(clientPetDescription, (updatedClientPetDescription == null) ? clientPetDescription : updatedClientPetDescription);
/* 183 */       this.collectedPets.add(petToAdd);
/*     */     } 
/*     */     
/* 186 */     for (PetSlot selectedPet : this.selectedPets) {
/* 187 */       ClientPet pet = selectedPet.getPet();
/* 188 */       if (pet != null) {
/* 189 */         ClientPet petToAdd = getCollectedPetForId(pet.getClassId());
/* 190 */         selectedPet.setPet(petToAdd);
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     if (this.activePet != null) {
/* 195 */       ClientPet petToAdd = getCollectedPetForId(this.activePet.getClassId());
/* 196 */       this.activePet = petToAdd;
/* 197 */       setUpMappedDfxsForActivePet();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refreshInventory() {
/* 202 */     Iterator<InventoryItem> inventoryIter = this.inventory.iterator();
/* 203 */     List<InventoryItem> items = new ArrayList<InventoryItem>();
/* 204 */     while (inventoryIter.hasNext()) {
/* 205 */       items.add(inventoryIter.next());
/*     */     }
/* 207 */     for (InventoryItem item : items) {
/* 208 */       if (item != null) {
/* 209 */         ClientItem itemToAdd = MainGameState.getItemRegistry().getItemForClassID(item.getClassId(), item.getTier());
/* 210 */         itemToAdd.setAmount(item.getAmount());
/* 211 */         this.inventory.setItemToSlot(itemToAdd, this.inventory.getSlotForItem(item));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refreshEquipment() {
/* 217 */     for (int i = 0; i < this.equipDoll.getItemsCount(); i++) {
/* 218 */       ClientItem item = this.equipDoll.getItem(i);
/* 219 */       if (item != null) {
/* 220 */         ClientItem itemToAdd = MainGameState.getItemRegistry().getItemForClassID(item.getClassId(), item.getTier());
/* 221 */         itemToAdd.setAmount(item.getAmount());
/* 222 */         this.equipDoll.setItem(i, itemToAdd);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setActivePet(ClientPet activePet) {
/* 228 */     if (this.activePet == activePet)
/*     */       return; 
/* 230 */     if (this.activePet != null)
/* 231 */       this.activePet.removePetEventsListener(this.petListener); 
/* 232 */     this.activePet = activePet;
/* 233 */     setUpMappedDfxsForActivePet();
/* 234 */     this.activePet.addPetEventsListener(this.petListener);
/* 235 */     fireActivePetChanged();
/*     */   }
/*     */   
/*     */   public boolean setActivePetFromClassId(String activePetClassId) {
/* 239 */     if (this.activePet == null || (!activePetClassId.equals("") && !this.activePet.getClassId().equals(activePetClassId))) {
/* 240 */       if (this.activePet != null)
/* 241 */         this.activePet.removePetEventsListener(this.petListener); 
/* 242 */       ClientPetDescription clientPetDescription = MainGameState.getPetRegistry().getPetForClassId(activePetClassId);
/* 243 */       ClientPetDescription updatedClientPetDescription = MainGameState.getPetRegistry().hasPetForClassId(activePetClassId + "-upgrade") ? MainGameState.getPetRegistry().getPetForClassId(activePetClassId + "-upgrade") : clientPetDescription;
/* 244 */       this.activePet = new ClientPet(clientPetDescription, (updatedClientPetDescription == null) ? clientPetDescription : updatedClientPetDescription);
/* 245 */       this.activePet.addPetEventsListener(this.petListener);
/* 246 */       setUpMappedDfxsForActivePet();
/* 247 */       fireActivePetChanged();
/*     */       
/* 249 */       return true;
/*     */     } 
/*     */     
/* 252 */     return false;
/*     */   }
/*     */   
/*     */   protected void setUpMappedDfxsForActivePet() {
/* 256 */     clearMappedDfx();
/* 257 */     addMappedDfx("move", this.activePet.getPetVisuals().getWalkDfx());
/* 258 */     addMappedDfx("idle", this.activePet.getPetVisuals().getIdleDfx());
/* 259 */     addMappedDfx("interact", this.activePet.getPetVisuals().getInteractDfx());
/* 260 */     addMappedDfx("die", this.activePet.getPetVisuals().getDeathDfx());
/*     */   }
/*     */   
/*     */   public ClientPet getActivePet() {
/* 264 */     return this.activePet;
/*     */   }
/*     */   
/*     */   public void setSelectedPet(int index, ClientPet clientPet) {
/* 268 */     if (this.selectedPets[index].getPet() == clientPet) {
/*     */       return;
/*     */     }
/* 271 */     this.selectedPets[index].setPet(clientPet);
/* 272 */     firePetSelectionChanged(index, clientPet);
/*     */     
/* 274 */     if (this.activePet == null) {
/* 275 */       setActivePet(clientPet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void refreshSelectedPets() {
/* 280 */     for (int i = 0; i < this.selectedPets.length; i++) {
/* 281 */       firePetSelectionChanged(i, this.selectedPets[i].getPet());
/* 282 */       this.selectedPets[i].setSelectable(this.selectedPets[i].isSelectable());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSelectedPetsFromClassIds(String[] selectedPetClassIds) {
/* 287 */     for (int i = 0; i < selectedPetClassIds.length; i++) {
/* 288 */       String selectedPetClassId = selectedPetClassIds[i];
/* 289 */       if (!selectedPetClassId.equals("")) {
/* 290 */         this.selectedPets[i].setPet(getCollectedPetForId(selectedPetClassId));
/*     */       } else {
/*     */         
/* 293 */         this.selectedPets[i].setPet(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientPet getSelectedPet(int index) {
/* 306 */     return this.selectedPets[index].getPet();
/*     */   }
/*     */   
/*     */   public PetSlot getPetSlot(int index) {
/* 310 */     return this.selectedPets[index];
/*     */   }
/*     */   
/*     */   public PetSlot[] getPetSlots() {
/* 314 */     return this.selectedPets;
/*     */   }
/*     */   
/*     */   public boolean isSubscriber() {
/* 318 */     return this.subscriptionState.isSubscriber();
/*     */   }
/*     */   
/*     */   public void setCharacterMounted(boolean characterMounted) {
/* 322 */     this.characterMounted = characterMounted;
/*     */   }
/*     */   
/*     */   public boolean isCharacterMounted() {
/* 326 */     return this.characterMounted;
/*     */   }
/*     */   
/*     */   public boolean isCharacterMountedWithPet() {
/* 330 */     return (getActivePet() != null && isCharacterMounted());
/*     */   }
/*     */   
/*     */   public void setPlayerDescription(PlayerDescription playerDescription) {
/* 334 */     this.playerDescription = playerDescription;
/*     */   }
/*     */   
/*     */   public PlayerDescription getPlayerDescription() {
/* 338 */     return this.playerDescription;
/*     */   }
/*     */   
/*     */   public Integer getStatSum(Short statId) {
/* 342 */     Stat stat = getStat(statId);
/* 343 */     return Integer.valueOf(stat.getSum());
/*     */   }
/*     */   
/*     */   private Stat getStat(Short statId) {
/* 347 */     Stat stat = ((StatSupport)getSupport(StatSupport.class)).getStatById(statId.shortValue());
/* 348 */     if (stat == null) {
/* 349 */       throw new IllegalArgumentException("There is no stat id: " + statId);
/*     */     }
/* 351 */     return stat;
/*     */   }
/*     */   
/*     */   public StatSupport getStatSupport() {
/* 355 */     return getSupport(StatSupport.class);
/*     */   }
/*     */   
/*     */   public float getStatAsFloat(Short statId) {
/* 359 */     Integer value = getStatSum(statId);
/* 360 */     return Stat.intToFloat(value.intValue());
/*     */   }
/*     */   
/*     */   public void useSkillbarItem(int slotId, double distance) {
/* 364 */     if (getActivePet() == null)
/*     */       return; 
/* 366 */     ClientItem skillbarItem = getActivePet().getSkillAt(slotId);
/* 367 */     if (skillbarItem != null) {
/* 368 */       Stat manaStat = getStat(Short.valueOf((short)14));
/* 369 */       Integer mana = Integer.valueOf(manaStat.getSum());
/* 370 */       if (mana != null && mana.intValue() >= skillbarItem.getManaCost(getStat(Short.valueOf((short)13)).getBase()))
/*     */       {
/*     */         
/* 373 */         if (skillbarItem.isReady() && !this.rpgStatusList.contains(RpgStatus.SILENCE) && checkGlobalCooldown(skillbarItem.getGlobalCooldown()) && !MainGameState.getPauseModel().isPaused()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 378 */           int value = mana.intValue() - skillbarItem.getManaCost(getStat(Short.valueOf((short)13)).getBase());
/* 379 */           manaStat.setBase(value);
/* 380 */           setStat(Short.valueOf((short)14), manaStat);
/* 381 */           fireSkillBarItemUsed(getId(), Inventory.TYPE_SKILLBAR, slotId);
/* 382 */           skillbarItem.use(this, getId(), Inventory.TYPE_SKILLBAR, slotId, getRotation(), distance);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void syncPlayerProperties(LoginResponseMessage synchronize) {
/* 400 */     setId(synchronize.getAcceptedId().intValue());
/* 401 */     setName(synchronize.getNick());
/* 402 */     setPosition(synchronize.getPosition());
/* 403 */     setRotation(synchronize.getAngle());
/*     */     
/* 405 */     PetRegistry petRegistry = MainGameState.getPetRegistry();
/* 406 */     String[] petIds = synchronize.getCollectedPetClassIds();
/* 407 */     Integer[] petLevels = synchronize.getCollectedPetClassLevels();
/* 408 */     Integer[] petXp = synchronize.getCollectedPetClassXp();
/* 409 */     for (int i = 0; i < petIds.length; i++) {
/* 410 */       ClientPetDescription clientPetDescription = MainGameState.getPetRegistry().getPetForClassId(petIds[i]);
/* 411 */       ClientPetDescription updatedClientPetDescription = MainGameState.getPetRegistry().hasPetForClassId(petIds[i] + "-upgrade") ? MainGameState.getPetRegistry().getPetForClassId(petIds[i] + "-upgrade") : clientPetDescription;
/* 412 */       ClientPet pet = new ClientPet(clientPetDescription, (updatedClientPetDescription == null) ? clientPetDescription : updatedClientPetDescription);
/* 413 */       pet.setLevel(petLevels[i].intValue());
/* 414 */       pet.setExp(petXp[i].intValue());
/* 415 */       addPet(pet);
/*     */     } 
/*     */     
/* 418 */     setSelectedPetsFromClassIds(synchronize.getSelectedPetClassIds());
/* 419 */     setPlayerDescription(synchronize.getPlayerDescription());
/* 420 */     JavaLocalization.getInstance().setMale((synchronize.getPlayerDescription().getGender() == PlayerDescription.Gender.MALE));
/* 421 */     setSubscriptionState(synchronize.getSubscriptionState());
/*     */     
/* 423 */     ((StatSupport)getSupport(StatSupport.class)).resetModifiers(this);
/* 424 */     updateStats(synchronize.getStats());
/*     */     
/* 426 */     LOGGER.info("ClientPlayer.syncPlayerProperties: stats = " + synchronize.getStats());
/*     */   }
/*     */   
/*     */   public void setSubscriptionState(SubscriptionState subscriptionState) {
/* 430 */     this.subscriptionState.set(subscriptionState);
/*     */   }
/*     */   
/*     */   public DefaultSubscriptionState getSubscriptionState() {
/* 434 */     return this.subscriptionState;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSupport(Object support) {
/* 440 */     Class<?> tmp = support.getClass();
/* 441 */     mapSupports(tmp, support);
/*     */   }
/*     */   
/*     */   private void mapSupports(Class<?> tmp, Object support) {
/* 445 */     while (tmp != null) {
/*     */       
/* 447 */       Class[] interfaces = tmp.getInterfaces();
/* 448 */       for (Class<RpgQueryableSupport> anInterface : interfaces) {
/* 449 */         if (anInterface != RpgQueryableSupport.class && RpgQueryableSupport.class.isAssignableFrom(anInterface))
/*     */         {
/* 451 */           mapSupports(anInterface, support);
/*     */         }
/*     */       } 
/*     */       
/* 455 */       if (RpgQueryableSupport.class.isAssignableFrom(tmp)) {
/* 456 */         _mapSupport(tmp, support);
/* 457 */         tmp = tmp.getSuperclass();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void _mapSupport(Class supportKey, Object support) {
/* 465 */     if (this.supportMap.containsKey(supportKey)) {
/* 466 */       throw new IllegalStateException("Duplicate RpgSupport mapping: duplicatedKeyClass=" + supportKey + " alreadyMappedObjectClass=" + this.supportMap.get(supportKey).getClass() + " triedToMapToObjectClass=" + support.getClass());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 471 */     this.supportMap.put(supportKey, support);
/*     */   }
/*     */   
/*     */   public void setStatNoUpdate(Short id, Stat value) {
/* 475 */     StatSupport statSupport = getSupport(StatSupport.class);
/* 476 */     Stat stat = statSupport.getStatById(id.shortValue());
/* 477 */     if (stat == null) {
/* 478 */       stat = new Stat(id, 0);
/* 479 */       statSupport.put(stat);
/*     */     } 
/* 481 */     stat.set(value);
/*     */   }
/*     */   
/*     */   public void setStat(Short id, Stat value) {
/* 485 */     setStatNoUpdate(id, value);
/* 486 */     fireStatChanged(id.shortValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float time) {
/* 491 */     super.update(time);
/* 492 */     if (this.chatController != null)
/* 493 */       this.chatController.update(time); 
/*     */   }
/*     */   
/*     */   public void updateBuffs(List<StateUpdateMessage.BuffData> buffDatas) {
/* 497 */     int size = buffDatas.size();
/* 498 */     for (int i = 0; i < size; i++) {
/* 499 */       StateUpdateMessage.BuffData buffData = buffDatas.get(i);
/* 500 */       this.buffSlots.put(buffData.getType(), buffData);
/* 501 */       fireBuffChanged(buffData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateStats(Collection<Stat> newStats) {
/* 506 */     for (Stat newStat : newStats) {
/* 507 */       setStatNoUpdate(newStat.getId(), newStat);
/*     */     }
/* 509 */     fireStatsChanged(newStats);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireStatChanged(short id) {
/* 524 */     switch (id) {
/*     */       case 11:
/*     */       case 12:
/* 527 */         fireUpdateHealth(getStatSum(Short.valueOf((short)12)).intValue(), getStatSum(Short.valueOf((short)11)).intValue());
/*     */         break;
/*     */       case 1:
/* 530 */         fireUpdateLives(getStatSum(Short.valueOf((short)1)).intValue());
/*     */         break;
/*     */       case 13:
/*     */       case 14:
/* 534 */         fireUpdateMana(getStatSum(Short.valueOf((short)14)).intValue(), getStatSum(Short.valueOf((short)13)).intValue());
/*     */         break;
/*     */       case 0:
/*     */       case 21:
/*     */       case 22:
/* 539 */         fireUpdateXp(getStatSum(Short.valueOf((short)21)).intValue(), getStatSum(Short.valueOf((short)22)).intValue(), getStatSum(Short.valueOf((short)0)).intValue());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 20:
/* 544 */         fireUpdateLevel(getStatSum(Short.valueOf((short)20)).intValue());
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fireUpdateHealth(int currentHealth, int maxHealth) {
/* 550 */     if (this.playerEventsListeners != null)
/* 551 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners)
/* 552 */         playerEventsListener.updateHealth(currentHealth, maxHealth);  
/*     */   }
/*     */   
/*     */   private void fireUpdateLives(int currentLives) {
/* 556 */     if (this.playerEventsListeners != null)
/* 557 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners)
/* 558 */         playerEventsListener.updateLives(currentLives);  
/*     */   }
/*     */   
/*     */   private void fireUpdateMana(int currentMana, int maxMana) {
/* 562 */     if (this.playerEventsListeners != null)
/* 563 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners)
/* 564 */         playerEventsListener.updateMana(currentMana, maxMana);  
/*     */   }
/*     */   
/*     */   private void fireUpdateXp(int startXp, int endXp, int currentXp) {
/* 568 */     if (this.playerEventsListeners != null)
/* 569 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners)
/* 570 */         playerEventsListener.updateXp(startXp, endXp, currentXp);  
/*     */   }
/*     */   
/*     */   private void fireUpdateLevel(int currentLevel) {
/* 574 */     if (this.playerEventsListeners != null)
/* 575 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners)
/* 576 */         playerEventsListener.updateLevel(currentLevel);  
/*     */   }
/*     */   
/*     */   private void fireStatsChanged(Collection<Stat> newStats) {
/* 580 */     for (Stat newStat : newStats) {
/* 581 */       fireStatChanged(newStat.getId().shortValue());
/*     */     }
/*     */   }
/*     */   
/*     */   private void fireBuffChanged(StateUpdateMessage.BuffData buffData) {
/* 586 */     if (this.playerEventsListeners != null) {
/* 587 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners) {
/* 588 */         playerEventsListener.updateBuff(buffData);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void fireNewPetCollected(ClientPet pet) {
/* 594 */     if (this.playerEventsListeners != null)
/* 595 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners) {
/* 596 */         playerEventsListener.newPetAcquired(pet);
/*     */       } 
/*     */   }
/*     */   
/*     */   protected void firePetSelectionChanged(int index, ClientPet clientPet) {
/* 601 */     if (this.playerEventsListeners != null)
/* 602 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners) {
/* 603 */         playerEventsListener.selectedPetsReconfigured(index, clientPet);
/*     */       } 
/*     */   }
/*     */   
/*     */   protected void fireActivePetChanged() {
/* 608 */     if (this.playerEventsListeners != null)
/* 609 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners) {
/* 610 */         playerEventsListener.activePetChanged();
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireRespawned() {
/* 615 */     if (this.playerEventsListeners != null)
/* 616 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners) {
/* 617 */         playerEventsListener.respawned();
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireSkillBarItemUsed(int containerId, int containerType, int slotId) {
/* 622 */     if (this.playerEventsListeners != null)
/* 623 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners)
/* 624 */         playerEventsListener.skillbarItemUsed(containerId, containerType, slotId);  
/*     */   }
/*     */   
/*     */   private void fireStatusUpdated(Collection<RpgStatus> changedStatus) {
/* 628 */     if (this.playerEventsListeners != null)
/* 629 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners)
/* 630 */         playerEventsListener.updateStatus(changedStatus);  
/*     */   }
/*     */   
/*     */   private void fireTownportalVisibillityChanged(boolean visible) {
/* 634 */     if (this.playerEventsListeners != null)
/* 635 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners)
/* 636 */         playerEventsListener.updateTownPortal(visible);  
/*     */   }
/*     */   
/*     */   public void die() {
/* 640 */     setBrain((Brain)DeadBrain.INSTANCE);
/*     */   }
/*     */   
/*     */   public boolean isAlive() {
/* 644 */     return (getBrain() != DeadBrain.INSTANCE);
/*     */   }
/*     */   
/*     */   public void live() {
/* 648 */     if (this.aliveBrain != null) {
/* 649 */       setBrain(this.aliveBrain);
/* 650 */       fireRespawned();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ClientPet getCollectedPetForId(String petId) {
/* 655 */     for (ClientPet collectedPet : this.collectedPets) {
/* 656 */       if (TcgGame.getRpgLoader().getPetManager().petRegistersAs(collectedPet.getBaseClassId()).equals(petId))
/* 657 */         return collectedPet; 
/*     */     } 
/* 659 */     throw new IllegalStateException("No collected pet with ID: " + petId);
/*     */   }
/*     */   
/*     */   public boolean hasCollectedPetForRefId(String ref) {
/* 663 */     for (ClientPet collectedPet : this.collectedPets) {
/* 664 */       if (TcgGame.getRpgLoader().getPetManager().petRegistersAs(collectedPet.getBaseClassId()).equals(ref))
/* 665 */         return true; 
/*     */     } 
/* 667 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void process(ResponseCollectedPetsMessage responseCollectedPetsMessage) {
/* 672 */     if (responseCollectedPetsMessage.getId() == getId()) {
/* 673 */       this.collectedPets.clear();
/* 674 */       String[] petClassIds = responseCollectedPetsMessage.getPetClassIds();
/*     */       
/* 676 */       PetRegistry petRegistry = MainGameState.getPetRegistry();
/* 677 */       Integer[] petLevels = responseCollectedPetsMessage.getPetClassLevels();
/* 678 */       Integer[] petXp = responseCollectedPetsMessage.getPetClassXp();
/* 679 */       for (int i = 0; i < petClassIds.length; i++) {
/* 680 */         ClientPetDescription clientPetDescription = MainGameState.getPetRegistry().getPetForClassId(petClassIds[i]);
/* 681 */         ClientPetDescription updatedClientPetDescription = MainGameState.getPetRegistry().hasPetForClassId(petClassIds[i] + "-upgrade") ? MainGameState.getPetRegistry().getPetForClassId(petClassIds[i] + "-upgrade") : clientPetDescription;
/* 682 */         ClientPet pet = new ClientPet(clientPetDescription, (updatedClientPetDescription == null) ? clientPetDescription : updatedClientPetDescription);
/* 683 */         pet.setLevel(petLevels[i].intValue());
/* 684 */         pet.setExp(petXp[i].intValue());
/* 685 */         addPet(pet);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateTownPortalButton(boolean visible) {
/* 691 */     fireTownportalVisibillityChanged(visible);
/*     */   }
/*     */   
/*     */   public void updateStatusList(Set<RpgStatus> newRpgStatusList) {
/* 695 */     this.rpgStatusList = (Set<RpgStatus>)MathUtils.setDisjunction(newRpgStatusList, this.rpgStatusList);
/* 696 */     fireStatusUpdated(newRpgStatusList);
/*     */   }
/*     */   
/*     */   public boolean canMove() {
/* 700 */     return !this.rpgStatusList.contains(RpgStatus.ROOTED);
/*     */   }
/*     */   
/*     */   public ClientChatController getChatController() {
/* 704 */     return this.chatController;
/*     */   }
/*     */   
/*     */   public void setChatController(ClientChatController chatController) {
/* 708 */     this.chatController = chatController;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGlobalCooldown(double itemGlobalCooldown) {
/* 717 */     if (itemGlobalCooldown != 0.0D) {
/* 718 */       this.globalCooldownUntil = (long)(itemGlobalCooldown * 1000.0D) + GlobalTime.getInstance().getCurrentTime();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkGlobalCooldown(double itemGlobalCooldown) {
/* 728 */     return (itemGlobalCooldown == 0.0D || GlobalTime.getInstance().getCurrentTime() > this.globalCooldownUntil);
/*     */   }
/*     */   
/*     */   public GiftBoxCollection getGiftBoxes() {
/* 732 */     return this.giftBoxCollection;
/*     */   }
/*     */   
/*     */   public void setExternalChatId(int externalChatId) {
/* 736 */     this.externalChatId = externalChatId;
/*     */   }
/*     */   
/*     */   public int getExternalChatId() {
/* 740 */     return this.externalChatId;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSpeed() {
/* 745 */     return getStatAsFloat(Short.valueOf((short)10));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAccelerationSpeed() {
/* 750 */     return getStatAsFloat(Short.valueOf((short)23));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTurnSpeed() {
/* 755 */     return 62.831852F;
/*     */   }
/*     */ 
/*     */   
/*     */   public <E extends RpgQueryableSupport> E getSupport(Class<E> supportClass) {
/* 760 */     return (E)this.supportMap.get(supportClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireEvent(SupportEvent event) {}
/*     */ 
/*     */   
/*     */   public int incrementAndGetImmunityHitCounter() {
/* 769 */     if (this.immunityHitCounter == Integer.MAX_VALUE) {
/* 770 */       this.immunityHitCounter = 0;
/*     */     } else {
/* 772 */       this.immunityHitCounter++;
/*     */     } 
/*     */     
/* 775 */     return this.immunityHitCounter;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ClientPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */